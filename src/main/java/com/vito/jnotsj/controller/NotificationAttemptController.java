package com.vito.jnotsj.controller;


import com.vito.jnotsj.entity.NotificationAttempt;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.security.CurrentUser;
import com.vito.jnotsj.security.UserAuth;
import com.vito.jnotsj.service.NotificationAttemptService;
import com.vito.jnotsj.vo.notificationAttempt.NotificationAttemptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class NotificationAttemptController {
    @Autowired
    private NotificationAttemptService notificationAttemptService;

    @GetMapping("notificationData/{id}/notificationAttempt")
    public List<NotificationAttemptVO> getAllAttempts(
            @PathVariable Long id
    ) {
        return this.notificationAttemptService.getNotificationAttemptForData(id);
    }

    @GetMapping("user/{id}/notificationAttempt")
    public List<NotificationAttemptVO> getAttemptsByUser(
            @PathVariable Long id
    ) {
        return this.notificationAttemptService.getNotificationAttemptForUser(id);
    }

    @PostMapping("notificationData/{id}/notificationAttempt")
    public NotificationAttemptVO createAttempt(
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

    @DeleteMapping("notificationAttempt/{id}")
    public NotificationAttemptVO deleteAttempt(
            @PathVariable
                    Long id,
            @CurrentUser
                    UserAuth user
    ) {
        return this.notificationAttemptService.deleteNotificationAttempt(
                id
        );
    }
}