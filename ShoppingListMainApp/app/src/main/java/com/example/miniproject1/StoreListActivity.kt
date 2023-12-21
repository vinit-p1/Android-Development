package com.example.miniproject1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import com.example.miniproject1.Model.Store
import com.example.miniproject1.ui.theme.MiniProject1Theme

class StoreList : ComponentActivity() {
    private val viewModel: ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniProject1Theme(vm = viewModel) {
                val dbViewModel = DBViewModel(application)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StoreListUI(dbvm = dbViewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreListUI(dbvm: DBViewModel) {
    val context = LocalContext.current
//    val tabs = listOf("List", "Map")
//    var selectedTabIndex by remember { mutableStateOf(0) }

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
        floatingActionButton = {
                               FloatingActionButton(
                                   onClick = {
                                             val storeId:Long = dbvm.insertStore(
                                                 Store(name = "New Store")
                                             )
                                       val intent = Intent(context, MapStoresActivity::class.java)
                                       intent.putExtra("storeId", storeId)
                                       context.startActivity(intent)
                                             },
                                   modifier = Modifier.padding(16.dp)) {
                                   Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                               }
        },
        content = {
            innerPadding ->
            Column (
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ){
                Spacer(modifier = Modifier.height(10.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Text(text = "Name")
                    Text(text = "Place")
                    Text(text = "Description")
                    Text(text = "Radius")

                }
                /*TODO LazyColumn*/

//                TabRow(
//                    selectedTabIndex = selectedTabIndex,
//                    modifier = Modifier.fillMaxWidth(),
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    contentColor = MaterialTheme.colorScheme.primary
//                ) {
//                    tabs.forEachIndexed { index, title ->
//                        Tab(
//                            selected = selectedTabIndex == index,
//                            onClick = {
//                                selectedTabIndex = index
//                            },
//                            text = {
//                                Text(text = title)
//                            }
//                        )
//                    }
//                }
//                when (selectedTabIndex) {
//                    0 -> StoreListTab(dbvm)
//                    1 -> StoreMapTab()
//                }
            }
        },

    ) 
}

