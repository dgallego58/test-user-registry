package co.com.dgallego58.infrastructure.api.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(-1)
public class GlobalHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalHandler.class);

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Handler.ErrorResponse> handelGlobal(Throwable e) {
        log.error("error on global validation ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new Handler.ErrorResponse("fail in our systems"));
    }

}
