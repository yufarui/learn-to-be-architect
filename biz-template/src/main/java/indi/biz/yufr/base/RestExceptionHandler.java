package indi.biz.yufr.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@ResponseBody
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<String> exposureMessageExceptionHandler(ServiceException e) {

        log.warn("异常信息", e);

        return ResponseEntity
                .status(convert(e.getType()))
                .body(e.getMessage());
    }

    private HttpStatus convert(ServiceErrorType type) {
        switch (type) {
            case BAD_REQUEST:
                return HttpStatus.BAD_REQUEST;
            case BIZ_ERROR:
            case SYSTEM_ERROR:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                return HttpStatus.SERVICE_UNAVAILABLE;
        }
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> exceptionHandler(Exception e) {

        log.warn("异常信息", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("系统异常");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<String> paramExceptionHandler(Exception e) {

        log.warn("异常信息", e);

        List<ObjectError> allErrors = new ArrayList<>();

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ve = (MethodArgumentNotValidException) e;
            allErrors = ve.getBindingResult().getAllErrors();
        } else if (e instanceof BindException) {
            BindException be = (BindException) e;
            allErrors = be.getBindingResult().getAllErrors();
        }

        List<String> errors = new ArrayList<>();
        for (ObjectError objectError : allErrors) {

            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                errors.add(fieldError.getField() + fieldError.getDefaultMessage());
            } else {
                errors.add(objectError.getDefaultMessage());
            }
        }

        String errorMessage = String.join(";", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }
}
