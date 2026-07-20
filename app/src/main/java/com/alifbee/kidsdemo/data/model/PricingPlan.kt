package com.alifbee.kidsdemo.data.model

data class PricingPlan(
    val id: String,
    val months: Int,
    val priceUsd: Double,
    val originalPriceUsd: Double?,
    val discountPercent: Int?,
    val isBestValue: Boolean,
)
