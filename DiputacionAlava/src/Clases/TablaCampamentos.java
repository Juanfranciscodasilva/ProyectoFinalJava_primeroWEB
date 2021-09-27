/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author iBaD
 */
public class TablaCampamentos {
     Connection con;

    public TablaCampamentos(Connection con) {
        this.con = con;
    }
     
     public void Insertar(Campamento c) throws Exception{
         String plantilla = "INSERT INTO CAMPAMENTOS VALUES (NULL,UPPER(?),UPPER(?),?,?,?,?,?);";
         PreparedStatement ps = con.prepareStatement(plantilla);
         ps.setString(1, c.getNombre());
         ps.setString(2, c.getLugar());
         ps.setDate(3, java.sql.Date.valueOf(c.getFechaI()));
         ps.setDate(4, java.sql.Date.valueOf(c.getFechaF()));
         ps.setInt(5, c.getEdadMin());
         ps.setInt(6, c.getEdadMax());
         ps.setInt(7, c.getCapacidad());
         ps.execute();
         ps.close();
     }
     
     public void Modificar(Campamento c) throws Exception{
         String plantilla = "UPDATE CAMPAMENTOS SET NOMBRE=UPPER(?),LUGAR=UPPER(?),FECHA_INICIO=?,FECHA_FIN=?,MIN_EDAD=?,MAX_EDAD=?,CAPACIDAD=? WHERE ID=?;";
         PreparedStatement ps = con.prepareStatement(plantilla);
         ps.setString(1, c.getNombre());
         ps.setString(2, c.getLugar());
         ps.setDate(3, java.sql.Date.valueOf(c.getFechaI()));
         ps.setDate(4, java.sql.Date.valueOf(c.getFechaF()));
         ps.setInt(5, c.getEdadMin());
         ps.setInt(6, c.getEdadMax());
         ps.setInt(7, c.getCapacidad());
         ps.setInt(8, c.getId());
         ps.execute();
         ps.close();
     }
     
     public ArrayList<Campamento> BuscarCampamentos(ArrayList<Campamento> campamentos) throws Exception{
         String plantilla = "SELECT * FROM CAMPAMENTOS;";
         PreparedStatement ps = con.prepareStatement(plantilla);
         ResultSet resultado = ps.executeQuery();
         while(resultado.next()){
              Campamento e = new Campamento();
               
                e.setId(resultado.getInt("ID"));
                e.setNombre(resultado.getString("nombre"));
                e.setLugar(resultado.getString("lugar"));
                LocalDate FechaI = resultado.getDate("fecha_inicio").toLocalDate();
                LocalDate FechaF = resultado.getDate("fecha_fin").toLocalDate();
                e.setFechaI(FechaI);
                e.setFechaF(FechaF);
                e.setEdadMin(resultado.getInt("min_edad"));
                e.setEdadMax(resultado.getInt("max_edad"));
                e.setCapacidad(resultado.getInt("capacidad"));
                campamentos.add(e);
         }
         return campamentos;
     }
     
     public void EliminarCampamento(Campamento camp) throws Exception{
         String plantilla = "DELETE FROM CAMPAMENTOS WHERE ID = ?";
         PreparedStatement ps = con.prepareStatement(plantilla);
         ps.setInt(1, camp.getId());
         ps.execute();
         ps.close();
     }
     
     public ArrayList<Campamento> BuscarCampamentosPersona(ArrayList<Integer> ids) throws Exception{
         ArrayList<Campamento> campamentos = new ArrayList();
         String plantilla = "SELECT * FROM CAMPAMENTOS WHERE ID = ?;";
         for(int x=0;x<ids.size();x++){
             PreparedStatement ps = con.prepareStatement(plantilla);
             ps.setInt(1, ids.get(x));
             ResultSet resultado = ps.executeQuery();
             if(resultado.next()){
                 Campamento camp = new Campamento();
                 camp.setId(resultado.getInt("ID"));
                 camp.setNombre(resultado.getString("nombre"));
                 camp.setLugar(resultado.getString("lugar"));
                 camp.setFechaI(resultado.getDate("fecha_inicio").toLocalDate());
                 camp.setFechaF(resultado.getDate("fecha_fin").toLocalDate());
                 camp.setEdadMin(resultado.getInt("min_edad"));
                 camp.setEdadMax(resultado.getInt("max_edad"));
                 camp.setCapacidad(resultado.getInt("capacidad"));
                 campamentos.add(camp);
             }
         }
         return campamentos;
     }
}
