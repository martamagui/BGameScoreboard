package com.mmag.bgamescoreboard.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.mmag.bgamescoreboard.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BGSToolbar(title: String, backAction: (() -> Unit)?) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(),
        title = {
            Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
            if (backAction != null) {
                IconButton(onClick = { backAction() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.toolbar_back_action_description)
                    )
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BGSScrollableToolbar(
    title: String,
    backAction: () -> Unit,
    deleteAction: () -> Unit,
    editAction: () -> Unit,
    markAsFavouriteAction: () -> Unit,
    isFavorite: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = { backAction() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { markAsFavouriteAction() }) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    tint = if(isFavorite) Color.Yellow else Color.White.copy(alpha = 0.6f),
                    contentDescription = stringResource(id = R.string.favorite_icon_description),
                )
            }
            IconButton(onClick = { editAction() }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(id = R.string.toolbar_edit_action_description),
                )
            }
            IconButton(onClick = { deleteAction() }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.delete_icon_description)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}