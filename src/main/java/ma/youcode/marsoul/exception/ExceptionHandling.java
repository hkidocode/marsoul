package ma.youcode.marsoul.exception;


import ma.youcode.marsoul.message.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(EntityExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEntityExistException(EntityExistException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEntityNotExistException(EntityNotExistException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(EmailExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEmailExistException(EmailExistException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(EmailNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEmailNotExistException(EmailNotExistException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handlePasswordNotMatchException(PasswordNotMatchException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleTokenExpiredException(TokenExpiredException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(TokenConfirmedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleTokenConfirmedException(TokenConfirmedException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleTokenNotFoundException(TokenNotFoundException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(ContextException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleContextException(ContextException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(UpdateImageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUpdateImageException(UpdateImageException exception, HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation failed", request.getServletPath());
        Set<String> validationErrors = new HashSet<>();
        Set<ConstraintViolation<?>> bindingResult = exception.getConstraintViolations();
        for (ConstraintViolation<?> fieldError: bindingResult) {
            validationErrors.add(fieldError.getMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return apiError;
    }
}
