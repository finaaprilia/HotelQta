package pembayaran;

import data.HotelData;

public class PembayaranKartu extends Pembayaran {
    private String noKartu;

    public PembayaranKartu(String idPembayaran, String noKartu) {
        super(idPembayaran);
        this.noKartu = noKartu;
    }

    @Override
    public boolean proses(double jumlah) {
        // Validasi sederhana: nomor kartu harus 16 digit angka
        if (jumlah <= 0 || noKartu == null || !noKartu.matches("\\d{16}")) {
            setStatus("Gagal");
            System.out.println("Pembayaran kartu gagal: nomor kartu harus 16 digit angka.");
            return false;
        }
        setJumlah(jumlah);
        setStatus("Sukses");
        System.out.println("Pembayaran Kartu (**** **** **** " + noKartu.substring(12) + ") sebesar "
                + HotelData.formatRupiah(jumlah) + " berhasil diproses.");
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
