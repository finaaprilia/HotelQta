package pembayaran;

import data.HotelData;

/** Implementasi konkret Pembayaran untuk metode E-Wallet (pewarisan). */
public class PembayaranEWallet extends Pembayaran {
    private String provider;

    public PembayaranEWallet(String idPembayaran, String provider) {
        super(idPembayaran);
        this.provider = provider;
    }

    // Override: proses pembayaran E-Wallet berbeda dari metode lainnya.
    @Override
    public boolean proses(double jumlah) {
        if (jumlah <= 0 || provider == null || provider.isBlank()) {
            setStatus("Gagal");
            System.out.println("Pembayaran E-Wallet gagal: provider tidak boleh kosong.");
            return false;
        }
        setJumlah(jumlah);
        setStatus("Sukses");
        System.out.println("Pembayaran E-Wallet (" + provider + ") sebesar " + HotelData.formatRupiah(jumlah)
                + " berhasil diproses.");
        return true;
    }

    // Override: mengembalikan identitas metode pembayaran konkret.
    @Override
    public String getMetode() {
        return "E-Wallet (" + provider + ")";
    }

    public String getProvider() {
        return provider;
    }
}
