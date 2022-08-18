package com.ead.notification.dtos

import com.ead.notification.enums.NotificationStatus
import com.ead.notification.models.NotificationModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

data class NotificationCommandDto(
    val title: String,
    val message: String,
    val userId: UUID
)

fun NotificationCommandDto.toModel() =
    NotificationModel(
        userId,
        title,
        message,
        LocalDateTime.now(ZoneId.of("UTC")),
        NotificationStatus.CREATED
    )