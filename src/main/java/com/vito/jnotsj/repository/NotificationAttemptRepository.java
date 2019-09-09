package com.vito.jnotsj.repository;

import com.vito.jnotsj.entity.NotificationAttempt;
import com.vito.jnotsj.entity.NotificationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationAttemptRepository extends JpaRepository<NotificationAttempt, Long> {
    List<NotificationAttempt> findAllByNotificationData_Id(@Param("id") Long id);
    List<NotificationAttempt> findAllByUser_Id(@Param("id")Long id);
}