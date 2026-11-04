# Sistem Manajemen dan Reservasi Hotel Qta

Sistem Manajemen dan Reservasi Hotel Qta adalah aplikasi berbasis Java Console (CLI) yang dibangun untuk memenuhi Tugas Pemrograman Berbasis Objek (PBO). Aplikasi ini mensimulasikan alur operasional hotel secara interaktif, mulai dari pengelolaan kamar oleh Admin, pendaftaran tamu, reservasi kamar, transaksi pembayaran berbagai metode, hingga operasional Check-In dan Check-Out serta sistem poin loyalitas oleh Resepsionis.

---

## Konsep PBO yang Diimplementasikan

Aplikasi ini menerapkan pilar utama Pemrograman Berbasis Objek:
* Encapsulation: Penggunaan access modifier private pada atribut dengan penyediaan getter dan setter.
* Inheritance: Class Tamu, Staf, Resepsionis, dan Admin mewarisi kelas induk Pengguna. Begitu pula dengan turunan kelas Kamar dan Pembayaran.
* Polymorphism (Method Overriding): Perhitungan harga kamar yang fleksibel (hitungHarga()) dan proses pembayaran (proses()) disesuaikan pada masing-masing sub-class.
* Abstraction dan Interface: Implementasi abstract class (Pengguna, Staf, Kamar, Pembayaran) serta interface (IPembayaran, IDapatDibatalkan).

---

## Prasyarat Sistem & Download

Sebelum menjalankan aplikasi, pastikan komputer sudah terpasang prasyarat berikut:
1. Java Development Kit (JDK 21 LTS):
   * Download Eclipse Temurin JDK 21 (Adoptium): https://adoptium.net/temurin/releases/?version=21
   * Download Oracle JDK 21: https://www.oracle.com/java/technologies/downloads/#java21
2. Text Editor / IDE: Visual Studio Code (dengan extension Java) atau IDE lain seperti IntelliJ IDEA / Eclipse.
3. Git: Untuk keperluan version control.

---

## Panduan Cara Menjalankan Aplikasi

### Cara 1: Menggunakan Visual Studio Code
1. Clone repositori ke komputer lokal:
   git clone https://github.com/username-kamu/HotelQta.git
2. Buka folder proyek di Visual Studio Code.
3. Buka file src/Main.java.
4. Klik tombol Run (ikon Play di sudut kanan atas VS Code) atau tekan tombol F5.
5. Jalankan interaksi program langsung melalui panel Terminal VS Code.

### Cara 2: Menggunakan Terminal / Command Prompt
1. Buka Terminal / CMD dan masuk ke direktori proyek (HotelQta/).
2. Kompilasi seluruh kode Java:
   javac -d bin src/Main.java src/*/*.java
3. Jalankan aplikasi:
   java -cp bin Main

---

## Kelompok 5
* 21120124140125	Fina Aprilia
* 21120124120043	Rosita Dwi Ahyani
* 21120124140162	Kiara Wulan Turnauli Manalu
* 21120124130044    Kayla Radhiya Putri Ghaisani
* 21120124130068	Amirah Rahmah Wijayanti