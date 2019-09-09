package com.vito.jnotsj.repository;

import com.vito.jnotsj.entity.NotificationData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationDataRepository extends CrudRepository<NotificationData, Long> {
    Iterable<NotificationData> findAllByOrderByEndDateDesc();
    Iterable<NotificationData> findAllByAuthor_IdOrderByEndDateDesc(@Param("id")Long id);
}