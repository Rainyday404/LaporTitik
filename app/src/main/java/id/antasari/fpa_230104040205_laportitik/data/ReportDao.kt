package id.antasari.fpa_230104040205_laportitik.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    // Ambil semua data secara Live (Flow), urutkan dari yang terbaru
    @Query("SELECT * FROM reports ORDER BY id DESC")
    fun getAllReports(): Flow<List<ReportEntity>>

    // Ambil satu data spesifik berdasarkan ID (untuk Detail Screen)
    @Query("SELECT * FROM reports WHERE id = :id")
    suspend fun getReportById(id: Long): ReportEntity?

    // Simpan Laporan (Jika ID sama, timpa saja/Replace)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ReportEntity)

    // Hapus Semua Data (Opsional, misal tombol reset)
    @Query("DELETE FROM reports")
    suspend fun clearReports()
}