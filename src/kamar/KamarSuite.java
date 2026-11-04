package kamar;

public class KamarSuite extends Kamar {
    private boolean ruangTamu;

    public KamarSuite(String noKamar, double hargaDasar, int kapasitas, boolean ruangTamu) {
        super(noKamar, hargaDasar, kapasitas);
        this.ruangTamu = ruangTamu;
    }

    @Override
    public double hitungHarga(int malam) {
        return (getHargaDasar() * 1.25) * malam;
    }

    @Override
    public String getFasilitas() {
        return "Super King Bed, Jacuzzi, Mini Bar, Dapur" + (ruangTamu ? ", Ruang Tamu Mewah" : "");
    }

    public boolean isRuangTamu() {
        return ruangTamu;
    }
}