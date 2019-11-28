package com.edu.fjzzit.web.myhotel.mapper;

import com.edu.fjzzit.web.myhotel.dto.RoomOrderDetailDTO;
import com.edu.fjzzit.web.myhotel.model.RoomOrder;
import com.edu.fjzzit.web.myhotel.model.RoomOrderDetail;
import com.edu.fjzzit.web.myhotel.model.UserInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserInfoMapperTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoomOrderMapper roomOrderMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private CheckInInfoMapper checkInInfoMapper;

    @Test
    public void testFindFirstByUserName(){
        UserInfo userInfo=userInfoMapper.findFirstByUserName("admin");
        Assert.assertNull(userInfo);

//        String salt="abcd";
//        SimpleHash simpleHash=new SimpleHash(Md5Hash.ALGORITHM_NAME,"123456",salt,3);
//
//        UserInfo userInfo2=new UserInfo();
//        userInfo2.setUserId(1);
//        userInfo2.setUserName("admin");
//        userInfo2.setSalt(salt);
//        userInfo2.setPassword(simpleHash.toString());
//        userInfo2.setCreateTime(new Date());
//        userInfo2.setUserState(Byte.parseByte("1"));
//
//        userInfo=userInfoMapper.findFirstByUserName("admin");
//        Assert.assertEquals(userInfo2,userInfo);
    }

    @Test
    @Rollback(false)
    public void testInsert(){
        String salt="abcd";
        SimpleHash simpleHash=new SimpleHash(Md5Hash.ALGORITHM_NAME,"123456",salt,3);

        UserInfo userInfo=new UserInfo();
        userInfo.setUserName("admin1");
        userInfo.setSalt(salt);
        userInfo.setPassword(simpleHash.toString());
        userInfo.setCreateTime(new Date());
        userInfo.setUserState(Byte.parseByte("1"));

        int insertResult=userInfoMapper.insert(userInfo);
        Assert.assertEquals(insertResult,1);
    }

    @Test
    public void getBedType(){
        String roomTypeName="普通房";
        String roomPriceName="普通房大床";
        String bedType=roomPriceName.substring(roomTypeName.length());//获取床型
        System.out.println(bedType);
    }

    @Test
    public void getRoomOrderNum(){
        RoomOrder roomOrder=new RoomOrder();

        roomOrder.setCustomerName("张三123");
        roomOrder.setCustomerPhone("12312222321");
        roomOrder.setRoomOrderState(Byte.parseByte("0"));
        roomOrderMapper.insert(roomOrder);//插入订单

        //获取生成的订单的流水号
        Long roomOrderNum=roomOrderMapper.selectRoomOrderNumByCustomerName("张三123");

        System.out.println("roomOrderNum->"+roomOrderNum);

        //Long roomOrderNum=roomOrder.getRoomOrderNum();
    }

    @Test
    public void checkin(){
        /*Integer roomNumIsExists=roomInfoMapper.queryRoomNumIsExists("2012");
        if (null==roomNumIsExists||roomNumIsExists==0) {//房间号不存在
            System.out.println("not in");
        }else{
            System.out.println("exsits in");
        }*/

        Long roomOrderNum= checkInInfoMapper.queryIsExistsOrder("张林育","1231");
        if (roomOrderNum!=null&&roomOrderNum!=0) {//存在订单流水号，有预定房间
            System.out.println("exsits in");
        }else{
            System.out.println("not in");
        }
    }

    @Test
    public void dateFormatByString(){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1=new Date();
            Date date2=new Date();
            date1 = inputFormat.parse("2019-11-08"); // 将字符型转换成日期型
            date2 = inputFormat.parse("2019-11-08"); // 将字符型转换成日期型

            long checkInTime=date1.getTime();
            long checkOutTime=date2.getTime();
            long times=checkOutTime-checkInTime;
            int day=Math.abs((int) (times/1000/60/60/24));
            if (day<=1)
                day=1;
            System.out.println(day);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
