package id.antasari.fpa_230104040205_laportitik.ui.screen

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import id.antasari.fpa_230104040205_laportitik.data.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object UserRepository {
    var userName by mutableStateOf("Guest")
    var userEmail by mutableStateOf("")
    var userRole by mutableStateOf("Citizen") // <-- Variable Role
    var profileImage by mutableStateOf<Bitmap?>(null)

    // Ambil data terbaru dari Server Appwrite
    fun refreshUserData() {
        CoroutineScope(Dispatchers.Main).launch {
            val user = AuthRepository.getUser()
            if (user != null) {
                userName = user["name"] ?: "Pengguna"
                userEmail = user["email"] ?: ""
                userRole = user["role"] ?: "Citizen" // <-- Simpan role di sini
            } else {
                clearUserData()
            }
        }
    }

    // Reset saat Logout
    fun clearUserData() {
        userName = "Guest"
        userEmail = ""
        userRole = "Citizen"
        profileImage = null
    }
}