# 📢 LaporTitik

![LaporTitik Logo](app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

**LaporTitik** adalah aplikasi mobile berbasis Android yang dirancang untuk memudahkan masyarakat dalam melaporkan masalah infrastruktur atau fasilitas umum (seperti jalan rusak, sampah menumpuk, lampu jalan mati) kepada pihak terkait atau relawan.

Aplikasi ini dibangun menggunakan **Kotlin** dan **Jetpack Compose** dengan **Appwrite** sebagai Backend-as-a-Service (BaaS).

---

## ✨ Fitur Utama

✅ **Authentication System**
* Login & Register yang aman.
* **Multi-Role System**: Mendukung 3 jenis pengguna:
    * **Citizen (Warga):** Pelapor masalah.
    * **Volunteer (Relawan):** Menindaklanjuti laporan.
    * **Admin:** Mengelola sistem.

✅ **Pelaporan Masalah**
* Input Judul, Deskripsi, dan Foto Bukti (Kamera/Galeri).
* **Geotagging:** Otomatis mendeteksi lokasi laporan menggunakan Google Maps.
* Status Laporan Real-time (Menunggu, Proses, Selesai).

✅ **Manajemen Data**
* **Draft Mode:** Simpan laporan secara offline sebelum dikirim.
* **Cloud Storage:** Foto tersimpan aman di Appwrite Storage.
* **Real-time Database:** Sinkronisasi data cepat.

---

## 🛠️ Teknologi yang Digunakan

* **Bahasa:** [Kotlin](https://kotlinlang.org/)
* **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material Design 3)
* **Backend:** [Appwrite](https://appwrite.io/) (Auth, Database, Storage)
* **Arsitektur:** MVVM (Model-View-ViewModel)
* **Asynchronous:** Coroutines & Flow
* **Navigasi:** Jetpack Navigation Compose
* **Peta:** Google Maps SDK for Android

---

## 🚀 Cara Menjalankan Project

Ikuti langkah ini untuk mencoba aplikasi di mesin lokal Anda:

1.  **Clone Repository**
    ```bash
    git clone [https://github.com/Rainyday404/LaporTitik.git](https://github.com/Rainyday404/LaporTitik.git)
    ```

2.  **Buka di Android Studio**
    * Pastikan menggunakan versi terbaru (Koala/Ladybug/Jellyfish).

3.  **Konfigurasi Appwrite**
    * Buat project baru di [Appwrite Console](https://cloud.appwrite.io/).
    * Update file `data/AppwriteClient.kt` dengan ID Anda:
    ```kotlin
    object AppwriteClient {
        // Sesuaikan Region (sgp = Singapore, cloud = Global)
        private const val ENDPOINT = "[https://sgp.cloud.appwrite.io/v1](https://sgp.cloud.appwrite.io/v1)" 
        private const val PROJECT_ID = "MASUKKAN_PROJECT_ID_ANDA"
    }
    ```

4.  **Build & Run**
    * Pastikan emulator/device memiliki koneksi internet.
    * Jalankan aplikasi.

---

## 👤 Author

**Ivan Dwika Bagaskara (Rain)**
* **NIM:** 230104040205
* **Role:** Lead Android Developer
* **Kampus:** UIN Antasari Banjarmasin
* **Program Studi:** Teknologi Informasi

---

> *"Code is like humor. When you have to explain it, it’s bad."*