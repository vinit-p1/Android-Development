package com.example.miniproject1

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.miniproject1.Model.Store
import com.example.miniproject1.ui.theme.MiniProject1Theme
import androidx.core.content.ContextCompat.getSystemService


class StoreList : ComponentActivity() {
    private val viewModel: ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniProject1Theme(vm = viewModel) {
                val dbViewModel = StoreListViewModel(application)
                if(ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                    ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ), 1
                    )
                }
                val locationManager: LocationManager =
                    getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val currLocation =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StoreListUI(dbvm = dbViewModel, currLocation = currLocation)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreListUI(dbvm: StoreListViewModel, currLocation: Location?) {
    val context = LocalContext.current
    val storesList by dbvm.store.collectAsState(emptyList())
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
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val storeId: Long = dbvm.insertStore(
                        Store(name = "New Store")
                    )
                    val intent = Intent(context, MapStoresActivity::class.java)
                    intent.putExtra("storeId", storeId)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(10.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                )
//                {
//                    Text(text = "Name")
//                    Text(text = "Place")
//                    Text(text = "Description")
//                    Text(text = "Radius")
//
//                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(storesList) { store ->
                        val newName = remember { mutableStateOf(store.name) }
                        val newDescription = remember { mutableStateOf(store.description) }
                        val newRadius = remember { mutableStateOf(store.radius) }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        )
                        {
                            var textModifier =
                                Modifier
                                    .padding(4.dp, 15.dp)
                                    .fillMaxWidth()
                                    .weight(1f)
                            Text(modifier = textModifier, text = " Name:")
                            BasicTextField(
                                modifier = textModifier,
                                value = newName.value,
                                onValueChange = {
                                    newName.value = it
                                    val storeCopy = store.copy(name = it)
                                    dbvm.updateStore(storeCopy)
                                },
                                textStyle = TextStyle(
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                singleLine = true
                            )

                            //edit location
                            IconButton(
                                onClick = {
                                    val intent = Intent(context, MapStoresActivity::class.java)
                                    intent.putExtra("STORE_ID", store.id)
                                    intent.putExtra("STORE_LONG", store.long)
                                    intent.putExtra("STORE_LAT", store.lat)
                                    context.startActivity(intent)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "set location"
                                )
                            }

                            //remove button
                            IconButton(
                                onClick = {
                                    dbvm.deleteStore(store.id)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove"
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp, 0.dp)
                        ) {

                            //description
                            var descrModifier = Modifier
                                .padding(4.dp, 0.dp)
                                .fillMaxWidth()
                                .weight(1f)
                            Text(
                                modifier = Modifier.padding(4.dp, 0.dp),
                                text = "Description: ",
                                fontSize = 10.sp
                            )

                            BasicTextField(
                                modifier = descrModifier,
                                value = newDescription.value,
                                onValueChange = {
                                    newDescription.value = it
                                    val storeCopy = store.copy(description = it)
                                    dbvm.updateStore(storeCopy)
                                },
                                singleLine = false,
                                textStyle = TextStyle(fontSize = 12.sp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp, 0.dp)
                        ) {

                            //location
                            var locationLong = store.long.toString()
                            var locationLat = store.lat.toString()
                            Text(
                                modifier = Modifier.padding(4.dp, 0.dp),
                                text = "Location (long:lat):  " + locationLong + ":" + locationLat,
                                fontSize = 10.sp
                            )

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp, 0.dp)
                        ) {

                            Text(
                                modifier = Modifier.padding(4.dp, 0.dp),
                                text = "Radius: ",
                                fontSize = 10.sp
                            )
                            BasicTextField(
                                modifier = Modifier
                                    .padding(4.dp, 0.dp, 4.dp, 4.dp)
                                    .requiredWidth(24.dp),
                                value = store.radius.toString(),
                                onValueChange = { newValue ->
                                    val newRadiusDouble = newValue.toDoubleOrNull() ?: 0.1
                                    val itemCopy = store.copy(radius = newRadiusDouble)
                                    dbvm.updateStore(itemCopy)
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                textStyle = TextStyle(fontSize = 10.sp)
                            )




                        }

                    }
                }
            }
        }
    )
}

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



