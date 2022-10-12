package com.gmhis_backk.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.gmhis_backk.domain.HttpResponse;
import com.gmhis_backk.exception.domain.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

/**
 * 
 * @author adjara
 *
 */
@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String ACCOUNT_LOCKED ="Votre compte a été verrouillé. Veuillez contacter l'administration";
    private static final String METHOD_IS_NOT_ALLOWED ="Cette méthode de demande n'est pas autorisée sur ce point de terminaison. Veuillez envoyer une demande '% s'";
    private static final String INTERNAL_SERVER_ERROR_MSG = "Une erreur s'est produite lors du traitement de la demande"; 
    private static final String INCORRECT_CREDENTIALS ="Nom d'utilisateur / mot de passe incorrect. Veuillez réessayer";
    private static final String ACCOUNT_DISABLED = "Votre compte a été désactivé. S'il s'agit d'une erreur, veuillez contacter l'administration";
    private static final String ERROR_PROCESSING_FILE ="Une erreur s'est produite lors du traitement du fichier";
    private static final String NOT_ENOUGH_PERMISSION = "Vous n'avez pas assez de permission";
    public static final String ERROR_PATH = "/erreur";
    
    
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException() {
        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException() {
        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException() {
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {
        return createHttpResponse(UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<HttpResponse> usernameExistException(UsernameExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException e) {
//        return createHttpResponse(BAD_REQUEST, "There is no mapping for this URL");
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(NotAnImageFileException.class)
    public ResponseEntity<HttpResponse> notAnImageFileException(NotAnImageFileException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> iOException(IOException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
    
    @ExceptionHandler(ResourceNameAlreadyExistException.class)
    public ResponseEntity<HttpResponse> resourceNameAlreadyExistException(ResourceNameAlreadyExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    
    @ExceptionHandler(ResourceNotFoundByIdException.class)
    public ResponseEntity<HttpResponse> resourceNotFoundByIdException(ResourceNotFoundByIdException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    
    @ExceptionHandler(PasswordDoNotMatchException.class)
    public ResponseEntity<HttpResponse> passwordDoNotMatchException(PasswordDoNotMatchException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<HttpResponse> InvalidInputException(InvalidInputException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    
    @ExceptionHandler(TransferException.class)
    public ResponseEntity<HttpResponse> TransferException(TransferException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    
    @ExceptionHandler(ArticleNotInDepotException.class)
    public ResponseEntity<HttpResponse> ArticleNotInDepotException(ArticleNotInDepotException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<HttpResponse> AutorizationException(AuthorizationException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    
    @ExceptionHandler(ApplicationErrorException.class)
    public ResponseEntity<HttpResponse> ApplicationErrorException(ApplicationErrorException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    
    //handling custom validation errors (request body)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException exception) {
    	 return createHttpResponse(BAD_REQUEST, exception.getBindingResult().getFieldError().getDefaultMessage());
	}
    
    
  //handling custom validation errors (request param)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> customValidationErrorHandlings(ConstraintViolationException exception, WebRequest request) {
    	 return createHttpResponse(BAD_REQUEST, exception.getMessage().split(": ")[1]);
	}
}
