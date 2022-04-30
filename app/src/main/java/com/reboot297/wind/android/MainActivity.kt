package com.reboot297.wind.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reboot297.wind.android.ui.theme.WindTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

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

        if (intent?.type == "application/json") {
            viewModel.load(intent?.data!!)
        }
    }
}

@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val list = viewModel.data.observeAsState(listOf()).value
    ListItems(list)
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
            Text(text = item.link, style = TextStyle(fontSize = 16.sp))
        })
    }
}