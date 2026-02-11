package blaybus.dosa.memonote.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice(basePackages = "blaybus.dosa.memonote")
public class MemoNoteExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldViolation> fields = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new FieldViolation(err.getField(), err.getDefaultMessage()))
                .toList();
        ApiError body = new ApiError("VALIDATION_ERROR", "Validation failed", fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        List<FieldViolation> fields = ex.getConstraintViolations().stream()
                .map(this::toFieldViolation)
                .toList();
        ApiError body = new ApiError("VALIDATION_ERROR", "Validation failed", fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleNotReadable(HttpMessageNotReadableException ex) {
        ApiError body = new ApiError("INVALID_REQUEST", "Request body is missing or malformed", List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException ex) {
        String message = (ex.getReason() == null || ex.getReason().isBlank())
                ? "Request failed"
                : ex.getReason();
        ApiError body = new ApiError(ex.getStatusCode().toString(), message, List.of());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    private FieldViolation toFieldViolation(ConstraintViolation<?> violation) {
        String field = violation.getPropertyPath() == null ? "" : violation.getPropertyPath().toString();
        String message = violation.getMessage();
        return new FieldViolation(field, message);
    }
}
