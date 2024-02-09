package com.tutorial;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class StudentDB {
    private String fileName;
    private List<Students> daftarMahasiswa; // Buat objek ArrayList<Student> untuk menyimpan data mahasiswa
    private Operasi operasi;
    public StudentDB(String fileName) {
        this.fileName = fileName;
        this.daftarMahasiswa = new ArrayList<>();
        this.operasi = new Operasi();
    }

    // Method untuk menampilkan data
    public void tampilkanDatabase() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;
        try {
            fileInput = new FileReader(fileName);
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("\nDatabase Tidak Ditemukan");
            System.err.println("Silahkan Masukan Database Terlebih Dahulu");
            return;
        }


        String data = bufferInput.readLine();
        int nomorData = 0;
        while (data != null) {
            nomorData++;

            StringTokenizer stringToken = new StringTokenizer(data, ",");

            // Ambil informasi nama, nim, jurusan dari token
            String nama = stringToken.nextToken();
            String nim = stringToken.nextToken();
            String jurusan = stringToken.nextToken();
            String kelas = stringToken.nextToken();

            System.out.println(nomorData + ".\tNama\t : " + nama);
            System.out.println("\tNIM\t\t : " + nim);
            System.out.println("\tJurusan\t : " + jurusan);
            System.out.println("\tKelas\t : " + kelas);

            // Buat objek Student dari informasi yang diambil
            Students student = new Students(nama, nim, jurusan, kelas);
            daftarMahasiswa.add(student); // Tambahkan objek Student ke ArrayList

            data = bufferInput.readLine();
        }

        boolean isLanjutkan = operasi.getYesNo("Apakah anda ingin melanjutkan ?");
        if (!isLanjutkan) {
            System.out.println("FAK U");
            System.exit(0);
        }

        fileInput.close();
        bufferInput.close();
    }

    // Mehtod untuk tambah data
    public void tambahDatabase() throws IOException{
        FileWriter fileOutput = new FileWriter(fileName, true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);
        FileReader fileInput = new FileReader(fileName);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        System.out.println("SILAHKAN INPUTKAN DATA MAHASISWA : ");
        Scanner userInput = new Scanner(System.in);
        System.out.println("Nama : ");
        String nama = userInput.nextLine();
        System.out.println("NIM : ");
        String nim = userInput.nextLine();
        System.out.println("Jurusan : ");
        String jurusan = userInput.nextLine();
        System.out.println("Kelas : ");
        String kelas = userInput.nextLine();

        String[] keywords = {nama+","+nim+","+jurusan+","+kelas};
        Students student = new Students(nama, nim, jurusan, kelas);
        daftarMahasiswa.add(student);

        boolean isExist = cekDataMahasiswa(keywords, false);

        if (!isExist) {
            System.out.println("==== DATA MAHASISWA YANG ANDA MASUKAN ====");
            System.out.println("Nama\t: " + nama);
            System.out.println("NIM\t: " + nim);
            System.out.println("Jurusan\t: " + jurusan);
            System.out.println("Kelas\t: " + kelas);

            boolean isTambah = operasi.getYesNo("Anda Yakin Ingin Menambahan Data Mahasiswa Diatas ? ");
            if (isTambah) {
                bufferOutput.write((nama + "," + nim + "," + jurusan + "," + kelas));
                bufferOutput.newLine();
                bufferOutput.flush();
            }
        } else {
            System.out.println("Figure yang akan anda masukkan sudah tersedia di database dengan data berikut : ");
            cekDataMahasiswa(keywords, true);
        }

        boolean isLanjutkan = operasi.getYesNo("Apakah anda ingin melanjutkan ?");
        if (!isLanjutkan) {
            System.out.println("FAK U");
            System.exit(0);
        }

        fileInput.close();
        bufferInput.close();
        fileOutput.close();
        bufferOutput.close();
    }

    // Method untuk mencari data
    public void cariData() throws IOException {
        try {
            File file = new File(fileName);
        } catch (Exception e)  {
            System.out.println("Database Tidak Ditemukan!");
            System.out.println("Silahkan Buat Database Terlebih Dahulu!");
            this.tambahDatabase();
        }

        Scanner userInput = new Scanner(System.in);

        System.out.println("Masukan Data Mahasiswa Yang Ingin Anda Cari : ");
        String cariData = userInput.nextLine();
        String[] keywords = cariData.split("\\s");

        cekDataMahasiswa(keywords, true);
    }

    public boolean cekDataMahasiswa(String[] keywords, boolean isDisplay) throws IOException{
        FileReader fileInput = new FileReader(fileName);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        int nomorData = 0;
        boolean isExist = false;
        boolean isMatch = false;
        while (data != null) {
            nomorData++;

            isMatch = true;
            for (String keyword : keywords) {
                isMatch = isMatch && data.toLowerCase().contains(keyword.toLowerCase());
            }
            if (isMatch) {
                if (isDisplay) {
                    isExist = true;
                    System.out.println("Data Mahasiswa Berhasil Ditemukan!");
                    StringTokenizer stringToken = new StringTokenizer(data, ",");

                    // Ambil informasi nama, nim, jurusan dari token
                    String nama = stringToken.nextToken();
                    String nim = stringToken.nextToken();
                    String jurusan = stringToken.nextToken();
                    String kelas = stringToken.nextToken();

                    System.out.println(nomorData + ".\tNama\t : " + nama);
                    System.out.println("\tNIM\t\t : " + nim);
                    System.out.println("\tJurusan\t : " + jurusan);
                    System.out.println("\tKelas\t : " + kelas);
                } else {
                    break;
                }
            }
            data = bufferInput.readLine();
        }
        if (!isExist) {
            System.out.println("Data Tidak Ditemukan!");
        }
        fileInput.close();
        bufferInput.close();
        return isMatch;
    }

    // Update Data Mahasiswa
    public void updateDataMahasiswa() throws IOException{
        Scanner userInput = new Scanner(System.in);

        File file = new File(fileName);
        FileReader fileInput = new FileReader(file);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        //Database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        System.out.println("DATABASE MAHASISWA");
        tampilkanDatabase();

        int nomorPilihan = operasi.getIntegerInput("Silahkan Pilih Data Mahaiswa Yang Ingin Anda Update : ");

        String data = bufferInput.readLine();
        int nomorData = 0;
        while (data != null) {
            nomorData++;

            StringTokenizer stringToken = new StringTokenizer(data, ",");

            if (nomorPilihan == nomorData) {
                System.out.println("==== DATA MAHASISWA YANG ANDA UPDATE ====");
                System.out.println("Nama\t: " + stringToken.nextToken());
                System.out.println("NIM\t: " + stringToken.nextToken());
                System.out.println("Jurusan\t: " + stringToken.nextToken());
                System.out.println("Kelas\t: " + stringToken.nextToken());

                //Update data

                //Ambil input dari user
                String[] fieldData = {"Nama Mahasiswa", "NIM Mahasiswa", "Jurusan Mahasiswa", "Kelas Mahasiswa"};
                String[] tempData = new String[4];

                //Refresh token
                stringToken = new StringTokenizer(data, ",");
                String originalData;
                for (int i = 0; i < fieldData.length; i++) {
                    boolean isUpdate = operasi.getYesNo("Apakah anda ingin mengubah " + fieldData[i] + " ?");
                    originalData = stringToken.nextToken();
                    if (isUpdate) {
                        userInput = new Scanner(System.in);
                        System.out.println("Masukkan " + fieldData[i] + " Yang Baru : ");
                        tempData[i] = userInput.nextLine();
                    } else {
                        tempData[i] = originalData;
                    }
                }

                //Tampilkan data baru ke layar
                stringToken = new StringTokenizer(data, ",");
                System.out.println("==== DATA MAHASISWA BARU YANG ANDA UPDATE ====");
                System.out.println("Nama\t: " + stringToken.nextToken() + " ---> " +tempData[0]) ;
                System.out.println("NIM\t\t: " + stringToken.nextToken() + " ---> " +tempData[1]);
                System.out.println("Jurusan\t: " + stringToken.nextToken() + " ---> " +tempData[2]);
                System.out.println("Kelas\t: " + stringToken.nextToken() + " ---> " +tempData[3]);

                boolean isUpdate = operasi.getYesNo("Apakah anda yakin ingin mengubah data tersebut ? ");
                if (isUpdate) {
                    boolean isExist = cekDataMahasiswa(tempData, false);
                    if (isExist) {
                        System.err.println("Data Figure Sudah Ada  di Database. \nProses Update Dibatalkan");
                        bufferOutput.write(data);
                    } else {
                        String nama = tempData[0];
                        String nim = tempData[1];
                        String jurusan = tempData[2];
                        String kelas = tempData[3];

                        bufferOutput.write(nama + "," + nim + "," + jurusan + "," + kelas);
                    }
                } else {
                    //Copy data
                    bufferOutput.write(data);
                }
            } else {
                //Copy data
                bufferOutput.write(data);
            }
            bufferOutput.newLine();
            data = bufferInput.readLine();
        }

        if (nomorPilihan > nomorData) {
            System.err.println("Pilihan anda tidak sesuai, silahkan pilih (1-" + nomorData + ")");

        }

        //Menulis data ke file
        bufferOutput.flush();
        // Close the file readers and writers
        bufferInput.close();
        bufferOutput.close();
        fileInput.close();
        fileOutput.close();

        System.gc();

        file.delete();
        tempDB.renameTo(file);

    }


}
