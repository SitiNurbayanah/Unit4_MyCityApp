package com.example.cityrecommendations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    AppNavHost(navController)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "city_list") {
        composable("city_list") { CityListScreen(navController) }
        composable("category_list/{city}") { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city")!!
            CategoryListScreen(city, navController)
        }
        composable("recommendation_list/{city}/{category}") { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city")!!
            val category = backStackEntry.arguments?.getString("category")!!
            RecommendationListScreen(city, category)
        }
    }
}

@Composable
fun CityListScreen(navController: NavHostController) {
    val cities = listOf("Bandung", "Jakarta", "Surabaya")
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(cities) { city ->
            Text(
                text = city,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate("category_list/$city")
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCityListScreen() {
    val navController = rememberNavController()
    CityListScreen(navController)
}

@Composable
fun CategoryListScreen(city: String, navController: NavHostController) {
    val categories = when (city) {
        "Bandung" -> listOf("Coffee Shops", "Restaurants", "Parks")
        "Jakarta" -> listOf("Museums", "Shopping Centers", "Food Stalls")
        "Surabaya" -> listOf("Beaches", "Historical Sites", "Cafes")
        else -> emptyList()
    }
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(categories) { category ->
            Text(
                text = category,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate("recommendation_list/$city/$category")
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryListScreen() {
    val navController = rememberNavController()
    CategoryListScreen(city = "Bandung", navController = navController)
}

@Composable
fun RecommendationListScreen(city: String, category: String) {
    val recommendations = listOf(
        "$category Place A in $city",
        "$category Place B in $city"
    )
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(recommendations) { recommendation ->
            Text(
                text = recommendation,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecommendationListScreen() {
    RecommendationListScreen(city = "Bandung", category = "Coffee Shops")
}
