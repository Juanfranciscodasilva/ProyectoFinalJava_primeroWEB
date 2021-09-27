/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author iBaD
 */
public class BaseDeDatos {
    
     private Connection con;
    
     public void conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String bd="diputacionalava";
            String url="jdbc:mysql://localhost:3307/"+bd;
            String login="root";
            String pass = "usbw";
            con = DriverManager.getConnection(url, login, pass);
            
            if(con==null){
                throw new Exception();
            }
        }
        catch(Exception e){
             System.out.print("Problemas en la conexión");
        }
    }
     
         public void desconcetar(){
        try{
            con.close();
        }
        catch(Exception e){
            System.out.print("Problemas en la conexión");
        }
    }
         
         public Connection getCon(){
        return con;
    }
}
