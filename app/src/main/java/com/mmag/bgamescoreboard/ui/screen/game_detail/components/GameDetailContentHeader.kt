package com.mmag.bgamescoreboard.ui.screen.game_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import com.mmag.bgamescoreboard.ui.theme.Typography
import com.mmag.bgamescoreboard.ui.theme.vertGradShadow


@Composable
fun GameDetailContentHeader(
    data: BoardGameWithGameRecordRelation,
    modifier: Modifier
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = data.game.image.asImageBitmap(),
                contentDescription = data.game.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .background(vertGradShadow)
                    .fillMaxSize()
                    .alpha(0.3f)
            )
            Text(
                text = data.game.name.capitalize(),
                Modifier.padding(8.dp),
                style = Typography.headlineLarge,
                color = Color.White
            )
        }
    }
}
