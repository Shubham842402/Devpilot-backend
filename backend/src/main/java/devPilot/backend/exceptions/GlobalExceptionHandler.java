package devPilot.backend.exceptions;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException ex) {
        return error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex) {
        return error(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult().getFieldErrors().stream()
                
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation failed");
        
        return error(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred");
    }

    private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
            "timestamp", Instant.now().toString(),
            "status", status.value(),
            "error", status.getReasonPhrase(),
            "message", message
        ));
    }

}
    

