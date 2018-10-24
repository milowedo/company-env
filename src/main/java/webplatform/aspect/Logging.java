package webplatform.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.logging.Logger;


@Aspect
@Component
public class Logging {

    private static Logger logger = Logger.getLogger(Logging.class.getName());


    //Logs all the usages of mappings
    @Around("webplatform.aspect.PointcutDeclaration.forController()")
    public Object beforeCallingAMapping(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        method.getAnnotation(RequestMapping.class);
        logger.info("ASPECTS Using mapping: " + method.getName());

        return proceedingJoinPoint.proceed();
    }

    //Logs when the exception was thrown
    @AfterThrowing(pointcut = "webplatform.aspect.PointcutDeclaration.forAll()", throwing = "throwable")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable throwable){
        logger.info("ASPECTS Method: " + joinPoint.getSignature().toShortString() + " has thrown an exception");
    }
}
