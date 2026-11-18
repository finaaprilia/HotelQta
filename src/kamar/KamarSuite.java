package kamar;

/** Subclass Kamar dengan tarif Suite dan opsi ruang tamu. */
public class KamarSuite extends Kamar {
    private static final double FAKTOR_SUITE = 1.25;
    private boolean ruangTamu;

    public KamarSuite(String nomorKamar, double hargaDasar, int kapasitas, boolean ruangTamu) {
        super(nomorKamar, hargaDasar, kapasitas);
        this.ruangTamu = ruangTamu;
    }

    // Override: aturan harga Suite menggantikan kontrak harga Kamar.
    @Override
    public double hitungHarga(int malam) {
        return getHargaDasar() * FAKTOR_SUITE * malam;
    }

    // Override: fasilitas khusus kamar Suite.
    @Override
    public String getFasilitas() {
        return "Super King Bed, Jacuzzi, Mini Bar, Dapur" + (ruangTamu ? ", Ruang Tamu Mewah" : "");
    }

    public boolean isRuangTamu() {
        return ruangTamu;
    }
}
