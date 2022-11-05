package com.ead.notification.controllers

import com.ead.notification.configs.EadLog
import com.ead.notification.configs.log
import com.ead.notification.configs.security.AuthenticationCurrentUserService
import com.ead.notification.dtos.NotificationDto
import com.ead.notification.models.NotificationModel
import com.ead.notification.services.NotificationService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class UserNotificationController(
    val notificationService: NotificationService,
    private val authenticationCurrentUserService: AuthenticationCurrentUserService
): EadLog {
    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/notifications")
    fun getAllNotificationsByUser(
        @PathVariable(value = "userId") userId: UUID,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = ["notificationId"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable
    ): ResponseEntity<Page<NotificationModel>> {

        log().info("dados do user: ${jacksonObjectMapper().writeValueAsString(authenticationCurrentUserService.currentUser)}")


        return ResponseEntity.status(HttpStatus.OK).body(
            notificationService.findAllNotificationsByUser(userId, pageable)
        )
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    fun updateNotification(
        @PathVariable(value = "userId") userId: UUID,
        @PathVariable(value = "notificationId") notificationId: UUID,
        @RequestBody notificationDto: @Valid NotificationDto
    ): ResponseEntity<Any> {
        val notificationModelOptional = notificationService.findByNotificationIdAndUserId(
            notificationId, userId
        )
        if (notificationModelOptional.isEmpty) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!")
        }
        notificationModelOptional.get().notificationStatus = notificationDto.notificationStatus
        notificationService.saveNotification(notificationModelOptional.get())
        return ResponseEntity.status(HttpStatus.OK).body(notificationModelOptional.get())
    }
}