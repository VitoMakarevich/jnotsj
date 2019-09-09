package com.vito.jnotsj.service;


import com.vito.jnotsj.entity.NotificationAttempt;
import com.vito.jnotsj.entity.NotificationData;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.repository.NotificationAttemptRepository;
import com.vito.jnotsj.repository.NotificationDataRepository;
import com.vito.jnotsj.repository.UserRepository;
import com.vito.jnotsj.security.UserAuth;
import com.vito.jnotsj.vo.notificationAttempt.NotificationAttemptVO;
import com.vito.jnotsj.vo.notificationData.NotificationDataVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationAttemptService {
    @Autowired
    private NotificationAttemptRepository notificationAttemptRepository;

    @Autowired
    private NotificationDataRepository notificationDataRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private NotificationAttemptVO entityToVo(NotificationAttempt notificationAttempt) {
        NotificationAttemptVO notificationAttemptVO = modelMapper.map(notificationAttempt, NotificationAttemptVO.class);

        return notificationAttemptVO;
    }

    public List<NotificationAttemptVO> getNotificationAttemptForData(Long id) {
        NotificationData existingNotificationData = this.notificationDataRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );
        return this.notificationAttemptRepository.findAllByNotificationData_Id(id).stream().map(this::entityToVo).collect(Collectors.toList());
    }

    public List<NotificationAttemptVO> getNotificationAttemptForUser(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return this.notificationAttemptRepository.findAllByUser_Id(id).stream().map(this::entityToVo).collect(Collectors.toList());
    }

    public NotificationAttemptVO createNotificationAttempt(Long notificationDataId, UserAuth user) {
        NotificationData existingNotificationData = this.notificationDataRepository.findById(notificationDataId).orElseThrow (
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        User existingUser = this.userRepository.findById(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        NotificationAttempt notificationAttempt = new NotificationAttempt();
        notificationAttempt.setNotificationData(existingNotificationData);
        notificationAttempt.setUser(existingUser);

        return entityToVo(notificationAttemptRepository.save(notificationAttempt));
    }

    public NotificationAttemptVO deleteNotificationAttempt(Long notificationAttemptId) {
        NotificationAttempt foundedNotificationAttempt = this.notificationAttemptRepository.findById(notificationAttemptId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.notificationAttemptRepository.deleteById(notificationAttemptId);

        return entityToVo(foundedNotificationAttempt);
    }
}