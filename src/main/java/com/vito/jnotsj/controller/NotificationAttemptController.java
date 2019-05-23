package com.vito.jnotsj.controller;


import com.vito.jnotsj.entity.NotificationAttempt;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.security.CurrentUser;
import com.vito.jnotsj.security.UserAuth;
import com.vito.jnotsj.service.NotificationAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class NotificationAttemptController {
    @Autowired
    private NotificationAttemptService notificationAttemptService;

    @GetMapping("notificationData/{id}/notificationAttempt")
    public List<NotificationAttempt> getAllAttempts(
            @PathVariable Long id
    ) {
        return this.notificationAttemptService.getNotificationAttemptForData(id);
    }

    @PostMapping("notificationData/{id}/notificationAttempt")
    public NotificationAttempt createAttempt(
            @PathVariable
                    Long id,
            @CurrentUser
                    UserAuth user
    ) {
        return this.notificationAttemptService.createNotificationAttempt(
                id,
                user
        );
    }
}