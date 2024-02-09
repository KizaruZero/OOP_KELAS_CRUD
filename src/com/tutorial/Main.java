package com.tutorial;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner userInput = new Scanner(System.in);
        StudentDB studentDB = new StudentDB("database.txt");
        boolean isLanjutkan = true;

        while (isLanjutkan) {
            System.out.println("SELAMAT DATANG DI DATABASE KELAS TEKNIK INFORMATIKA A");
            System.out.println("1. LIHAT DAFTAR MAHASISWA");
            System.out.println("2. CARI DATA MAHASISWA");
            System.out.println("3. TAMBAH DATA MAHASISWA");
            System.out.println("4. UPDATE DATA MAHASISWA");
            System.out.println("5. HAPUS DATA MAHASISWA");
            System.out.println("6. KELUAR");
            System.out.println("Silahkan Pilih Nomor Menu Diatas : (1-6)");
            String pilihanUser = userInput.nextLine();
            switch (pilihanUser) {
                case "1" -> studentDB.tampilkanDatabase();
                case "2" -> studentDB.cariData();
                case "3" -> studentDB.tambahDatabase();
                case "4" -> studentDB.updateDataMahasiswa();
                case "5" -> studentDB.hapusDataMahasiswa();
                case "6" -> isLanjutkan = false;
                default -> System.err.println("Pilihan anda tidak sesuai, silahkan pilih 1-6");
            }
        }
    }
}
