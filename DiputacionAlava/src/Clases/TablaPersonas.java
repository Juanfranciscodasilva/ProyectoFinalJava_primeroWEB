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
public class TablaPersonas {
    Connection con;

    public TablaPersonas(Connection con) {
        this.con = con;
    }
    
    public Persona BuscarPersona(Persona p) throws Exception{
        String plantilla = "SELECT * FROM PERSONAS WHERE UPPER(NOMBRE)=? AND UPPER(APELLIDO1)=? AND UPPER(APELLIDO2)=? AND FECHA_NAC=?;";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setString(1, p.getNombre().toUpperCase());
        ps.setString(2, p.getApellido1().toUpperCase());
        ps.setString(3, p.getApellido2().toUpperCase());
        ps.setDate(4, java.sql.Date.valueOf(p.getFechaNacimiento()));
        ResultSet consulta = ps.executeQuery();
        if(consulta.next()){
            p.setId(consulta.getInt("ID"));
            p.setEmail(consulta.getString("EMAIL"));
            p.setDireccion(consulta.getString("DIRECCION"));
            p.setTelefono(consulta.getString("TELEFONO"));
            return p;
        }
        else{
            return null;
        }
    }
    
    public void Insertar(Persona p) throws Exception{
        String plantilla = "INSERT INTO PERSONAS VALUES (NULL,?,?,?,?,?,?,?);";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getApellido1());
        ps.setString(3, p.getApellido2());
        ps.setDate(4, java.sql.Date.valueOf(p.getFechaNacimiento()));
        ps.setString(5, p.getTelefono());
        ps.setString(6, p.getEmail());
        ps.setString(7, p.getDireccion());
        ps.execute();
        ps.close();
    }
    
    public int BuscarCodigo(Persona p) throws Exception{
        String plantilla = "SELECT ID FROM PERSONAS WHERE UPPER(NOMBRE)=? AND UPPER(APELLIDO1)=? AND UPPER(APELLIDO2)=? AND FECHA_NAC=?;";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setString(1, p.getNombre().toUpperCase());
        ps.setString(2, p.getApellido1().toUpperCase());
        ps.setString(3, p.getApellido2().toUpperCase());
        ps.setDate(4, java.sql.Date.valueOf(p.getFechaNacimiento()));
        ResultSet consulta = ps.executeQuery();
        if(consulta.next()){
          return consulta.getInt("ID");  
        }else{
            return -1;
        }
        
    }
    
    public void EliminarPersona(int id) throws Exception{
        String plantilla = "DELETE FROM PERSONAS WHERE ID=?;";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setInt(1,id);
        int n = ps.executeUpdate();
        ps.close();
        if(n!=1){
            System.out.print("No se ha eliminado a ninguna persona");
        }
    }
    
    public ArrayList<Persona> BuscarListaDePersonas(ArrayList<Integer> codigos) throws Exception{
        String plantilla = "SELECT * FROM PERSONAS WHERE id=?;";
        ArrayList<Persona> personas = new ArrayList();
        for(int x=0;x<codigos.size();x++){
            PreparedStatement ps = con.prepareStatement(plantilla);
            ps.setInt(1, codigos.get(x));
            ResultSet consulta = ps.executeQuery();
            if(consulta.next()){
                Persona p = new Persona();
                p.setId(consulta.getInt("ID"));
                p.setNombre(consulta.getString("NOMBRE"));
                p.setApellido1(consulta.getString("APELLIDO1"));
                p.setApellido2(consulta.getString("APELLIDO2"));
                p.setFechaNacimiento(consulta.getDate("FECHA_NAC").toLocalDate());
                p.setEmail(consulta.getString("EMAIL"));
                p.setDireccion(consulta.getString("DIRECCION"));
                p.setTelefono(consulta.getString("TELEFONO"));
                personas.add(p);
            }
        }
        return personas;
    }
    
    public void EliminarVariasPersonas(ArrayList<Integer> ids) throws Exception{
        String plantilla = "DELETE FROM PERSONAS WHERE ID=?;";
        for(int x=0;x<ids.size();x++){
           PreparedStatement ps = con.prepareStatement(plantilla);
            ps.setInt(1,ids.get(x));
            ps.execute();
            ps.close(); 
        }
        
    }
    
    public boolean HayPersonas() throws Exception{
        String plantilla = "SELECT * FROM PERSONAS;";
            PreparedStatement ps = con.prepareStatement(plantilla);
            ResultSet consulta = ps.executeQuery();
            if(consulta.next()){
                return true;
        }
        return false;
    }
    
}
