package reservasi;

/** Interface/kontrak untuk objek yang mendukung pembatalan dan perhitungan refund. */
public interface IDapatDibatalkan {
    boolean batalkan();

    double hitungRefund();
}
