package com.vito.jnotsj.notification.service;


import com.vito.jnotsj.auth.entity.Role;
import com.vito.jnotsj.auth.entity.RoleName;
import com.vito.jnotsj.notification.entity.NotificationAttempt;
import com.vito.jnotsj.notification.entity.NotificationData;
import com.vito.jnotsj.auth.entity.User;
import com.vito.jnotsj.notification.kafka.KafkaProducer;
import com.vito.jnotsj.notification.mailEnitty.NotificationAttemptEmail;
import com.vito.jnotsj.notification.mailEnitty.NotificationAttemptedData;
import com.vito.jnotsj.notification.mappers.NotificationAttemptMapper;
import com.vito.jnotsj.notification.repository.NotificationAttemptRepository;
import com.vito.jnotsj.notification.repository.NotificationDataRepository;
import com.vito.jnotsj.auth.repository.UserRepository;
import com.vito.jnotsj.auth.UserAuth;
import com.vito.jnotsj.notification.vo.notificationAttempt.NotificationAttemptVO;
import com.vito.jnotsj.notification.vo.notificationData.NotificationDataVO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationAttemptService {
    private final NotificationAttemptRepository notificationAttemptRepository;

    private final NotificationDataRepository notificationDataRepository;

    private final UserRepository userRepository;

    private final NotificationAttemptMapper notificationAttemptMapper;

    private final NotificationMailService notificationMailService;

    @RolesAllowed({"USER", "ADMIN"})
    public List<NotificationAttemptVO> getNotificationAttemptForData(Long id) {
        this.notificationDataRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );
        return this.notificationAttemptRepository.findAllByNotificationData_Id(id).stream().map(notificationAttemptMapper::entityToVo).collect(Collectors.toList());
    }

    @RolesAllowed({"ADMIN", "USER"})
    public List<NotificationAttemptVO> getNotificationAttemptForUser(Long userId) {
        return this.notificationAttemptRepository.findAllByUser_Id(userId).stream().map(notificationAttemptMapper::entityToVo).collect(Collectors.toList());
    }

    @Transactional
    @RolesAllowed({"ADMIN", "USER"})
    public NotificationAttemptVO createNotificationAttempt(Long notificationDataId, UserAuth user) {
        NotificationData existingNotificationData = this.notificationDataRepository.findById(notificationDataId).orElseThrow (
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        boolean alreadyExists = this.notificationAttemptRepository.existsByNotificationData_Id_AndUser_Id(notificationDataId, user.getUser().getId());
        if(alreadyExists) {
            throw new NotificationAttemptAlreadyExistsException();
        }
        User existingUser = this.userRepository.findById(user.getUser().getId()).get();
        NotificationAttempt notificationAttempt = NotificationAttempt.builder()
                .notificationData(existingNotificationData)
                .user(existingUser).build();
        NotificationAttempt notificationAttemptCreated = notificationAttemptRepository.save(notificationAttempt);
        notificationMailService.sendUserNotificationAttemptedEmail(existingUser);

        return notificationAttemptMapper.entityToVo(notificationAttemptCreated);
    }

    @Transactional
    @RolesAllowed({"ADMIN", "USER"})
    public NotificationAttemptVO deleteNotificationAttempt(Long notificationAttemptId) {
        NotificationAttempt foundedNotificationAttempt = this.notificationAttemptRepository.findById(notificationAttemptId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.notificationAttemptRepository.deleteById(notificationAttemptId);

        return notificationAttemptMapper.entityToVo(foundedNotificationAttempt);
    }
}