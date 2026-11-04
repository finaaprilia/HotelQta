package pembayaran;

public class PembayaranEWallet extends Pembayaran {
    private String provider;

    public PembayaranEWallet(String idPembayaran, String tanggal, String provider) {
        super(idPembayaran, tanggal);
        this.provider = provider;
    }

    @Override
    public boolean proses(double jumlah) {
        setJumlah(jumlah);
        setStatus("Sukses");
        System.out.println("Pembayaran E-Wallet (" + provider + ") sebesar Rp" + jumlah + " berhasil diproses.");
        return true;
    }

    @Override
    public String getMetode() {
        return "E-Wallet (" + provider + ")";
    }

    public String getProvider() {
        return provider;
    }
}