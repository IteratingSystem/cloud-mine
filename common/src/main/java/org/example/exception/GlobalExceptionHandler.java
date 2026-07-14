package org.example.exception;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.example.result.R;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GlobalExceptionHandler {

    @ExceptionHandler(BlockException.class)
    public R<Void> handleBlockException(BlockException e) {
        log.warn("Sentinel 限流/熔断: {}", e.getClass().getSimpleName());
        return R.blockError("服务繁忙，请稍后重试");
    }

    @ExceptionHandler(BusinessException.class)
    public R<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
        return R.error(e.getCode(), e.getMessage());
    }




    @ExceptionHandler(NoResourceFoundException.class)
    public R<Void> handleException(NoResourceFoundException e) {
        log.error("运行时异常: {}", e.getMessage(), e);
        return R.error("运行时异常，请求无效资源!");
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Void> handleException(HttpRequestMethodNotSupportedException e) {
        log.error("异常访问: {}", e.getMessage(), e);
        return R.error("异常访问，请求无效资源!");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Void> handleException(HttpMessageNotReadableException e) {
        log.error("异常访问: {}", e.getMessage());
        return R.error("异常访问，参数异常,未传入任何参数!");
    }


    @ExceptionHandler(RuntimeException.class)
    public R<Void> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常: {}", e.getMessage(),e);
        return R.error("服务内部错误");
    }
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return R.error("系统繁忙，请稍后再试");
    }
}