package ${base}.base;

import lombok.Getter;
import org.slf4j.helpers.MessageFormatter;

import java.util.Arrays;

@Getter
public class ServiceException extends RuntimeException {

    private ServiceErrorType type;
    private String message;
    private Throwable cause;

    public ServiceException(ServiceErrorType type, String message, Object... args) {

        this.type = type;

        if (args == null || args.length == 0) {
            this.message = message;
            return;
        }

        if (args[args.length - 1] instanceof Throwable) {
            this.message = MessageFormatter.arrayFormat(message, Arrays.copyOfRange(args, 0, args.length - 1)).getMessage();
            this.cause = (Throwable) args[args.length - 1];
        } else {
            this.message = MessageFormatter.arrayFormat(message, args).getMessage();
        }

        return;
    }

}
