import java.util.Scanner;
import user.*;
import kamar.*;
import pembayaran.*;
import reservasi.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=================================================");
        System.out.println("       SELAMAT DATANG DI SISTEM HOTEL QTA       ");
        System.out.println("=================================================\n");

        // 1. Pilih Admin Bertugas & Kelola Kamar
        System.out.println("--- MODUL ADMIN (PENGELOLAAN KAMAR) ---");
        System.out.println("1. Amirah");
        System.out.println("2. Kiara");
        System.out.print("Pilih Admin Bertugas (1-2): ");
        int pilihAdmin = input.nextInt();
        input.nextLine(); // Consume newline

        Admin admin = null;
        if (pilihAdmin == 2) {
            admin = new Admin("A002", "Kiara", "kiara@hotel.com", "08111111112", "admin123", "NIP005", 6000000);
        } else {
            admin = new Admin("A001", "Amirah", "admin@hotel.com", "08111111111", "admin123", "NIP004", 6000000);
        }

        // Contoh Kamar yang dikelola Admin
        KamarDeluxe kamar101 = new KamarDeluxe("101", 500000, 2, true);

        System.out.print("Apakah Admin (" + admin.getNama() + ") ingin mengubah harga dasar Kamar 101 (Rp500.000)? (y/n): ");
        if (input.nextLine().equalsIgnoreCase("y")) {
            System.out.print("Masukkan harga baru untuk Kamar 101: Rp");
            double hargaBaru = input.nextDouble();
            input.nextLine(); // Consume newline
            admin.kelolaKamar(kamar101, hargaBaru);
        } else {
            admin.kelolaKamar(kamar101, 500000);
        }

        // 2. Pilih Resepsionis Bertugas
        System.out.println("\n--- PILIH RESEPSIONIS BERTUGAS ---");
        System.out.println("1. Rosita");
        System.out.println("2. Lily");
        System.out.println("3. Kayla");
        System.out.print("Pilihan Resepsionis (1-3): ");
        int pilihResepsionis = input.nextInt();
        input.nextLine(); // Consume newline

        Resepsionis resepsionis = null;
        switch (pilihResepsionis) {
            case 1:
                resepsionis = new Resepsionis("S001", "Rosita", "rosita@hotel.com", "08987654321", "staf123", "NIP001", 4500000);
                break;
            case 2:
                resepsionis = new Resepsionis("S002", "Lily", "lily@hotel.com", "08987654322", "staf123", "NIP002", 4500000);
                break;
            case 3:
                resepsionis = new Resepsionis("S003", "Kayla", "kayla@hotel.com", "08987654323", "staf123", "NIP003", 4500000);
                break;
            default:
                resepsionis = new Resepsionis("S001", "Rosita", "rosita@hotel.com", "08987654321", "staf123", "NIP001", 4500000);
                break;
        }

        // 3. Registrasi Tamu
        System.out.println("\n--- FORM REGISTRASI TAMU ---");
        System.out.print("Masukkan ID Tamu (contoh: U001) : ");
        String idTamu = input.nextLine();
        System.out.print("Masukkan Nama Lengkap         : ");
        String namaTamu = input.nextLine();
        System.out.print("Masukkan Email                : ");
        String emailTamu = input.nextLine();
        System.out.print("Masukkan No. Telepon          : ");
        String noTelpTamu = input.nextLine();

        Tamu tamu = new Tamu(idTamu, namaTamu, emailTamu, noTelpTamu, "pass123");

        // 4. Pemilihan Tipe Kamar
        System.out.println("\n--- PILIH TIPE KAMAR ---");
        System.out.println("1. Kamar Deluxe 101 (Harga: Rp" + kamar101.getHargaDasar() + " / malam - View Laut)");
        System.out.println("2. Kamar Standard 102 (Harga: Rp350.000 / malam)");
        System.out.println("3. Kamar Suite 301 (Harga: Rp800.000 / malam)");
        System.out.print("Pilihan Anda (1-3): ");
        int pilihanKamar = input.nextInt();

        Kamar kamarPilihan = null;
        switch (pilihanKamar) {
            case 1:
                kamarPilihan = kamar101;
                break;
            case 2:
                kamarPilihan = new KamarStandard("102", 350000, 2);
                break;
            case 3:
                kamarPilihan = new KamarSuite("301", 800000, 4, true);
                break;
            default:
                kamarPilihan = kamar101;
                break;
        }

        System.out.print("Masukkan Lama Menginap (malam): ");
        int lamaMalam = input.nextInt();
        input.nextLine(); // Consume newline

        // Buat Objek Reservasi
        Reservasi reservasi = new Reservasi("RES-" + System.currentTimeMillis() % 10000, tamu, kamarPilihan, "2026-11-10", lamaMalam);

        // 5. Tambah Layanan Tambahan
        System.out.println("\n--- LAYANAN TAMBAHAN ---");
        System.out.print("Apakah ingin menambah Sarapan Pagi (Rp75.000)? (y/n): ");
        if (input.nextLine().equalsIgnoreCase("y")) {
            reservasi.tambahLayanan("Sarapan Pagi", 75000);
        }

        System.out.print("Apakah ingin menambah Laundry Express (Rp50.000)? (y/n): ");
        if (input.nextLine().equalsIgnoreCase("y")) {
            reservasi.tambahLayanan("Laundry Express", 50000);
        }

        // Ringkasan Tagihan
        System.out.println("\n-------------------------------------------------");
        System.out.println("Fasilitas Kamar : " + kamarPilihan.getFasilitas());
        System.out.println("TOTAL BIAYA     : Rp" + reservasi.hitungTotal());
        System.out.println("-------------------------------------------------");

        // 6. Proses Pembayaran
        System.out.println("\n--- METODE PEMBAYARAN ---");
        System.out.println("1. Tunai");
        System.out.println("2. Kartu Kredit / Debit");
        System.out.println("3. E-Wallet (GoPay/OVO)");
        System.out.print("Pilih Metode (1-3): ");
        int pilihanBayar = input.nextInt();
        input.nextLine(); // Consume newline

        Pembayaran pembayaran = null;
        if (pilihanBayar == 1) {
            pembayaran = new PembayaranTunai("PAY-" + System.currentTimeMillis() % 1000, "2026-11-04");
        } else if (pilihanBayar == 2) {
            System.out.print("Masukkan No. Kartu: ");
            String noKartu = input.nextLine();
            pembayaran = new PembayaranKartu("PAY-" + System.currentTimeMillis() % 1000, "2026-11-04", noKartu);
        } else {
            System.out.print("Masukkan E-Wallet (GoPay/OVO/ShopeePay): ");
            String provider = input.nextLine();
            pembayaran = new PembayaranEWallet("PAY-" + System.currentTimeMillis() % 1000, "2026-11-04", provider);
        }

        System.out.println();
        reservasi.konfirmasiPembayaran(pembayaran);

        // 7. Operasional Resepsionis
        System.out.println("\n--- OPERASIONAL RESEPSIONIS (" + resepsionis.getNama().toUpperCase() + ") ---");
        System.out.print("Proses Check-In Tamu Sekarang? (y/n): ");
        if (input.nextLine().equalsIgnoreCase("y")) {
            resepsionis.prosesCheckIn(reservasi);
            System.out.println("Status Kamar : " + kamarPilihan.getStatus());

            System.out.print("\nProses Check-Out Tamu Sekarang? (y/n): ");
            if (input.nextLine().equalsIgnoreCase("y")) {
                resepsionis.prosesCheckOut(reservasi);
                System.out.println("Status Kamar           : " + kamarPilihan.getStatus());
                System.out.println("Poin Loyalitas " + tamu.getNama() + " : " + tamu.getPoinLoyalitas() + " Poin");
            }
        }

        System.out.println("\n=================================================");
        System.out.println("    TRANSAKSI SELESAI - TERIMA KASIH (HOTEL QTA) ");
        System.out.println("=================================================");

        input.close();
    }
}