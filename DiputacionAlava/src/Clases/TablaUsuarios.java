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
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author iBaD
 */
public class TablaUsuarios {
    
    private Connection con;

    public TablaUsuarios(Connection con) {
        this.con = con;
    }
    
    public Usuario BuscarUsuario(String usuario)throws Exception {
        String plantilla = "SELECT * FROM USUARIOS WHERE USUARIO=?;";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setString(1, usuario);
        ResultSet consulta = ps.executeQuery();
        if(consulta.next()){
            Usuario u = new Usuario();
            u.setUsuario(consulta.getString("usuario"));
            u.setPass(consulta.getString("pass"));
            u.setAdmin(consulta.getBoolean("admin"));
            return u;
        }else{
            return null;
        }
    }
    
    public void Insertar(Usuario u) throws Exception{
        String plantilla = "INSERT INTO USUARIOS VALUES (?,?,?);";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setString(1, u.getUsuario());
        String passEncriptada = DigestUtils.md5Hex(u.getPass());
        ps.setString(2, passEncriptada);
        ps.setBoolean(3, u.isAdmin());
        ps.execute();
        ps.close();
    }
    
    public ArrayList<Usuario> UsuariosNoAdmin() throws Exception{
        String plantilla = "SELECT * FROM USUARIOS WHERE ADMIN=FALSE;";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ResultSet consulta = ps.executeQuery();
        ArrayList<Usuario> usuarios=new ArrayList();
        while(consulta.next()){
            Usuario u = new Usuario();
            u.setUsuario(consulta.getString("usuario"));
            u.setPass(consulta.getString("pass"));
            u.setAdmin(consulta.getBoolean("admin"));
            usuarios.add(u);
        }
        return usuarios;
    }
    
    public void ConvertirEnAdmin(String usuario) throws Exception{
        String plantilla = "UPDATE USUARIOS SET ADMIN = TRUE WHERE USUARIO = ?;";
        PreparedStatement ps = con.prepareStatement(plantilla);
        ps.setString(1, usuario);
        ps.execute();
        ps.close();
    }
}
