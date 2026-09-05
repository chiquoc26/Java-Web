package com.example.demobtvenha25_8.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "Product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "image")
    private String image;

    @Column(name = "cate_id", nullable = false)
    private int cateId;

    @Column(name = "created_date")
    private Timestamp createdDate;

    // Dùng để hiển thị tên danh mục (join thủ công từ JDBC)
    @Transient
    private String cateName;

    public Product() {}

    public Product(String productName, String description, BigDecimal price,
                   int quantity, String image, int cateId) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.cateId = cateId;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public int getCateId() { return cateId; }
    public void setCateId(int cateId) { this.cateId = cateId; }

    public Timestamp getCreatedDate() { return createdDate; }
    public void setCreatedDate(Timestamp createdDate) { this.createdDate = createdDate; }

    public String getCateName() { return cateName; }
    public void setCateName(String cateName) { this.cateName = cateName; }
}
