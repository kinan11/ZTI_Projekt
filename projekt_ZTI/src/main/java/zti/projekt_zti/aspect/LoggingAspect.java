package zti.projekt_zti.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Aspekt odpowiedzialny za logowanie informacji o wywołanych metodach.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    HttpServletRequest request;

    /**
     * Definiuje punkt przecięcia dla metod oznaczonych adnotacjami @PostMapping, @GetMapping i @DeleteMapping
     * w pakiecie zti.projekt_zti i jego podpakietach.
     */
    @Pointcut("within(zti.projekt_zti..*)" +
            "&& (@annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public void pointcut() {
    }

    /**
     * Loguje informacje przed wykonaniem metody.
     *
     * @param joinPoint punkt przecięcia
     */
    @Before("pointcut()")
    public void logMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getStaticPart().getSignature();
        Class<?>[] parameters = signature.getParameterTypes();
        try {
            logger.info("INFO BEFORE - path: {}, method: {}, arguments: {} ",
                    mapper.writeValueAsString(request.getRequestURL()),
                    request.getMethod(),
                    mapper.writeValueAsString(parameters));
        } catch (JsonProcessingException e) {
            logger.error("Error", e);
        }
    }

    /**
     * Loguje informacje po wykonaniu metody.
     *
     * @param responseEntity obiekt ResponseEntity zwracany przez metodę
     */
    @AfterReturning(pointcut = "pointcut()", returning = "responseEntity")
    public void logMethodAfter(ResponseEntity<?> responseEntity) {
        try {
            int statusCode = responseEntity.getStatusCodeValue();
            logger.info("INFO AFTER - path: {}, method: {}, returning: {}",
                    mapper.writeValueAsString(request.getRequestURL()),
                    request.getMethod(),
                    mapper.writeValueAsString(statusCode));
        } catch (JsonProcessingException e) {
            logger.error("Error", e);
        }
    }

}