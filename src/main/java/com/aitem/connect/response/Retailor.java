package com.aitem.connect.response;

import com.aitem.connect.enums.OrderStatus;
import com.aitem.connect.model.AddressModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class Retailor {

    private String email;
    private String phone;
}
