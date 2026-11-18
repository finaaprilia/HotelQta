package kamar;

/**
 * Class abstrak induk tipe kamar (abstraksi dan pewarisan).
 * Subclass wajib menentukan perhitungan harga dan fasilitasnya sendiri.
 */
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

    // Kontrak abstrak: implementasi subclass dipilih saat runtime (polimorfisme).
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
