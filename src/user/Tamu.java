package user;

import data.HotelData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kamar.Kamar;
import reservasi.Reservasi;

/**
 * Tamu hotel. Relasi: Tamu 1 --membuat--> 0..* Reservasi.
 */
public class Tamu extends Pengguna {
    private String noIdentitas;
    private int poinLoyalitas;
    private List<Reservasi> daftarReservasi; // sisi "0..*" dari relasi membuat

    public Tamu(String id, String nama, String email, String noTelp, String password, String noIdentitas) {
        super(id, nama, email, noTelp, password);
        this.noIdentitas = noIdentitas;
        this.poinLoyalitas = 0;
        this.daftarReservasi = new ArrayList<>();
    }

    /** Mencari kamar yang tersedia untuk rentang tanggal yang diminta. */
    public List<Kamar> cariKamar(Date tglMasuk, Date tglKeluar) {
        List<Kamar> hasil = new ArrayList<>();
        if (tglMasuk == null || tglKeluar == null || !tglKeluar.after(tglMasuk)) {
            return hasil; // rentang tanggal tidak valid
        }
        for (Kamar k : HotelData.getDaftarKamar()) {
            if (k.isTersedia()) {
                hasil.add(k);
            }
        }
        return hasil;
    }

    /**
     * Membuat reservasi baru milik tamu ini (status awal: Menunggu Pembayaran).
     * Tanggal, kamar, dan layanan diisi setelahnya pada objek Reservasi yang dikembalikan.
     */
    public Reservasi buatReservasi() {
        Reservasi reservasi = new Reservasi(HotelData.generateIdReservasi(), this);
        daftarReservasi.add(reservasi);
        HotelData.tambahReservasi(reservasi);
        return reservasi;
    }

    /** Membatalkan reservasi milik tamu ini berdasarkan id. */
    public boolean batalkanReservasi(String id) {
        for (Reservasi r : daftarReservasi) {
            if (r.getIdReservasi().equalsIgnoreCase(id)) {
                double refund = r.hitungRefund(); // dihitung sebelum status berubah
                if (r.batalkan()) {
                    System.out.println("Refund yang diterima: " + HotelData.formatRupiah(refund));
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public void tambahPoin(int poin) {
        this.poinLoyalitas += poin;
    }

    @Override
    public String getPeran() {
        return "Tamu";
    }

    public String getNoIdentitas() {
        return noIdentitas;
    }

    public int getPoinLoyalitas() {
        return poinLoyalitas;
    }

    public List<Reservasi> getDaftarReservasi() {
        return daftarReservasi;
    }
}
