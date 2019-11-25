package com.edu.fjzzit.web.myhotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo {
    private Long customerNum;
    private String customerNickName;
    private String customerName;
    private Integer customerSex;
    private String customerPhone;
    private String customerId;
    private String customerAddress;
}
