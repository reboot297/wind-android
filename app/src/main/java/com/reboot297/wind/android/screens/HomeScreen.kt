package com.reboot297.wind.android.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reboot297.wind.android.MainViewModel

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(hiltViewModel())
}

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val list = viewModel.data.observeAsState().value?.items.orEmpty()
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items = list, itemContent = { item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Text(text = item.title.toString(), style = TextStyle(fontSize = 16.sp))

                item.links?.forEach{
                    Text(
                        text = it.toString(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.Blue
                        ),
                        modifier = Modifier.clickable {
                            viewModel.openUrl(it)
                        }
                    )
                }
                item.media?.forEach{
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.Blue
                        ),
                        modifier = Modifier.clickable {
                            viewModel.openUrl(it)
                        }
                    )
                }
            }
        })
    }
}