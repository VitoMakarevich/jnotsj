package com.vito.jnotsj.common.kafkaProcessing;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessNotMoreThanOnce {
    Class<? extends UniqueMessage> value();
}
