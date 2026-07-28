package com.alifbee.kidsdemo.data.model

data class MatchPairsQuestion(
    val questionText: String,
    val groupA: List<MatchAnswer>,
    val groupB: List<MatchAnswer>,
)

data class MatchAnswer(
    val id: Int,
    val text: String,
)
