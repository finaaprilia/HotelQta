package kamar;

public class KamarDeluxe extends Kamar {
    private boolean viewLaut;

    public KamarDeluxe(String noKamar, double hargaDasar, int kapasitas, boolean viewLaut) {
        super(noKamar, hargaDasar, kapasitas);
        this.viewLaut = viewLaut;
    }

    @Override
    public double hitungHarga(int malam) {
        double total = getHargaDasar() * malam;
        if (viewLaut) {
            total += 150000 * malam;
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