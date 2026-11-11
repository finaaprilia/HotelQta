package user;

import data.HotelData;
import kamar.Kamar;
import reservasi.Reservasi;

/**
 * Resepsionis. Relasi dependency: Resepsionis ..> Reservasi (memproses).
 */
public class Resepsionis extends Staf {

    public Resepsionis(String id, String nama, String email, String noTelp, String password, String nip, double gaji) {
        super(id, nama, email, noTelp, password, nip, gaji);
    }

    public boolean checkIn(String idReservasi) {
        Reservasi r = HotelData.cariReservasi(idReservasi);
        if (r == null) {
            System.out.println("Reservasi " + idReservasi + " tidak ditemukan.");
            return false;
        }
        if (!r.getStatus().equalsIgnoreCase("Dikonfirmasi")) {
            System.out.println("Check-In gagal. Status reservasi saat ini: " + r.getStatus()
                    + " (harus 'Dikonfirmasi').");
            return false;
        }
        r.setStatus("Check-In");
        for (Kamar k : r.getDaftarKamar()) {
            k.setStatus("Terisi");
        }
        System.out.println("Resepsionis " + getNama() + " berhasil memproses Check-In " + r.getIdReservasi() + ".");
        return true;
    }

    public boolean checkOut(String idReservasi) {
        Reservasi r = HotelData.cariReservasi(idReservasi);
        if (r == null) {
            System.out.println("Reservasi " + idReservasi + " tidak ditemukan.");
            return false;
        }
        if (!r.getStatus().equalsIgnoreCase("Check-In")) {
            System.out.println("Check-Out gagal. Status reservasi saat ini: " + r.getStatus()
                    + " (harus 'Check-In').");
            return false;
        }
        r.setStatus("Selesai");
        for (Kamar k : r.getDaftarKamar()) {
            k.setStatus("Tersedia");
        }
        r.getTamu().tambahPoin(10);
        System.out.println("Resepsionis " + getNama() + " berhasil memproses Check-Out " + r.getIdReservasi()
                + ". Tamu mendapat +10 Poin Loyalitas!");
        return true;
    }

    @Override
    public String getPeran() {
        return "Resepsionis";
    }
}
