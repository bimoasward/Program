/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pencairan.kredit.nasabah;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultRowSorter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author BIMO
 */
public class Form_Input_Nasabah extends javax.swing.JFrame {

    /**
     * Creates new form Form_Input_Nasabah
     */
    Koneksi koneksi = new Koneksi();
    Object [] obj = new Object[3];
    //ResultSet hasil;
    Statement stat;
    
    private DefaultTableModel tblNasabah;
    
    public Form_Input_Nasabah() {
        initComponents();
        this.getRootPane().setDefaultButton(btn_Save);
        setTitle("Form Input Nasabah");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        btn_Edit.setEnabled(false);
        btn_Delete.setEnabled(false);
        
        tblNasabah = new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        tbl_Nasabah.setModel(tblNasabah);
        
        tblNasabah.addColumn("Kode Nasabah");
        tblNasabah.addColumn("Nama Nasabah");
        tblNasabah.addColumn("Kredit Yang Diminta");
        
        isiTabel();
    }
    
    public void cek(){
        if(txt_Kode.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Data Kode Belum Diisi !");
                txt_Kode.requestFocus(true);
            } else if(txt_Nama.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Data Nama Belum Diisi !");
                txt_Nama.requestFocus(true);
            } else if(combo_duit.getSelectedItem().equals("pilih"))
            {
                JOptionPane.showMessageDialog(null, "Data Kredit Yang Diminta Belum Dipilih !");
            }
    }
    
    public void simpan(){
        try{
            
            Koneksi Objkoneksi = new Koneksi();
            Connection con = Objkoneksi.bukakoneksi();
            Statement st = con.createStatement();
            if(txt_Kode.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Data Kode Belum Diisi !");
                txt_Kode.requestFocus(true);
            } else if(txt_Nama.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Data Nama Belum Diisi !");
                txt_Nama.requestFocus(true);
            } else if(combo_duit.getSelectedItem().equals("pilih"))
            {
                JOptionPane.showMessageDialog(null, "Data Kredit Yang Diminta Belum Dipilih !");
            }else{
            String sql = "insert into nasabah values('"+
			txt_Kode.getText()+"','"+
			txt_Nama.getText()+"','"+
			combo_duit.getSelectedItem()+"')";   
            
            int vHasil = st.executeUpdate(sql);
            if (vHasil >0)
//  	    JOptionPane.showMessageDialog(null, "Sukses Menambah Data");
 		con.close();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, (e.getMessage()));
            System.out.println(e.getMessage());
        }
    }
    
    public void isiTabel(){
        try{
            Connection con = koneksi.bukakoneksi();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM nasabah ORDER BY Duit_kredit ASC ";
            ResultSet rs = st.executeQuery(sql);
            
            
            while(rs.next()){
                
                obj[0] = rs.getString("Kode_Nasabah");
                obj[1] = rs.getString("Nama");
                obj[2] = rs.getString("Duit_kredit");
                
                tblNasabah.addRow(obj);
                
            }
            st.close();
            rs.close();
        }catch(SQLException sqlexc){
            JOptionPane.showMessageDialog(null,sqlexc,"Koneksi Database",JOptionPane.WARNING_MESSAGE);
            System.exit(0);  
        }
    }
    
    // menghapus isi tabel
    void bersihTabel(){
        if (tblNasabah.getRowCount() > 0) {
            for (int i = tblNasabah.getRowCount() - 1; i > -1; i--) {
                tblNasabah.removeRow(i);
            }
        }
        tblNasabah.fireTableDataChanged();
    }
    
