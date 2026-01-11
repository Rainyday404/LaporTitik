package id.antasari.fpa_230104040205_laportitik.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Daftarkan Entity dan Converter di sini
@Database(entities = [ReportEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // Pintu akses ke DAO
    abstract fun reportDao(): ReportDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Cek apakah database sudah ada? Jika belum, buat baru.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "laportitik_database" // Nama file database di HP user
                )
                    .fallbackToDestructiveMigration() // Reset jika struktur berubah (aman buat dev)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}