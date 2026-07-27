package com.alifbee.kidsdemo.ui.screens.moreapps.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun AppCard(
    app: AppItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(18.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.5.dp, LocalCustomColors.current.cardBorder, shape)
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(app.iconUrl)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.cd_app_icon),
            placeholder = painterResource(R.drawable.ic_alifbee_2),
            error = painterResource(R.drawable.ic_alifbee_2),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(14.dp)),
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = app.name,
                color = LocalCustomColors.current.brandPurple,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = app.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp),
            )
            Text(
                text = stringResource(R.string.view_on_app_store),
                color = LocalCustomColors.current.linkBlue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Preview
@Composable
private fun AppCardPreview() {
    KidsDemoTheme {
        AppCard(
            app = AppItem(
                id = "1",
                name = "AlifBee - Learn Arabic The Easy Way",
                description = "Discover the fun world of Arabic with AlifBee Kids - Let them have fun while learning.",
                iconUrl = "",
                storeUrl = "",
            ),
            onClick = {},
        )
    }
}
