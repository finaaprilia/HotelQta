package kamar;

public abstract class Kamar {
    private String nomorKamar;
    private double hargaDasar;
    private String status; // Tersedia, Dipesan, Terisi, Perawatan
    private int kapasitas;

    public Kamar(String nomorKamar, double hargaDasar, int kapasitas) {
        this.nomorKamar = nomorKamar;
        this.hargaDasar = hargaDasar;
        this.kapasitas = kapasitas;
        this.status = "Tersedia";
    }

    public boolean isTersedia() {
        return this.status.equalsIgnoreCase("Tersedia");
    }

    // Method abstrak: dihitung/diisi berbeda oleh tiap tipe kamar (polymorphism)
    public abstract double hitungHarga(int malam);

    public abstract String getFasilitas();

    // Getter dan Setter (encapsulation)
    public String getNomorKamar() {
        return nomorKamar;
    }

    public double getHargaDasar() {
        return hargaDasar;
    }

    public void setHargaDasar(double hargaDasar) {
        if (hargaDasar <= 0) {
            throw new IllegalArgumentException("Harga dasar harus lebih dari 0.");
        }
        this.hargaDasar = hargaDasar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getKapasitas() {
        return kapasitas;
    }
}
