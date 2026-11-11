package kamar;

public class KamarDeluxe extends Kamar {
    private static final double TAMBAHAN_VIEW_LAUT = 150000; // per malam
    private boolean viewLaut;

    public KamarDeluxe(String nomorKamar, double hargaDasar, int kapasitas, boolean viewLaut) {
        super(nomorKamar, hargaDasar, kapasitas);
        this.viewLaut = viewLaut;
    }

    @Override
    public double hitungHarga(int malam) {
        double total = getHargaDasar() * malam;
        if (viewLaut) {
            total += TAMBAHAN_VIEW_LAUT * malam;
        }
        return total;
    }

    @Override
    public String getFasilitas() {
        return "King Bed, AC, Wi-Fi, Smart TV, Balkon" + (viewLaut ? " (View Laut)" : "");
    }

    public boolean isViewLaut() {
        return viewLaut;
    }
}
