package com.ead.notification.services

import com.ead.notification.models.NotificationModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID
import java.util.Optional

interface NotificationService {
    fun saveNotification(notificationModel: NotificationModel): NotificationModel
    fun findAllNotificationsByUser(userId: UUID, pageable: Pageable): Page<NotificationModel>
    fun findByNotificationIdAndUserId(notificationId: UUID, userId: UUID): Optional<NotificationModel>
}