package com.vito.jnotsj.notification.controller;


import com.vito.jnotsj.notification.entity.NotificationData;
import com.vito.jnotsj.auth.UserAuth;
import com.vito.jnotsj.notification.service.NotificationDataService;
import com.vito.jnotsj.notification.vo.notificationData.NotificationDataVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notificationData")
@RequiredArgsConstructor
class NotificationController {
    private final NotificationDataService notificationDataService;

    @GetMapping
    public List<NotificationDataVO> getAllNotifications(){
        return this.notificationDataService.getNotifications();
    }

    @PostMapping
    @ResponseBody
    public NotificationDataVO postNotification(
            @RequestBody
            @Valid
            NotificationDataVO notificationData,
            @AuthenticationPrincipal
            UserAuth user
    ) {
        return this.notificationDataService.createNotification(notificationData, user);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public NotificationData putNotification(
            @PathVariable
            Long id,
            @RequestBody
            @Valid
            NotificationDataVO notificationData
    ) {
        return this.notificationDataService.updateNotification(id, notificationData);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public NotificationData deleteNotification(
            @PathVariable
            Long id
    ) {
        return this.notificationDataService.deleteNotification(id);
    }
}