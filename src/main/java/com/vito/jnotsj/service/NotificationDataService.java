package com.vito.jnotsj.service;

import com.vito.jnotsj.entity.NotificationData;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.repository.NotificationDataRepository;
import com.vito.jnotsj.repository.UserRepository;
import com.vito.jnotsj.security.UserAuth;
import com.vito.jnotsj.vo.notificationData.NotificationDataVO;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    private NotificationDataVO entityToVo(NotificationData notificationData) {
        NotificationDataVO notificationDataVO = modelMapper.map(notificationData, NotificationDataVO.class);

        return notificationDataVO;
    }

    public List<NotificationDataVO> getNotifications() {
        return StreamSupport.stream(notificationDataReposirory.findAllByOrderByEndDateDesc().spliterator(), false).map(
                entity -> entityToVo(entity)
        )
        .collect(Collectors.toList());
    }

    public List<NotificationData> getNotificationsByUser(Long id) {
        return StreamSupport.stream(notificationDataReposirory.findAllByAuthor_IdOrderByEndDateDesc(id).spliterator(), false)
                .collect(Collectors.toList());
    }

    public NotificationDataVO createNotification(NotificationDataVO notificationDataVO, UserAuth user) {
        User existingUser = this.userRepository.findById(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        NotificationData notificationData = new NotificationData();
        notificationData.setEndDate(notificationDataVO.getEndDate());
        notificationData.setStartDate(notificationDataVO.getStartDate());
        notificationData.setAuthor(existingUser);
        notificationData.setText(notificationDataVO.getText());
        return entityToVo(notificationDataReposirory.save(notificationData));
    }

    public NotificationData updateNotification(Long id, NotificationDataVO notificationDataVO) {
        NotificationData existing = notificationDataReposirory.findById(id).orElseThrow (
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setId(existing.getId());
        notificationData.setEndDate(notificationDataVO.getEndDate());
        notificationData.setStartDate(notificationDataVO.getStartDate());

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