/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pencairan.kredit.nasabah;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author BIMO
 */
public class Form_Perankingan extends javax.swing.JFrame {

    /**
     * Creates new form Form_Perankingan
     */
    
    Koneksi koneksi = new Koneksi();
    Object [] obj = new Object[7];
    private DefaultTableModel tblRanking;
    
    public Form_Perankingan() {
        initComponents();
        
        btn_ulang.setEnabled(false);
        btn_cetak.setEnabled(false);
        
        setTitle("Form Ranking");
        setResizable(false);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
         tblRanking = new DefaultTableModel(){
        
             @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
         tbl_ranking.setModel(tblRanking);
         
        tblRanking.addColumn("Ranking");
        tblRanking.addColumn("Kode Nasabah");
        tblRanking.addColumn("Nama");
        tblRanking.addColumn("Kredit");
        tblRanking.addColumn("Jaminan");
        tblRanking.addColumn("Nilai S");
        tblRanking.addColumn("Hasil V");
    }
    
    public void resetNo(){
		int brs = tblRanking.getRowCount();
		for(int i=0;i<brs;i++){
			String no = String.valueOf(i+1);
			tblRanking.setValueAt(no,i,0);
		}
    }
    
    public void isiTabel(){
        try{
            Connection con = koneksi.bukakoneksi();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM penilaian,nasabah where nasabah.Kode_Nasabah = penilaian.Kode_nasabah AND Jaminan = '"+combo_jaminan.getSelectedItem()+"' AND Duit_Kredit = '"+combo_kredit.getSelectedItem()+"' ORDER BY Nilai_V DESC ";
            ResultSet rs = st.executeQuery(sql);
            
            
            while(rs.next()){
                
                obj[1] = rs.getString("Kode_Nasabah");
                obj[2] = rs.getString("Nama");
                obj[3] = rs.getString("Duit_kredit");
                obj[4] = rs.getString("Jaminan");
                obj[5] = rs.getString("Nilai_S");
                obj[6] = rs.getString("Nilai_V");
                
                tblRanking.addRow(obj);
                resetNo();
                
            }
            st.close();
            rs.close();
        }catch(SQLException sqlexc){
            JOptionPane.showMessageDialog(null,sqlexc,"Koneksi Database",JOptionPane.WARNING_MESSAGE);
            System.exit(0);  
        }               
    }
    
    public void hapustable(){
        if (tblRanking.getRowCount() > 0) {
            for (int i = tblRanking.getRowCount() - 1; i > -1; i--) {
                tblRanking.removeRow(i);
            }
        }
        tblRanking.fireTableDataChanged();
    }
    
    public void ulang(){
        try{
            Koneksi ObjKoneksi = new Koneksi();
            Connection con = ObjKoneksi.bukakoneksi();
            Statement st = con.createStatement();
            String sql ="UPDATE penilaian SET Nilai_V = NULL";
            int vHasil = st.executeUpdate(sql);
            if(vHasil>0){
            }
            con.close();
        }
        catch(Exception e){
        }
    }
    
    public void Nilai_v(){
    double value = 0.0;
    
    try{Connection con = koneksi.bukakoneksi();
            Statement st = con.createStatement();
            String sql = "SELECT SUM(Nilai_S) FROM penilaian,nasabah where nasabah.Kode_Nasabah = penilaian.Kode_nasabah AND Jaminan = '"+combo_jaminan.getSelectedItem()+"' and Duit_Kredit = '"+combo_kredit.getSelectedItem()+"' ";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String sum = rs.getString(1);
            value += Double.parseDouble(sum);
            
            try{
                String coba = "SELECT count(*) FROM penilaian,nasabah where nasabah.Kode_Nasabah = penilaian.Kode_nasabah AND Jaminan = '"+combo_jaminan.getSelectedItem()+"' AND Duit_Kredit = '"+combo_kredit.getSelectedItem()+"'";
                ResultSet lanjut = st.executeQuery(coba);
                lanjut.next();
                int count = lanjut.getInt(1);
                Double hasil = 0.0;
                double[] S = new double[count];
                double[] V = new double[count];
            for(int i = 0; i < count;i++){
                
                try{
                    String tes = "SELECT Nilai_S FROM penilaian,nasabah where nasabah.Kode_Nasabah = penilaian.Kode_nasabah AND Jaminan = '"+combo_jaminan.getSelectedItem()+"' AND Duit_Kredit = '"+combo_kredit.getSelectedItem()+"' limit "+i+",1";
                    ResultSet selanjutnya = st.executeQuery(tes);
                    selanjutnya.next();
                    hasil = selanjutnya.getDouble(1);
                    S[i] = hasil;
                    V[i] = S[i]/value;
                    try{
                        String s = "UPDATE penilaian SET Nilai_V = '"+V[i]+"' WHERE Nilai_S = '"+S[i]+"'";
                        try (
                            PreparedStatement pstmt = con.prepareStatement(s)) { 
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }catch(Exception e){
                        
                    }
                }catch(Exception e){
                    
                }
            }
            }catch(SQLException e){
           
            }
       }catch(SQLException e){
           
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        combo_kredit = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_ranking = new javax.swing.JTable();
        btn_lihat = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        combo_jaminan = new javax.swing.JComboBox();
        btn_ulang = new javax.swing.JButton();
        btn_cetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Ranking");

        jLabel2.setText("Kredit Yang Diminta");

        combo_kredit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pilih", "11.000.000", "20.000.000", "30.000.000", "40.000.000", "50.000.000", "60.000.000", "70.000.000", "80.000.000", "90.000.000", "100.000.000", "110.000.000", "120.000.000", "130.000.000", "140.000.000", "150.000.000", "160.000.000", "170.000.000", "180.000.000", "190.000.000", "200.000.000", "250.000.000", "300.000.000", "400.000.000", "500.000.000" }));

        tbl_ranking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Kode", "Nama", "Kredit", "Jaminan", "Nilai S", "Nilai V"
            }
        ));
        jScrollPane1.setViewportView(tbl_ranking);

        btn_lihat.setText("Lihat");
        btn_lihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lihatActionPerformed(evt);
            }
        });

        jLabel3.setText("Jaminan");

        combo_jaminan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pilih", "Sertifikat Tanah", "Kendaraan" }));

        btn_ulang.setText("Ulangi");
        btn_ulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ulangActionPerformed(evt);
            }
        });

        btn_cetak.setText("Cetak");
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(281, 281, 281))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(37, 37, 37))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(91, 91, 91)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(combo_kredit, 0, 186, Short.MAX_VALUE)
                                    .addComponent(combo_jaminan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(167, 167, 167))))))
            .addGroup(layout.createSequentialGroup()
                .addGap(278, 278, 278)
                .addComponent(btn_lihat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ulang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cetak)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(combo_kredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(combo_jaminan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_lihat)
                    .addComponent(btn_ulang)
                    .addComponent(btn_cetak))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_lihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lihatActionPerformed
        // TODO add your handling code here:
        Nilai_v();
        isiTabel();
        
        btn_cetak.setEnabled(true);
        btn_lihat.setEnabled(false);
        btn_ulang.setEnabled(true);
    }//GEN-LAST:event_btn_lihatActionPerformed

    private void btn_ulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ulangActionPerformed
        // TODO add your handling code here:
        ulang();
        hapustable();
        btn_lihat.setEnabled(true);
        btn_ulang.setEnabled(false);
        combo_jaminan.setSelectedIndex(0);
        combo_kredit.setSelectedIndex(0);
    }//GEN-LAST:event_btn_ulangActionPerformed

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
        // TODO add your handling code here:
        try{
            HashMap parameter = new HashMap();
            parameter.put("Jaminan",combo_jaminan.getSelectedItem());
            parameter.put("Kredit",combo_kredit.getSelectedItem());
            Connection con = koneksi.bukakoneksi();
        
            File report_file = new File("./src/Laporan/Laporan.jasper");
            try{
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(report_file);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, con);
                JasperViewer jv = new JasperViewer(jasperPrint, false);
                jv.setVisible(true);
            }catch (JRException ex){
                System.out.println(ex);
            }
        }catch (Exception e){
         System.out.println(e);
        }
    }//GEN-LAST:event_btn_cetakActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Perankingan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Perankingan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Perankingan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Perankingan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Perankingan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_lihat;
    private javax.swing.JButton btn_ulang;
    private javax.swing.JComboBox combo_jaminan;
    private javax.swing.JComboBox combo_kredit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_ranking;
    // End of variables declaration//GEN-END:variables
}
