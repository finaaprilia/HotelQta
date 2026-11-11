package user;

import data.HotelData;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import kamar.Kamar;
import kamar.KamarDeluxe;
import kamar.KamarStandard;
import kamar.KamarSuite;
import reservasi.Reservasi;

public class Admin extends Staf {

    public Admin(String id, String nama, String email, String noTelp, String password, String nip, double gaji) {
        super(id, nama, email, noTelp, password, nip, gaji);
    }

    /** Menu pengelolaan kamar: lihat, tambah, ubah harga, ubah status. */
    public void kelolaKamar() {
        Scanner in = HotelData.getScanner();
        boolean selesai = false;
        while (!selesai) {
            System.out.println("\n--- KELOLA KAMAR (Admin " + getNama() + ") ---");
            System.out.println("1. Lihat daftar kamar");
            System.out.println("2. Tambah kamar baru");
            System.out.println("3. Ubah harga dasar kamar");
            System.out.println("4. Ubah status kamar (Tersedia / Perawatan)");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");
            switch (in.nextLine().trim()) {
                case "1" -> tampilkanKamar();
                case "2" -> tambahKamar(in);
                case "3" -> ubahHarga(in);
                case "4" -> ubahStatus(in);
                case "0" -> selesai = true;
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    /** Menampilkan laporan ringkas reservasi, pendapatan, dan kondisi kamar. */
    public void lihatLaporan() {
        System.out.println("\n=========== LAPORAN HOTEL QTA ===========");

        Map<String, Integer> perStatus = new LinkedHashMap<>();
        double pendapatan = 0;
        for (Reservasi r : HotelData.getDaftarReservasi()) {
            perStatus.merge(r.getStatus(), 1, Integer::sum);
            String s = r.getStatus();
            if (s.equalsIgnoreCase("Dikonfirmasi") || s.equalsIgnoreCase("Check-In") || s.equalsIgnoreCase("Selesai")) {
                pendapatan += r.hitungTotal();
            }
        }
        System.out.println("Total reservasi : " + HotelData.getDaftarReservasi().size());
        for (Map.Entry<String, Integer> e : perStatus.entrySet()) {
            System.out.println("  - " + e.getKey() + " : " + e.getValue());
        }
        System.out.println("Pendapatan      : " + HotelData.formatRupiah(pendapatan));

        Map<String, Integer> perKamar = new LinkedHashMap<>();
        for (Kamar k : HotelData.getDaftarKamar()) {
            perKamar.merge(k.getStatus(), 1, Integer::sum);
        }
        System.out.println("Total kamar     : " + HotelData.getDaftarKamar().size());
        for (Map.Entry<String, Integer> e : perKamar.entrySet()) {
            System.out.println("  - " + e.getKey() + " : " + e.getValue());
        }
        System.out.println("=========================================");
    }

    // ---- Bantuan untuk kelolaKamar ----

    private void tampilkanKamar() {
        System.out.println();
        System.out.printf("%-6s %-9s %-14s %-10s %s%n", "No", "Tipe", "Harga Dasar", "Kapasitas", "Status");
        for (Kamar k : HotelData.getDaftarKamar()) {
            System.out.printf("%-6s %-9s %-14s %-10d %s%n", k.getNomorKamar(),
                    k.getClass().getSimpleName().replace("Kamar", ""),
                    HotelData.formatRupiah(k.getHargaDasar()), k.getKapasitas(), k.getStatus());
        }
    }

    private void tambahKamar(Scanner in) {
        try {
            System.out.print("Tipe (1=Standard, 2=Deluxe, 3=Suite): ");
            String tipe = in.nextLine().trim();
            System.out.print("Nomor kamar: ");
            String nomor = in.nextLine().trim();
            if (nomor.isEmpty() || HotelData.cariKamar(nomor) != null) {
                System.out.println("Nomor kamar kosong atau sudah dipakai.");
                return;
            }
            System.out.print("Harga dasar per malam: Rp");
            double harga = Double.parseDouble(in.nextLine().trim());
            System.out.print("Kapasitas (orang): ");
            int kapasitas = Integer.parseInt(in.nextLine().trim());
            if (harga <= 0 || kapasitas <= 0) {
                System.out.println("Harga dan kapasitas harus lebih dari 0.");
                return;
            }

            Kamar baru;
            switch (tipe) {
                case "1" -> baru = new KamarStandard(nomor, harga, kapasitas);
                case "2" -> {
                    System.out.print("View laut? (y/n): ");
                    baru = new KamarDeluxe(nomor, harga, kapasitas, in.nextLine().trim().equalsIgnoreCase("y"));
                }
                case "3" -> {
                    System.out.print("Ada ruang tamu? (y/n): ");
                    baru = new KamarSuite(nomor, harga, kapasitas, in.nextLine().trim().equalsIgnoreCase("y"));
                }
                default -> {
                    System.out.println("Tipe tidak valid.");
                    return;
                }
            }
            HotelData.tambahKamar(baru);
            System.out.println("Kamar " + nomor + " berhasil ditambahkan.");
        } catch (NumberFormatException e) {
            System.out.println("Input angka tidak valid. Penambahan dibatalkan.");
        }
    }

    private void ubahHarga(Scanner in) {
        System.out.print("Nomor kamar: ");
        Kamar k = HotelData.cariKamar(in.nextLine().trim());
        if (k == null) {
            System.out.println("Kamar tidak ditemukan.");
            return;
        }
        try {
            System.out.print("Harga baru untuk kamar " + k.getNomorKamar() + ": Rp");
            double hargaBaru = Double.parseDouble(in.nextLine().trim());
            k.setHargaDasar(hargaBaru);
            System.out.println("Admin " + getNama() + " memperbarui harga kamar " + k.getNomorKamar()
                    + " menjadi " + HotelData.formatRupiah(hargaBaru));
        } catch (NumberFormatException e) {
            System.out.println("Input angka tidak valid.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void ubahStatus(Scanner in) {
        System.out.print("Nomor kamar: ");
        Kamar k = HotelData.cariKamar(in.nextLine().trim());
        if (k == null) {
            System.out.println("Kamar tidak ditemukan.");
            return;
        }
        String status = k.getStatus();
        if (!status.equalsIgnoreCase("Tersedia") && !status.equalsIgnoreCase("Perawatan")) {
            System.out.println("Kamar sedang " + status + " (terkait reservasi), status tidak dapat diubah manual.");
            return;
        }
        System.out.print("Status baru (1=Tersedia, 2=Perawatan): ");
        String p = in.nextLine().trim();
        if (p.equals("1")) {
            k.setStatus("Tersedia");
        } else if (p.equals("2")) {
            k.setStatus("Perawatan");
        } else {
            System.out.println("Pilihan tidak valid.");
            return;
        }
        System.out.println("Status kamar " + k.getNomorKamar() + " sekarang: " + k.getStatus());
    }

    @Override
    public String getPeran() {
        return "Admin";
    }
}
