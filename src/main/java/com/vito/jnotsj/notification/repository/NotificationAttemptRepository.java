package com.vito.jnotsj.notification.repository;

import com.vito.jnotsj.notification.entity.NotificationAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationAttemptRepository extends JpaRepository<NotificationAttempt, Long> {
    List<NotificationAttempt> findAllByNotificationData_Id(@Param("id") Long id);
    List<NotificationAttempt> findAllByUser_Id(@Param("id")Long id);
    boolean existsByNotificationData_Id_AndUser_Id(@Param("dataId")Long notificationDataId, @Param("userId") Long userId);
}