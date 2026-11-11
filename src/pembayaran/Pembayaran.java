package pembayaran;

import java.util.Date;

public abstract class Pembayaran implements IPembayaran {
    private String idPembayaran;
    private double jumlah;
    private Date tanggal;
    private String status; // Pending, Sukses, Gagal

    public Pembayaran(String idPembayaran) {
        this.idPembayaran = idPembayaran;
        this.tanggal = new Date();
        this.status = "Pending";
    }

    // Dideklarasikan ulang sebagai abstract agar sesuai diagram;
    // implementasinya ada di PembayaranTunai/Kartu/EWallet (polymorphism)
    @Override
    public abstract boolean proses(double jumlah);

    @Override
    public abstract String getMetode();

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public double getJumlah() {
        return jumlah;
    }

    protected void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }

    protected void setStatus(String status) {
        this.status = status;
    }
}
