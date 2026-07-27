package com.alifbee.kidsdemo.ui.screens.homescreen.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.Service
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme

@Composable
internal fun ServicesSection(
    services: List<Service>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.our_services),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            services.chunked(3).forEach { rowServices ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    rowServices.forEach { service ->
                        ServiceCard(
                            service = service,
                            modifier = Modifier.weight(1f),
                        )
                    }
                    repeat(3 - rowServices.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun ServiceCard(
    service: Service,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = {})
            .background(colorResource(service.backgroundColorRes), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(service.iconRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(52.dp),
        )
        Text(
            text = service.name,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ServicesSectionPreview() {
    KidsDemoTheme {
        ServicesSection(
            services = listOf(
                Service(id = "1", name = "Notes", iconRes = R.drawable.ic_notes, backgroundColorRes = R.color.service_bg_gray),
                Service(id = "2", name = "Self Assessment", iconRes = R.drawable.ic_notes, backgroundColorRes = R.color.service_bg_pink),
                Service(id = "3", name = "Vocabulary", iconRes = R.drawable.ic_notes, backgroundColorRes = R.color.service_bg_cream),
                Service(id = "4", name = "Grammar", iconRes = R.drawable.ic_notes, backgroundColorRes = R.color.service_bg_gray),
                Service(id = "5", name = "Stories", iconRes = R.drawable.ic_notes, backgroundColorRes = R.color.service_bg_pink),
            ),
        )
    }
}
