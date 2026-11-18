package pembayaran;

import data.HotelData;

/** Implementasi konkret Pembayaran untuk transaksi tunai (pewarisan). */
public class PembayaranTunai extends Pembayaran {

    public PembayaranTunai(String idPembayaran) {
        super(idPembayaran);
    }

    // Override: aturan proses pembayaran tunai.
    @Override
    public boolean proses(double jumlah) {
        if (jumlah <= 0) {
            setStatus("Gagal");
            return false;
        }
        setJumlah(jumlah);
        setStatus("Sukses");
        System.out.println("Pembayaran tunai sebesar " + HotelData.formatRupiah(jumlah) + " diterima di Kasir.");
        return true;
    }

    // Override: mengembalikan identitas metode pembayaran konkret.
    @Override
    public String getMetode() {
        return "Tunai";
    }
}
