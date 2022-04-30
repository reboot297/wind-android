package com.reboot297.wind.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import androidx.lifecycle.viewmodel.compose.viewModel
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

        viewModel.urlData.observe(this) {
            it?.let {
                openUrl(it)
            }
            viewModel.urlData.postValue(null)
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW)
            .setData(Uri.parse(url)))
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

    val viewModel = hiltViewModel<MainViewModel>()
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items = list, itemContent = { item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        viewModel.openUrl(item.link)
                    })
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Text(text = item.title, style = TextStyle(fontSize = 16.sp))
                Text(
                    text = item.link,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.Blue
                    )
                )
            }
        })
    }
}