/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author iBaD
 */
public class TablaCampPer {
    
    Connection con;

    public TablaCampPer(Connection con) {
        this.con = con;
    }
    
    public void Insertar(Persona p, Campamento c)throws Exception{
        String plantilla = "INSERT INTO CAMP_PER VALUES (?,?);";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setInt(1, c.getId());
        ps.setInt(2, p.getId());
        ps.execute();
        ps.close();
    }
    
    public void EliminarCampamento(Campamento camp) throws Exception{
         String plantilla = "DELETE FROM CAMP_PER WHERE ID_CAMP = ?";
         PreparedStatement ps = con.prepareStatement(plantilla);
         ps.setInt(1, camp.getId());
         ps.execute();
         ps.close();
     }
    
    public ArrayList<Integer> BuscarCampamentos(int id_per) throws Exception{
        String plantilla = "SELECT ID_CAMP FROM CAMP_PER WHERE ID_PER = ?;";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setInt(1, id_per);
        ResultSet resultado = ps.executeQuery();
        ArrayList<Integer> codigos = new ArrayList();
        while(resultado.next()){
            codigos.add(resultado.getInt("id_camp"));
        }
        return codigos;
    }
    
    public void RetirarPersona(int id_camp, int id_per) throws Exception{
        String plantilla = "DELETE FROM CAMP_PER WHERE ID_CAMP = ? AND ID_PER = ?";
         PreparedStatement ps = con.prepareStatement(plantilla);
         ps.setInt(1, id_camp);
         ps.setInt(2, id_per);
         ps.execute();
         ps.close();
    }
    
    public boolean ComprobarCampamentosDeUnaPersona(int id_per) throws Exception{
            String plantilla = "SELECT * FROM CAMP_PER WHERE ID_PER = ?;";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setInt(1, id_per);
        ResultSet resultado = ps.executeQuery();
        if(resultado.next()){
            return true;
        }
        return false;
    }
    
    public ArrayList<Integer> BuscarPersonasCamp(int id_camp) throws Exception{
        String plantilla = "SELECT ID_PER FROM CAMP_PER WHERE ID_CAMP = ?;";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setInt(1, id_camp);
        ResultSet resultado = ps.executeQuery();
        ArrayList<Integer> codigos = new ArrayList();
        while(resultado.next()){
            codigos.add(resultado.getInt("id_per"));
        }
        return codigos;
    }
    
    public boolean ComprobarNumeroDeCampamentos(int id) throws Exception{
        String plantilla = "SELECT * FROM CAMP_PER WHERE ID_PER = ?;";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        int contador=0;
        while(resultado.next()){
            contador = contador +1;
        }
        if(contador<3){
            return true;
        }else{
            return false;
        }
    }
    
    public ArrayList<Integer> ComprobarCampamentosDeVariasPersonas(ArrayList<Integer> ids) throws Exception{
        String plantilla = "SELECT * FROM CAMP_PER WHERE ID_PER = ?;";  
        ArrayList<Integer> ids_eliminar = new ArrayList();
        for(int x=0;x<ids.size();x++){
          PreparedStatement ps = con.prepareStatement(plantilla);
          ps.setInt(1, ids.get(x));  
          ResultSet resultado = ps.executeQuery();
          if(!resultado.next()){
              ids_eliminar.add(ids.get(x));
            }
        }
       return ids_eliminar;
    }
}
