package com.vito.jnotsj.security;

import net.bytebuddy.implementation.attribute.AnnotationRetention;
import org.jboss.jandex.AnnotationTarget;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;


@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {}
