package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.OrderItem;

public class OrderItemDTO {

    private Long productId;
    private String name;
    private Double price;
    private  Integer quantity;

    public OrderItemDTO(){
    }

    public OrderItemDTO(Long productId, String name, Integer quantity, Double price) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItemDTO(OrderItem entity){
        productId = entity.getProduct().getId();
        name = entity.getProduct().getName();
        quantity = entity.getQuantity();
        price = entity.getPrice();
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    protected Double getSubTotal(){
        return price * quantity;
    }
}
