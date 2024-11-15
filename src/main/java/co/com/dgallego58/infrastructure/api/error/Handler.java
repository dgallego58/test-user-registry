package co.com.dgallego58.infrastructure.api.error;

import co.com.dgallego58.domain.access.usecase.service.RegistryAccessService;
import co.com.dgallego58.infrastructure.security.filter.jwt.SchematicJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(-2)
public class Handler {

    private static final Logger log = LoggerFactory.getLogger(Handler.class);

    @ExceptionHandler({SchematicJwtException.class})
    public ResponseEntity<ErrorResponse> handleSchematicJwtException(SchematicJwtException e) {
        log.warn("error on jwt validation ", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<ErrorResponse> handleSqlEx(SQLException e) {
        log.error("error on sql validation ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("mail already exists"));
    }

    @ExceptionHandler({RegistryAccessService.UnrecognizableEmailException.class})
    public ResponseEntity<ErrorResponse> handleInvalidMail(RegistryAccessService.UnrecognizableEmailException e) {
        log.error("error on email validation ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleInvalidMail(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors()
                          .stream().map(em -> {

                    String field = ((FieldError) em).getField();
                    String error = em.getDefaultMessage();
                    return field + "=" + error;
                }).collect(Collectors.joining(","));
        log.error("error on email validation {}", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(message));
    }


    public record ErrorResponse(String message) {
    }
}

