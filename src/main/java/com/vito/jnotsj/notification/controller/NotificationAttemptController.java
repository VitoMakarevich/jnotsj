package com.vito.jnotsj.notification.controller;


import com.vito.jnotsj.auth.UserAuth;
import com.vito.jnotsj.notification.service.NotificationAttemptService;
import com.vito.jnotsj.notification.vo.notificationAttempt.NotificationAttemptVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
class NotificationAttemptController {
    private final NotificationAttemptService notificationAttemptService;

    @GetMapping("notificationData/{id}/notificationAttempt")
    public List<NotificationAttemptVO> getAllAttempts(
            @PathVariable Long id
    ) {
        return this.notificationAttemptService.getNotificationAttemptForData(id);
    }

    @GetMapping("/notificationAttempt")
    public List<NotificationAttemptVO> getAttemptsByUser(
            UserAuth user
            ) {
        return this.notificationAttemptService.getNotificationAttemptForUser(user);
    }

    @PostMapping("notificationData/{id}/notificationAttempt")
    public NotificationAttemptVO createAttempt(
            @PathVariable
            Long id,
            @AuthenticationPrincipal
            UserAuth user
    ) {
        return this.notificationAttemptService.createNotificationAttempt(
                id,
                (UserAuth) user
        );
    }

    @DeleteMapping("notificationAttempt/{id}")
    public NotificationAttemptVO deleteAttempt(
            @PathVariable
                    Long id,
            UserAuth user
    ) {
        return this.notificationAttemptService.deleteNotificationAttempt(
                id
        );
    }
}