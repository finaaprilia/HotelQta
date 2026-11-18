package kamar;

/** Subclass Kamar dengan perhitungan harga standar; contoh pewarisan. */
public class KamarStandard extends Kamar {

    public KamarStandard(String nomorKamar, double hargaDasar, int kapasitas) {
        super(nomorKamar, hargaDasar, kapasitas);
    }

    // Override: menyediakan perhitungan harga khusus kamar Standard.
    @Override
    public double hitungHarga(int malam) {
        return getHargaDasar() * malam;
    }

    // Override: menyediakan fasilitas yang sesuai tipe Standard.
    @Override
    public String getFasilitas() {
        return "Single/Double Bed, AC, Wi-Fi, TV, Kamar Mandi";
    }
}
