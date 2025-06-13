package com.example.redesignstarbucks.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define Starbucks color palette for consistency
object StarbucksColors {
    val DarkGreen = Color(0xFF006241)
    val MediumGreen = Color(0xFF00704A)
    val LightGreen = Color(0xFFD4E9E2)
    val Cream = Color(0xFFF3F1E7)
    val DarkGrey = Color(0xFF333333)
    val LightGrey = Color(0xFFF2F2F2)
}

// Data classes to model the UI data
data class FavoriteItem(val name: String, val price: String, val imageUrl: String)
data class NavItem(val label: String, val icon: ImageVector)

/**
 * The main entry point for the Starbucks Home Screen.
 * It's structured using a Scaffold to easily place the top bar, content, and bottom navigation.
 */
@Composable
fun StarbucksHomeScreen() {
    Scaffold(
        containerColor = StarbucksColors.Cream,
        topBar = { TopGreetingBar() },
        bottomBar = { BottomNavBar() }
    ) { paddingValues ->
        // Main content area, uses a LazyColumn for scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item { BalanceCard() }
            item { FavoritesSection() }
            item { PromoSection() }
        }
    }
}

/**
 * Displays the "Good morning" greeting and profile icon at the top of the screen.
 */
@Composable
fun TopGreetingBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                "Good morning,",
                style = MaterialTheme.typography.titleMedium,
                color = StarbucksColors.DarkGrey.copy(alpha = 0.7f)
            )
            Text(
                "Revy", // Replace with dynamic user name
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = StarbucksColors.DarkGrey
            )
        }
        // Placeholder for a profile picture
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(StarbucksColors.LightGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = StarbucksColors.DarkGreen,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

/**
 * A prominent card showing the Starbucks card balance and quick actions.
 */
@Composable
fun BalanceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        // Using a Box to overlay content on a gradient background
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(StarbucksColors.DarkGreen, StarbucksColors.MediumGreen)
                    )
                )
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "STARBUCKS REWARDS",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "$12.75",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    "Current Balance",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                // Action buttons at the bottom of the card
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { /* Handle Add Money */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Add money", color = StarbucksColors.DarkGreen, fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(
                        onClick = { /* Handle Scan in Store */ },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = BorderStroke(1.dp, Color.White), // Fixed: Added BorderStroke import
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Scan in store", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

/**
 * A horizontally scrolling section for "Your Favorites".
 */
@Composable
fun FavoritesSection() {
    // Mock data for favorite items
    val favorites = listOf(
        FavoriteItem("Caramel Macchiato", "$5.65", "caramel_macchiato"),
        FavoriteItem("Iced Brown Sugar Oatmilk", "$6.25", "brown_sugar_oatmilk"),
        FavoriteItem("Chocolate Croissant", "$3.75", "chocolate_croissant")
    )

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            "Your Favorites",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = StarbucksColors.DarkGrey
        )
        Spacer(modifier = Modifier.height(12.dp))
        // LazyRow is efficient for long lists
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(favorites) { item ->
                FavoriteItemCard(item)
            }
        }
    }
}

/**
 * A single card for a favorite item.
 */
@Composable
fun FavoriteItemCard(item: FavoriteItem) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.width(160.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .background(StarbucksColors.LightGrey),
                contentAlignment = Alignment.Center
            ) {
                Text("Image of\n${item.name}", textAlign = TextAlign.Center)
            }
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = StarbucksColors.DarkGrey,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    item.price,
                    fontSize = 14.sp,
                    color = StarbucksColors.DarkGrey.copy(alpha = 0.8f)
                )
            }
        }
    }
}

/**
 * A section to display promotional banners.
 */
@Composable
fun PromoSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Summer specials",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = StarbucksColors.DarkGrey
        )
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = StarbucksColors.MediumGreen),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "New Pineapple Passionfruit Drink",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { /* Handle Order Now */ },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("Order Now", color = StarbucksColors.DarkGreen, fontWeight = FontWeight.Bold)
                    }
                }
                // Placeholder for promo image
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(StarbucksColors.LightGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Promo Icon",
                        tint = StarbucksColors.DarkGreen,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

/**
 * The custom bottom navigation bar.
 */
@Composable
fun BottomNavBar() {
    val navItems = listOf(
        NavItem("Home", Icons.Filled.Home),
        NavItem("Order", Icons.Filled.Coffee),
        NavItem("Scan", Icons.Filled.QrCodeScanner),
        NavItem("Gift", Icons.Filled.CardGiftcard),
        NavItem("Stores", Icons.Filled.LocationOn)
    )
    var selectedItemIndex by remember { mutableStateOf(0) }
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { selectedItemIndex = index },
                icon = { Icon(item.icon, contentDescription = item.label, modifier = Modifier.size(26.dp)) },
                label = { Text(item.label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = StarbucksColors.MediumGreen,
                    selectedTextColor = StarbucksColors.MediumGreen,
                    unselectedIconColor = StarbucksColors.DarkGrey.copy(alpha = 0.6f),
                    unselectedTextColor = StarbucksColors.DarkGrey.copy(alpha = 0.6f),
                    indicatorColor = StarbucksColors.LightGreen
                )
            )
        }
    }
}

/**
 * Preview function to see the layout in Android Studio.
 */
@Preview(showBackground = true, device = "id:pixel_6")
@Composable
fun StarbucksHomeScreenPreview() {
    MaterialTheme {
        StarbucksHomeScreen()
    }
}
