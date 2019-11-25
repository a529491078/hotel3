package com.edu.fjzzit.web.myhotel.model;

public enum ErrorCodeEnum implements ErrorCode {

    //通用异常
    NON_USERNAME("400","该用户不存在!"),
    UNVERIFY_OLDPSD("400","旧密码不正确!"),
    NON_ROLENAME("400","该角色不存在!"),
    NON_RESERVEROOM("401","您未预定房间请先选择预定类型!"),
    ROOMNUM_ISNOT_EXISTS("402","该房间号不存在!"),
    CHECKIN_MORETHAN_LIMIT("403","入住登记次数超过限制!"),
    ALREADY_CHECKIN("404","已入住!"),
    ORDER_IS_CANCEL("405","订单已取消!"),
    IS_CHECKIN("406","该客户已入住，无法取消!"),
    IS_CHANCELED("407","该订单已取消!"),

    ;

    /** 错误码 */
    private final String code;

    /** 描述 */
    private final String description;

    /**
     * @param code
     * @param description
     */
   ErrorCodeEnum(String code, String description){
        this.code=code;
        this.description=description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
