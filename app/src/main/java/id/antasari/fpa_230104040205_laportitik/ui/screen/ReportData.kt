package id.antasari.fpa_230104040205_laportitik.ui.screen

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import id.antasari.fpa_230104040205_laportitik.data.AppDatabase
import id.antasari.fpa_230104040205_laportitik.data.ReportEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Repository Global (Penghubung UI <-> Database)
object ReportRepository {
    // List yang akan dibaca oleh DraftScreen (Sekarang pakai Entity dari Database)
    var drafts = mutableStateListOf<ReportEntity>()

    // Database & DAO (Akan diisi saat aplikasi start)
    private var database: AppDatabase? = null
    private val scope = CoroutineScope(Dispatchers.IO) // Thread untuk proses berat

    // Fungsi Inisialisasi (Dipanggil di MainActivity)
    fun init(context: Context) {
        database = AppDatabase.getDatabase(context)

        // Mulai pantau data dari database secara Real-time
        scope.launch {
            database?.reportDao()?.getAllReports()?.collect { listLaporan ->
                // Saat data di DB berubah, update list di UI
                CoroutineScope(Dispatchers.Main).launch {
                    drafts.clear()
                    drafts.addAll(listLaporan)
                }
            }
        }
    }

    // Fungsi Tambah Laporan (Simpan ke Database HP)
    fun addReport(title: String, desc: String, img: Bitmap?) {
        scope.launch {
            val newReport = ReportEntity(
                title = title,
                description = desc,
                image = img,
                time = "Baru Saja", // Nanti bisa diganti tanggal asli
                status = "Menunggu Sinyal"
            )
            database?.reportDao()?.insertReport(newReport)
        }
    }

    // Fungsi Ambil 1 Laporan (Untuk Detail Screen)
    fun getReportById(id: Long): ReportEntity? {
        return drafts.find { it.id == id }
    }
}