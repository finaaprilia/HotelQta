package reservasi;

import java.util.ArrayList;
import java.util.List;
import user.Tamu;
import kamar.Kamar;
import pembayaran.Pembayaran;

public class Reservasi implements IDapatDibatalkan {
    private String idReservasi;
    private Tamu tamu;
    private Kamar kamar;
    private String tanggalCheckIn;
    private int lamaMalam;
    private String status;
    private List<LayananTambahan> daftarLayanan;
    private Pembayaran pembayaran;

    public Reservasi(String idReservasi, Tamu tamu, Kamar kamar, String tanggalCheckIn, int lamaMalam) {
        this.idReservasi = idReservasi;
        this.tamu = tamu;
        this.kamar = kamar;
        this.tanggalCheckIn = tanggalCheckIn;
        this.lamaMalam = lamaMalam;
        this.status = "Menunggu Pembayaran";
        this.daftarLayanan = new ArrayList<>();
    }

    public void tambahLayanan(String nama, double harga) {
        daftarLayanan.add(new LayananTambahan(nama, harga));
    }

    public double hitungTotal() {
        double totalKamar = kamar.hitungHarga(lamaMalam);
        double totalLayanan = 0;
        for (LayananTambahan l : daftarLayanan) {
            totalLayanan += l.getHarga();
        }
        return totalKamar + totalLayanan;
    }

    public void konfirmasiPembayaran(Pembayaran pembayaran) {
        this.pembayaran = pembayaran;
        double total = hitungTotal();
        if (pembayaran.proses(total)) {
            this.status = "Dikonfirmasi";
            this.kamar.setStatus("Dipesan");
            System.out.println("Reservasi " + idReservasi + " berhasil dikonfirmasi!");
        }
    }

    @Override
    public boolean batalkan() {
        if (!status.equalsIgnoreCase("Check-In") && !status.equalsIgnoreCase("Selesai")) {
            this.status = "Dibatalkan";
            this.kamar.setStatus("Tersedia");
            System.out.println("Reservasi " + idReservasi + " telah dibatalkan.");
            return true;
        }
        return false;
    }

    @Override
    public double hitungRefund(int hariSebelumCheckIn) {
        double total = hitungTotal();
        if (hariSebelumCheckIn >= 3) {
            return total * 1.0;
        } else if (hariSebelumCheckIn >= 1) {
            return total * 0.5;
        }
        return 0;
    }

    // Getter dan Setter
    public String getIdReservasi() {
        return idReservasi;
    }

    public Tamu getTamu() {
        return tamu;
    }

    public Kamar getKamar() {
        return kamar;
    }

    public String getTanggalCheckIn() {
        return tanggalCheckIn;
    }

    public int getLamaMalam() {
        return lamaMalam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LayananTambahan> getDaftarLayanan() {
        return daftarLayanan;
    }

    public Pembayaran getPembayaran() {
        return pembayaran;
    }
}