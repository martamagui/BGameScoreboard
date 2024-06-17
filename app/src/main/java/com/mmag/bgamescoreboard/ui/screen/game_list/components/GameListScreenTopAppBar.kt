package com.mmag.bgamescoreboard.ui.screen.game_list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmag.bgamescoreboard.R


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GameListScreenTopAppBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(id = R.string.game_list_screen_title),
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )
    })
}