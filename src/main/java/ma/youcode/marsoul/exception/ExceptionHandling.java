package ma.youcode.marsoul.exception;


import ma.youcode.marsoul.message.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(BusNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleBusNotExistException(BusNotExistException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(EquipmentNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEquipmentNotExistException(EquipmentNotExistException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(PersonNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handlePersonNotExistException(PersonNotExistException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(SocietyNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleSocietyNotExistException(SocietyNotExistException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(VoyageNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleVoyageNotExistException(VoyageNotExistException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleDataIntegrityViolationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation failed", request.getServletPath());
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError: bindingResult.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return apiError;
    }
}
