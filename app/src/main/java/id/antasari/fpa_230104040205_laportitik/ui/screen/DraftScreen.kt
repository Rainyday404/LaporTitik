package id.antasari.fpa_230104040205_laportitik.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
// Import Entity dari Database
import id.antasari.fpa_230104040205_laportitik.data.ReportEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraftScreen(navController: NavController) {
    // Ambil Data Live dari Repository (Database)
    val draftList = ReportRepository.drafts

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Drafts", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (draftList.isNotEmpty()) {
                        IconButton(onClick = { /* TODO: Hapus Semua */ }) {
                            Icon(Icons.Default.Delete, contentDescription = "Clear", tint = Color.Red)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Header Offline
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFF9800))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("OFFLINE DATABASE", fontWeight = FontWeight.Bold, color = Color(0xFFFF9800))
                        Text("Data saved locally on device", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Pending Reports (${draftList.size})", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // List Data
            if (draftList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No drafts found", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(draftList) { report ->
                        DraftItemCard(report) { clickedId ->
                            navController.navigate("detail/$clickedId")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DraftItemCard(report: ReportEntity, onItemClick: (Long) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(report.id) }
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            // Tampilkan Gambar (Perlu konversi Bitmap?)
            if (report.image != null) {
                Image(
                    bitmap = report.image.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp))
                )
            } else {
                Box(modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)).background(Color.LightGray))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(report.title, fontWeight = FontWeight.Bold)
                Text(report.description, maxLines = 1, fontSize = 12.sp, color = Color.Gray)
                Text(report.status, fontSize = 12.sp, color = Color(0xFFE65100))
            }

            Icon(Icons.Default.CloudQueue, contentDescription = null, tint = Color.Gray)
        }
    }
}