/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pencairan.kredit.nasabah;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BIMO
 */
public class Form_Input_Nilai extends javax.swing.JFrame {

    /**
     * Creates new form FormDataNasabah
     */
    Koneksi koneksi = new Koneksi();
    Object [] obj = new Object[11];
    private DefaultTableModel tblNilai;
    
    String status_rumah="";
    String tanah="";
    String interview="";
    String kendaraan="";
    
    double tanggungan;
    double gaji;
    double harga_kendaraan;
    
    perhitungan_wp wp;
    
    
    public Form_Input_Nilai() {
        initComponents();
        setTitle("Form Input Nilai");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        txt_Kode.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_duit.setEnabled(false);
        txt_hasil.setEnabled(false);
        txt_harga.setEnabled(false);
        txt_harga.setText("0");
        
        btn_save.setEnabled(false);
        btn_edit.setEnabled(false);
        btn_delete.setEnabled(false);
        
        combo_kendaraan.setEnabled(false);
        combo_tanah.setEnabled(false);
        
        
        
        tblNilai =  new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        tbl_nilai.setModel(tblNilai);
        
        tblNilai.addColumn("Kode Nasabah");
        tblNilai.addColumn("Nama Nasabah");
        tblNilai.addColumn("Kredit");
        tblNilai.addColumn("Status Rumah");
        tblNilai.addColumn("Tanggungan");
        tblNilai.addColumn("Gaji / Omzet");
        tblNilai.addColumn("Hasil Interview");
        tblNilai.addColumn("Jaminan");
        tblNilai.addColumn("Sertifikat Tanah");
        tblNilai.addColumn("Jenis");
        tblNilai.addColumn("Harga");
        
        isiTabel();
                       
    }
    
    public void isiTabel(){
        try{
            Connection con = koneksi.bukakoneksi();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM penilaian,nasabah WHERE nasabah.Kode_Nasabah = penilaian.Kode_Nasabah ORDER BY Duit_kredit ASC";
//            String sql = "Select penilaian.Kode_Nasabah,penilaian.Nama,penilaian.Duit_kredit,penilaian.Status_Rumah,penilaian.Jumlah_tanggungan,penilaian.Gaji,penilaian.Hasil_Interview,penilaian.Jaminan"
//                    + ",sertifikat_tanah.Sertifikat, jaminan.Jenis_Kendaraan, jaminan.Harga from penilaian "
//                    + "LEFT JOIN sertifikat_tanah ON sertifikat_tanah.Kode_Nasabah = penilaian.Kode_Nasabah "
//                    + "LEFT JOIN jaminan ON jaminan.Kode_Nasabah = penilaian.Kode_Nasabah";
            ResultSet rs = st.executeQuery(sql);
            
            
            while(rs.next()){
                
                obj[0] = rs.getString("Kode_Nasabah");
                obj[1] = rs.getString("Nama");
                obj[2] = rs.getString("Duit_kredit");
                obj[3] = rs.getString("Status_Rumah");
                obj[4] = rs.getString("Jumlah_tanggungan");
                obj[5] = rs.getString("Gaji");
                obj[6] = rs.getString("Hasil_Interview");
                obj[7] = rs.getString("Jaminan");
                obj[8] = rs.getString("Sertifikat");
                obj[9] = rs.getString("Jenis_Kendaraan");
                obj[10] = rs.getString("Harga");
                
                tblNilai.addRow(obj);
                
            }
            st.close();
            rs.close();
        }catch(SQLException sqlexc){
            JOptionPane.showMessageDialog(null,sqlexc,"Koneksi Database",JOptionPane.WARNING_MESSAGE);
            System.exit(0);  
        }               
    }
    
    public void bersihTabel(){
        if (tblNilai.getRowCount() > 0) {
            for (int i = tblNilai.getRowCount() - 1; i > -1; i--) {
                tblNilai.removeRow(i);
            }
        }
        tblNilai.fireTableDataChanged();
    }
    
    public void bersihTextfield(){
        txt_Kode.setText("");
        txt_gaji.setText("");
        txt_duit.setText("");
        txt_nama.setText("");
        txtJmlhTanggungan.setText("");
        txt_harga.setText("");
        txt_hasil.setText("");
        combo_status.setSelectedIndex(0);
        combo_jaminan.setSelectedIndex(0);
        combo_kendaraan.setSelectedIndex(0);
        combo_tanah.setSelectedIndex(0);        
        btn_group_karakter.clearSelection();
        btn_group_kasflow.clearSelection();
        btn_group_kondisi.clearSelection();
        btn_group_koralteral.clearSelection();
    }
    
