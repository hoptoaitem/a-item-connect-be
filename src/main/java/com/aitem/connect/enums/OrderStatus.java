package com.aitem.connect.enums;

import lombok.Getter;

public enum OrderStatus {
    IN_CART,
    CHECKED_OUT,
    LOOKING_FOR_DRIVER,
    DRIVER_ACCEPTED,
    CLERK_ASSIGNED_ORDER_TO_DRIVER,
    DELIVERED;
}
