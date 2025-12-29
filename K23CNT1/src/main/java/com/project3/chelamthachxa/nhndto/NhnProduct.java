package com.project3.chelamthachxa.nhndto;

import jakarta.persistence.*;

@Entity
@Table(name = "nhn_sanpham")
public class NhnProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nhn_sanpham_id")
    private Long id;

    @Column(name = "nhn_tensanpham", nullable = false)
    private String name;

    @Column(name = "nhn_mota")
    private String description;

    @Column(name = "nhn_gia", nullable = false)
    private Double price;

    @Column(name = "nhn_soluongton")
    private Integer stock;

    @Column(name = "nhn_image_url")
    private String imageUrl;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
