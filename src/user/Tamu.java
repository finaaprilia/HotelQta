package user;

public class Tamu extends Pengguna {
    private int poinLoyalitas;

    public Tamu(String id, String nama, String email, String noTelp, String password) {
        super(id, nama, email, noTelp, password);
        this.poinLoyalitas = 0;
    }

    public void tambahPoin(int poin) {
        this.poinLoyalitas += poin;
    }

    public int getPoinLoyalitas() {
        return poinLoyalitas;
    }

    @Override
    public String getPeran() {
        return "Tamu";
    }
}