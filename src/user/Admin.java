package user;

import kamar.Kamar;

public class Admin extends Staf {

    public Admin(String id, String nama, String email, String noTelp, String password, String nip, double gaji) {
        super(id, nama, email, noTelp, password, nip, gaji);
    }

    public void kelolaKamar(Kamar kamar, double hargaBaru) {
        kamar.setHargaDasar(hargaBaru);
        System.out.println("Admin " + getNama() + " memperbarui harga kamar " + kamar.getNoKamar() + " menjadi Rp" + hargaBaru);
    }

    @Override
    public String getPeran() {
        return "Admin";
    }
}