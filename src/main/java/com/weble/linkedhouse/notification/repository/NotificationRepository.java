package com.weble.linkedhouse.notification.repository;

import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByCustomerCustomerEmail(String customerEmail, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Notification n set n.notificationType = :notificationType where n.notificationId IN :notificationIds")
    void bulkCheckUpdate(@Param("notificationType") NotificationType notificationType, @Param("notificationIds") List<Long> notificationIds);

}
