package com.example.miniproject1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.miniproject1.ui.theme.MiniProject1Theme

class StoreList : ComponentActivity() {
    private val viewModel: ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniProject1Theme(vm = viewModel) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StoreListUI()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreListUI() {
    val context = LocalContext.current
    val tabs = listOf("List", "Map")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Your Stores")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val mainActivityIntent = Intent(context, MainActivity::class.java)
                        context.startActivity(mainActivityIntent)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")

                    }
                },
                actions = {
                    IconButton(onClick = {
                        val optionsActivityIntent = Intent(context, OptionsActivity::class.java)
                        context.startActivity(optionsActivityIntent)
                    }) {
                        Icon(Icons.Default.Settings, contentDescription = "Options")
                    }

                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ))
        },
        content = {
            innerPadding ->
            Column (
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ){
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(text = title)
                            }
                        )
                    }
                }
                when (selectedTabIndex) {
                    0 -> StoreListTab()
                    1 -> StoreMapTab()
                }
            }
        },

    ) 
}

@Composable
fun StoreMapTab() {
    Text(text = "This is map",
        color = MaterialTheme.colorScheme.primary // or any other color that stands out
    )

}

@Composable
fun StoreListTab() {

    Text(text = "This is list")
}
