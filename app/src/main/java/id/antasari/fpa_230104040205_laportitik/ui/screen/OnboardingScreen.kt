package id.antasari.fpa_230104040205_laportitik.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

// 1. Data Class diperbarui dengan parameter 'showArrow'
data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val buttonText: String,
    val showArrow: Boolean = false // Default false agar tidak error jika tidak diisi
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    // Warna Oranye Sesuai Mockup
    val BrandOrange = Color(0xFFFF8A00)

    // 2. Isi Halaman Sesuai PDF
    val pages = listOf(
        // PAGE 1: Splash Screen
        OnboardingPage(
            title = "DisasterMap",
            description = "Swipe to learn how we help\nyou save lives.",
            icon = Icons.Default.Place,
            buttonText = "Get Started",
            showArrow = true // Panah hanya di halaman 1
        ),
        // PAGE 2: Report in Seconds
        OnboardingPage(
            title = "Report in Seconds",
            description = "Instantly document infrastructure damage or emergencies. Snap a photo, add a location, and help your community stay safe.",
            icon = Icons.Default.AddLocationAlt,
            buttonText = "Next"
        ),
        // PAGE 3: Works Offline
        OnboardingPage(
            title = "Works Offline",
            description = "No signal? No problem. Your reports are saved securely on your device and will automatically send once a connection is restored.",
            icon = Icons.Default.SignalWifiOff,
            buttonText = "Next"
        ),
        // PAGE 4: Stay Informed
        OnboardingPage(
            title = "Stay Informed & Safe",
            description = "Receive real-time alerts and location-based warnings to keep you and your community out of harm's way.",
            icon = Icons.Default.NotificationsActive,
            buttonText = "Get Started"
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // --- HEADER: TOMBOL SKIP ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                // Muncul di Page 1, 2, 3
                if (pagerState.currentPage < pages.size - 1) {
                    TextButton(onClick = { navController.navigate("login") }) {
                        Text("Skip", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // --- KONTEN TENGAH (Pager) ---
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { position ->
                OnboardingPageContent(page = pages[position], brandColor = BrandOrange)
            }

            // --- FOOTER (Indikator & Tombol) ---
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // INDIKATOR DOTS
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    repeat(pages.size) { iteration ->
                        val isSelected = pagerState.currentPage == iteration
                        val color = if (isSelected) BrandOrange else Color(0xFFFFCC80)
                        val width = if (isSelected) 24.dp else 8.dp

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .height(8.dp)
                                .width(width)
                                .clip(RoundedCornerShape(4.dp))
                                .background(color)
                        )
                    }
                }

                // TOMBOL UTAMA
                Button(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < pages.size - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                navController.navigate("login") {
                                    popUpTo("onboarding") { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandOrange),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = pages[pagerState.currentPage].buttonText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        // Tampilkan panah jika showArrow bernilai true
                        if (pages[pagerState.currentPage].showArrow) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage, brandColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // ILUSTRASI (Shadow lembut & Background Putih)
        Box(
            modifier = Modifier
                .size(260.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            // Lingkaran Glow
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .shadow(elevation = 20.dp, shape = CircleShape, spotColor = brandColor)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = page.icon,
                    contentDescription = null,
                    tint = brandColor,
                    modifier = Modifier.size(64.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // JUDUL
        Text(
            text = page.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // DESKRIPSI
        Text(
            text = page.description,
            fontSize = 15.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}