package reservasi;

import data.HotelData;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kamar.Kamar;
import pembayaran.Pembayaran;
import user.Tamu;

/**
 * Relasi sesuai diagram:
 *  - Tamu 1 --membuat--> 0..* Reservasi
 *  - Reservasi --memesan--> 1..* Kamar
 *  - Reservasi <>-- memiliki -- 0..* LayananTambahan (komposisi)
 *  - Reservasi --dibayar dengan--> 0..1 Pembayaran
 */
public class Reservasi implements IDapatDibatalkan {
    private String idReservasi;
    private Date tglCheckIn;
    private Date tglCheckOut;
    private String status; // Menunggu Pembayaran, Dikonfirmasi, Check-In, Selesai, Dibatalkan
    private double totalBiaya;

    private Tamu tamu;
    private List<Kamar> daftarKamar;
    private List<LayananTambahan> daftarLayanan;
    private Pembayaran pembayaran; // 0..1

    public Reservasi(String idReservasi, Tamu tamu) {
        this.idReservasi = idReservasi;
        this.tamu = tamu;
        this.status = "Menunggu Pembayaran";
        this.daftarKamar = new ArrayList<>();
        this.daftarLayanan = new ArrayList<>();
    }

    public double hitungTotal() {
        int malam = getLamaMalam();
        double total = 0;
        for (Kamar k : daftarKamar) {
            total += k.hitungHarga(malam); // polymorphism: tiap tipe kamar beda perhitungan
        }
        for (LayananTambahan l : daftarLayanan) {
            total += l.hitungSubtotal();
        }
        this.totalBiaya = total;
        return total;
    }

    public void tambahLayanan(LayananTambahan l) {
        if (l != null) {
            daftarLayanan.add(l);
        }
    }

    /** Menambah kamar ke reservasi (sisi 1..* dari relasi memesan). */
    public void tambahKamar(Kamar kamar) {
        if (kamar == null || !kamar.isTersedia()) {
            throw new IllegalArgumentException("Kamar tidak tersedia untuk dipesan.");
        }
        if (!daftarKamar.contains(kamar)) {
            daftarKamar.add(kamar);
        }
    }

    @Override
    public boolean batalkan() {
        if (status.equalsIgnoreCase("Check-In") || status.equalsIgnoreCase("Selesai")
                || status.equalsIgnoreCase("Dibatalkan")) {
            System.out.println("Reservasi " + idReservasi + " tidak dapat dibatalkan (status: " + status + ").");
            return false;
        }
        this.status = "Dibatalkan";
        for (Kamar k : daftarKamar) {
            if (k.getStatus().equalsIgnoreCase("Dipesan")) {
                k.setStatus("Tersedia");
            }
        }
        System.out.println("Reservasi " + idReservasi + " telah dibatalkan.");
        return true;
    }

    /**
     * Kebijakan refund (hanya jika sudah dibayar dan berstatus Dikonfirmasi):
     * H-3 atau lebih = 100%, H-1 sampai H-2 = 50%, selebihnya 0%.
     */
    @Override
    public double hitungRefund() {
        if (pembayaran == null || !pembayaran.getStatus().equalsIgnoreCase("Sukses")
                || !status.equalsIgnoreCase("Dikonfirmasi") || tglCheckIn == null) {
            return 0;
        }
        long hariSebelum = ChronoUnit.DAYS.between(java.time.LocalDate.now(), HotelData.keLocalDate(tglCheckIn));
        double total = hitungTotal();
        if (hariSebelum >= 3) {
            return total;
        } else if (hariSebelum >= 1) {
            return total * 0.5;
        }
        return 0;
    }

    /** Jumlah malam menginap, dihitung dari tglCheckIn dan tglCheckOut. */
    public int getLamaMalam() {
        if (tglCheckIn == null || tglCheckOut == null) {
            return 0;
        }
        long malam = ChronoUnit.DAYS.between(HotelData.keLocalDate(tglCheckIn), HotelData.keLocalDate(tglCheckOut));
        return (int) Math.max(malam, 0);
    }

    // Getter dan Setter (encapsulation)
    public String getIdReservasi() {
        return idReservasi;
    }

    public Date getTglCheckIn() {
        return tglCheckIn;
    }

    public void setTglCheckIn(Date tglCheckIn) {
        this.tglCheckIn = tglCheckIn;
    }

    public Date getTglCheckOut() {
        return tglCheckOut;
    }

    public void setTglCheckOut(Date tglCheckOut) {
        this.tglCheckOut = tglCheckOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }

    public Tamu getTamu() {
        return tamu;
    }

    public List<Kamar> getDaftarKamar() {
        return daftarKamar;
    }

    public List<LayananTambahan> getDaftarLayanan() {
        return daftarLayanan;
    }

    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Pembayaran pembayaran) {
        this.pembayaran = pembayaran;
    }
}
