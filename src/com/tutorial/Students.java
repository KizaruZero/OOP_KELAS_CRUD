package com.tutorial;

public class Students {
    private String nama;
    private String nim;
    private String jurusan;
    private String kelas;

    public Students(String nama, String nim, String jurusan, String kelas) {
        this.nama = nama;
        this.nim = nim;
        this.jurusan = jurusan;
        this.kelas = kelas;
    }

    // getter dan setter untuk setiap atribut
    public String getNama() {
        return this.nama;
    }
    public void setNama(String newNama) {
        this.nama = newNama;
    }
    public String getNim() {
        return this.nim;
    }
    public void setNim(String nim) {
        this.nim = nim;
    }
    public String getJurusan() {
        return this.jurusan;
    }
    public void setJurusan(String newJurusan) {
        this.jurusan = newJurusan;
    }
    public String getKelas() {
        return this.kelas;
    }
    public void setKelas(String newKelas) {
        this.kelas = newKelas;
    }

}
