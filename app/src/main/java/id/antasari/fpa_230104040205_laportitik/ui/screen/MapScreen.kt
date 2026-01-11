package id.antasari.fpa_230104040205_laportitik.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

// Import OSM
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(navController: NavController) {
    val context = LocalContext.current
    Configuration.getInstance().userAgentValue = context.packageName

    // Koordinat
    val banjarmasinPoint = GeoPoint(-3.316694, 114.590111)
    val disasterPoint = GeoPoint(-3.326, 114.600)

    Box(modifier = Modifier.fillMaxSize()) {

        // 1. LAYER PALING BAWAH: PETA (Full Screen)
        AndroidView(
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(14.5)
                    controller.setCenter(banjarmasinPoint)

                    // Marker Bencana
                    val marker1 = Marker(this)
                    marker1.position = disasterPoint
                    marker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    marker1.title = "Pohon Tumbang"
                    overlays.add(marker1)

                    // Marker Kampus
                    val marker2 = Marker(this)
                    marker2.position = banjarmasinPoint
                    marker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    marker2.title = "Posko UIN"
                    overlays.add(marker2)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // 2. LAYER ATAS: TOMBOL BACK FLOATING (Kiri Atas)
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 48.dp, start = 20.dp)
                .shadow(8.dp, CircleShape)
                .background(Color.White, CircleShape)
                .size(48.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
        }

        // 3. LAYER ATAS: JUDUL FLOATING (Tengah Atas)
        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Red, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Live Monitoring", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }

        // 4. LAYER ATAS: INFO CARD (Bawah)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        ) {
            // Tombol GPS Kecil
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                FloatingActionButton(
                    onClick = { /* Aksi recenter nanti */ },
                    containerColor = Color.White,
                    contentColor = Color.Blue,
                    shape = CircleShape,
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(Icons.Default.GpsFixed, contentDescription = "Recenter")
                }
            }

            // Kartu Detail Lokasi
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icon Lokasi Besar
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color(0xFFFFEBEE), CircleShape), // Merah muda
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Red, modifier = Modifier.size(28.dp))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Teks Detail
                    Column {
                        Text("Pohon Tumbang", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Jl. A. Yani Km 4.5", fontSize = 14.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Status: Menunggu Petugas", fontSize = 12.sp, color = Color(0xFFE65100), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}