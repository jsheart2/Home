package database;

public class DataBoking {
    private String nama, jumlah, price, getNama, Tglboking, menit;

    public DataBoking() {
    }

    public DataBoking(String nama, String jumlah, String price, String getNama, String tglboking, String menit) {
        this.nama = nama;
        this.jumlah = jumlah;
        this.price = price;
        this.getNama = getNama;
        this.Tglboking = tglboking;
        this.menit = menit;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGetNama() {
        return getNama;
    }

    public void setGetNama(String getNama) {
        this.getNama = getNama;
    }

    public String getTglboking() {
        return Tglboking;
    }

    public void setTglboking(String tglboking) {
        Tglboking = tglboking;
    }

    public String getMenit() {
        return menit;
    }

    public void setMenit(String menit) {
        this.menit = menit;
    }
}
