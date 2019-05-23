package com.vito.jnotsj.controller;


import com.vito.jnotsj.entity.NotificationData;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.security.CurrentUser;
import com.vito.jnotsj.security.UserAuth;
import com.vito.jnotsj.service.NotificationDataService;
import com.vito.jnotsj.vo.notificationData.NotificationDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notificationData")
class NotificationController {
    @Autowired
    private NotificationDataService notificationDataService;

    @GetMapping
    public List<NotificationData> getAllNotifications(){
        return this.notificationDataService.getNotifications();
    }

    @PostMapping
    @ResponseBody
    public NotificationData postNotification(
            @RequestBody
            @Valid
            NotificationDataVO notificationData,
            @CurrentUser
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