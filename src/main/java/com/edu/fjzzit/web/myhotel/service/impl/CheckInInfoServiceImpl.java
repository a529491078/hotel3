package com.edu.fjzzit.web.myhotel.service.impl;
import com.edu.fjzzit.web.myhotel.dto.CheckInInfoDTO;
import com.edu.fjzzit.web.myhotel.mapper.CheckInInfoMapper;
import com.edu.fjzzit.web.myhotel.mapper.RoomInfoMapper;
import com.edu.fjzzit.web.myhotel.mapper.RoomOrderDetailMapper;
import com.edu.fjzzit.web.myhotel.mapper.RoomOrderMapper;
import com.edu.fjzzit.web.myhotel.model.ErrorCodeEnum;
import com.edu.fjzzit.web.myhotel.model.MyException;
import com.edu.fjzzit.web.myhotel.service.CheckInInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckInInfoServiceImpl implements CheckInInfoService {

    @Autowired
    private CheckInInfoMapper checkInInfoMapper;

    @Autowired
    private RoomOrderDetailMapper roomOrderDetailMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private RoomOrderMapper roomOrderMapper;

    /**
     * 入住登记
     * @param customerName
     * @param customerPhone
     * @param customerID
     * @return 返回房间号
     */
    @Override
    public String checkIn(String customerName, String customerPhone, String customerID) throws Exception {
        //查找客户是否有预定房间，若无，登记入住失败
        //查询订单流水号
        Long roomOrderNum = checkInInfoMapper.queryIsExistsOrder(customerName, customerPhone);
        if (roomOrderNum != null && roomOrderNum != 0) {//存在订单流水号，有预定房间
            //判断订单状态，room_order_state(0->未入住,1->已入住,2->已取消),
            Byte roomOrderState;//存储订单状态
            roomOrderState=roomOrderMapper.queryOrderState(roomOrderNum);
            //若订单状态为0则可以入住登记,若为1抛出已入住异常，若为2抛出订单已取消异常
            if (roomOrderState==Byte.parseByte("1")){
                throw new MyException(ErrorCodeEnum.ALREADY_CHECKIN);
            }else if (roomOrderState==Byte.parseByte("2")){
                throw new MyException(ErrorCodeEnum.ORDER_IS_CANCEL);
            }else{
                //查询订单详细中的房间类型流水号
                Long roomTypeNum = roomOrderDetailMapper.queryRoomTypeNumByRoomOrderNum(roomOrderNum);
                //查询订单详细中的订房数量，与入住表中该客户已入住登记次数校验，若已入住登记次数大于定房数则抛出入住登记房间
                //超过限制异常，入住登记失败
                Integer roomCount=roomOrderDetailMapper.queryRoomCountByRoomOrderNum(roomOrderNum);//查询订单详细中的订房数量
                Integer checkInCount=checkInInfoMapper.queryCustomerCheckInCount(customerName,customerPhone);//查询入住登记表中同个客户的登记入住次数
                if (checkInCount>=roomCount) {//若已入住登记次数超过定房数，则抛出入住登记房间超过限制异常
                    throw new MyException(ErrorCodeEnum.CHECKIN_MORETHAN_LIMIT);
                }else {//入住次数符合订房数
                    //查询空房号集合
                    //房间状态为0->未入住
                    Integer roomState = 0;
                    List<String> roomNumList = roomInfoMapper.selectRoomNumByRoomTypeNum(roomTypeNum, roomState);
                    String roomNum = "";//保存空房号
                    for (String rn : roomNumList
                    ) {
                        if (rn != null && rn != "") {
                            roomNum = rn;
                            break;
                        }
                    }
                    //入住登记成功，入住状态变更->check_in_state(0->已入住,1->已退房)
                    CheckInInfoDTO checkInInfoDTO = new CheckInInfoDTO();
                    checkInInfoDTO.setCustomerName(customerName);
                    checkInInfoDTO.setCustomerPhone(customerPhone);
                    checkInInfoDTO.setRoomNum(roomNum);
                    checkInInfoDTO.setCustomerId(customerID);
                    checkInInfoDTO.setCheckInState(0);
                    checkInInfoMapper.insert(checkInInfoDTO);
                    //订单状态变更->room_order_state(0->未入住,1->已入住)
                    //设置订单状态为1->已入住
                    roomOrderState = Byte.parseByte("1");
                    roomOrderMapper.updateRoomOrderState(roomOrderNum, roomOrderState);
                    //房间状态变更->room_state(0->未入住,1->已入住)
                    roomState = 1;
                    roomInfoMapper.updateRoomStateByRoomNum(roomNum, roomState);
                    //返回房间号给客户
                    return roomNum;
                }
            }
        } else {//客户无预定，现场预定房间，登记入住（抛出自定义无预定房间异常）
            throw new MyException(ErrorCodeEnum.NON_RESERVEROOM);
        }
    }

    /**
     * 办理退房
     * @param roomNum
     */
    @Override
    public void checkOut(String roomNum) throws Exception  {
        //1.查询房号是否存在，若存在->修改room_state(0->未入住,1->已入住),否则返回房号不存在的错误异常
        Integer roomNumIsExists=roomInfoMapper.queryRoomNumIsExists(roomNum);
        if (null==roomNumIsExists||roomNumIsExists==0){//房间号不存在
            throw new MyException(ErrorCodeEnum.ROOMNUM_ISNOT_EXISTS);
        }else{//房间号校验正确
            //房间状态为0->未入住
            Integer roomState=0;
            roomInfoMapper.updateRoomStateByRoomNum(roomNum,roomState);
            //2.办理退房成功，入住状态变更->check_in_state(0->已入住,1->已退房)
            roomState=1;
            checkInInfoMapper.updateCheckInStateByRoomNum(roomNum,roomState);
        }
    }
}
