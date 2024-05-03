package ${parent.groupId}.${parent.childLastPackage}.common.enums;

import lombok.Data;

public enum ResponseEnum {

    OK(200, "ok"),
    SHOW_FAIL(500, ""),
    SHOW_SUCCESS(200, ""),
    UNAUTHORIZED(403, "Unauthorized"),
    EXCEPTION(500, "An unknown problem occurred"),
    NOT_SUPPORT(500, "Method not support"),
    METHOD_ARGUMENT_NOT_VALID(400, "Parameter verification failed"),
    API_NOT_FOUND(404, "Not Found"),
    BAD_CREDNTIAL(401, "Ban credntial");

    private final Integer code;

    private final String msg;

    public Integer value() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseEnum{" + "code='" + code + '\'' + ", msg='" + msg + '\'' + "} " + super.toString();
    }

}
