package pembayaran;

public abstract class Pembayaran implements IPembayaran {
    private String idPembayaran;
    private double jumlah;
    private String tanggal;
    private String status;

    public Pembayaran(String idPembayaran, String tanggal) {
        this.idPembayaran = idPembayaran;
        this.tanggal = tanggal;
        this.status = "Pending";
    }

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}