package pembayaran;

/** Interface sebagai kontrak: setiap metode pembayaran harus memproses dan menyebutkan metodenya. */
public interface IPembayaran {
    boolean proses(double jumlah);

    String getMetode();
}
