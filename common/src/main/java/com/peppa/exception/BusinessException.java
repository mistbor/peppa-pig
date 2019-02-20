package com.peppa.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public static BusinessException buildBusinessException(BusinessExceptionSet e) {
        return new BusinessException(e.getCode(), e.getMessage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessException)) return false;
        BusinessException that = (BusinessException) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(code, message);
    }
}
