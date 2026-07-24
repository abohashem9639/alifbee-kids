package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.datastore.DataStoreManager
import com.alifbee.kidsdemo.data.model.Syllabus
import com.alifbee.kidsdemo.data.remote.ApiService
import com.alifbee.kidsdemo.data.remote.mapper.toDomain
import com.alifbee.kidsdemo.data.remote.safeApiCall
import com.alifbee.kidsdemo.data.result.BaseRes
import com.alifbee.kidsdemo.data.result.map
import com.alifbee.kidsdemo.data.result.onSuccess
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoteSyllabusRepository @Inject constructor(
    private val api: ApiService,
    private val dataStoreManager: DataStoreManager,
) : SyllabusRepository {

    private var cached: Syllabus? = null

    override suspend fun getSyllabus(): BaseRes<Syllabus> {
        val result = safeApiCall { api.getSyllabus(environment = "prod") }
            .map { it.toDomain() }
            .onSuccess { syllabus ->
                cached = syllabus
                dataStoreManager.setSyllabus(syllabus)
            }

        if (result is BaseRes.Body) return result

        val offline = cached ?: dataStoreManager.syllabus.first()
        return offline?.let { BaseRes.Body(it) } ?: result
    }
}
