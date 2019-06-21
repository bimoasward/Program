/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pencairan.kredit.nasabah;

/**
 *
 * @author BIMO
 */
public class perhitungan_wp {
    
    //inisialisasi bobot
    double nilai_S;
    int Bobot_Status_Rumah = 5;
    int Bobot_Cost_Jumlah_Tanggungan = 3;
    int Bobot_Gaji = 5;
    int Bobot_Sertifikat_Tanah = 5;
    int Bobot_Interview = 5;
    
    Form_Input_Nilai F_I_N;
    
    N_Jaminan N_J;
    N_Rumah N_R;
    N_Interview N_I;
    
    double nilai_tanah, nilai_rumah, nilai_interview;
    
    
    //menghitung bobot
    public double cari_C() {
        double C = Bobot_Status_Rumah + Bobot_Cost_Jumlah_Tanggungan + Bobot_Gaji + Bobot_Sertifikat_Tanah + Bobot_Interview;
        return C;
    }
    
    //method penyamaan bobot = 1
    public double cari_C1(double C) {
        double C1 = Bobot_Status_Rumah / C;
        return C1;
     }
    
    public double cari_C2(double C) {
        double C2 = (Bobot_Cost_Jumlah_Tanggungan-(Bobot_Cost_Jumlah_Tanggungan*2)) / C;
        return C2;
     }
    
    public double cari_C3(double C) {
        double C3 = Bobot_Gaji / C;
        return C3;
     }
    
    public double cari_C4(double C) {
        double C4 = Bobot_Sertifikat_Tanah / C;
        return C4;
     }
    
    public double cari_C6(double C) {
        double C6 = Bobot_Interview / C;
        return C6;
     }
    
    //apabila ang dipilih sertifikat tanah maka akan ada nilai
    class N_Jaminan{
        public String Nilai_Tanah;
        public String Nilai_Kendaraan;
        public double Harga;
        
        private double nilai(){
            if(("SHM".equals(Nilai_Tanah))&&("-".equals(Nilai_Kendaraan))){
                return 5;
            }
            else if(("SHGB".equals(Nilai_Tanah))&&("-".equals(Nilai_Kendaraan))){
                return 5;
            }
            else if(("Induk".equals(Nilai_Tanah))&&("-".equals(Nilai_Kendaraan))){
                return 2;
            }
            else if(("Pecahan".equals(Nilai_Tanah))&&("-".equals(Nilai_Kendaraan))){
                return 1;
            }
            else {
                return Harga;
            }
        }
        
    }
    
    //membuat nilai rumah
    class N_Rumah{
        public String Nilai_Rumah;
        
        private double nilai(){
            if(("Milik Keluarga".equals(Nilai_Rumah))){
                return 3;
            }
            else if(("Dinas".equals(Nilai_Rumah))){
                return 2;
            }
            else {      
                return 5;
            }
        }
    }
    
    //membuat nilai interview
    class N_Interview{
        public String Nilai_Interview;
        
        private double nilai(){
            if(("Baik".equals(Nilai_Interview))){
                return 5;
            }
            else {
                return 0;
            }
        }
    }
    
    public void Hitung_Nilai_S (String status_rumah, double tanggungan, double gaji, String tanah, String kendaraan, double harga_kendaraan, String interview){
       N_J = new N_Jaminan();
       N_R = new N_Rumah();
       N_I = new N_Interview();
       
       N_J.Nilai_Tanah = tanah;
       N_J.Nilai_Kendaraan = kendaraan;
       N_J.Harga = harga_kendaraan;
       nilai_tanah = N_J.nilai();
       
       
       N_R.Nilai_Rumah = status_rumah;
       nilai_rumah = N_R.nilai();
       
       N_I.Nilai_Interview = interview;
       nilai_interview = N_I.nilai();
       
       
       
        perhitungan_wp perhitungan = new perhitungan_wp();
    
        F_I_N = new Form_Input_Nilai();
        
       double C, C1, C2, C3, C4, C5, C6;
       C = perhitungan.cari_C();
       
       C1 = perhitungan.cari_C1(C);
       C2 = perhitungan.cari_C2(C);
       C3 = perhitungan.cari_C3(C);
       C4 = perhitungan.cari_C4(C);
       C6 = perhitungan.cari_C6(C);
       
       
       nilai_S = (Math.pow(nilai_rumah, C1))*(Math.pow(tanggungan, C2))
                           *(Math.pow(gaji, C3))*(Math.pow(nilai_tanah, C4))
                           *(Math.pow(nilai_interview, C6));
    }
}
