import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.sql.*;

public class Menu extends JFrame{
    public static void main(String[] args) {
        Menu window = new Menu();
        window.setSize(480, 568);
        window.setLocationRelativeTo(null);
        window.setContentPane(window.mainPanel);
        window.getContentPane().setBackground(Color.WHITE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    private int selectedIndex = -1;
    private String selectedNim = ""; // Untuk menyimpan NIM yang dipilih saat update

    private ArrayList<Mahasiswa> listMahasiswa;

    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel JurusanLabel;
    private JComboBox jurusanComboBox;

    // constructor
    public Menu() {
        // Inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // Inisialisasi database
        database = new Database();

        // Isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // Ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // Atur isi combo box
        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel<>(jenisKelaminData));

        String[] jurusanData = {
                "",
                "Teknik Informatika",
                "Sistem Informasi",
                "Teknik Elektro",
                "Teknik Mesin",
                "Teknik Sipil",
                "Manajemen",
                "Akuntansi",
                "Ilmu Komputer",
                "Statistika",
                "Fisika"
        };

        jurusanComboBox.setModel(new DefaultComboBoxModel<>(jurusanData));

        // Sembunyikan tombol delete
        deleteButton.setVisible(false);

        // Event listener untuk tombol add/update
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // Saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    int option = JOptionPane.showConfirmDialog(
                            null,
                            "Apakah Anda yakin ingin menghapus data ini?",
                            "Konfirmasi Hapus Data",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (option == JOptionPane.YES_OPTION) {
                        deleteData();
                    }
                }
            }
        });

        // Saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = mahasiswaTable.getSelectedRow();

                selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedJurusan = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();

                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                jurusanComboBox.setSelectedItem(selectedJurusan);

                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Jurusan"};
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            int i = 0;

            while (resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("jurusan");

                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error mengambil data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return temp;
    }

    public void insertData() {
        // Ambil nilai dari textfield dan combobox
        String nim = nimField.getText().trim();
        String nama = namaField.getText().trim();
        String jenisKelamin = (jenisKelaminComboBox.getSelectedItem() != null)
                ? jenisKelaminComboBox.getSelectedItem().toString().trim()
                : "";
        String jurusan = (jurusanComboBox.getSelectedItem() != null)
                ? jurusanComboBox.getSelectedItem().toString().trim()
                : "";

        // Validasi input tidak boleh kosong
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || jurusan.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Periksa apakah NIM sudah ada di database
        try {
            ResultSet rs = database.selectQuery("SELECT * FROM mahasiswa WHERE nim = '" + nim + "'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "NIM " + nim + " sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error memeriksa NIM: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buat query SQL untuk memasukkan data ke dalam database
        String sql = "INSERT INTO mahasiswa (nim, nama, jenis_kelamin, jurusan) VALUES ('"
                + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + jurusan + "')";

        // Eksekusi query
        try {
            database.insertUpdateDeleteQuery(sql);

            // Perbarui tabel
            mahasiswaTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Feedback
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error menambahkan data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData() {
        // Ambil data dari form
        String nim = nimField.getText().trim();
        String nama = namaField.getText().trim();
        String jenisKelamin = (jenisKelaminComboBox.getSelectedItem() != null)
                ? jenisKelaminComboBox.getSelectedItem().toString().trim()
                : "";
        String jurusan = (jurusanComboBox.getSelectedItem() != null)
                ? jurusanComboBox.getSelectedItem().toString().trim()
                : "";

        // Validasi input tidak boleh kosong
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || jurusan.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Jika NIM diubah, periksa apakah NIM baru sudah ada di database
        if (!nim.equals(selectedNim)) {
            try {
                ResultSet rs = database.selectQuery("SELECT * FROM mahasiswa WHERE nim = '" + nim + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "NIM " + nim + " sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error memeriksa NIM: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Buat query SQL untuk mengupdate data
        String sql = "UPDATE mahasiswa SET nim = '" + nim + "', nama = '" + nama +
                "', jenis_kelamin = '" + jenisKelamin + "', jurusan = '" + jurusan +
                "' WHERE nim = '" + selectedNim + "'";

        // Eksekusi query
        try {
            database.insertUpdateDeleteQuery(sql);

            // Perbarui tabel
            mahasiswaTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Feedback
            JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error mengubah data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        // Buat query SQL untuk menghapus data
        String sql = "DELETE FROM mahasiswa WHERE nim = '" + selectedNim + "'";

        // Eksekusi query
        try {
            database.insertUpdateDeleteQuery(sql);

            // Perbarui tabel
            mahasiswaTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Feedback
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error menghapus data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearForm() {
        // Kosongkan semua text field dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        jurusanComboBox.setSelectedItem("");

        // Ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // Sembunyikan button delete
        deleteButton.setVisible(false);

        // Ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
        selectedNim = "";
    }
}