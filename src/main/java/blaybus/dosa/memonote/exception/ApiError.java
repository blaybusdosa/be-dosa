package blaybus.dosa.memonote.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiError {
    private final String code;
    private final String message;
    private final List<FieldViolation> fields;
}
