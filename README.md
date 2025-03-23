# TP5DPBO2025C1

Saya Yazid Madarizel dengan NIM 2305328 mengerjakan soal TP 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

---

## Desain Program

Sistem manajemen mahasiswa ini mengimplementasikan aplikasi CRUD (Create, Read, Update, Delete) lengkap menggunakan Java Swing dan MySQL. Berikut adalah penjelasan mengenai desain dan alur program:

### Struktur Kelas
1. **Kelas Mahasiswa**  
   - Merepresentasikan model data mahasiswa dengan atribut: nim, nama, jenisKelamin, dan jurusan  
   - Memiliki metode getter dan setter untuk semua atribut  

2. **Kelas Database**  
   - Menangani koneksi dan operasi database  
   - Menyediakan metode untuk query SELECT, INSERT, UPDATE, dan DELETE  

3. **Kelas Menu**  
   - Kelas utama yang mengelola UI dan logika aplikasi  
   - Mengimplementasikan event handling untuk semua interaksi pengguna  
   - Menangani operasi CRUD melalui koneksi database  

### Alur Program

#### Inisialisasi
1. Aplikasi dimulai dengan membuat instance dari kelas Menu  
2. Menyetel properti jendela utama (ukuran, posisi, dll.)  
3. Menginisialisasi ArrayList mahasiswa  
4. Membuka koneksi ke database  
5. Menyiapkan komponen UI dan event listener  
6. Memuat data mahasiswa yang ada dari database ke dalam tabel  

#### Operasi Tambah Mahasiswa
1. Pengguna mengisi formulir dengan data mahasiswa  
2. Menekan tombol "Tambah"  
3. Sistem melakukan validasi input:  
   - Memeriksa apakah ada kolom yang kosong  
   - Memastikan NIM belum ada dalam database  
4. Jika valid, data dimasukkan ke dalam database  
5. Tampilan tabel diperbarui  
6. Formulir dikosongkan  
7. Menampilkan pesan sukses  

#### Operasi Perbarui Mahasiswa
1. Pengguna memilih baris dari tabel  
2. Data mahasiswa ditampilkan di kolom formulir  
3. Tombol "Tambah" berubah menjadi "Perbarui"  
4. Tombol "Hapus" menjadi terlihat  
5. Pengguna mengedit data  
6. Menekan tombol "Perbarui"  
7. Sistem melakukan validasi input  
8. Jika valid, data dalam database diperbarui  
9. Tampilan tabel diperbarui  
10. Formulir dikosongkan  
11. Menampilkan pesan sukses  

#### Operasi Hapus Mahasiswa
1. Pengguna memilih baris dari tabel  
2. Menekan tombol "Hapus"  
3. Sistem menampilkan dialog konfirmasi  
4. Jika dikonfirmasi, data dihapus dari database  
5. Tampilan tabel diperbarui  
6. Formulir dikosongkan  
7. Menampilkan pesan sukses  

#### Operasi Batal
1. Pengguna menekan tombol "Batal"  
2. Sistem mengosongkan semua kolom formulir  
3. Tombol "Perbarui" kembali menjadi "Tambah"  
4. Tombol "Hapus" disembunyikan  
5. Baris yang dipilih di tabel dibatalkan  

### Pengaturan Database
SQL yang disertakan akan membuat:  
1. Database `db_mahasiswa`  
2. Tabel `mahasiswa` dengan kolom id, nim, nama, jenis_kelamin, dan jurusan  
3. Data contoh untuk memulai  


### **Dokumentasi Program**  

![menambah data](https://github.com/user-attachments/assets/21cd1e1b-4365-4925-ba51-1247a3a54acc)

![data berhasil ditambah](https://github.com/user-attachments/assets/4615a0ab-55bf-431b-82ad-8188b6a6b8cb)

![mengubah data](https://github.com/user-attachments/assets/8eeaf480-927b-4456-8e94-a0e27f8790db)

![data berhasil diubah](https://github.com/user-attachments/assets/7ca30cac-e50b-45a6-8f5c-60df7e2a22d7)

![konfirmasi hapus data](https://github.com/user-attachments/assets/4807f42d-e9c7-4ccc-abda-72f1ef81d01f)

![data berhasil dihapus](https://github.com/user-attachments/assets/e93cefa0-b485-4488-a8e6-b4795f49b68c)

![error field kosong](https://github.com/user-attachments/assets/d491dd85-1310-4148-84d0-9cca918a0898)

![error nim sudah terdaftar](https://github.com/user-attachments/assets/e8489853-67e7-4391-b8b3-12dd4a54522e)


