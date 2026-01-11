package id.antasari.fpa_230104040205_laportitik.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

// 1. TABEL DATABASE
@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // ID otomatis bertambah
    val title: String,
    val description: String,
    val image: Bitmap?, // Bitmap perlu dikonversi
    val time: String,
    val status: String,
    val isSynced: Boolean = false // Penanda status kirim ke server
)

// 2. CONVERTER (Bitmap <-> ByteArray)
// Database tidak bisa simpan gambar langsung, jadi harus diubah jadi kode byte.
class Converters {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) return null
        val outputStream = ByteArrayOutputStream()
        // Kompres gambar ke PNG kualitas 50% agar database tidak berat
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        if (byteArray == null) return null
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}