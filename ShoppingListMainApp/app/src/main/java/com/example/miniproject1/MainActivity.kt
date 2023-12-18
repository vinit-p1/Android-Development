package com.example.miniproject1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.miniproject1.ui.theme.MiniProject1Theme
import java.lang.StackWalker.Option
import androidx.activity.viewModels

class MainActivity : ComponentActivity() {
    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniProject1Theme (vm = viewModel){
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FrontPage()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrontPage() {
    val context = LocalContext.current
    Scaffold(
        topBar =
        {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Shopping List Manager")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(80.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Rounded.ShoppingCart,
                "add_shopping_cart",
                modifier = Modifier.size(200.dp))
            FilledTonalButton(
                onClick = {
                    val productListActivityIntent = Intent(context, ProductListActivity::class.java)
                    context.startActivity(productListActivityIntent)
                },
                modifier = Modifier
                    .widthIn(250.dp)
            ) {
                Text(text = "Show Shopping List",
                    fontSize = 20.sp)
            }
            FilledTonalButton(
                onClick = {
                    val storeListActivityIntent = Intent(context, StoreList::class.java)
                    context.startActivity(storeListActivityIntent)
                },
                modifier = Modifier
                    .widthIn(250.dp)
            ) {
                Text(text = "Store List",
                    fontSize = 20.sp)
            }
            FilledTonalButton(
                onClick = {
                    val optionsActivityIntent = Intent(context, OptionsActivity::class.java)
                    context.startActivity(optionsActivityIntent)
                },
                modifier = Modifier
                    .widthIn(250.dp)
            ) {
                Text(text = "Options",
                    fontSize = 20.sp)
            }
        }

    }
}

