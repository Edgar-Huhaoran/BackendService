package com.hyrax.backend.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    USER_NAME_NULL(HttpStatus.BAD_REQUEST, 4001, "user name can't be null", "用户名不能为空"),
    USER_NAME_EXISTS(HttpStatus.BAD_REQUEST, 4002, "user name already exists", "用户名已存在"),
    USER_NAME_NOT_EXISTS(HttpStatus.BAD_REQUEST, 4003, "user name does not exists", "用户名不存在"),
    PASSWORD_NULL(HttpStatus.BAD_REQUEST, 4004, "password can't be null", "密码不能为空"),
    PASSWORD_ILLEGAL(HttpStatus.BAD_REQUEST, 4005, "password is illegal", "密码不符合要求"),
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, 4006, "password incorrect", "密码不正确"),
    VEHICLE_INFO_INVALID(HttpStatus.BAD_REQUEST, 4007, "vehicle information is invalid", "车辆信息不合法"),
    ID_NULL(HttpStatus.BAD_REQUEST, 4008, "id can't be null", "ID不能为空"),
    REFUEL_APPOINT_ILLEGAL(HttpStatus.BAD_REQUEST, 4009, "refuel appointment is illegal", "加油预约参数不合法"),

    TOKEN_NOT_EXISTS(HttpStatus.FORBIDDEN, 4031, "user token do not exists", "token不存在"),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, 4032, "user token is invalid", "token无效"),
    TOKEN_EXPIRED(HttpStatus.FORBIDDEN, 4033, "user token is expired", "token过期"),
    NO_PERMISSION(HttpStatus.FORBIDDEN, 4033, "do not have permission to access", "没有访问权限"),

    UNKNOWN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "unknown internal server error", "服务器未知错误");

    private final HttpStatus status;
    private final int errorCode;
    private final String detailMessage;
    private final String clinetMessage;

    ErrorType(HttpStatus status, int errorCode, String detailMessage, String clientMessage) {
        this.status = status;
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
        this.clinetMessage = clientMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public String getClinetMessage() {
        return clinetMessage;
    }
}
