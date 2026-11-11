package user;

/**
 * Class abstrak induk untuk semua pengguna sistem (Tamu dan Staf).
 */
public abstract class Pengguna {
    private String id;
    private String nama;
    private String email;
    private String noTelp;
    private String password;

    public Pengguna(String id, String nama, String email, String noTelp, String password) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.noTelp = noTelp;
        this.password = password;
    }

    public boolean login(String email, String password) {
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }

    // Method abstrak: tiap subclass menentukan perannya sendiri (polymorphism)
    public abstract String getPeran();

    // Getter dan Setter (encapsulation)
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
}