    public void simpan(){
        try{
            Koneksi Objkoneksi = new Koneksi();
            Connection con = Objkoneksi.bukakoneksi();
            Statement st = con.createStatement();
                            
            wp = new perhitungan_wp();
            
            tanggungan = Double.parseDouble(this.txtJmlhTanggungan.getText());
            gaji = Double.parseDouble(this.txt_gaji.getText());
            harga_kendaraan = Double.parseDouble(this.txt_harga.getText());
            interview = String.valueOf(this.txt_hasil.getText());
            
            status_rumah = (String) combo_status.getSelectedItem();
            tanah = (String) combo_tanah.getSelectedItem();
            kendaraan = (String) combo_kendaraan.getSelectedItem();
            
            wp.Hitung_Nilai_S(status_rumah, tanggungan, gaji, tanah, kendaraan, harga_kendaraan, interview);
            
            String sql = "insert into penilaian (Kode_Nasabah,Status_Rumah,Jumlah_Tanggungan,Gaji,Hasil_Interview,Jaminan,Sertifikat,Jenis_Kendaraan,Harga,Nilai_S) values('"+
			txt_Kode.getText()+"','"+
			combo_status.getSelectedItem()+"','"+
                        txtJmlhTanggungan.getText()+"','"+
                        txt_gaji.getText()+"','"+
                        txt_hasil.getText()+"','"+
                        combo_jaminan.getSelectedItem()+"','"+
                        combo_tanah.getSelectedItem()+"','"+
                        combo_kendaraan.getSelectedItem()+"','"+
                        txt_harga.getText()+"','"+
                        wp.nilai_S+"')";
            
            int vHasil = st.executeUpdate(sql);
            if (vHasil >0)
//  	    	JOptionPane.showMessageDialog(null, "Sukses Menambah Data");
 		con.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void simpan_cek(){
        try{
                Koneksi Objkoneksi = new Koneksi();
                Connection con = Objkoneksi.bukakoneksi();
                Statement st = con.createStatement();
                String sql = "select * from penilaian";
                ResultSet rs = st.executeQuery(sql);
                if(rs.next()){
                    if(txt_Kode.getText().equals(rs.getString("Kode_Nasabah")))
                    {
                        JOptionPane.showMessageDialog(null, "Data sudah ada !");
                    }else
                    {
                      simpan();
                    }
                }else{
                      simpan();
                    }
                    rs.close();
                    con.close();
                }
                catch(Exception e){ 
                        }
    }
           
    public void edit(){
        try{
            Koneksi Objkoneksi = new Koneksi();
            Connection con = Objkoneksi.bukakoneksi();
            Statement st = con.createStatement();
        
            wp = new perhitungan_wp();
            
            tanggungan = Double.parseDouble(this.txtJmlhTanggungan.getText());
            gaji = Double.parseDouble(this.txt_gaji.getText());
            harga_kendaraan = Double.parseDouble(this.txt_harga.getText());
            interview = String.valueOf(this.txt_hasil.getText());
            
            status_rumah = (String) combo_status.getSelectedItem();
            tanah = (String) combo_tanah.getSelectedItem();
            kendaraan = (String) combo_kendaraan.getSelectedItem();
            
            wp.Hitung_Nilai_S(status_rumah, tanggungan, gaji, tanah, kendaraan, harga_kendaraan, interview);
//            
            String sql ="update penilaian set Status_Rumah = '"+combo_status.getSelectedItem()+
                        "',Jumlah_tanggungan = '"+txtJmlhTanggungan.getText()+
                        "',Gaji = '"+txt_gaji.getText()+
                        "',Hasil_Interview = '"+txt_hasil.getText()+
                        "',Jaminan = '"+combo_jaminan.getSelectedItem()+
                        "',Sertifikat = '"+combo_tanah.getSelectedItem()+
                        "',Jenis_Kendaraan = '"+combo_kendaraan.getSelectedItem()+
                        "',Harga = '"+txt_harga.getText()+
                        "',Nilai_S = '"+wp.nilai_S+
                        "' Where Kode_Nasabah = '"+txt_Kode.getText()+"'";
            
            int vHasil = st.executeUpdate(sql);
            if(vHasil>0){
            }
            con.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        try{
            Koneksi Objkoneksi = new Koneksi();
            Connection con = Objkoneksi.bukakoneksi();
            Statement st = con.createStatement();
            
            wp = new perhitungan_wp();
            
            tanggungan = Double.parseDouble(this.txtJmlhTanggungan.getText());
            gaji = Double.parseDouble(this.txt_gaji.getText());
            harga_kendaraan = Double.parseDouble(this.txt_harga.getText());
            interview = String.valueOf(this.txt_hasil.getText());
            
            status_rumah = (String) combo_status.getSelectedItem();
            tanah = (String) combo_tanah.getSelectedItem();
            kendaraan = (String) combo_kendaraan.getSelectedItem();
            
            wp.Hitung_Nilai_S(status_rumah, tanggungan, gaji, tanah, kendaraan, harga_kendaraan, interview);
            
            String sql = "update penilaian set Jaminan = '"+combo_jaminan.getSelectedItem()+
                         "',Nilai_S = '"+wp.nilai_S+
                         "' Where Kode_Nasabah = '"+txt_Kode.getText()+"'";
            
            int vHasil = st.executeUpdate(sql);
            if (vHasil >0)
 		con.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void delete(){
        try{
            Koneksi ObjKoneksi = new Koneksi();
            Connection con = ObjKoneksi.bukakoneksi();
            Statement st = con.createStatement();
            String sql ="DELETE FROM penilaian WHERE Kode_Nasabah = '"+
            txt_Kode.getText()+"'";
            int vHasil = st.executeUpdate(sql);
            if(vHasil>0){
            }
            con.close();
        }
        catch(Exception e){
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_group_karakter = new javax.swing.ButtonGroup();
        btn_group_kondisi = new javax.swing.ButtonGroup();
        btn_group_kasflow = new javax.swing.ButtonGroup();
        btn_group_koralteral = new javax.swing.ButtonGroup();
        jLabel3 = new javax.swing.JLabel();
        txt_Kode = new javax.swing.JTextField();
        Jbtn_Cari = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_nilai = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        combo_status = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtJmlhTanggungan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_gaji = new javax.swing.JTextField();
        btn_save = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        combo_jaminan = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        combo_kendaraan = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jrbKarakterBaik = new javax.swing.JRadioButton();
        jrbKarakterTidakBaik = new javax.swing.JRadioButton();
        jrbKondisiBaik = new javax.swing.JRadioButton();
        jrbKondisiTidakBaik = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jrbKasFlowBaik = new javax.swing.JRadioButton();
        jrbKasFlowTidakBaik = new javax.swing.JRadioButton();
        jrbKoralteralBaik = new javax.swing.JRadioButton();
        jrbKoralteralTidakBaik = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        txt_hasil = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btn_hasil = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        combo_tanah = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        txt_duit = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Kode");

        txt_Kode.setEditable(false);
        txt_Kode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_KodeActionPerformed(evt);
            }
        });

        Jbtn_Cari.setText("Cari");
        Jbtn_Cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Jbtn_CariActionPerformed(evt);
            }
        });

        jLabel1.setText("Nama");

        txt_nama.setEditable(false);
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        tbl_nilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Nasabah", "Nama", "Krdit Yang Diambil", "Status Rumah", "Tanggungan", "Gaji / Omzet", "Hasil Interview", "Jaminan", "Sertifikat Tanah", "Jenis", "Harga"
            }
        ));
        tbl_nilai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_nilaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_nilai);

        jLabel2.setText("Status rumah tinggal saat ini");

        combo_status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pilih", "Milik Keluarga", "Dinas", "Milik Pribadi" }));

        jLabel4.setText("Jumlah Tanggungan");

        jLabel5.setText("Gaji / Omzet");

        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_batal.setText("Cancel");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        jLabel7.setText("Jaminan");

        combo_jaminan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pilih", "Sertifikat Tanah", "Kendaraan" }));
        combo_jaminan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_jaminanActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel14.setText("Jenis Kendaraan");

        jLabel16.setText("Harga Pasar Rp.");

        combo_kendaraan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Mobil", "Motor" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_kendaraan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_harga, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                .addGap(19, 19, 19))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jLabel14)
                    .addContainerGap(201, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(combo_kendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel14)
                    .addContainerGap(44, Short.MAX_VALUE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setText("Karakter");

        jLabel11.setText("Kondisi");

        jLabel12.setText("Kas flow & Kapasitas");

        btn_group_karakter.add(jrbKarakterBaik);
        jrbKarakterBaik.setText("Baik");

        btn_group_karakter.add(jrbKarakterTidakBaik);
        jrbKarakterTidakBaik.setText("Tidak Baik");

        btn_group_kondisi.add(jrbKondisiBaik);
        jrbKondisiBaik.setText("Baik");

        btn_group_kondisi.add(jrbKondisiTidakBaik);
        jrbKondisiTidakBaik.setText("Tidak Baik");

        jLabel13.setText("Koralteral");

        btn_group_kasflow.add(jrbKasFlowBaik);
        jrbKasFlowBaik.setText("Baik");

        btn_group_kasflow.add(jrbKasFlowTidakBaik);
        jrbKasFlowTidakBaik.setText("Tidak Baik");

        btn_group_koralteral.add(jrbKoralteralBaik);
        jrbKoralteralBaik.setText("Baik");

        btn_group_koralteral.add(jrbKoralteralTidakBaik);
        jrbKoralteralTidakBaik.setText("Tidak Baik");

        jLabel6.setText("Hasil Interview");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Interview");

        btn_hasil.setText("Hasil");
        btn_hasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hasilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jrbKoralteralBaik)
                                .addGap(18, 18, 18)
                                .addComponent(jrbKoralteralTidakBaik))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jrbKasFlowBaik)
                                .addGap(18, 18, 18)
                                .addComponent(jrbKasFlowTidakBaik))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jrbKondisiBaik)
                                    .addComponent(jrbKarakterBaik))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jrbKarakterTidakBaik)
                                    .addComponent(jrbKondisiTidakBaik)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_hasil)
                .addGap(105, 105, 105))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(txt_hasil, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jrbKarakterBaik)
                    .addComponent(jrbKarakterTidakBaik))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jrbKondisiBaik)
                    .addComponent(jrbKondisiTidakBaik))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jrbKasFlowBaik)
                    .addComponent(jrbKasFlowTidakBaik))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jrbKoralteralBaik)
                    .addComponent(jrbKoralteralTidakBaik))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_hasil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_hasil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Sertifikat Tanah");

        combo_tanah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "SHM", "SHGB", "Induk", "Pecahan" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(combo_tanah, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(combo_tanah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel15.setText("Kredit");

        txt_duit.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(combo_status, 0, 146, Short.MAX_VALUE)
                                            .addComponent(txt_gaji)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtJmlhTanggungan, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel15))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txt_nama)
                                            .addComponent(txt_Kode, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                            .addComponent(txt_duit))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Jbtn_Cari, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addGap(115, 115, 115)
                                            .addComponent(combo_jaminan, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(188, 188, 188)
                                .addComponent(btn_save)
                                .addGap(10, 10, 10)
                                .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_batal)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7))
                            .addComponent(combo_jaminan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_Kode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(Jbtn_Cari))
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel1)
                                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel15)
                                            .addComponent(txt_duit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(combo_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(txtJmlhTanggungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(txt_gaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_save)
                            .addComponent(btn_edit)
                            .addComponent(btn_batal)
                            .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Jbtn_CariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Jbtn_CariActionPerformed
        // TODO add your handling code here:
        try {
            tabel_cari list = new tabel_cari(this, "Nasabah");
            list.setVisible(true);
            if (list.a.length() != 0) {
                txt_Kode.setText(list.a);
                txt_nama.setText(list.b);
                txt_duit.setText(list.c);
            } else {
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_Jbtn_CariActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
        simpan_cek();
        bersihTabel();
        isiTabel();
        bersihTextfield();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        bersihTextfield();
        btn_save.setEnabled(false);
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        edit();
        bersihTabel();
        isiTabel();
        bersihTextfield();
        btn_save.setEnabled(false);
    }//GEN-LAST:event_btn_editActionPerformed

    private void tbl_nilaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_nilaiMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tbl_nilai.getModel();
        int selectedRowIndex    = tbl_nilai.getSelectedRow();
        
        txt_Kode.setText(model.getValueAt(selectedRowIndex, 0).toString());
        txt_nama.setText(model.getValueAt(selectedRowIndex, 1).toString());
        txt_duit.setText(model.getValueAt(selectedRowIndex, 2).toString());
        combo_status.setSelectedItem(model.getValueAt(selectedRowIndex, 3).toString());
        txtJmlhTanggungan.setText(model.getValueAt(selectedRowIndex, 4).toString());
        txt_gaji.setText(model.getValueAt(selectedRowIndex, 5).toString());
        txt_hasil.setText(model.getValueAt(selectedRowIndex, 6).toString());
        combo_jaminan.setSelectedItem(model.getValueAt(selectedRowIndex, 7).toString());
        combo_tanah.setSelectedItem(model.getValueAt(selectedRowIndex, 8).toString());
        combo_kendaraan.setSelectedItem(model.getValueAt(selectedRowIndex, 9).toString());
        txt_harga.setText(model.getValueAt(selectedRowIndex, 10).toString());
        
        btn_group_karakter.setSelected(null, rootPaneCheckingEnabled);
        btn_group_kasflow.setSelected(null, rootPaneCheckingEnabled);
        btn_group_koralteral.setSelected(null, rootPaneCheckingEnabled);
        btn_group_kondisi.setSelected(null, rootPaneCheckingEnabled);
        
        txt_Kode.setEnabled(false);
        btn_edit.setEnabled(true);
        btn_delete.setEnabled(true);
        btn_save.setEnabled(false);
        btn_batal.setEnabled(false);
    }//GEN-LAST:event_tbl_nilaiMouseClicked

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        delete();
        bersihTabel();
        isiTabel();
        bersihTextfield();
        btn_save.setEnabled(false);
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_hasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hasilActionPerformed
        // TODO add your handling code here:
        int karakter = 0;
            if(jrbKarakterBaik.isSelected()){
	   	karakter=1;
            }else{
                karakter=0;
            }
            
            int kondisi = 0;
            if(jrbKondisiBaik.isSelected()){
	   	kondisi=1;
            }else{
                kondisi=0;
            }
            
            int kasflow = 0;
            if(jrbKasFlowBaik.isSelected()){
	   	kasflow=1;
            }else{
                kasflow=0;
            }
            
            int koralteral = 0;
            if(jrbKoralteralBaik.isSelected()){
	   	koralteral=1;
            }else{
                koralteral=0;
            }
            
            int hasil=0;
            hasil = karakter + kondisi + kasflow + koralteral;
            
            if (hasil == 4){
                txt_hasil.setText("Baik");
            }else{
                txt_hasil.setText("Tidak Baik");
            }
        btn_save.setEnabled(true);
    }//GEN-LAST:event_btn_hasilActionPerformed

    private void combo_jaminanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_jaminanActionPerformed
        // TODO add your handling code here:
        int pil1 = 0;
        pil1 = combo_jaminan.getSelectedIndex();
        if (pil1==0){
            combo_kendaraan.setEnabled(false);
            txt_harga.setEnabled(false);
            combo_tanah.setEnabled(false);
            txt_harga.setText("0");
        }
        else if (pil1==1){
            combo_kendaraan.setEnabled(false);
            txt_harga.setEnabled(false);
            combo_tanah.setEnabled(true);
            combo_kendaraan.setSelectedIndex(0);
            txt_harga.setText("0");
            
        }else{
            combo_kendaraan.setEnabled(true);
            txt_harga.setEnabled(true);
            combo_tanah.setEnabled(false);
            combo_tanah.setSelectedIndex(0);
            txt_harga.setText("0");
        }
    }//GEN-LAST:event_combo_jaminanActionPerformed

    private void txt_KodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_KodeActionPerformed
        // TODO add your handling code here:
//        if (txt_Kode.getText().equals("")){
//            txtJmlhTanggungan.setEnabled(false);
//            txt_gaji.setEnabled(false);
//            combo_jaminan.setEnabled(false);
//            combo_status.setEnabled(false);
//        }
//        else{
//            txtJmlhTanggungan.setEnabled(true);
//            txt_gaji.setEnabled(true);
//            combo_jaminan.setEnabled(true);
//            combo_status.setEnabled(true);
//        }
    }//GEN-LAST:event_txt_KodeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_Input_Nilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Input_Nilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Input_Nilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Input_Nilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Input_Nilai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Jbtn_Cari;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.ButtonGroup btn_group_karakter;
    private javax.swing.ButtonGroup btn_group_kasflow;
    private javax.swing.ButtonGroup btn_group_kondisi;
    private javax.swing.ButtonGroup btn_group_koralteral;
    private javax.swing.JButton btn_hasil;
    private javax.swing.JButton btn_save;
    private javax.swing.JComboBox combo_jaminan;
    private javax.swing.JComboBox combo_kendaraan;
    private javax.swing.JComboBox combo_status;
    private javax.swing.JComboBox combo_tanah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton jrbKarakterBaik;
    private javax.swing.JRadioButton jrbKarakterTidakBaik;
    private javax.swing.JRadioButton jrbKasFlowBaik;
    private javax.swing.JRadioButton jrbKasFlowTidakBaik;
    private javax.swing.JRadioButton jrbKondisiBaik;
    private javax.swing.JRadioButton jrbKondisiTidakBaik;
    private javax.swing.JRadioButton jrbKoralteralBaik;
    private javax.swing.JRadioButton jrbKoralteralTidakBaik;
    private javax.swing.JTable tbl_nilai;
    private javax.swing.JTextField txtJmlhTanggungan;
    private javax.swing.JTextField txt_Kode;
    private javax.swing.JTextField txt_duit;
    private javax.swing.JTextField txt_gaji;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_hasil;
    private javax.swing.JTextField txt_nama;
    // End of variables declaration//GEN-END:variables
}
