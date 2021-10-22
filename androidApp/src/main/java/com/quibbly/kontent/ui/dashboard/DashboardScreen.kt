package com.quibbly.kontent.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.statusBarsPadding
import com.quibbly.kontent.R
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SearchTopBar(
                searchQuery = "",
                onSearchChanged = { },
                modifier = Modifier,
            )
        },
    ) {

    }
}

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class,
)
@Composable
private fun SearchTopBar(
    searchQuery: String,
    onSearchChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val searchTextFieldInteractionSource = remember { MutableInteractionSource() }
    val searchTextFieldFocused by searchTextFieldInteractionSource.collectIsFocusedAsState()
    val searchCorners by animateIntAsState(targetValue = if (searchTextFieldFocused) 10 else 50)
    val searchShape by remember { derivedStateOf { RoundedCornerShape(searchCorners) } }

    Surface(
        modifier = modifier
            .wrapContentHeight()
            .statusBarsPadding(),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            UserHeader(
                header = stringResource(R.string.app_name),
                lastVisited = LocalDateTime.parse("2018-12-14T09:55:00"),
                modifier = Modifier.fillMaxWidth(),
            )
            val keyboardController = LocalSoftwareKeyboardController.current
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(searchShape),
                value = searchQuery,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                onValueChange = onSearchChanged,
                interactionSource = searchTextFieldInteractionSource,
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(stringResource(R.string.search))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search),
                    )
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = searchQuery.isNotEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        IconButton(onClick = {
                            onSearchChanged("")
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = stringResource(R.string.clear_search),
                            )
                        }
                    }
                },
                shape = searchShape,
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun UserHeader(
    header: String,
    lastVisited: LocalDateTime,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = header,
                style = MaterialTheme.typography.h6,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(
                    R.string.last_visited_date,
                    lastVisited.toJavaLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy"))
                ),
                style = MaterialTheme.typography.caption,
            )
        }
        Icon(
            imageVector = Icons.Rounded.Person,
            tint = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
            contentDescription = stringResource(R.string.your_avatar),
            modifier = Modifier.size(52.dp)
                .background(Color.LightGray, CircleShape)
                .padding(4.dp)
        )
    }
}