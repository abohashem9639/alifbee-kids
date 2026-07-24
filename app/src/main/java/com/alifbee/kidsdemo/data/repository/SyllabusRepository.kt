package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.Syllabus
import com.alifbee.kidsdemo.data.result.BaseRes

interface SyllabusRepository {
    suspend fun getSyllabus(): BaseRes<Syllabus>
}
