package id.antasari.fpa_230104040205_laportitik.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import id.antasari.fpa_230104040205_laportitik.ui.theme.OrangePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolunteerScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                TopAppBar(
                    title = {
                        Column {
                            Text("Priority Queue", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("San Francisco, CA", fontSize = 12.sp, color = Color.Gray) // Lokasi Statis dulu
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )

                // STATISTIK HEADER (Critical, Nearby, Assigned)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatCard("Critical", "12", Color(0xFFFFEBEE), Color.Red)
                    StatCard("Nearby", "5", Color(0xFFE3F2FD), Color(0xFF2196F3))
                    StatCard("Assigned", "3", Color(0xFFE8F5E9), Color(0xFF4CAF50))
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {
            // --- SECTION 1: HIGH PRIORITY ---
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("High Priority Incidents", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Score > 8.0", color = Color.Gray, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                // KARTU KRITIS (Besar)
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Surface(color = Color.Red, shape = RoundedCornerShape(4.dp)) {
                                Text("CRITICAL", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                            Text("9.2 SCORE", color = Color.Red, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Structural Collapse", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("0.5 mi • 12m ago", color = Color.Gray)

                        Spacer(modifier = Modifier.height(16.dp))
                        // Info Grid Kecil (S9, C8, V7)
                        Row {
                            ScoreBadge("S9", "Severity")
                            Spacer(modifier = Modifier.width(12.dp))
                            ScoreBadge("C8", "Casualty")
                            Spacer(modifier = Modifier.width(12.dp))
                            ScoreBadge("V7", "Vulnerability")
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { /* Handle Response */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("RESPOND NOW", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // --- SECTION 2: STANDARD QUEUE ---
            item {
                Text("Standard Queue", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                StandardCard("Urban Flooding", "1.2 mi away", "7.8", "Updated 2h ago")
            }
            item {
                StandardCard("Power Outage", "5.0 mi away", "6.4", "Updated 5h ago")
            }
        }
    }
}

@Composable
fun StatCard(label: String, count: String, bgColor: Color, textColor: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.width(100.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(count, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(label, fontSize = 12.sp, color = textColor)
        }
    }
}

@Composable
fun ScoreBadge(code: String, label: String) {
    Column {
        Text(code, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(label, fontSize = 10.sp, color = Color.Gray)
    }
}

@Composable
fun StandardCard(title: String, dist: String, score: String, time: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(dist, color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(time, color = Color.LightGray, fontSize = 10.sp)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(score, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = OrangePrimary)
                Text("SCORE", fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}