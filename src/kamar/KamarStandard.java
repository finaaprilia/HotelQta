package kamar;

public class KamarStandard extends Kamar {

    public KamarStandard(String nomorKamar, double hargaDasar, int kapasitas) {
        super(nomorKamar, hargaDasar, kapasitas);
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
