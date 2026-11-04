package user;

import reservasi.Reservasi;

public class Resepsionis extends Staf {

    public Resepsionis(String id, String nama, String email, String noTelp, String password, String nip, double gaji) {
        super(id, nama, email, noTelp, password, nip, gaji);
    }

    public void prosesCheckIn(Reservasi reservasi) {
        if (reservasi != null && reservasi.getStatus().equalsIgnoreCase("Dikonfirmasi")) {
            reservasi.setStatus("Check-In");
            reservasi.getKamar().setStatus("Terisi");
            System.out.println("Resepsionis " + getNama() + " berhasil memproses Check-In.");
        }
    }

    public void prosesCheckOut(Reservasi reservasi) {
        if (reservasi != null && reservasi.getStatus().equalsIgnoreCase("Check-In")) {
            reservasi.setStatus("Selesai");
            reservasi.getKamar().setStatus("Tersedia");
            reservasi.getTamu().tambahPoin(10);
            System.out.println("Resepsionis " + getNama() + " berhasil memproses Check-Out. Tamu mendapat +10 Poin Loyalitas!");
        }
    }

    @Override
    public String getPeran() {
        return "Resepsionis";
    }
}