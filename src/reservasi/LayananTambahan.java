package reservasi;

public class LayananTambahan {
    private String namaLayanan;
    private double harga;

    public LayananTambahan(String namaLayanan, double harga) {
        this.namaLayanan = namaLayanan;
        this.harga = harga;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public double getHarga() {
        return harga;
    }
}