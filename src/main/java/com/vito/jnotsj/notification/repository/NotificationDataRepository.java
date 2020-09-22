package com.vito.jnotsj.notification.repository;

import com.vito.jnotsj.notification.entity.NotificationData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface NotificationDataRepository extends CrudRepository<NotificationData, Long> {
    Iterable<NotificationData> findAllByOrderByEndDateDesc();
    Iterable<NotificationData> findAllByAuthor_IdOrderByEndDateDesc(@Param("id")Long id);
}