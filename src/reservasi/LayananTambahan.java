package reservasi;

public class LayananTambahan {
    private String namaLayanan;
    private double harga;
    private int jumlah;

    public LayananTambahan(String namaLayanan, double harga, int jumlah) {
        this.namaLayanan = namaLayanan;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public double hitungSubtotal() {
        return harga * jumlah;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public double getHarga() {
        return harga;
    }

    public int getJumlah() {
        return jumlah;
    }
}
