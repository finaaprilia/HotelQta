package pembayaran;

public interface IPembayaran {
    boolean proses(double jumlah);
    String getMetode();
}