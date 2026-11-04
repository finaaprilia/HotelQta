package pembayaran;

public class PembayaranTunai extends Pembayaran {

    public PembayaranTunai(String idPembayaran, String tanggal) {
        super(idPembayaran, tanggal);
    }

    @Override
    public boolean proses(double jumlah) {
        setJumlah(jumlah);
        setStatus("Sukses");
        System.out.println("Pembayaran tunai sebesar Rp" + jumlah + " diterima di Kasir.");
        return true;
    }

    @Override
    public String getMetode() {
        return "Tunai";
    }
}