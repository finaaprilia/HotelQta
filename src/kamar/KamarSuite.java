package kamar;

public class KamarSuite extends Kamar {
    private static final double FAKTOR_SUITE = 1.25;
    private boolean ruangTamu;

    public KamarSuite(String nomorKamar, double hargaDasar, int kapasitas, boolean ruangTamu) {
        super(nomorKamar, hargaDasar, kapasitas);
        this.ruangTamu = ruangTamu;
    }

    @Override
    public double hitungHarga(int malam) {
        return getHargaDasar() * FAKTOR_SUITE * malam;
    }

    @Override
    public String getFasilitas() {
        return "Super King Bed, Jacuzzi, Mini Bar, Dapur" + (ruangTamu ? ", Ruang Tamu Mewah" : "");
    }

    public boolean isRuangTamu() {
        return ruangTamu;
    }
}
