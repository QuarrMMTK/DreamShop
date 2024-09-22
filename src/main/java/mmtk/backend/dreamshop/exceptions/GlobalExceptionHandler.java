package mmtk.backend.dreamshop.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * @Author quarr
 * @Created 9/25/24 10:29 AM
 * @Project DreamShop
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ProblemDetail handle(Exception exception) {
        ProblemDetail errorDetail;

        // Log the exception with stack trace
        logger.error("Exception caught: ", exception);

        switch (exception) {
            case BadRequestException badRequestException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
                errorDetail.setProperty("Description", "The username or password is incorrect");
            }
            case AccountStatusException accountStatusException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("Description", "The account is locked");
            }
            case AccessDeniedException accessDeniedException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("Description", "You are not authorized to access this resource");
            }
            case SignatureException signatureException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("Description", "The JWT signature is incorrect");
            }
            case ExpiredJwtException expiredJwtException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
                errorDetail.setProperty("Description", "The JWT token has expired");
            }
            case null, default -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), "An unexpected error occurred");
                errorDetail.setProperty("Description", "Internal Server Error");
            }
        }

        return errorDetail;
    }
}
