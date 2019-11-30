package com.edu.fjzzit.web.myhotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyOrderDTO {
    private Long roomOrderNum;
    private String customerName;
    private String customerPhone;
    private Byte roomOrderState;
    private List<RoomOrderDetailDTO> roomOrderDetailDTOList;

}
