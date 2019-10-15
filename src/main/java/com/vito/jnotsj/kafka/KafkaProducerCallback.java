package com.vito.jnotsj.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
public class KafkaProducerCallback implements ListenableFutureCallback {
    @Override
    public void onFailure(final Throwable throwable) {
        log.error("Unable to send message= ", throwable);
    }

    @Override
    public void onSuccess(Object result) {
        log.debug("Sent message= " + result);
    }
}
