package com.vito.jnotsj.service;


import com.vito.jnotsj.entity.NotificationAttempt;
import com.vito.jnotsj.entity.NotificationData;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.repository.NotificationAttemptRepository;
import com.vito.jnotsj.repository.NotificationDataRepository;
import com.vito.jnotsj.repository.UserRepository;
import com.vito.jnotsj.security.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NotificationAttemptService {
    @Autowired
    private NotificationAttemptRepository notificationAttemptRepository;

    @Autowired
    private NotificationDataRepository notificationDataRepository;

    @Autowired
    private UserRepository userRepository;

    public List<NotificationAttempt> getNotificationAttemptForData(Long id) {
        NotificationData existingNotificationData = this.notificationDataRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );
        return this.notificationAttemptRepository.findAllByNotificationData_Id(id);
    }

    public NotificationAttempt createNotificationAttempt(Long notificationDataId, UserAuth user) {
        NotificationData existingNotificationData = this.notificationDataRepository.findById(notificationDataId).orElseThrow (
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        User existingUser = this.userRepository.findById(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        NotificationAttempt notificationAttempt = new NotificationAttempt();
        notificationAttempt.setNotificationData(existingNotificationData);
        notificationAttempt.setUser(existingUser);

        return notificationAttemptRepository.save(notificationAttempt);
    }
}