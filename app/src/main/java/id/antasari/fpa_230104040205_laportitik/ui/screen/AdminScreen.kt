package id.antasari.fpa_230104040205_laportitik.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border // <--- PENTING: Import ini untuk memperbaiki error border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward // Update icon arrow
import androidx.compose.material.icons.automirrored.filled.Logout // Update icon logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import id.antasari.fpa_230104040205_laportitik.ui.theme.OrangePrimary // Import warna tema kita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(navController: NavController) {
    // Warna khusus Admin (Dark Theme)
    val DarkBg = Color(0xFF121212)
    val CardBg = Color(0xFF1E1E1E)
    val TextWhite = Color.White
    val TextGray = Color.Gray
    // Definisi Warna Orange Manual (karena Color.Orange tidak ada)
    val WarningOrange = Color(0xFFFF9800)

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Analytics, contentDescription = null, tint = TextWhite)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Admin Analytics", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Overview & Stats", color = TextGray, fontSize = 12.sp)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg),
                actions = {
                    IconButton(onClick = {
                        // Logout (kembali ke Login)
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }) {
                        // Gunakan AutoMirrored icon
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", tint = Color.Red)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // 1. Overview Cards (Total, Pending, Resolved)
            Text("Overview", color = TextWhite, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Card Besar (Total)
                AdminStatCard(
                    modifier = Modifier.weight(1f).height(140.dp),
                    title = "Total Reports",
                    count = "1,245",
                    color = OrangePrimary, // Pakai warna tema kita
                    icon = Icons.Default.Description
                )

                // 2 Card Kecil Vertikal
                Column(modifier = Modifier.weight(1f).height(140.dp), verticalArrangement = Arrangement.SpaceBetween) {
                    AdminStatCardSmall("Pending", "342", WarningOrange) // Pakai variabel orange manual tadi
                    AdminStatCardSmall("Resolved", "89", Color(0xFF4CAF50)) // Green
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Incidents by Category (Progress Bars)
            Text("Incidents by Category", color = TextWhite, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = CardBg),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    AdminCategoryItem("Natural Disasters", 0.7f, Color(0xFF2196F3))
                    Spacer(modifier = Modifier.height(16.dp))
                    AdminCategoryItem("Medical Emergency", 0.5f, Color(0xFFE91E63))
                    Spacer(modifier = Modifier.height(16.dp))
                    AdminCategoryItem("Infrastructure", 0.3f, WarningOrange)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Real-time Hotspots (Peta Gelap Simulasi)
            Text("Real-time Hotspots", color = TextWhite, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF2C2C2C)) // Peta Gelap
            ) {
                // Titik-titik hotspot
                HotspotDot(Alignment.Center, Color.Red)
                HotspotDot(Alignment.TopStart, WarningOrange) // Ganti Color.Orange jadi WarningOrange
                HotspotDot(Alignment.BottomEnd, Color.Red)

                Text("Live Map View", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.align(Alignment.BottomStart).padding(16.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Recent Activity List
            Text("Recent Activity", color = TextWhite, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            AdminActivityItem("Medical Supply Request", "10m ago", Icons.Default.MedicalServices)
            AdminActivityItem("Bridge Collapse Reported", "1h ago", Icons.Default.Warning)
            AdminActivityItem("New Volunteer Joined", "2h ago", Icons.Default.PersonAdd)

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// --- Komponen-komponen Pendukung Admin ---

@Composable
fun AdminStatCard(modifier: Modifier, title: String, count: String, color: Color, icon: ImageVector) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(color.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color)
            }
            Column {
                Text(count, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(title, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun AdminStatCardSmall(title: String, count: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().height(64.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(count, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = color)
                Text(title, fontSize = 12.sp, color = Color.Gray)
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun AdminCategoryItem(label: String, progress: Float, color: Color) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = Color.White, fontSize = 14.sp)
            Text("${(progress * 100).toInt()}%", color = Color.Gray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress }, // Perbaikan syntax progress
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = color,
            trackColor = Color(0xFF333333)
        )
    }
}

@Composable
fun BoxScope.HotspotDot(alignment: Alignment, color: Color) {
    Box(
        modifier = Modifier
            .align(alignment)
            .padding(40.dp) // Offset random
            .size(12.dp)
            .background(color.copy(alpha = 0.6f), CircleShape)
            .border(1.dp, Color.White, CircleShape) // Ini aman sekarang karena import border ada
    )
}

@Composable
fun AdminActivityItem(title: String, time: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).background(Color(0xFF333333), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, color = Color.White, fontWeight = FontWeight.Medium)
            Text(time, color = Color.Gray, fontSize = 12.sp)
        }
    }
}