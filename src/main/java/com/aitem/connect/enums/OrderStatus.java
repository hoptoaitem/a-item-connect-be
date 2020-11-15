package com.aitem.connect.enums;

import lombok.Getter;

public enum OrderStatus {
    IN_CART,
    CHECKED_OUT,
    SUBMITTED_FOR_PAYMENT,
    LOOKING_FOR_DRIVER,
    DRIVER_ACCEPTED,
    DRIVER_DECLINED,
    CLERK_ASSIGNED_ORDER_TO_DRIVER,
    DELIVERED;
}
