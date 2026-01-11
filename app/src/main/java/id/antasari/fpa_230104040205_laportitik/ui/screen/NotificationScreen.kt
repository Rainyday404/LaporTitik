package id.antasari.fpa_230104040205_laportitik.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import id.antasari.fpa_230104040205_laportitik.ui.theme.OrangePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                TopAppBar(
                    title = { Text("Notifikasi", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        TextButton(onClick = { /* Tandai dibaca */ }) {
                            Text("Tandai dibaca", color = OrangePrimary, fontWeight = FontWeight.Bold)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
                // Filter Tabs
                Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    FilterChip(selected = true, label = "Semua", color = OrangePrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterChip(selected = false, label = "Penting", color = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterChip(selected = false, label = "Laporan Saya", color = Color.Gray)
                }
                Divider(color = Color.LightGray.copy(alpha = 0.3f))
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF9F9F9)) // Background abu sangat muda
                .padding(16.dp)
        ) {
            // --- SECTION: HARI INI ---
            item { SectionHeader("HARI INI") }

            item {
                NotificationCard(
                    title = "Laporan Diverifikasi",
                    desc = "Laporan banjir di Jalan Melati telah diverifikasi oleh tim lapangan BPBD.",
                    time = "15 menit yang lalu",
                    icon = Icons.Default.CheckCircle,
                    iconColor = Color.White,
                    iconBgColor = OrangePrimary
                )
            }

            item {
                NotificationCard(
                    title = "Bantuan Medis Dikirim",
                    desc = "Unit medis sedang menuju lokasi Anda. Estimasi tiba: 10 menit.",
                    time = "45 menit yang lalu",
                    icon = Icons.Default.LocalHospital,
                    iconColor = Color.White,
                    iconBgColor = Color(0xFF2196F3), // Biru
                    actionText = "Lacak Unit >"
                )
            }

            item {
                NotificationCard(
                    title = "Update Foto Lokasi",
                    desc = "Tim relawan mengunggah foto terbaru kondisi di Posko Pengungsian A.",
                    time = "2 jam lalu",
                    icon = Icons.Default.CameraAlt,
                    iconColor = Color.White,
                    iconBgColor = Color.DarkGray
                )
            }

            // --- SECTION: KEMARIN ---
            item { SectionHeader("KEMARIN") }

            item {
                NotificationCard(
                    title = "Info Cuaca Ekstrem",
                    desc = "BMKG memprediksi hujan lebat disertai angin kencang sore ini.",
                    time = "Kemarin, 14:00",
                    icon = Icons.Default.Cloud,
                    iconColor = Color.White,
                    iconBgColor = Color(0xFF607D8B)
                )
            }
        }
    }
}

// Komponen Filter Chip Kecil
@Composable
fun FilterChip(selected: Boolean, label: String, color: Color) {
    Surface(
        color = if (selected) color.copy(alpha = 0.1f) else Color.Transparent,
        shape = RoundedCornerShape(20.dp),
        border = if (!selected) androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray) else null,
        modifier = Modifier.height(32.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(label, color = if (selected) color else Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// Header Section (HARI INI, KEMARIN)
@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}

// Kartu Notifikasi
@Composable
fun NotificationCard(
    title: String,
    desc: String,
    time: String,
    icon: ImageVector,
    iconColor: Color,
    iconBgColor: Color,
    actionText: String? = null
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon Bulat
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(iconBgColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Konten Teks
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    // Titik Merah (Unread indicator - Opsional)
                    if (title == "Laporan Diverifikasi") {
                        Box(modifier = Modifier.size(8.dp).background(OrangePrimary, CircleShape))
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(desc, fontSize = 12.sp, color = Color.Gray, lineHeight = 16.sp)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(time, fontSize = 11.sp, color = Color.LightGray)
                    if (actionText != null) {
                        Text(actionText, fontSize = 12.sp, color = OrangePrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}