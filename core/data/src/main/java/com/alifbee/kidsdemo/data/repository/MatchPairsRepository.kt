package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.MatchPairsQuestion

interface MatchPairsRepository {
    suspend fun getMatchPairs(): MatchPairsQuestion
}
