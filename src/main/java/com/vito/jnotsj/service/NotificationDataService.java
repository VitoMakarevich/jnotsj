package com.vito.jnotsj.service;

import com.vito.jnotsj.entity.NotificationData;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.repository.NotificationDataRepository;
import com.vito.jnotsj.repository.UserRepository;
import com.vito.jnotsj.security.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NotificationDataService {
    @Autowired
    private NotificationDataRepository notificationDataReposirory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    public List<NotificationData> getNotifications() {
        return StreamSupport.stream(notificationDataReposirory.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public NotificationData createNotification(NotificationData notificationData, UserAuth user) {
        User existingUser = this.userRepository.findById(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        notificationData.setAuthor(existingUser);
        return notificationDataReposirory.save(notificationData);
    }

    public NotificationData updateNotification(Long id, NotificationData notificationData) {
        NotificationData existing = notificationDataReposirory.findById(id).orElseThrow (
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        notificationData.setId(existing.getId());

        return notificationDataReposirory.save(notificationData);
    }

    public NotificationData deleteNotification(Long id) {
        NotificationData existing = notificationDataReposirory.findById(id).orElseThrow (
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        notificationDataReposirory.deleteById(id);

        return existing;
    }
}