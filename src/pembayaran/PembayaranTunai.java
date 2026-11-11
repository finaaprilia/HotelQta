package pembayaran;

import data.HotelData;

public class PembayaranTunai extends Pembayaran {

    public PembayaranTunai(String idPembayaran) {
        super(idPembayaran);
    }

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

    @Override
    public String getMetode() {
        return "Tunai";
    }
}
