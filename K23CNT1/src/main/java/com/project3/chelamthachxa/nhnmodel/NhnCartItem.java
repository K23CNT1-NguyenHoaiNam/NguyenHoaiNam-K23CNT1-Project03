package com.project3.chelamthachxa.nhnmodel;

public class NhnCartItem {
    private Long productId;
    private String name;
    private String imageUrl;
    private int quantity;
    private Double price;

    public NhnCartItem() {
    }

    public NhnCartItem(Long productId, String name, String imageUrl, int quantity, Double price) {
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Double getTotalPrice() {
        return this.price * this.quantity;
    }
}
