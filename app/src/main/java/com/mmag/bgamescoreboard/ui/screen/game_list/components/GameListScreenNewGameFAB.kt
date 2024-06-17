package com.mmag.bgamescoreboard.ui.screen.game_list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes

@Composable
fun GameListScreenNewGameFAB(navHostController: NavHostController) {
    FloatingActionButton(
        onClick = { navHostController.navigate(BGSConfigRoutes.NEW_GAME) },
        modifier = Modifier
            .padding(24.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.game_list_add))
    }
}