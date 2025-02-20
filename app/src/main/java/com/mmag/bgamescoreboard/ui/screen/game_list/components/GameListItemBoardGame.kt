package com.mmag.bgamescoreboard.ui.screen.game_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.ui.common.SwipeableItemBackground
import com.mmag.bgamescoreboard.ui.theme.vertGradShadow
import com.mmag.bgamescoreboard.utils.capitalizeFirstLetter
import kotlinx.coroutines.delay


@Composable
fun GameListItemBoardGame(
    onClickAction: () -> Unit,
    onDismiss: () -> Unit,
    boardGame: BoardGame,
    modifier: Modifier,
) {
    var show by remember { mutableStateOf(true) }
    var resetState by remember { mutableStateOf(false) }

    var dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart || it ==  SwipeToDismissBoxValue.StartToEnd) {
                show = false
                !resetState
                true
            } else false
        }, positionalThreshold = { 100F }
    )

    ElevatedCard(
        modifier = modifier,
        onClick = { onClickAction() }
    ) {
        SwipeToDismissBox(
            state = dismissState,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (!state.hasFocus) {
                        resetState = true
                    }
                },
            backgroundContent = { SwipeableItemBackground(dismissState = dismissState) },
            enableDismissFromStartToEnd = true,
            enableDismissFromEndToStart = false
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = boardGame.image.asImageBitmap(),
                    contentDescription = boardGame.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .background(vertGradShadow)
                        .fillMaxSize()
                        .alpha(0.2f)
                )
                GameListItemTitle(boardGame, Modifier.fillMaxWidth())
            }
        }
    }

    LaunchedEffect(show) {
        delay(200)
        if (!show) {
            onDismiss()
            dismissState.reset()
        }
        if (resetState) {
            dismissState.reset()
            resetState = false
        }
    }
}

@Composable
private fun GameListItemTitle(boardGame: BoardGame, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = boardGame.name.capitalizeFirstLetter(),
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
        if (boardGame.isFavorite) {
            Icon(
                imageVector = Icons.Filled.Star,
                tint = Color.White,
                contentDescription = stringResource(id = R.string.favorite_icon_description),
            )
        }
    }
}