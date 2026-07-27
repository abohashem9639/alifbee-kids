package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.result.BaseRes

interface UserRepository {
    suspend fun updateLearningLanguage(code: String): BaseRes<Unit>
}
