package com.vito.jnotsj.common.kafkaProcessing;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Service
@Slf4j
public class KafkaProcessingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Object processMessage(Class storeTableClass, BaseKafkaMessage kafkaMessage, ProceedingJoinPoint pjp, Acknowledgment acknowledgment) throws Throwable {
        Object returnVal;
        boolean alreadyProcessed = entityManager.find(storeTableClass, kafkaMessage.getUuid()) != null;
        log.debug("Already processed status is " + alreadyProcessed);
        if(alreadyProcessed) {
            log.debug("Skipping processing because already processed");
            return null;
        }

        returnVal = pjp.proceed();
        UniqueMessage uniqueMessage = (UniqueMessage) storeTableClass.newInstance();
        uniqueMessage.setId(UUID.randomUUID());
        uniqueMessage.setPayload(kafkaMessage);
        entityManager.persist(storeTableClass.cast(uniqueMessage));
        acknowledgment.acknowledge();

        return returnVal;
    }
}
