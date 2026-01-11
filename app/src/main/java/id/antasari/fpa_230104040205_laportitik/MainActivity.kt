package id.antasari.fpa_230104040205_laportitik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable // <--- INI YANG HILANG TADI
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Import Screen & Data
import id.antasari.fpa_230104040205_laportitik.ui.screen.*
import id.antasari.fpa_230104040205_laportitik.ui.theme.LaporTitikTheme // Sesuaikan jika merah, ganti MaterialTheme saja
import id.antasari.fpa_230104040205_laportitik.data.AppwriteClient
import id.antasari.fpa_230104040205_laportitik.data.AuthRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Nyalakan Database Offline
        ReportRepository.init(applicationContext)

        // 2. Nyalakan Database Online (Appwrite)
        AppwriteClient.init(applicationContext)

        // 3. Cek User Login
        UserRepository.refreshUserData()

        setContent {
            // Gunakan MaterialTheme bawaan jika LaporTitikTheme error
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

// --- PERBAIKAN DI SINI: TAMBAHKAN @Composable ---
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Cek status login saat aplikasi mulai untuk menentukan halaman awal
    // (Opsional: Bisa dibuat lebih canggih nanti, sementara default ke onboarding)

    NavHost(navController = navController, startDestination = "onboarding") {

        // Auth
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }

        // Fitur Utama
        composable("home") { HomeScreen(navController) }
        composable("report") { ReportScreen(navController) }
        composable("draft") { DraftScreen(navController) }
        composable("map") { MapScreen(navController) }
        composable("profile") { ProfileScreen(navController) }

        // Detail Laporan
        composable(
            route = "detail/{reportId}",
            arguments = listOf(navArgument("reportId") { type = NavType.LongType })
        ) { backStackEntry ->
            val reportId = backStackEntry.arguments?.getLong("reportId") ?: 0L
            DetailScreen(navController, reportId)
        }

        // Fitur Lain
        composable("notification") { NotificationScreen(navController) }
        composable("volunteer") { VolunteerScreen(navController) }
        composable("admin") { AdminScreen(navController) }
    }
}