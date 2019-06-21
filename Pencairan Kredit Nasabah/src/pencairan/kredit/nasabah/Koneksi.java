/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pencairan.kredit.nasabah;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author BIMO
 */
public class Koneksi {
    public Connection bukakoneksi() throws SQLException  {
        Connection con = null;
     	try {
            Class.forName("com.mysql.jdbc.Driver");
      	    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_pencairan_kredit","root","");
            
            return con;
        }catch (SQLException se) {
      	    System.out.println("No Connection Open");
      	    return null;
    	}
        catch (Exception ex) {
      	   System.out.println("Could not open connection");
      	   return null;
    	}
  	}
}
