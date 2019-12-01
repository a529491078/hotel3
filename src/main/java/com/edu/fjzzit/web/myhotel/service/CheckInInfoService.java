package com.edu.fjzzit.web.myhotel.service;


public interface CheckInInfoService {

    /**
     * 入住登记
     * @param customerName
     * @param customerPhone
     * @param customerID
     * @return 返回房间号
     */
    String checkIn(String customerName, String customerPhone, String customerID) throws Exception ;

    /**
     * 办理退房
     * @param roomNum
     */
    void checkOut(String roomNum) throws Exception ;

}
