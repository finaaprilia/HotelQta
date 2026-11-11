package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import kamar.Kamar;
import kamar.KamarDeluxe;
import kamar.KamarStandard;
import kamar.KamarSuite;
import reservasi.Reservasi;
import user.Admin;
import user.Pengguna;
import user.Resepsionis;
import user.Tamu;

/**
 * Penyimpanan data in-memory + utilitas (bukan bagian dari class diagram domain).
 * Berfungsi sebagai "database" sederhana agar Tamu/Resepsionis/Admin dapat
 * mencari kamar dan reservasi berdasarkan id.
 */
public final class HotelData {
    private static final List<Pengguna> daftarPengguna = new ArrayList<>();
    private static final List<Kamar> daftarKamar = new ArrayList<>();
    private static final List<Reservasi> daftarReservasi = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    private static final String FORMAT_TANGGAL = "dd-MM-yyyy";
    private static int counterTamu = 1;
    private static int counterReservasi = 0;
    private static int counterPembayaran = 0;

    private HotelData() {
    }

    /** Mengisi data awal (admin, resepsionis, kamar, dan satu tamu demo). */
    public static void inisialisasi() {
        daftarPengguna.add(new Admin("A001", "Amirah", "amirah@hotel.com", "08111111111", "admin123", "NIP004", 6000000));
        daftarPengguna.add(new Admin("A002", "Kiara", "kiara@hotel.com", "08111111112", "admin123", "NIP005", 6000000));
        daftarPengguna.add(new Resepsionis("S001", "Rosita", "rosita@hotel.com", "08987654321", "staf123", "NIP001", 4500000));
        daftarPengguna.add(new Resepsionis("S002", "Lily", "lily@hotel.com", "08987654322", "staf123", "NIP002", 4500000));
        daftarPengguna.add(new Resepsionis("S003", "Kayla", "kayla@hotel.com", "08987654323", "staf123", "NIP003", 4500000));
        daftarPengguna.add(new Tamu("T001", "Budi Santoso", "budi@mail.com", "08123456789", "budi123", "3374010101010001"));

        daftarKamar.add(new KamarDeluxe("101", 500000, 2, true));
        daftarKamar.add(new KamarStandard("102", 350000, 2));
        daftarKamar.add(new KamarStandard("103", 350000, 3));
        daftarKamar.add(new KamarDeluxe("201", 500000, 2, false));
        daftarKamar.add(new KamarSuite("301", 800000, 4, true));
        daftarKamar.add(new KamarSuite("302", 800000, 3, false));
    }

    // ---- Pengguna ----
    public static Pengguna login(String email, String password) {
        for (Pengguna p : daftarPengguna) {
            if (p.login(email, password)) {
                return p;
            }
        }
        return null;
    }

    public static boolean emailTerdaftar(String email) {
        for (Pengguna p : daftarPengguna) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public static void tambahPengguna(Pengguna p) {
        daftarPengguna.add(p);
    }

    public static String generateIdTamu() {
        return String.format("T%03d", ++counterTamu);
    }

    // ---- Kamar ----
    public static List<Kamar> getDaftarKamar() {
        return Collections.unmodifiableList(daftarKamar);
    }

    public static Kamar cariKamar(String nomorKamar) {
        for (Kamar k : daftarKamar) {
            if (k.getNomorKamar().equalsIgnoreCase(nomorKamar)) {
                return k;
            }
        }
        return null;
    }

    public static void tambahKamar(Kamar kamar) {
        daftarKamar.add(kamar);
    }

    // ---- Reservasi & Pembayaran ----
    public static List<Reservasi> getDaftarReservasi() {
        return Collections.unmodifiableList(daftarReservasi);
    }

    public static Reservasi cariReservasi(String idReservasi) {
        for (Reservasi r : daftarReservasi) {
            if (r.getIdReservasi().equalsIgnoreCase(idReservasi)) {
                return r;
            }
        }
        return null;
    }

    public static void tambahReservasi(Reservasi r) {
        daftarReservasi.add(r);
    }

    public static String generateIdReservasi() {
        return String.format("RES-%03d", ++counterReservasi);
    }

    public static String generateIdPembayaran() {
        return String.format("PAY-%03d", ++counterPembayaran);
    }

    // ---- Utilitas ----
    public static Scanner getScanner() {
        return scanner;
    }

    /** Parse "dd-MM-yyyy"; mengembalikan null bila format salah. */
    public static Date parseTanggal(String teks) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TANGGAL);
        sdf.setLenient(false);
        try {
            return sdf.parse(teks.trim());
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatTanggal(Date tanggal) {
        return tanggal == null ? "-" : new SimpleDateFormat(FORMAT_TANGGAL).format(tanggal);
    }

    public static LocalDate keLocalDate(Date tanggal) {
        return tanggal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /** Contoh: 1250000 -> "Rp1.250.000" */
    public static String formatRupiah(double nominal) {
        return "Rp" + String.format("%,.0f", nominal).replace(',', '.');
    }
}
