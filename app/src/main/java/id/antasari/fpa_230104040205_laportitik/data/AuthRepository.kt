package id.antasari.fpa_230104040205_laportitik.data

import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AuthRepository {

    suspend fun register(name: String, email: String, pass: String, role: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                // Hapus sesi lama jika ada (Silent/Diam-diam)
                try { AppwriteClient.account.deleteSession("current") } catch (e: Exception) { }

                // 1. Buat Akun
                AppwriteClient.account.create(
                    userId = ID.unique(),
                    email = email,
                    password = pass,
                    name = name
                )

                // 2. Login
                AppwriteClient.account.createEmailPasswordSession(
                    email = email,
                    password = pass
                )

                // 3. Simpan Role
                AppwriteClient.account.updatePrefs(mapOf("role" to role))

                Result.success("Register Berhasil!")
            } catch (e: AppwriteException) {
                // Tampilkan pesan error asli dari Appwrite
                Result.failure(e)
            }
        }
    }

    // ... (Fungsi login, getUser, logout biarkan seperti sebelumnya) ...
    // Pastikan fungsi Login juga punya "deleteSession" di baris pertamanya.
    suspend fun login(email: String, pass: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                try { AppwriteClient.account.deleteSession("current") } catch (e: Exception) { }

                AppwriteClient.account.createEmailPasswordSession(
                    email = email,
                    password = pass
                )
                Result.success("Login Berhasil!")
            } catch (e: AppwriteException) {
                Result.failure(e)
            }
        }
    }

    suspend fun getUser(): Map<String, String>? {
        return withContext(Dispatchers.IO) {
            try {
                val user = AppwriteClient.account.get()
                val role = user.prefs.data["role"] as? String ?: "Citizen"
                mapOf("name" to user.name, "email" to user.email, "role" to role)
            } catch (e: Exception) { null }
        }
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            try { AppwriteClient.account.deleteSession("current") } catch (e: Exception) { }
        }
    }
}