package id.antasari.fpa_230104040205_laportitik.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, reportId: Long) { // Menerima Parameter ID

    // Cari Data Asli dari Repository
    val report = ReportRepository.getReportById(reportId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Laporan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (report != null) {
            // TAMPILKAN DATA ASLI
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // 1. Gambar Asli
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    if (report.image != null) {
                        Image(
                            bitmap = report.image.asImageBitmap(),
                            contentDescription = "Foto Bukti",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ImageNotSupported, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                            Text("No Image", color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 2. Judul & Status
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = report.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFFFFE0B2) // Oranye Muda
                    ) {
                        Text(
                            text = "Pending",
                            color = Color(0xFFE65100),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Text(report.time, color = Color.Gray, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(24.dp))

                // 3. Deskripsi Asli
                Text("Deskripsi Kejadian", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = report.description,
                    color = Color.DarkGray,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 4. Timeline (Dummy Visual)
                Text("Status Terkini", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
                TimelineItem("Laporan Disimpan", "Menunggu koneksi internet...", true, true)
            }
        } else {
            // Jika Error (Data Hilang)
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Data tidak ditemukan!", color = Color.Gray)
            }
        }
    }
}

// Komponen Kecil untuk Timeline
@Composable
fun TimelineItem(title: String, desc: String, isActive: Boolean, isLast: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(24.dp)) {
            Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(if(isActive) Color(0xFFFF8A00) else Color.Gray))
            if (!isLast) Box(modifier = Modifier.width(2.dp).fillMaxHeight().background(Color.LightGray))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(desc, fontSize = 12.sp, color = Color.Gray)
        }
    }
}