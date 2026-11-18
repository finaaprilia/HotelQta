package pembayaran;

import java.util.Date;

/**
 * Class abstrak dasar pembayaran: menerapkan kontrak IPembayaran dan mewariskan
 * data umum ke setiap metode pembayaran konkret.
 */
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

    // Implementasi ditunda ke subclass; pemanggilan melalui Pembayaran bersifat polimorfik.
    @Override
    public abstract boolean proses(double jumlah);

    // Override kontrak interface; implementasi konkret disediakan oleh subclass.
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
