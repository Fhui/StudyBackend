package com.harry.common;

/**
 * @author harry
 * @create 2019-03-12 16:15
 **/
public enum ResponseCode {

    Success("0",""),

    Param_error("2","参数错误"),

    Primary_key_error("3","主键设置失败"),

    Insert_error("10","数据添加失败"),

    Update_error("11","数据更新失败"),

    Delete_error("12","数据删除失败"),

    Select_error("13", "数据查询失败"),
    Data_repeat_error("14", "数据已存在"),
    Data_too_much_error("15","数据过多"),
    Data_status_error("16","数据的状态错误"),
    Audit_error("50","审核失败"),

    //xxx HTTP Status
    NotFound("404",""),

    //0xxx  common

    E0020("0020","查询出错"),
    E0021("0021","查询没有数据"),
    E0022("0022","查询数据过多"),

    //1xxx System
    E1001("1001","用户账号或密码错误"),
    E1002("1002", "登录已过期"),

    E1003("1003", "登录出错"),

    E1004_NoMenuPermission("1004","没有菜单权限"),
    E1005("1005","系统异常，导出失败"),


    //2


    //9xxx Other



    UndefinedError("9998", "服务端发生错误"),
    OtherError("9999","Other error"),

    ;

    private String code;
    private String message;

    ResponseCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return this.getCode() + ":" + this.getMessage();
    }
}
