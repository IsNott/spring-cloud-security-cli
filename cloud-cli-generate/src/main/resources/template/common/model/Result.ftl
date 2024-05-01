package ${parent.groupId}.${parent.childLastPackage}.common.model;

import lombok.Data;
import ${parent.groupId}.${parent.childLastPackage}.common.enums.ResponseEnum;

@Data
public class Result<T extends Object> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> Result = new Result<>();
        Result.setData(data);
        Result.setCode(ResponseEnum.OK.value());
        Result.setMsg(ResponseEnum.OK.getMsg());
        return Result;
    }

    public static <T> Result<T> success() {
        Result<T> Result = new Result<>();
        Result.setCode(ResponseEnum.OK.value());
        Result.setMsg(ResponseEnum.OK.getMsg());
        return Result;
    }


    public static <T> Result<T> success(Integer code, T data) {
        Result<T> Result = new Result<>();
        Result.setCode(code);
        Result.setData(data);
        return Result;
    }

    public static <T> Result<T> showFailMsg(String msg) {
        Result<T> Result = new Result<>();
        Result.setMsg(msg);
        Result.setCode(ResponseEnum.SHOW_FAIL.value());
        return Result;
    }

    public static <T> Result<T> fail(ResponseEnum responseEnum) {
        Result<T> Result = new Result<>();
        Result.setMsg(responseEnum.getMsg());
        Result.setCode(responseEnum.value());
        return Result;
    }

    public static <T> Result<T> fail(ResponseEnum responseEnum, T data) {
        Result<T> Result = new Result<>();
        Result.setMsg(responseEnum.getMsg());
        Result.setCode(responseEnum.value());
        Result.setData(data);
        return Result;
    }

    public static <T> Result<T> fail(Integer code, String msg, T data) {
        Result<T> Result = new Result<>();
        Result.setMsg(msg);
        Result.setCode(code);
        Result.setData(data);
        return Result;
    }


    public static <T> Result<T> fail(Integer code, T data) {
        Result<T> Result = new Result<>();
        Result.setCode(code);
        Result.setData(data);
        return Result;
    }

}