    public void edit(){
        try{
            Koneksi Objkoneksi = new Koneksi();
            Connection con = Objkoneksi.bukakoneksi();
            Statement st = con.createStatement();
            
            /*int pil1=0;
            int duit=0;
            
            pil1=combo_duit.getSelectedIndex();
            if (pil1==0) { duit = 0;}
            if (pil1==1) { duit = 11000000;}
            if (pil1==2) { duit = 20000000;}
            if (pil1==3) { duit = 30000000;}
            if (pil1==4) { duit = 40000000;}
            if (pil1==5) { duit = 50000000;}
            if (pil1==6) { duit = 60000000;}
            if (pil1==7) { duit = 70000000;}
            if (pil1==8) { duit = 80000000;}
            if (pil1==9) { duit = 90000000;}
            if (pil1==10) { duit = 100000000;}
            if (pil1==11) { duit = 110000000;}
            if (pil1==12) { duit = 120000000;}
            if (pil1==13) { duit = 130000000;}
            if (pil1==14) { duit = 140000000;}
            if (pil1==15) { duit = 150000000;}
            if (pil1==16) { duit = 160000000;}
            if (pil1==17) { duit = 170000000;}
            if (pil1==18) { duit = 180000000;}
            if (pil1==19) { duit = 190000000;}
            if (pil1==20) { duit = 200000000;}
            if (pil1==21) { duit = 250000000;}
            if (pil1==22) { duit = 300000000;}
            if (pil1==23) { duit = 400000000;}
            if (pil1==24) { duit = 500000000;}*/
            
            String sql ="update nasabah set Nama ='"+txt_Nama.getText()+
            "',Duit_kredit = '"+combo_duit.getSelectedItem()+
            "' Where Kode_Nasabah = '"+txt_Kode.getText()+"'";
            
            int vHasil = st.executeUpdate(sql);
            if(vHasil>0){
//                    JOptionPane.showMessageDialog(null, "Sukses Mengubah Data");
            }
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
            String sql ="DELETE FROM nasabah WHERE Kode_Nasabah = '"+
            txt_Kode.getText()+"'";
            int vHasil = st.executeUpdate(sql);
            if(vHasil>0){
                JOptionPane.showMessageDialog(null, "Sukses Menghapus Data");
            }
            con.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        try{
            Koneksi ObjKoneksi = new Koneksi();
            Connection Kon = ObjKoneksi.bukakoneksi();
            Statement st = Kon.createStatement();
            String sql ="DELETE FROM penilaian WHERE Kode_Nasabah = '"+
            txt_Kode.getText()+"'";
            int vHasil = st.executeUpdate(sql);
            if(vHasil>0){
            }
            Kon.close();
        }catch(Exception e){
        }        
    }
    
    void search_nasabah(){
        try {
            Koneksi Objkoneksi = new Koneksi();
            Connection con = Objkoneksi.bukakoneksi();
            Statement st = con.createStatement();
            String sql = "select * from nasabah where Kode_Nasabah like '%"+txt_Cari.getText()+"%' or Nama like '%"+txt_Cari.getText()+"%'";
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                txt_Kode.setText(""+rs.getString("Kode_Nasabah"));
                txt_Nama.setText(""+rs.getString("Nama"));
                combo_duit.setSelectedItem(""+rs.getString("Duit_kredit"));
            }
            st.close();
            rs.close();
            txt_Kode.setEnabled(false);
            
        } catch (Exception e) {
        }
    }
    
    //menghapus ???
    void bersihTextfield(){
        txt_Kode.setText("");
        txt_Nama.setText("");
        combo_duit.setSelectedIndex(0);
        txt_Cari.setText("");
    }
         
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_Kode = new javax.swing.JTextField();
        txt_Nama = new javax.swing.JTextField();
        btn_Save = new javax.swing.JButton();
        btn_Edit = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();
        btn_Cancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Nasabah = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txt_Cari = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        combo_duit = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Kode    :");

        jLabel2.setText("Nama   :");

