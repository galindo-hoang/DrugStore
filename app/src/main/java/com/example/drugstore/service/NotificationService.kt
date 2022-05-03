package com.example.drugstore.service

import com.example.drugstore.data.models.Notification
import com.example.drugstore.data.repository.AuthRepo
import com.example.drugstore.data.repository.NotificationRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationService @Inject constructor(
    private val notificationRepo: NotificationRepo,
    private val authRepo: AuthRepo
) {
    suspend fun fetchNotificationByUserID() = authRepo.getCurrentUserId()
        ?.let { notificationRepo.fetchNotificationByUserID(it) }

    suspend fun addNotification(notification: Notification) = notificationRepo.addNotification(notification)
}