package com.aitem.connect.response;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

@Data
public class ItemResponse {
    private String id;
    private String name;
    private String type;
    private String price;
    private long quantity;
}
