# Sistem Manajemen dan Reservasi Hotel Qta

Aplikasi Java Console (CLI) untuk Tugas Proyek Pemrograman Berorientasi Objek (PBO). Implementasi mengikuti **Class Diagram Tahap 1** (18 class/interface domain + `Main`).

## Fitur
- **Tamu**: registrasi, login, cari kamar, buat reservasi (bisa >1 kamar + layanan tambahan), bayar (Tunai / Kartu / E-Wallet), lihat & batalkan reservasi (dengan refund), poin loyalitas.
- **Resepsionis**: lihat semua reservasi, Check-In, Check-Out (+10 poin loyalitas untuk tamu).
- **Admin**: kelola kamar (tambah, ubah harga, ubah status Tersedia/Perawatan) dan lihat laporan.

## Konsep PBO
| Konsep | Penerapan |
|---|---|
| Encapsulation | Semua atribut `private`, diakses lewat getter/setter |
| Inheritance | `Pengguna` → `Tamu`, `Staf` → `Resepsionis`, `Admin`; `Kamar` → `KamarStandard/Deluxe/Suite`; `Pembayaran` → `PembayaranTunai/Kartu/EWallet` |
| Polymorphism | `getPeran()`, `hitungHarga()`, `getFasilitas()`, `proses()`, `getMetode()` di-override per subclass |
| Abstraction | abstract class `Pengguna`, `Staf`, `Kamar`, `Pembayaran` |
| Interface | `IPembayaran` (diimplementasi `Pembayaran`), `IDapatDibatalkan` (diimplementasi `Reservasi`) |
| Association / Composition | `Tamu`→`Reservasi` (0..*), `Reservasi`→`Kamar` (1..*), `Reservasi`◆`LayananTambahan` (0..*), `Reservasi`→`Pembayaran` (0..1) |
| Dependency | `Resepsionis` ..> `Reservasi` |

## Struktur
```
src/
├── Main.java                 (menu CLI, login berbasis peran)
├── data/HotelData.java       (penyimpanan in-memory + utilitas; bukan class domain)
├── user/        Pengguna, Tamu, Staf, Resepsionis, Admin
├── kamar/       Kamar, KamarStandard, KamarDeluxe, KamarSuite
├── reservasi/   Reservasi, LayananTambahan, IDapatDibatalkan
└── pembayaran/  IPembayaran, Pembayaran, PembayaranTunai, PembayaranKartu, PembayaranEWallet
```

## Akun Demo
| Peran | Email | Password |
|---|---|---|
| Admin | amirah@hotel.com / kiara@hotel.com | admin123 |
| Resepsionis | rosita@hotel.com / lily@hotel.com / kayla@hotel.com | staf123 |
| Tamu | budi@mail.com | budi123 |

Tamu baru dapat mendaftar lewat menu **Registrasi**. Format tanggal: `dd-MM-yyyy`. Nomor kartu: 16 digit angka.

## Cara Menjalankan
Prasyarat: JDK 21.

```
javac -d bin src/Main.java src/*/*.java
java -cp bin Main
```
(atau buka `src/Main.java` di VS Code lalu Run)

## Aturan Bisnis
- Harga: Standard = harga dasar × malam; Deluxe + Rp150.000/malam bila view laut; Suite = harga dasar × 1,25 × malam.
- Total = jumlah harga semua kamar + Σ(harga × jumlah) layanan.
- Refund (hanya reservasi yang sudah dibayar): H-3 atau lebih = 100%, H-1 s.d. H-2 = 50%, selebihnya 0%.
- Status reservasi: Menunggu Pembayaran → Dikonfirmasi → Check-In → Selesai (atau Dibatalkan).
- Status kamar: Tersedia → Dipesan → Terisi → Tersedia (atau Perawatan oleh Admin).

## Kelompok 5
* 21120124140125 Fina Aprilia
* 21120124120043 Rosita Dwi Ahyani
* 21120124140162 Kiara Wulan Turnauli Manalu
* 21120124130044 Kayla Radhiya Putri Ghaisani
* 21120124130068 Amirah Rahmah Wijayanti
