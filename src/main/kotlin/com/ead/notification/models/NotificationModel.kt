package com.ead.notification.models

import com.ead.notification.enums.NotificationStatus
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_NOTIFICATIONS")
class NotificationModel(
    @Column(nullable = false)
    var userId: UUID,

    @Column(nullable = false, length = 150)
    var title: String,

    @Column(nullable = false)
    var message: String,

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    var creationDate: LocalDateTime,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var notificationStatus: NotificationStatus

) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var notificationId: UUID? = null
}