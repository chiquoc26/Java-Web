package com.example.demobtvenha25_8.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Filter sitemesh cho decorator
public class SiteMeshFilter implements Filter {

    private static class MappingRule {
        String pattern;
        String decorator;

        MappingRule(String pattern, String decorator) {
            this.pattern = pattern;
            this.decorator = decorator;
        }

        boolean matches(String path) {
            if (pattern.endsWith("/*")) {
                String prefix = pattern.substring(0, pattern.length() - 2);
                return path.startsWith(prefix);
            } else if (pattern.endsWith("*")) {
                String prefix = pattern.substring(0, pattern.length() - 1);
                return path.startsWith(prefix);
            } else {
                return path.equals(pattern);
            }
        }
    }

    private final List<MappingRule> rules = new ArrayList<>();
    private ServletContext servletContext;

    private static final Pattern TITLE_PATTERN = Pattern.compile("<title[^>]*>(.*?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern HEAD_PATTERN = Pattern.compile("<head[^>]*>(.*?)</head>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern BODY_PATTERN = Pattern.compile("<body[^>]*>(.*?)</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    private static final Pattern SITEMESH_TITLE = Pattern.compile("<sitemesh:write\\s+property=\"title\"\\s*>(.*?)</sitemesh:write>|<sitemesh:write\\s+property=\"title\"\\s*/>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern SITEMESH_HEAD = Pattern.compile("<sitemesh:write\\s+property=\"head\"\\s*/>|<sitemesh:write\\s+property=\"head\"\\s*>(.*?)</sitemesh:write>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern SITEMESH_BODY = Pattern.compile("<sitemesh:write\\s+property=\"body\"\\s*/>|<sitemesh:write\\s+property=\"body\"\\s*>(.*?)</sitemesh:write>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        loadConfiguration();
    }

    private void loadConfiguration() {
        InputStream is = servletContext.getResourceAsStream("/WEB-INF/sitemesh3.xml");
        if (is == null) {
            is = servletContext.getResourceAsStream("/web-INF/sitemesh3.xml");
        }

        if (is != null) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);
                NodeList list = doc.getElementsByTagName("mapping");
                for (int i = 0; i < list.getLength(); i++) {
                    Element el = (Element) list.item(i);
                    String path = el.getAttribute("path");
                    String decorator = el.getAttribute("decorator");
                    if (!path.isBlank() && !decorator.isBlank()) {
                        rules.add(new MappingRule(path.trim(), decorator.trim()));
                    }
                }
            } catch (Exception e) {
                servletContext.log("Loi doc sitemesh3.xml: " + e.getMessage(), e);
            } finally {
                try {
                    is.close();
                } catch (IOException ignored) {}
            }
        }

        // Cau hinh mac dinh
        if (rules.isEmpty()) {
            rules.add(new MappingRule("/views/login.jsp", "none"));
            rules.add(new MappingRule("/views/register.jsp", "none"));
            rules.add(new MappingRule("/login", "none"));
            rules.add(new MappingRule("/register", "none"));
            rules.add(new MappingRule("/image*", "none"));
            rules.add(new MappingRule("/user/*", "/decorators/default.jsp"));
            rules.add(new MappingRule("/views/user/*", "/decorators/default.jsp"));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String requestUri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = requestUri.substring(contextPath.length());

        // Tim decorator
        String decorator = null;
        for (MappingRule rule : rules) {
            if (rule.matches(path)) {
                decorator = rule.decorator;
                break;
            }
        }

        if (decorator == null || "none".equalsIgnoreCase(decorator) || isStaticResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Bat noi dung html trang con
        HtmlResponseWrapper responseWrapper = new HtmlResponseWrapper(resp);
        chain.doFilter(request, responseWrapper);

        if (responseWrapper.isRedirect() || resp.isCommitted() || responseWrapper.getStatus() >= 400) {
            return;
        }

        String htmlContent = responseWrapper.getCapturedHtml();
        if (htmlContent == null || htmlContent.isBlank()) {
            return;
        }

        // Parse title, head, body
        String title = extractMatch(TITLE_PATTERN, htmlContent);
        String head = extractMatch(HEAD_PATTERN, htmlContent);
        String body = extractMatch(BODY_PATTERN, htmlContent);

        if (head != null) {
            head = TITLE_PATTERN.matcher(head).replaceAll("");
        }

        if (body == null) {
            body = htmlContent;
        }

        req.setAttribute("title", title != null ? title : "");
        req.setAttribute("head", head != null ? head : "");
        req.setAttribute("body", body);

        // Render decorator
        RequestDispatcher dispatcher = req.getRequestDispatcher(decorator);
        if (dispatcher != null) {
            HtmlResponseWrapper decoratorWrapper = new HtmlResponseWrapper(resp);
            dispatcher.include(req, decoratorWrapper);
            String decoratorHtml = decoratorWrapper.getCapturedHtml();

            if (decoratorHtml != null && !decoratorHtml.isBlank()) {
                String finalHtml = replaceSiteMeshTags(decoratorHtml, title, head, body);

                resp.setContentType("text/html;charset=UTF-8");
                byte[] bytes = finalHtml.getBytes(StandardCharsets.UTF_8);
                resp.setContentLength(bytes.length);
                resp.getOutputStream().write(bytes);
                resp.getOutputStream().flush();
                return;
            }
        }

        resp.setContentType("text/html;charset=UTF-8");
        byte[] bytes = htmlContent.getBytes(StandardCharsets.UTF_8);
        resp.setContentLength(bytes.length);
        resp.getOutputStream().write(bytes);
        resp.getOutputStream().flush();
    }

    private String replaceSiteMeshTags(String decoratorHtml, String title, String head, String body) {
        String result = decoratorHtml;

        // Title
        if (title != null && !title.isBlank()) {
            result = SITEMESH_TITLE.matcher(result).replaceAll(Matcher.quoteReplacement(title));
        } else {
            // Giữ lại nội dung mặc định bên trong <sitemesh:write property="title">Mặc định</sitemesh:write>
            Matcher m = SITEMESH_TITLE.matcher(result);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String defaultTitle = m.group(1);
                m.appendReplacement(sb, Matcher.quoteReplacement(defaultTitle != null ? defaultTitle : ""));
            }
            m.appendTail(sb);
            result = sb.toString();
        }

        // Head
        result = SITEMESH_HEAD.matcher(result).replaceAll(Matcher.quoteReplacement(head != null ? head : ""));

        // Body
        result = SITEMESH_BODY.matcher(result).replaceAll(Matcher.quoteReplacement(body != null ? body : ""));

        return result;
    }

    private String extractMatch(Pattern pattern, String content) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    private boolean isStaticResource(String path) {
        String lower = path.toLowerCase();
        return lower.endsWith(".css") || lower.endsWith(".js") || lower.endsWith(".png")
                || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".gif")
                || lower.endsWith(".ico") || lower.endsWith(".svg") || lower.endsWith(".woff")
                || lower.endsWith(".woff2");
    }

    @Override
    public void destroy() {}

    // Wrapper de lay html output
    private static class HtmlResponseWrapper extends HttpServletResponseWrapper {
        private final CharArrayWriter charWriter = new CharArrayWriter();
        private final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        private PrintWriter writer;
        private ServletOutputStream outputStream;
        private boolean isRedirect = false;
        private int status = SC_OK;

        public HtmlResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void setStatus(int sc) {
            this.status = sc;
            super.setStatus(sc);
        }

        @Override
        public int getStatus() {
            return status;
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            this.isRedirect = true;
            super.sendRedirect(location);
        }

        public boolean isRedirect() {
            return isRedirect;
        }

        @Override
        public PrintWriter getWriter() {
            if (writer == null) {
                writer = new PrintWriter(charWriter);
            }
            return writer;
        }

        @Override
        public ServletOutputStream getOutputStream() {
            if (outputStream == null) {
                outputStream = new ServletOutputStream() {
                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setWriteListener(WriteListener writeListener) {}

                    @Override
                    public void write(int b) {
                        byteStream.write(b);
                    }
                };
            }
            return outputStream;
        }

        public String getCapturedHtml() {
            if (writer != null) {
                writer.flush();
            }
            if (charWriter.size() > 0) {
                return charWriter.toString();
            }
            if (byteStream.size() > 0) {
                String encoding = getCharacterEncoding();
                if (encoding == null) encoding = "UTF-8";
                try {
                    return byteStream.toString(encoding);
                } catch (Exception e) {
                    return byteStream.toString(StandardCharsets.UTF_8);
                }
            }
            return "";
        }
    }
}
