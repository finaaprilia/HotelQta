package pembayaran;

public class PembayaranKartu extends Pembayaran {
    private String noKartu;

    public PembayaranKartu(String idPembayaran, String tanggal, String noKartu) {
        super(idPembayaran, tanggal);
        this.noKartu = noKartu;
    }

    @Override
    public boolean proses(double jumlah) {
        setJumlah(jumlah);
        setStatus("Sukses");
        System.out.println("Pembayaran Kartu (" + noKartu + ") sebesar Rp" + jumlah + " berhasil diproses.");
        return true;
    }

    @Override
    public String getMetode() {
        return "Kartu Kredit/Debit";
    }

    public String getNoKartu() {
        return noKartu;
    }
}