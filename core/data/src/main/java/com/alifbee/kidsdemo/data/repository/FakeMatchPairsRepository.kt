package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.MatchAnswer
import com.alifbee.kidsdemo.data.model.MatchPairsQuestion
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeMatchPairsRepository @Inject constructor() : MatchPairsRepository {

    override suspend fun getMatchPairs(): MatchPairsQuestion {
        delay(50L)
        return MatchPairsQuestion(
            questionText = "Match the pairs of words/phrases",
            groupA = listOf(
                MatchAnswer(id = 0, text = "Near"),
                MatchAnswer(id = 1, text = "Picnic"),
                MatchAnswer(id = 2, text = "The Guide"),
            ),
            groupB = listOf(
                MatchAnswer(id = 0, text = "قرب"),
                MatchAnswer(id = 2, text = "الدليل"),
                MatchAnswer(id = 1, text = "النزهة"),
            ),
        )
    }
}
