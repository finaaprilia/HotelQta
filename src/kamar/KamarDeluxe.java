package kamar;

/** Subclass Kamar yang mewarisi data/perilaku umum dan menambahkan opsi view laut. */
public class KamarDeluxe extends Kamar {
    private static final double TAMBAHAN_VIEW_LAUT = 150000; // per malam
    private boolean viewLaut;

    public KamarDeluxe(String nomorKamar, double hargaDasar, int kapasitas, boolean viewLaut) {
        super(nomorKamar, hargaDasar, kapasitas);
        this.viewLaut = viewLaut;
    }

    // Override: mengganti implementasi harga milik kontrak abstrak Kamar.
    @Override
    public double hitungHarga(int malam) {
        double total = getHargaDasar() * malam;
        if (viewLaut) {
            total += TAMBAHAN_VIEW_LAUT * malam;
        }
        return total;
    }

    // Override: fasilitas khusus kamar Deluxe.
    @Override
    public String getFasilitas() {
        return "King Bed, AC, Wi-Fi, Smart TV, Balkon" + (viewLaut ? " (View Laut)" : "");
    }

    public boolean isViewLaut() {
        return viewLaut;
    }
}
