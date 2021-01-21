package indi.biz.yufr.base;

import indi.biz.yufr.config.SpringContext;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;

@Getter
public class ServiceException extends RuntimeException {

    private static final MessageSource messageSource = SpringContext.getBean(MessageSource.class);

    private ServiceErrorType type;
    private String message;
    private Throwable cause;

    public ServiceException(ServiceErrorType type, String codeOrMessage, Object... args) {

        this.type = type;

        if (args == null || args.length == 0) {
            this.message = messageSource.getMessage(codeOrMessage, null, codeOrMessage, LocaleContextHolder.getLocale());
            return;
        }

        if (args[args.length - 1] instanceof Throwable) {
            this.message = messageSource.getMessage(codeOrMessage,
                    Arrays.copyOfRange(args, 0, args.length - 1), codeOrMessage, LocaleContextHolder.getLocale());
            this.cause = (Throwable) args[args.length - 1];
        } else {
            this.message = messageSource.getMessage(codeOrMessage, args, codeOrMessage, LocaleContextHolder.getLocale());
        }
    }

}
