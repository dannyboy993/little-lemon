package com.example.littlelemon

import android.app.Application
import android.content.Context
import android.view.MenuItem
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.HighlightBlack
import com.example.littlelemon.ui.theme.HighlightGray
import com.example.littlelemon.ui.theme.PrimaryGreen
import com.example.littlelemon.ui.theme.PrimaryYellow


@Composable
fun Home(context: Context, navController: NavController, database: MenuDatabase) { // možda će trebati NavHOSTController

    val databaseMenuItems = database.MenuDao().getAllMenuItems().observeAsState(emptyList()).value

    val searchPhrase = remember {
        mutableStateOf("")
    }

    Column {
        Header(navController = navController)
        UpperPanel() {
            searchPhrase.value = it
        }
        LowerPannel(databaseMenuItems = databaseMenuItems, searchPhrase)
    }

}

@Composable
fun MenuItems(menuItems: List<MenuItemRoom>) {
    //Retrieve items from the database as a state and assign data to the menu items variable.

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        for (menuItem in menuItems) {
            MenuItem(menuItem)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItem: MenuItemRoom) {
    Column() {
        Text(text = menuItem.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row(modifier = Modifier.fillMaxWidth()) {
            Column() {
                Text(text = menuItem.description, fontSize = 16.sp, modifier = Modifier.fillMaxWidth(0.8f))
                Text(text = "$" + menuItem.price.toString())
            }

            GlideImage(model = menuItem.imageUrl, contentDescription = "",
                Modifier.size(width = 50.dp, height = 50.dp),
                contentScale = ContentScale.Crop
                )


        }
    }


}

@Composable
fun LowerPannel(databaseMenuItems: List<MenuItemRoom>, search: MutableState<String>) {
    val categories = databaseMenuItems.map { // IZ BAZE PODATAKA POVLAČIŠ SVA JELA I NJIHOVE KATEGORIJE I PRETVARAŠ U SET JER SET NE DUPLA
        it.category.replaceFirstChar { character ->
            character.uppercaseChar()
        }
    }.toSet()

    val selectedCategory = remember {
        mutableStateOf("")
    }

    val items = if (search.value == "") {
        databaseMenuItems
    } else {
        databaseMenuItems.filter { it.title.contains(search.value, ignoreCase = true) }
    }

    val filteredItemsByCat = if (selectedCategory.value == "" ||  selectedCategory.value == "all") {
        items
    } else {
        items.filter { it.category.contains(selectedCategory.value, ignoreCase = true) }
    }

    Column(modifier = Modifier.padding(20.dp)){
        Text(text = "Order for delivery!", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        MenuCategories(categories = categories) {
            selectedCategory.value = it
        }
        MenuItems(menuItems = filteredItemsByCat)
    }
}

@Composable
fun MenuCategories(categories: Set<String>, categoryLambda : (sel :String) -> Unit) {
    val cat = remember {
        mutableStateOf("")
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(0.dp, 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        CategoryButton(category = "All" ){
            cat.value = it.lowercase()
            categoryLambda(it.lowercase())
        }

        for (category in categories) {
            CategoryButton(category = category ){
                cat.value = it
                categoryLambda(it)
            }
        }
    }
}

@Composable
fun CategoryButton(category: String, selectedCategory: (sel: String) -> Unit) {
    val isClicked = remember {
        mutableStateOf(false)
    }

    Button(onClick = {
        isClicked.value = !isClicked.value
        selectedCategory(category)
    }, shape = RoundedCornerShape(25.dp)
        ,
        colors = ButtonDefaults.buttonColors(
            contentColor = HighlightBlack,
            backgroundColor = HighlightGray
        )

        ) {
        Text(text = category, fontSize = 12.sp)
    }

}

@Composable
fun UpperPanel(search : (parameter:String) -> Unit) {

    val searchPhrase = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .background(PrimaryGreen)
        .padding(horizontal = 20.dp, vertical = 10.dp)) {

        Text(text = "Little Lemon", color = PrimaryYellow, fontSize = 48.sp, fontWeight = FontWeight.Medium)
        Text(text = "Chicago", color = Color.White, fontSize = 24.sp)
        
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = "We are family owned Mediterranean restaurant, focused on serving  traditional dishes with a modern twist.",
                color = Color.White,
                modifier = Modifier.fillMaxWidth(0.75f))

            Image(painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero image",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(100.dp, 80.dp))
                    //.size(100.dp))
        }
        Spacer(modifier = Modifier.size(10.dp))

        OutlinedTextField(value = searchPhrase.value,
            onValueChange = {
                searchPhrase.value = it
                search(searchPhrase.value)
            },
            placeholder = {
                Text(text = "Enter a search phrase")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search , contentDescription = "Search icon")
            },

            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
            )
    }
}

@Composable
fun Header(navController: NavController) {
    Row( //HEADER ROW
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(250.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "",
            modifier = Modifier
                .size(120.dp)
                .padding(start = 50.dp)
                .clickable {
                    navController.navigate(Profile.route)
                }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview(){
    //Home()
}


