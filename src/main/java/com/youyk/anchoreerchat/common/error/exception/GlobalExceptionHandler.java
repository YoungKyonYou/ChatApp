package com.youyk.anchoreerchat.common.error.exception;




import com.youyk.anchoreerchat.common.error.ErrorResponse;
import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.common.response.constant.DataConstants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<DataResponse> exception(final BaseCustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(DataResponse.of(DataConstants.NO_DATA.getData(), ErrorResponse.of(e.getCode(), e.getMessage())));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DataResponse> exception(final DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(DataResponse.of(DataConstants.NO_DATA.getData(), ErrorResponse.of("DATA_INTEGRITY_VIOLATION", e.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResponse> exception(final MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(DataResponse.of(DataConstants.NO_DATA.getData(), ErrorResponse.of("INVALID_REQUEST", e.getMessage())));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DataResponse> exception(final HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(DataResponse.of(DataConstants.NO_DATA.getData(), ErrorResponse.of("INVALID_REQUEST", e.getMessage())));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<DataResponse> exception(final MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(DataResponse.of(DataConstants.NO_DATA.getData(), ErrorResponse.of("INVALID_REQUEST", e.getMessage())));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<DataResponse> exception(final NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(DataResponse.of(DataConstants.NO_DATA.getData(), ErrorResponse.of("RESOURCE_NOT_FOUND", e.getMessage())));
    }
}
