# Host: localhost  (Version 5.6.26)
# Date: 2017-12-15 19:51:05
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "nasabah"
#

DROP TABLE IF EXISTS `nasabah`;
CREATE TABLE `nasabah` (
  `Kode_Nasabah` varchar(17) NOT NULL,
  `Nama` varchar(50) NOT NULL,
  `Duit_kredit` varchar(20) NOT NULL,
  PRIMARY KEY (`Kode_Nasabah`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "nasabah"
#

INSERT INTO `nasabah` VALUES ('A001','Abid Aqila Pranaja','40.000.000'),('A002','Abinaya Alexi','40.000.000'),('A003','Abqari Agam Agler','40.000.000'),('A004','Abraham Achilles','40.000.000'),('A005','Dareen Achazia','40.000.000'),('A006','Darian Aquila Evarado','40.000.000');

#
# Structure for table "penilaian"
#

DROP TABLE IF EXISTS `penilaian`;
CREATE TABLE `penilaian` (
  `Id_Penilaian` int(10) NOT NULL AUTO_INCREMENT,
  `Kode_Nasabah` varchar(17) NOT NULL,
  `Status_Rumah` varchar(20) DEFAULT NULL,
  `Jumlah_tanggungan` int(5) DEFAULT NULL,
  `Gaji` int(50) DEFAULT NULL,
  `Hasil_Interview` varchar(10) DEFAULT NULL,
  `Jaminan` varchar(30) NOT NULL,
  `Sertifikat` varchar(20) NOT NULL,
  `Jenis_Kendaraan` varchar(30) NOT NULL,
  `Harga` int(20) NOT NULL,
  `Nilai_S` double(10,5) NOT NULL,
  `Nilai_V` double(10,5) DEFAULT NULL,
  PRIMARY KEY (`Id_Penilaian`),
  UNIQUE KEY `Kode_Nasabah` (`Kode_Nasabah`),
  KEY `Kode_Nasabah_2` (`Kode_Nasabah`),
  KEY `Kode_Nasabah_3` (`Kode_Nasabah`),
  CONSTRAINT `penilaian_ibfk_1` FOREIGN KEY (`Kode_Nasabah`) REFERENCES `nasabah` (`Kode_Nasabah`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

#
# Data for table "penilaian"
#

INSERT INTO `penilaian` VALUES (19,'A001','Dinas',3,3500000,'Baik','Sertifikat Tanah','SHM','-',0,53.67000,NULL),(20,'A002','Milik Keluarga',2,3000000,'Tidak Baik','Sertifikat Tanah','Induk','-',0,0.00000,NULL),(21,'A003','Dinas',5,5000000,'Baik','Sertifikat Tanah','Induk','-',0,44.45908,NULL),(22,'A004','Milik Pribadi',4,3000000,'Baik','Sertifikat Tanah','Induk','-',0,49.98982,NULL),(23,'A005','Milik Keluarga',6,4000000,'Baik','Sertifikat Tanah','Pecahan','-',0,38.85120,NULL),(24,'A006','Dinas',3,5000000,'Baik','Sertifikat Tanah','SHM','-',0,57.99705,NULL);

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `Username` varchar(50) NOT NULL,
  `Pass` varchar(20) NOT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "user"
#

INSERT INTO `user` VALUES ('admin','admin');
