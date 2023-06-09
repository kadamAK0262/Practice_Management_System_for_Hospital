package com.revature.loggers;

import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.annotation.Pointcut;
//import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j

public class Logging {

	org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Logging.class);

	@Pointcut(value="execution(* com.revature.*.*.*(..))")
	public void myPointcut() {

	}

	@Around("myPointcut()")
	public Object applicationlogger(ProceedingJoinPoint pjp) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().toString();
		Object[] array = pjp.getArgs();
		log.info("method invoke" + className + ":" + methodName + "()" + "arguments : "
				+ mapper.writeValueAsString(array));
		Object obj = pjp.proceed();

		log.info(className + ":" + methodName + "()" + "Response : " + mapper.writeValueAsString(obj));
		return obj;

	}
}
