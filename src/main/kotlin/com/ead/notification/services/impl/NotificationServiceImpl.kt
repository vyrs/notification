package com.ead.notification.services.impl

import com.ead.notification.enums.NotificationStatus
import com.ead.notification.models.NotificationModel
import com.ead.notification.repositories.NotificationRepository
import com.ead.notification.services.NotificationService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.Optional

@Service
class NotificationServiceImpl(private val notificationRepository: NotificationRepository) : NotificationService {

    override fun saveNotification(notificationModel: NotificationModel): NotificationModel {
        return notificationRepository.save(notificationModel)
    }

    override fun findAllNotificationsByUser(userId: UUID, pageable: Pageable): Page<NotificationModel> {
        return notificationRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageable)
    }

    override fun findByNotificationIdAndUserId(notificationId: UUID, userId: UUID): Optional<NotificationModel> {
        return notificationRepository.findByNotificationIdAndUserId(notificationId, userId)
    }
}