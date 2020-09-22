package com.vito.jnotsj.notification.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Notification attempt already exists")
public class NotificationAttemptAlreadyExistsException extends RuntimeException { }
