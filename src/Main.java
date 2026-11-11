import data.HotelData;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import kamar.Kamar;
import pembayaran.Pembayaran;
import pembayaran.PembayaranEWallet;
import pembayaran.PembayaranKartu;
import pembayaran.PembayaranTunai;
import reservasi.LayananTambahan;
import reservasi.Reservasi;
import user.Admin;
import user.Pengguna;
import user.Resepsionis;
import user.Tamu;

/**
 * Titik masuk aplikasi (CLI). Alur: login -> menu sesuai peran (Tamu / Resepsionis / Admin).
 */
public class Main {
    private static final Scanner INPUT = HotelData.getScanner();

    private static final String[] NAMA_LAYANAN = {"Sarapan Pagi", "Laundry Express", "Antar-Jemput Bandara"};
    private static final double[] HARGA_LAYANAN = {75000, 50000, 150000};

    public static void main(String[] args) {
        HotelData.inisialisasi();

        boolean jalan = true;
        while (jalan) {
            System.out.println("\n=================================================");
            System.out.println("       SELAMAT DATANG DI SISTEM HOTEL QTA");
            System.out.println("=================================================");
            System.out.println("1. Login");
            System.out.println("2. Registrasi Tamu Baru");
            System.out.println("0. Keluar");
            switch (bacaInt("Pilih: ")) {
                case 1 -> login();
                case 2 -> registrasiTamu();
                case 0 -> jalan = false;
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan Sistem Hotel Qta.");
        INPUT.close();
    }

    // ================= LOGIN & REGISTRASI =================

    private static void login() {
        System.out.println("\n--- LOGIN ---");
        String email = bacaTeks("Email    : ");
        String password = bacaTeks("Password : ");

        Pengguna pengguna = HotelData.login(email, password);
        if (pengguna == null) {
            System.out.println("Login gagal: email atau password salah.");
            return;
        }
        System.out.println("Login berhasil. Selamat datang, " + pengguna.getNama() + " (" + pengguna.getPeran() + ")!");

        if (pengguna instanceof Tamu tamu) {
            menuTamu(tamu);
        } else if (pengguna instanceof Resepsionis resepsionis) {
            menuResepsionis(resepsionis);
        } else if (pengguna instanceof Admin admin) {
            menuAdmin(admin);
        }
    }

    private static void registrasiTamu() {
        System.out.println("\n--- REGISTRASI TAMU ---");
        String nama = bacaTeks("Nama Lengkap  : ");
        String email = bacaTeks("Email         : ");
        if (nama.isEmpty() || email.isEmpty() || !email.contains("@")) {
            System.out.println("Nama dan email (harus memuat '@') wajib diisi.");
            return;
        }
        if (HotelData.emailTerdaftar(email)) {
            System.out.println("Email sudah terdaftar.");
            return;
        }
        String noTelp = bacaTeks("No. Telepon   : ");
        String noIdentitas = bacaTeks("No. Identitas : ");
        String password = bacaTeks("Password      : ");
        if (password.length() < 4) {
            System.out.println("Password minimal 4 karakter.");
            return;
        }
        Tamu tamu = new Tamu(HotelData.generateIdTamu(), nama, email, noTelp, password, noIdentitas);
        HotelData.tambahPengguna(tamu);
        System.out.println("Registrasi berhasil. ID Anda: " + tamu.getId() + ". Silakan login.");
    }

    // ================= MENU TAMU =================

    private static void menuTamu(Tamu tamu) {
        boolean selesai = false;
        while (!selesai) {
            System.out.println("\n--- MENU TAMU: " + tamu.getNama() + " (Poin: " + tamu.getPoinLoyalitas() + ") ---");
            System.out.println("1. Cari kamar");
            System.out.println("2. Buat reservasi");
            System.out.println("3. Lihat reservasi saya");
            System.out.println("4. Bayar reservasi");
            System.out.println("5. Batalkan reservasi");
            System.out.println("0. Logout");
            switch (bacaInt("Pilih: ")) {
                case 1 -> cariKamar(tamu);
                case 2 -> buatReservasi(tamu);
                case 3 -> lihatReservasiTamu(tamu);
                case 4 -> bayarReservasiTamu(tamu);
                case 5 -> batalkanReservasiTamu(tamu);
                case 0 -> selesai = true;
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void cariKamar(Tamu tamu) {
        Date[] periode = bacaPeriode();
        List<Kamar> hasil = tamu.cariKamar(periode[0], periode[1]);
        tampilkanKamar(hasil);
    }

    private static void buatReservasi(Tamu tamu) {
        Date[] periode = bacaPeriode();
        List<Kamar> tersedia = tamu.cariKamar(periode[0], periode[1]);
        if (tersedia.isEmpty()) {
            System.out.println("Tidak ada kamar tersedia.");
            return;
        }
        tampilkanKamar(tersedia);

        // Pilih satu atau lebih kamar (relasi 1..*)
        List<Kamar> dipilih = new ArrayList<>();
        while (true) {
            int no = bacaInt("Pilih nomor urut kamar (0 = selesai memilih): ");
            if (no == 0) {
                if (dipilih.isEmpty()) {
                    System.out.println("Pilih minimal 1 kamar.");
                    continue;
                }
                break;
            }
            if (no < 1 || no > tersedia.size()) {
                System.out.println("Nomor urut tidak valid.");
                continue;
            }
            Kamar k = tersedia.get(no - 1);
            if (dipilih.contains(k)) {
                System.out.println("Kamar " + k.getNomorKamar() + " sudah dipilih.");
            } else {
                dipilih.add(k);
                System.out.println("Kamar " + k.getNomorKamar() + " ditambahkan.");
            }
        }

        Reservasi reservasi = tamu.buatReservasi();
        reservasi.setTglCheckIn(periode[0]);
        reservasi.setTglCheckOut(periode[1]);
        for (Kamar k : dipilih) {
            reservasi.tambahKamar(k);
        }

        // Layanan tambahan (komposisi 0..*)
        System.out.println("\n--- LAYANAN TAMBAHAN ---");
        while (true) {
            for (int i = 0; i < NAMA_LAYANAN.length; i++) {
                System.out.println((i + 1) + ". " + NAMA_LAYANAN[i] + " (" + HotelData.formatRupiah(HARGA_LAYANAN[i]) + ")");
            }
            int pil = bacaInt("Pilih layanan (0 = selesai): ");
            if (pil == 0) {
                break;
            }
            if (pil < 1 || pil > NAMA_LAYANAN.length) {
                System.out.println("Pilihan tidak valid.");
                continue;
            }
            int jumlah = bacaInt("Jumlah: ");
            if (jumlah < 1) {
                System.out.println("Jumlah minimal 1.");
                continue;
            }
            reservasi.tambahLayanan(new LayananTambahan(NAMA_LAYANAN[pil - 1], HARGA_LAYANAN[pil - 1], jumlah));
            System.out.println(NAMA_LAYANAN[pil - 1] + " x" + jumlah + " ditambahkan.");
        }

        System.out.println("\nReservasi dibuat:");
        cetakReservasi(reservasi);

        if (bacaTeks("Bayar sekarang? (y/n): ").equalsIgnoreCase("y")) {
            bayar(reservasi);
        } else {
            System.out.println("Reservasi disimpan dengan status 'Menunggu Pembayaran'. Bayar lewat menu 4.");
        }
    }

    private static void lihatReservasiTamu(Tamu tamu) {
        if (tamu.getDaftarReservasi().isEmpty()) {
            System.out.println("Anda belum memiliki reservasi.");
            return;
        }
        for (Reservasi r : tamu.getDaftarReservasi()) {
            cetakReservasi(r);
        }
    }

    private static void bayarReservasiTamu(Tamu tamu) {
        String id = bacaTeks("ID Reservasi yang akan dibayar: ");
        for (Reservasi r : tamu.getDaftarReservasi()) {
            if (r.getIdReservasi().equalsIgnoreCase(id)) {
                if (!r.getStatus().equalsIgnoreCase("Menunggu Pembayaran")) {
                    System.out.println("Reservasi ini tidak menunggu pembayaran (status: " + r.getStatus() + ").");
                } else {
                    bayar(r);
                }
                return;
            }
        }
        System.out.println("Reservasi tidak ditemukan di akun Anda.");
    }

    private static void batalkanReservasiTamu(Tamu tamu) {
        String id = bacaTeks("ID Reservasi yang akan dibatalkan: ");
        if (!tamu.batalkanReservasi(id)) {
            System.out.println("Pembatalan tidak berhasil (ID tidak ditemukan atau status tidak mengizinkan).");
        }
    }

    /** Memproses pembayaran (polymorphism: Pembayaran bisa Tunai/Kartu/EWallet). */
    private static void bayar(Reservasi reservasi) {
        for (Kamar k : reservasi.getDaftarKamar()) {
            if (!k.isTersedia()) {
                System.out.println("Kamar " + k.getNomorKamar() + " sudah tidak tersedia. Silakan batalkan reservasi ini.");
                return;
            }
        }

        System.out.println("\n--- METODE PEMBAYARAN ---");
        System.out.println("1. Tunai");
        System.out.println("2. Kartu Kredit / Debit");
        System.out.println("3. E-Wallet (GoPay/OVO/ShopeePay)");
        int pilih = bacaInt("Pilih metode (1-3): ");

        Pembayaran pembayaran;
        String idBayar = HotelData.generateIdPembayaran();
        switch (pilih) {
            case 1 -> pembayaran = new PembayaranTunai(idBayar);
            case 2 -> pembayaran = new PembayaranKartu(idBayar, bacaTeks("No. Kartu (16 digit): "));
            case 3 -> pembayaran = new PembayaranEWallet(idBayar, bacaTeks("Provider E-Wallet: "));
            default -> {
                System.out.println("Metode tidak valid. Pembayaran dibatalkan.");
                return;
            }
        }

        double total = reservasi.hitungTotal();
        if (pembayaran.proses(total)) {
            reservasi.setPembayaran(pembayaran);
            reservasi.setStatus("Dikonfirmasi");
            for (Kamar k : reservasi.getDaftarKamar()) {
                k.setStatus("Dipesan");
            }
            System.out.println("Reservasi " + reservasi.getIdReservasi() + " berhasil dikonfirmasi!");
        } else {
            System.out.println("Pembayaran gagal. Reservasi tetap 'Menunggu Pembayaran'.");
        }
    }

    // ================= MENU RESEPSIONIS =================

    private static void menuResepsionis(Resepsionis resepsionis) {
        boolean selesai = false;
        while (!selesai) {
            System.out.println("\n--- MENU RESEPSIONIS: " + resepsionis.getNama() + " ---");
            System.out.println("1. Lihat semua reservasi");
            System.out.println("2. Proses Check-In");
            System.out.println("3. Proses Check-Out");
            System.out.println("0. Logout");
            switch (bacaInt("Pilih: ")) {
                case 1 -> {
                    if (HotelData.getDaftarReservasi().isEmpty()) {
                        System.out.println("Belum ada reservasi.");
                    }
                    for (Reservasi r : HotelData.getDaftarReservasi()) {
                        cetakReservasi(r);
                    }
                }
                case 2 -> resepsionis.checkIn(bacaTeks("ID Reservasi: "));
                case 3 -> resepsionis.checkOut(bacaTeks("ID Reservasi: "));
                case 0 -> selesai = true;
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // ================= MENU ADMIN =================

    private static void menuAdmin(Admin admin) {
        boolean selesai = false;
        while (!selesai) {
            System.out.println("\n--- MENU ADMIN: " + admin.getNama() + " ---");
            System.out.println("1. Kelola kamar");
            System.out.println("2. Lihat laporan");
            System.out.println("0. Logout");
            switch (bacaInt("Pilih: ")) {
                case 1 -> admin.kelolaKamar();
                case 2 -> admin.lihatLaporan();
                case 0 -> selesai = true;
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // ================= TAMPILAN & INPUT =================

    private static void tampilkanKamar(List<Kamar> daftar) {
        if (daftar.isEmpty()) {
            System.out.println("Tidak ada kamar tersedia untuk periode tersebut.");
            return;
        }
        System.out.println();
        for (int i = 0; i < daftar.size(); i++) {
            Kamar k = daftar.get(i);
            System.out.println((i + 1) + ". Kamar " + k.getNomorKamar() + " ["
                    + k.getClass().getSimpleName().replace("Kamar", "") + "] - "
                    + HotelData.formatRupiah(k.getHargaDasar()) + "/malam - kapasitas " + k.getKapasitas()
                    + " orang\n   Fasilitas: " + k.getFasilitas());
        }
    }

    private static void cetakReservasi(Reservasi r) {
        System.out.println("-------------------------------------------------");
        System.out.println("ID Reservasi : " + r.getIdReservasi() + "   [" + r.getStatus() + "]");
        System.out.println("Tamu         : " + r.getTamu().getNama());
        System.out.println("Check-In     : " + HotelData.formatTanggal(r.getTglCheckIn()));
        System.out.println("Check-Out    : " + HotelData.formatTanggal(r.getTglCheckOut()) + " (" + r.getLamaMalam() + " malam)");
        for (Kamar k : r.getDaftarKamar()) {
            System.out.println("Kamar        : " + k.getNomorKamar() + " ("
                    + k.getClass().getSimpleName().replace("Kamar", "") + ") "
                    + HotelData.formatRupiah(k.hitungHarga(r.getLamaMalam())));
        }
        for (LayananTambahan l : r.getDaftarLayanan()) {
            System.out.println("Layanan      : " + l.getNamaLayanan() + " x" + l.getJumlah() + " "
                    + HotelData.formatRupiah(l.hitungSubtotal()));
        }
        System.out.println("TOTAL BIAYA  : " + HotelData.formatRupiah(r.hitungTotal()));
        if (r.getPembayaran() != null) {
            System.out.println("Pembayaran   : " + r.getPembayaran().getMetode() + " (" + r.getPembayaran().getStatus() + ")");
        }
        System.out.println("-------------------------------------------------");
    }

    /** Meminta tanggal masuk & keluar; mengulang sampai valid. */
    private static Date[] bacaPeriode() {
        while (true) {
            Date masuk = bacaTanggal("Tanggal check-in  (dd-MM-yyyy): ");
            Date keluar = bacaTanggal("Tanggal check-out (dd-MM-yyyy): ");
            if (HotelData.keLocalDate(masuk).isBefore(LocalDate.now())) {
                System.out.println("Tanggal check-in tidak boleh sebelum hari ini.");
            } else if (!keluar.after(masuk)) {
                System.out.println("Tanggal check-out harus setelah check-in.");
            } else {
                return new Date[] {masuk, keluar};
            }
        }
    }

    private static Date bacaTanggal(String prompt) {
        while (true) {
            Date d = HotelData.parseTanggal(bacaTeks(prompt));
            if (d != null) {
                return d;
            }
            System.out.println("Format tanggal salah. Contoh: 25-12-2026");
        }
    }

    private static String bacaTeks(String prompt) {
        System.out.print(prompt);
        return INPUT.nextLine().trim();
    }

    private static int bacaInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(bacaTeks(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }
}
