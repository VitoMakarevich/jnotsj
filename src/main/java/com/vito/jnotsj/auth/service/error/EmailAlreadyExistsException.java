package com.vito.jnotsj.auth.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email already exists")
public class EmailAlreadyExistsException extends RuntimeException { }
