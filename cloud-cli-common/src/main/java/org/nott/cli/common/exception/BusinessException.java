package org.nott.cli.common.exception;

import lombok.Getter;
import org.nott.cli.common.enums.ResponseEnum;
import org.nott.cli.common.model.Result;

@Getter
public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -4137688758944857209L;

    /**
     * http状态码
     */
    private Integer code;

    private Object object;

    private Result<?> result;

    public BusinessException(Result<?> result) {
        this.result = result;
    }

    public BusinessException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.code = responseEnum.value();
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = ResponseEnum.SHOW_FAIL.value();
    }

    public BusinessException(String msg, Object object) {
        super(msg);
        this.code = ResponseEnum.SHOW_FAIL.value();
        this.object = object;
    }

    public BusinessException(Integer code) {
        super(ResponseEnum.EXCEPTION.getMsg());
        this.code = code;
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(Integer code, String msg, Object object) {
        super(msg);
        this.code = code;
        this.object = object;
    }
}
