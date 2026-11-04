package kamar;

public abstract class Kamar {
    private String noKamar;
    private double hargaDasar;
    private String status;
    private int kapasitas;

    public Kamar(String noKamar, double hargaDasar, int kapasitas) {
        this.noKamar = noKamar;
        this.hargaDasar = hargaDasar;
        this.kapasitas = kapasitas;
        this.status = "Tersedia";
    }

    public boolean isTersedia() {
        return this.status.equalsIgnoreCase("Tersedia");
    }

    // Method abstract untuk di-override oleh sub-class
    public abstract double hitungHarga(int malam);
    public abstract String getFasilitas();

    // Getter dan Setter
    public String getNoKamar() { 
        return noKamar; 
    }
    
    public double getHargaDasar() { 
        return hargaDasar; 
    }
    
    public void setHargaDasar(double hargaDasar) { 
        this.hargaDasar = hargaDasar; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public int getKapasitas() { 
        return kapasitas; 
    }
}