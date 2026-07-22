package com.alifbee.kidsdemo.ui.screens.homescreen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.PricingPlan
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors
import java.util.Locale

@Composable
internal fun PricingSection(
    plans: List<PricingPlan>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        plans.take(3).forEach { plan ->
            PricingPlanItem(
                plan = plan,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
        }
    }
}

@Composable
private fun PricingPlanItem(
    plan: PricingPlan,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .alpha(if (plan.discountPercent != null) 1f else 0f)
                .background(
                    LocalCustomColors.current.pricingBadgeBg,
                    RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                )
                .padding(horizontal = 16.dp, vertical = 4.dp),
        ) {
            Text(
                text = stringResource(R.string.discount_format, plan.discountPercent ?: 0),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = when {
                        plan.isBestValue || plan.discountPercent != null -> LocalCustomColors.current.pricingCardLavender
                        else -> LocalCustomColors.current.pricingCardGray
                    },
                    shape = if (plan.isBestValue) {
                        RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    } else {
                        RoundedCornerShape(12.dp)
                    },
                )
                .padding(horizontal = 8.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = plan.months.toString(),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(
                    if (plan.months == 1) R.string.pricing_month else R.string.pricing_months
                ),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            plan.originalPriceUsd?.let { originalPrice ->
                Text(
                    text = formatPrice(originalPrice),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier.padding(top = 16.dp),
                )
            }
            Text(
                text = formatPrice(plan.priceUsd),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            if (plan.months > 1) {
                Text(
                    text = stringResource(R.string.per_month_format, formatPrice(plan.priceUsd / plan.months)),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (plan.isBestValue) 1f else 0f)
                .background(
                    LocalCustomColors.current.pricingBadgeBg,
                    RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                )
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.best_value),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

private fun formatPrice(value: Double): String {
    val formatted = if (value % 1.0 == 0.0) {
        value.toInt().toString()
    } else {
        String.format(Locale.US, "%.2f", value)
    }
    return "$$formatted"
}

@Preview(showBackground = true)
@Composable
private fun PricingSectionPreview() {
    KidsDemoTheme {
        PricingSection(
            plans = listOf(
                PricingPlan(id = "1", months = 1, priceUsd = 9.0, originalPriceUsd = null, discountPercent = null, isBestValue = false),
                PricingPlan(id = "2", months = 6, priceUsd = 36.0, originalPriceUsd = 54.0, discountPercent = 33, isBestValue = false),
                PricingPlan(id = "3", months = 12, priceUsd = 54.0, originalPriceUsd = 108.0, discountPercent = 50, isBestValue = true),
            ),
        )
    }
}
