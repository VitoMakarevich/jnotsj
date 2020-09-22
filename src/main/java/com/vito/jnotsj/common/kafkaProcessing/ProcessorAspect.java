package com.vito.jnotsj.common.kafkaProcessing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class ProcessorAspect {
    private final KafkaProcessingService kafkaProcessingService;


    @Pointcut("@annotation(ProcessNotMoreThanOnce)")
    public void processNotMoreThanOnceAnnotation() { }

    @Around("processNotMoreThanOnceAnnotation()")
    public Object aroundProcessNotMoreThanOnce(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        ProcessNotMoreThanOnce myAnnotation = method.getAnnotation(ProcessNotMoreThanOnce.class);
        Class storeTableClass = myAnnotation.value();
        BaseKafkaMessage kafkaMessage = null;
        Acknowledgment acknowledgment = null;
        Object[] pjpArgs = pjp.getArgs();
        for(Object arg: pjpArgs) {
            if(arg instanceof BaseKafkaMessage) {
                kafkaMessage = (BaseKafkaMessage) arg;
            }
            if (arg instanceof Acknowledgment) {
                acknowledgment = (Acknowledgment) arg;
            }
        }
        if(kafkaMessage == null) {
            log.error("Not found expected message in annotated method");
            throw new IllegalArgumentException("Method argument should have param instanceof" + BaseKafkaMessage.class.getName());
        }

        if(acknowledgment == null) {
            log.error("Not found expected acknowledgment in annotated method");
            throw new IllegalArgumentException("At least one param might be instance of" + Acknowledgment.class.getName());
        }

        return kafkaProcessingService.processMessage(storeTableClass, kafkaMessage, pjp, acknowledgment);
    }
}
