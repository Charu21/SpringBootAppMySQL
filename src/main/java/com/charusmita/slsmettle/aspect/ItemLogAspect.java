package com.charusmita.slsmettle.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
public class ItemLogAspect {
    private static final Logger logger =
            LoggerFactory.getLogger(ItemLogAspect.class);

    @Pointcut("execution(* com.charusmita.slsmettle.controller.ItemController.* (..))")
    public void logging() {

    }

    @Around("logging()")
    public Object itemAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("**********Incoming http request**********");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = proceedingJoinPoint.getSignature().getDeclaringTypeName() + "."
                + proceedingJoinPoint.getSignature().getName();
        RequestLog requestLog = new RequestLog(url, ip, classMethod);
        logger.info("Request : {}", requestLog);
        return proceedingJoinPoint.proceed();
    }

    private record RequestLog(
            String url,
            String ip,
            String classMethod
    ) {

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    '}';
        }
    }
}
