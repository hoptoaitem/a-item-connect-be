package com.aitem.connect.request;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemDetails {
    private UUID itemId;
    private Integer quantity;
}
