package id.antasari.fpa_230104040205_laportitik.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import id.antasari.fpa_230104040205_laportitik.ui.theme.OrangePrimary
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        // TOMBOL TENGAH: SOS (Lapor)
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("report") },
                containerColor = OrangePrimary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(65.dp).offset(y = 50.dp) // Offset agar pas di tengah bottom bar
            ) {
                Icon(Icons.Default.Add, contentDescription = "SOS", modifier = Modifier.size(32.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.Center,

        // --- MENU BAWAH (BOTTOM NAVIGATION) ---
        // Ini adalah akses utama ke MAP sesuai PDF
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                // Menu 1: Home
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 10.sp) },
                    selected = true,
                    onClick = { /* Stay Here */ },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = OrangePrimary, indicatorColor = Color.White)
                )
                // Menu 2: MAP (AKSES PETA DI SINI)
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Map, contentDescription = "Map") },
                    label = { Text("Map", fontSize = 10.sp) },
                    selected = false,
                    onClick = { navController.navigate("map") }
                )
                // Spacer untuk tombol SOS di tengah
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Add, contentDescription = null, tint = Color.Transparent) },
                    label = { },
                    selected = false,
                    onClick = { },
                    enabled = false
                )
                // Menu 3: Alerts (Notifikasi)
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Notifications, contentDescription = "Alerts") },
                    label = { Text("Alerts", fontSize = 10.sp) },
                    selected = false,
                    onClick = { navController.navigate("notification") }
                )
                // Menu 4: Profile
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Person, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 10.sp) },
                    selected = false,
                    onClick = { navController.navigate("profile") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            // HEADER LOKASI
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Current Location", fontSize = 12.sp, color = Color.Gray)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Banjarmasin, ID", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
                Box(
                    modifier = Modifier.size(45.dp).clip(CircleShape).background(Color.LightGray).clickable { navController.navigate("profile") },
                    contentAlignment = Alignment.Center
                ) {
                    if (UserRepository.profileImage != null) {
                        Image(
                            bitmap = UserRepository.profileImage!!.asImageBitmap(),
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // STATUS AMAN (HIJAU)
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("STATUS: SAFE ZONE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No immediate threats detected nearby.", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                        // Shortcut Kecil ke Peta
                        Button(
                            onClick = { navController.navigate("map") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            modifier = Modifier.height(32.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Map", color = Color(0xFF4CAF50), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(12.dp))
                        Text(" Last updated: Just now", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TOMBOL SOS
            Button(
                onClick = { navController.navigate("report") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("SOS EMERGENCY REQUEST", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // LAYANAN GRID (Klik Shelters -> Buka Map)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ServiceItem("Shelters", Icons.Outlined.Home, Color(0xFF2196F3)) { navController.navigate("map") }
                ServiceItem("First Aid", Icons.Outlined.MedicalServices, Color(0xFFFF9800)) {}
                ServiceItem("Check-in", Icons.Outlined.CheckCircle, Color(0xFF4CAF50)) {}
                ServiceItem("Drafts", Icons.Outlined.History, Color(0xFF9C27B0)) { navController.navigate("draft") }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // RECENT DISASTERS
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Recent Disasters", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("See All", color = OrangePrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))

            DisasterCard("Flash Flood Warning", "Severe flooding reported in downtown.", "0.5 mi", "10 mins ago • Official Alert", Icons.Default.WaterDrop, Color(0xFF2196F3), Color(0xFFE3F2FD))
            Spacer(modifier = Modifier.height(12.dp))
            DisasterCard("Power Outage", "Grid failure affecting North District.", "1.2 mi", "1 hr ago • Community Report", Icons.Default.ElectricBolt, Color(0xFFFFC107), Color(0xFFFFF8E1))

            Spacer(modifier = Modifier.height(80.dp)) // Jarak agar tidak tertutup menu bawah
        }
    }
}

// UI WIDGETS
@Composable
fun ServiceItem(title: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Box(modifier = Modifier.size(60.dp).background(color.copy(alpha = 0.1f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
    }
}

@Composable
fun DisasterCard(title: String, desc: String, distance: String, time: String, icon: ImageVector, iconColor: Color, bgColor: Color) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier.size(40.dp).background(bgColor, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(distance, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(desc, fontSize = 13.sp, color = Color.DarkGray, lineHeight = 18.sp, maxLines = 2)
                Spacer(modifier = Modifier.height(8.dp))
                Text(time, fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}