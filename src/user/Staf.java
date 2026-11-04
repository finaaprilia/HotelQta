package user;

public abstract class Staf extends Pengguna {
    private String nip;
    private double gaji;

    public Staf(String id, String nama, String email, String noTelp, String password, String nip, double gaji) {
        super(id, nama, email, noTelp, password);
        this.nip = nip;
        this.gaji = gaji;
    }

    public String getNip() {
        return nip;
    }

    public double getGaji() {
        return gaji;
    }
}