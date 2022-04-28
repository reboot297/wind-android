package com.reboot297.wind.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reboot297.wind.android.ui.theme.WindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WindTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    ListItems(viewModel.data)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WindTheme {
        MainScreen()
    }
}

@Composable
fun ListItems(list: List<Item>) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items = list, itemContent = { item ->
            Text(text = item.title, style = TextStyle(fontSize = 16.sp))
        })
    }
}