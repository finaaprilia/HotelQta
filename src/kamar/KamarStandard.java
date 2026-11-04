package kamar;

public class KamarStandard extends Kamar {

    public KamarStandard(String noKamar, double hargaDasar, int kapasitas) {
        super(noKamar, hargaDasar, kapasitas);
    }

    @Override
    public double hitungHarga(int malam) {
        return getHargaDasar() * malam;
    }

    @Override
    public String getFasilitas() {
        return "Single/Double Bed, AC, Wi-Fi, TV, Kamar Mandi";
    }
}