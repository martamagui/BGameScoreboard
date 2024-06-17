package com.mmag.bgamescoreboard.ui.screen.game_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.screen.game_list.GameListUiState
import com.mmag.bgamescoreboard.ui.theme.Typography

@Composable
fun GameListContentHeader(uiState: GameListUiState, modifier: Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(126.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "${uiState.recordsCount}",
                        style = Typography.displayMedium
                    )
                    Text(text = stringResource(id = R.string.game_list_record_amount_title))
                }

            }
            Spacer(modifier = Modifier.width(12.dp))
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "${uiState.data?.size ?: 0}",
                        style = Typography.displayMedium
                    )
                    Text(text = stringResource(id = R.string.game_list_games_amount_title))
                }
            }
        }
    }
}
