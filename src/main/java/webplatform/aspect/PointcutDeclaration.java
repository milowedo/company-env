package webplatform.aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class PointcutDeclaration {

    @Pointcut("execution(* webplatform.controller.*.*(..))")
    public static void forController(){}

    @Pointcut("execution(* webplatform.dao.*.*(..))")
    public static void forDao(){}

    @Pointcut("execution(* webplatform.service.*.*(..))")
    public static void forService(){}

    @Pointcut("execution(* webplatform.entity.*.*(..))")
    public static void forEntity(){}

    @Pointcut("execution(* webplatform.entity.*.get*(..))")
    public static void forGetter(){}

    @Pointcut("execution(* webplatform.entity.*.set*(..))")
    public static void forSetter(){}

    @Pointcut("forDao() && forController() && forEntity() && forService()")
    public static void forAll(){}
}
