///*
// * Copyright 2023 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package ru.akhilko.feature.search
//
//import android.app.appsearch.SearchResult
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.Orientation
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.safeDrawing
//import androidx.compose.foundation.layout.systemBars
//import androidx.compose.foundation.layout.windowInsetsBottomHeight
//import androidx.compose.foundation.layout.windowInsetsPadding
//import androidx.compose.foundation.layout.windowInsetsTopHeight
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
//import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
//import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
//import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.ClickableText
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.key.Key
//import androidx.compose.ui.input.key.key
//import androidx.compose.ui.input.key.onKeyEvent
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.platform.testTag
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.AnnotatedString
//import androidx.compose.ui.text.SpanStyle
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.PreviewParameter
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import ru.akhilko.feature.search.R as searchR
//
//@Composable
//internal fun SearchRoute(
//    onBackClick: () -> Unit,
//    onInterestsClick: () -> Unit,
//    onTopicClick: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    searchViewModel: SearchViewModel = hiltViewModel(),
//) {
//    val recentSearchQueriesUiState by searchViewModel.recentSearchQueriesUiState.collectAsStateWithLifecycle()
//    val searchResultUiState by searchViewModel.searchResultUiState.collectAsStateWithLifecycle()
//    val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()
//    SearchScreen(
//        modifier = modifier,
//        searchQuery = searchQuery,
//        searchResultUiState = searchResultUiState,
//        onSearchQueryChanged = searchViewModel::onSearchQueryChanged,
//        onSearchTriggered = searchViewModel::onSearchTriggered,
//        onBackClick = onBackClick,
//    )
//}
//
//@Composable
//internal fun SearchScreen(
//    modifier: Modifier = Modifier,
//    searchQuery: String = "",
//    searchResultUiState: SearchResultUiState = SearchResultUiState.Loading,
//    onSearchQueryChanged: (String) -> Unit = {},
//    onSearchTriggered: (String) -> Unit = {},
//    onBackClick: () -> Unit = {},
//) {
//    Column(modifier = modifier) {
//        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
//        SearchToolbar(
//            onBackClick = onBackClick,
//            onSearchQueryChanged = onSearchQueryChanged,
//            onSearchTriggered = onSearchTriggered,
//            searchQuery = searchQuery,
//        )
//        when (searchResultUiState) {
//            SearchResultUiState.Loading,
//            SearchResultUiState.LoadFailed,
//            -> Unit
//
//            SearchResultUiState.SearchNotReady -> SearchNotReadyBody()
//            SearchResultUiState.EmptyQuery,
//            -> {
//            }
//
//            is SearchResultUiState.Success -> {
//                if (searchResultUiState.isEmpty()) {
//                    EmptySearchResultBody(
//                        searchQuery = searchQuery,
//                    )
//                } else {
//                    SearchResultBody(
//                        searchQuery = searchQuery,
//                        onSearchTriggered = onSearchTriggered,
//                    )
//                }
//            }
//        }
//        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
//    }
//}
//
//@Composable
//fun EmptySearchResultBody(
//    searchQuery: String,
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(horizontal = 48.dp),
//    ) {
//        val message =
//            stringResource(id = searchR.string.feature_search_result_not_found, searchQuery)
//        val start = message.indexOf(searchQuery)
//        Text(
//            text = AnnotatedString(
//                text = message,
//                spanStyles = listOf(
//                    AnnotatedString.Range(
//                        SpanStyle(fontWeight = FontWeight.Bold),
//                        start = start,
//                        end = start + searchQuery.length,
//                    ),
//                ),
//            ),
//            style = MaterialTheme.typography.bodyLarge,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(vertical = 24.dp),
//        )
//    }
//}
//
//@Composable
//private fun SearchNotReadyBody() {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(horizontal = 48.dp),
//    ) {
//        Text(
//            text = stringResource(id = searchR.string.feature_search_not_ready),
//            style = MaterialTheme.typography.bodyLarge,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(vertical = 24.dp),
//        )
//    }
//}
//
//@Composable
//private fun SearchResultBody(
//    searchQuery: String,
//    onSearchTriggered: (String) -> Unit,
//    searchResults: List<SearchResult>
//) {
//    val state = rememberLazyStaggeredGridState()
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//    ) {
//        LazyVerticalStaggeredGrid(
//            columns = StaggeredGridCells.Adaptive(300.dp),
//            contentPadding = PaddingValues(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            verticalItemSpacing = 24.dp,
//            modifier = Modifier
//                .fillMaxSize(),
//            state = state,
//        ) {
//            if (searchResults.isNotEmpty()) {
//                item(
//                    span = StaggeredGridItemSpan.FullLine,
//                ) {
//                    Text(
//                        text = buildAnnotatedString {
//                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                                append(stringResource(id = searchR.string.feature_search_results))
//                            }
//                        },
//                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//                    )
//                }
//
//                newsFeed(
//                    feedState = Success(feed = newsResources),
//                    onNewsResourcesCheckedChanged = onNewsResourcesCheckedChanged,
//                    onNewsResourceViewed = onNewsResourceViewed,
//                    onTopicClick = onTopicClick,
//                    onExpandedCardClick = {
//                        onSearchTriggered(searchQuery)
//                    },
//                )
//            }
//        }
//        val itemsAvailable = topics.size + newsResources.size
//        val scrollbarState = state.scrollbarState(
//            itemsAvailable = itemsAvailable,
//        )
//        state.DraggableScrollbar(
//            modifier = Modifier
//                .fillMaxHeight()
//                .windowInsetsPadding(WindowInsets.systemBars)
//                .padding(horizontal = 2.dp)
//                .align(Alignment.CenterEnd),
//            state = scrollbarState,
//            orientation = Orientation.Vertical,
//            onThumbMoved = state.rememberDraggableScroller(
//                itemsAvailable = itemsAvailable,
//            ),
//        )
//    }
//}
//
//@Composable
//private fun RecentSearchesBody(
//    recentSearchQueries: List<String>,
//    onClearRecentSearches: () -> Unit,
//    onRecentSearchClicked: (String) -> Unit,
//) {
//    Column {
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth(),
//        ) {
//            Text(
//                text = buildAnnotatedString {
//                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                        append(stringResource(id = searchR.string.feature_search_recent_searches))
//                    }
//                },
//                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//            )
//            if (recentSearchQueries.isNotEmpty()) {
//                IconButton(
//                    onClick = {
//                        onClearRecentSearches()
//                    },
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                ) {
//                    Icon(
//                        imageVector = NiaIcons.Close,
//                        contentDescription = stringResource(
//                            id = searchR.string.feature_search_clear_recent_searches_content_desc,
//                        ),
//                        tint = MaterialTheme.colorScheme.onSurface,
//                    )
//                }
//            }
//        }
//        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
//            items(recentSearchQueries) { recentSearch ->
//                Text(
//                    text = recentSearch,
//                    style = MaterialTheme.typography.headlineSmall,
//                    modifier = Modifier
//                        .padding(vertical = 16.dp)
//                        .clickable { onRecentSearchClicked(recentSearch) }
//                        .fillMaxWidth(),
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun SearchToolbar(
//    searchQuery: String,
//    onSearchQueryChanged: (String) -> Unit,
//    onSearchTriggered: (String) -> Unit,
//    onBackClick: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = modifier.fillMaxWidth(),
//    ) {
//        IconButton(onClick = { onBackClick() }) {
//            Icon(
//                imageVector = NiaIcons.ArrowBack,
//                contentDescription = stringResource(
//                    id = string.core_ui_back,
//                ),
//            )
//        }
//        SearchTextField(
//            onSearchQueryChanged = onSearchQueryChanged,
//            onSearchTriggered = onSearchTriggered,
//            searchQuery = searchQuery,
//        )
//    }
//}
//
//@Composable
//private fun SearchTextField(
//    searchQuery: String,
//    onSearchQueryChanged: (String) -> Unit,
//    onSearchTriggered: (String) -> Unit,
//) {
//    val focusRequester = remember { FocusRequester() }
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    val onSearchExplicitlyTriggered = {
//        keyboardController?.hide()
//        onSearchTriggered(searchQuery)
//    }
//
//    TextField(
//        colors = TextFieldDefaults.colors(
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent,
//        ),
//        leadingIcon = {
//            Icon(
//                imageVector = NiaIcons.Search,
//                contentDescription = stringResource(
//                    id = searchR.string.feature_search_title,
//                ),
//                tint = MaterialTheme.colorScheme.onSurface,
//            )
//        },
//        trailingIcon = {
//            if (searchQuery.isNotEmpty()) {
//                IconButton(
//                    onClick = {
//                        onSearchQueryChanged("")
//                    },
//                ) {
//                    Icon(
//                        imageVector = NiaIcons.Close,
//                        contentDescription = stringResource(
//                            id = searchR.string.feature_search_clear_search_text_content_desc,
//                        ),
//                        tint = MaterialTheme.colorScheme.onSurface,
//                    )
//                }
//            }
//        },
//        onValueChange = {
//            if ("\n" !in it) onSearchQueryChanged(it)
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .focusRequester(focusRequester)
//            .onKeyEvent {
//                if (it.key == Key.Enter) {
//                    onSearchExplicitlyTriggered()
//                    true
//                } else {
//                    false
//                }
//            }
//            .testTag("searchTextField"),
//        shape = RoundedCornerShape(32.dp),
//        value = searchQuery,
//        keyboardOptions = KeyboardOptions(
//            imeAction = ImeAction.Search,
//        ),
//        keyboardActions = KeyboardActions(
//            onSearch = {
//                onSearchExplicitlyTriggered()
//            },
//        ),
//        maxLines = 1,
//        singleLine = true,
//    )
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
//}
//
//@Preview
//@Composable
//private fun SearchToolbarPreview() {
//    NiaTheme {
//        SearchToolbar(
//            searchQuery = "",
//            onBackClick = {},
//            onSearchQueryChanged = {},
//            onSearchTriggered = {},
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun EmptySearchResultColumnPreview() {
//    NiaTheme {
//        EmptySearchResultBody(
//            onInterestsClick = {},
//            searchQuery = "C++",
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun RecentSearchesBodyPreview() {
//    NiaTheme {
//        RecentSearchesBody(
//            onClearRecentSearches = {},
//            onRecentSearchClicked = {},
//            recentSearchQueries = listOf("kotlin", "jetpack compose", "testing"),
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun SearchNotReadyBodyPreview() {
//    NiaTheme {
//        SearchNotReadyBody()
//    }
//}
//
//@DevicePreviews
//@Composable
//private fun SearchScreenPreview(
//    @PreviewParameter(SearchUiStatePreviewParameterProvider::class)
//    searchResultUiState: SearchResultUiState,
//) {
//    NiaTheme {
//        SearchScreen(searchResultUiState = searchResultUiState)
//    }
//}
