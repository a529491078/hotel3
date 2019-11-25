package com.edu.fjzzit.web.myhotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {
    private Long roomTypeNum;

    private String roomTypeName;

    private String bedType;

    private String floor;

    private String roomTypeDecs;

    private String roomTypeImg;

}