        btn_Save.setText("Save");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        btn_Edit.setText("Edit");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });

        btn_Delete.setText("Delete");
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });

        btn_Cancel.setText("Cancel");
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelActionPerformed(evt);
            }
        });

        tbl_Nasabah.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode Nasabah", "Nama Nasabah", "Kredit Yang Diminta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Nasabah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_NasabahMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_NasabahMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Nasabah);

        jLabel3.setText("Cari :");

        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        jLabel4.setText("Kredit Yang Diminta");

        combo_duit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pilih", "11.000.000", "20.000.000", "30.000.000", "40.000.000", "50.000.000", "60.000.000", "70.000.000", "80.000.000", "90.000.000", "100.000.000", "110.000.000", "120.000.000", "130.000.000", "140.000.000", "150.000.000", "160.000.000", "170.000.000", "180.000.000", "190.000.000", "200.000.000", "250.000.000", "300.000.000", "400.000.000", "500.000.000" }));
        combo_duit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_duitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_Kode)
                                    .addComponent(txt_Nama, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_Save)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Edit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Delete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Cancel)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txt_Cari, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(combo_duit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(btn_search)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Kode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(combo_duit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Save)
                    .addComponent(btn_Edit)
                    .addComponent(btn_Delete)
                    .addComponent(btn_Cancel)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        simpan();
        bersihTabel();
        isiTabel();
        bersihTextfield();
        btn_Edit.setEnabled(false);
        btn_Delete.setEnabled(false);
        btn_Save.setEnabled(true);
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void tbl_NasabahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NasabahMouseClicked
        // TODO add your handling code here:
        /*int pil1=0;
        String duit;
            
            pil1=combo_duit.getSelectedIndex();
            if (pil1==0) { duit = "0";}
            if (pil1==1) { duit = "11.000.000";}
            if (pil1==2) { duit = "20.000.000";}
            if (pil1==3) { duit = "30.000.000";}
            if (pil1==4) { duit = "40.000.000";}
            if (pil1==5) { duit = "50.000.000";}
            if (pil1==6) { duit = "60.000.000";}
            if (pil1==7) { duit = "70.000.000";}
            if (pil1==8) { duit = "80.000.000";}
            if (pil1==9) { duit = "90.000.000";}
            if (pil1==10) { duit = "100.000.000";}
            if (pil1==11) { duit = "110.000.000";}
            if (pil1==12) { duit = "120.000.000";}
            if (pil1==13) { duit = "130.000.000";}
            if (pil1==14) { duit = "140.000.000";}
            if (pil1==15) { duit = "150.000.000";}
            if (pil1==16) { duit = "160.000.000";}
            if (pil1==17) { duit = "170.000.000";}
            if (pil1==18) { duit = "180.000.000";}
            if (pil1==19) { duit = "190.000.000";}
            if (pil1==20) { duit = "200.000.000";}
            if (pil1==21) { duit = "250.000.000";}
            if (pil1==22) { duit = "300.000.000";}
            if (pil1==23) { duit = "400.000.000";}
            if (pil1==24) { duit = "500.000.000";}*/
            
        DefaultTableModel model = (DefaultTableModel)tbl_Nasabah.getModel();
        int selectedRowIndex    = tbl_Nasabah.getSelectedRow();
        
        txt_Kode.setText(model.getValueAt(selectedRowIndex, 0).toString());
        txt_Nama.setText(model.getValueAt(selectedRowIndex, 1).toString());
        combo_duit.setSelectedItem(model.getValueAt(selectedRowIndex, 2).toString());
        btn_Save.setEnabled(false);
        txt_Kode.setEnabled(false);
        btn_Edit.setEnabled(true);
        btn_Delete.setEnabled(true);
    }//GEN-LAST:event_tbl_NasabahMouseClicked

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        // TODO add your handling code here:
        delete();
        bersihTabel();
        isiTabel();
        bersihTextfield();
        txt_Kode.setEnabled(true);
        btn_Save.setEnabled(true);
    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        edit();
        bersihTabel();
        isiTabel();
        bersihTextfield();
        btn_Save.setEnabled(true);
        txt_Kode.setEnabled(true);
    }//GEN-LAST:event_btn_EditActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
        search_nasabah();
        txt_Kode.setEnabled(false);
        btn_Delete.setEnabled(true);
        btn_Edit.setEnabled(true);
        btn_Save.setEnabled(false);
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
        // TODO add your handling code here:
        bersihTextfield();
        txt_Kode.setEnabled(true);
        btn_Edit.setEnabled(false);
        btn_Delete.setEnabled(false);
        btn_Save.setEnabled(true);
    }//GEN-LAST:event_btn_CancelActionPerformed

    private void tbl_NasabahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NasabahMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_NasabahMouseEntered

    private void combo_duitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_duitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_duitActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Input_Nasabah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Input_Nasabah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Input_Nasabah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Input_Nasabah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Input_Nasabah().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Cancel;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_Edit;
    private javax.swing.JButton btn_Save;
    private javax.swing.JButton btn_search;
    private javax.swing.JComboBox combo_duit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_Nasabah;
    private javax.swing.JTextField txt_Cari;
    private javax.swing.JTextField txt_Kode;
    private javax.swing.JTextField txt_Nama;
    // End of variables declaration//GEN-END:variables
}
