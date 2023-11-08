package com.example.miniproject1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.miniproject1.Model.Product
import com.example.miniproject1.ui.theme.MiniProject1Theme
import kotlinx.coroutines.currentCoroutineContext
import kotlin.coroutines.coroutineContext

class ProductListActivity : ComponentActivity() {
    val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniProject1Theme (vm = viewModel){
                val dbViewModel = ProductDBViewModel(application)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductInterface(dbvm = dbViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInterface(dbvm : ProductDBViewModel) {

    val productsList by dbvm.product.collectAsState(emptyList())
    val context = LocalContext.current
    var productName by remember {
        mutableStateOf("")
    }
    var productPrice by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf(0.0f)
    }
    var productQty by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableStateOf(0)
    }
    var checkedState by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Shopping List")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val mainActivityIntent = Intent(context, MainActivity::class.java)
                        context.startActivity(mainActivityIntent)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")

                    }
                },

                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ))
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {  }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
        ) {

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically)
            {
                TextField(
                    value = productName,
                    onValueChange = {
                        productName = it
                    },
                    modifier = Modifier
                        .padding(3.dp, 10.dp)
                        .size(200.dp, 60.dp),
                    label = { Text("Product Name") }
                )
                Text(text = "Purchased?")
                Checkbox(checked = checkedState, onCheckedChange = { checkedState = it })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically){

                TextField(
                    value = productPrice,
                    onValueChange = {
                        productPrice = it
                        price = productPrice.toFloat()
                    },
                    modifier = Modifier
                        .padding(5.dp, 10.dp)
                        .size(200.dp, 55.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Price") }
                )


                TextField(
                    value = productQty,
                    onValueChange = {
                        productQty = it
                        quantity = productQty.toInt()
                    },
                    modifier = Modifier
                        .padding(5.dp, 10.dp)
                        .size(200.dp, 55.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Quantity")}
                )

            }



            Button(
                onClick = { dbvm.insertProduct(Product(name = productName, price = price.toDouble(), quantity = quantity, isPurchased = checkedState))
               },
                modifier = Modifier
                    .requiredHeight(50.dp)
                    .requiredWidth(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            {
                Text(text = "Add product")
            }
            
            Spacer(modifier = Modifier.requiredHeight(10.dp))
            
            LazyColumn(modifier = Modifier
                .fillMaxSize()
            ){
                items(productsList){product ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),

                        verticalAlignment = Alignment.CenterVertically
                    ){
                        // Checkbox to see if its purchased
                        Checkbox(
                            checked = product.isPurchased,
                            onCheckedChange = {checked ->
                                val productCopy = product.copy(isPurchased = checked)
                                dbvm.updateProduct(productCopy)
                            }
                        )

                        // Product Name
                        BasicTextField(
                            modifier = Modifier
                                .padding(4.dp, 6.dp)
                                .weight(1f)
                                .width(IntrinsicSize.Min),
                            value = product.name,
                            onValueChange = {changedName ->
                                val productCopy = product.copy(name = changedName)
                                dbvm.updateProduct(productCopy) },
                            textStyle = TextStyle(
                                fontSize = 25.sp,
                                color = MaterialTheme.colorScheme.onSurface ),
                            singleLine = true
                        )
                        
                        Spacer(modifier = Modifier.requiredWidth(3.dp))

                        // Product Quantity
                        Text(text = "X", fontSize = 15.sp)
                        BasicTextField(
                            modifier = Modifier
                                .background(color = Color.Transparent)
                                .padding(4.dp, 6.dp)
                                .weight(1f)
                                .width(IntrinsicSize.Min)
                                .size(5.dp, 35.dp),
                            value = product.quantity.toString(),
                            onValueChange = { newQty ->
                                val newQtyInt = newQty.toIntOrNull() ?: 1
                                val productCopy = product.copy(quantity = newQtyInt)
                                dbvm.updateProduct(productCopy)
                            },
                            textStyle = TextStyle(
                                fontSize = 25.sp,
                                color = MaterialTheme.colorScheme.onSurface ),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)

                        )

                        // Price
                        Text(text = "PLN", fontSize = 15.sp)
                        BasicTextField(
                            modifier = Modifier
                                .background(color = Color.Transparent)
                                .padding(4.dp, 6.dp)
                                .weight(1f)
                                .width(IntrinsicSize.Min)
                                .size(5.dp, 35.dp),
                            value = product.price.toString(),
                            onValueChange = { newPrice ->
                                val newPriceDouble = newPrice.toDoubleOrNull() ?: 0.1
                                val productCopy = product.copy(price = newPriceDouble)
                                dbvm.updateProduct(productCopy)
                            },
                            textStyle = TextStyle(
                                fontSize = 25.sp,
                                color = MaterialTheme.colorScheme.onSurface ),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)

                        )

                        // Delete the Product
                        IconButton(onClick = { dbvm.deleteProduct(product) }) {

                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Product")

                        }
                    }


                }
//                Spacer(modifier = Modifier.requiredHeight(1.dp))
            }

        }
    }
}