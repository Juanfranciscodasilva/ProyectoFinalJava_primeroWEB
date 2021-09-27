/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacionalava;

import Ventanas.*;
import Clases.*;
import java.util.ArrayList;
import javax.swing.JComboBox;
public class Main {
       private static IniciarSesion insesion = new IniciarSesion();
       private static RegistrarUsuario ru;
       private static VPrincipal p;
       private static CrearCampamento cc;
       private static ModificarEliminarCamp mec;
       private static InscribirPersona ip;
       private static RetirarPersona rp;
       private static GenteInscrita gi;
       private static DarPermisos dp;
       private static BaseDeDatos bd = new BaseDeDatos();
       private static TablaUsuarios tablaU;
       private static TablaCampamentos tablaC;
       private static TablaPersonas tablaP;
       private static TablaCampPer tablaCP;
       private static ArrayList<Campamento> campamentos;
    
    public static void main(String[] args) {
        bd.conectar();
         tablaU = new TablaUsuarios(bd.getCon());
         tablaC = new TablaCampamentos(bd.getCon());
         tablaP = new TablaPersonas(bd.getCon());
         tablaCP = new TablaCampPer(bd.getCon());
        insesion.setVisible(true);
    }

    public static Usuario BuscarUsuario(String usuario) throws Exception {
        tablaU = new TablaUsuarios(bd.getCon());
        Usuario u = tablaU.BuscarUsuario(usuario);
        return u;
    }
    
    public static void AbrirIniciarSesionAdmin(int opcion){
        insesion.dispose();
        insesion = new IniciarSesion();
        insesion.IniciarAdmin(opcion);
        insesion.setVisible(true);
    }
    
    public static void ReiniciarIniciarSesion(){
        insesion.dispose();
        insesion = new IniciarSesion();
        insesion.setVisible(true);
    }
    
    public static void AbrirRegistrarUsuario(){
        insesion.dispose();
        ru = new RegistrarUsuario();
        ru.setVisible(true);
    }
    
    public static void InsertarUsuario(Usuario u) throws Exception{
        tablaU.Insertar(u);
        ru.dispose();
        insesion = new IniciarSesion();
        insesion.setVisible(true);
    }
    
    public static void CerrarRegistrarUsuario(){
        ru.dispose();
        insesion = new IniciarSesion();
        insesion.setVisible(true);
    }
    
    public static void AbrirDarPermisos(){
        insesion.dispose();
        dp = new DarPermisos();
        dp.setVisible(true);
    }
    
    public static JComboBox<String> RellenarComboUsuarios(JComboBox combo) throws Exception{
        ArrayList<Usuario> usuarios = tablaU.UsuariosNoAdmin();
        if(usuarios.isEmpty()){
            combo.addItem("---No hay usuarios sin admin---");
        }else{
            try{
             for(int x=0;x<usuarios.size();x++){
             String nombre = usuarios.get(x).getUsuario();
             combo.addItem(nombre);
             }
         }catch(Exception e){
                     System.out.println("ha ocurrido un error "+ e.getMessage());
                     } 
        }
            
         return combo; 
    }
    
    public static void CerrarDarPermisos(){
        dp.dispose();
        insesion = new IniciarSesion();
        insesion.setVisible(true);
    }
    
    public static void ConvertirEnAdmin(String usuario) throws Exception{
        tablaU.ConvertirEnAdmin(usuario);
        CerrarDarPermisos();
    }

    public static void IniciarAplicacion() {
        insesion.dispose();
        p = new VPrincipal();
        p.setVisible(true);
    }

    public static void CerrarPrograma() {
        bd.desconcetar();
        System.exit(0);
    }

    public static void CerrarSesion() {
        p.dispose();
        insesion = new IniciarSesion();
        insesion.setVisible(true);
    }
    
    public static void CrearCampamento(){
        cc = new CrearCampamento();
        p.dispose();
        cc.Crear();
        cc.setVisible(true);
    }
    
    public static void AbrirEliminarModificarCamp(int opcion){
        BuscarCampamentos();
        if(campamentos.isEmpty()){
            p.NoCampamentos();
        }else{
            mec = new ModificarEliminarCamp();
            p.dispose();
            if(opcion==0){
                mec.Eliminar();
            }else if(opcion==1){
                mec.Modificar();
            }else if(opcion==2){
                mec.VisualizarCampamentos();
            }
            mec.setVisible(true);  
        }
    }

    public static void CerrarModificarEliminarCamp() {
        mec.dispose();
        p = new VPrincipal();
        p.setVisible(true);
    }

    public static void CerrarCrearCampamento(int opcion) {
        cc.dispose();
        if(opcion==1){
            mec.setVisible(true);
        }else{
            p = new VPrincipal();
            p.setVisible(true); 
        }
    }
    
    public static void ModificarCamp(Campamento c){
        cc = new CrearCampamento();
        mec.setVisible(false);
        cc.Modificar(c);
        cc.setVisible(true);
    }
    
    public static void InsertarNuevoCampamento(Campamento c) throws Exception{
        tablaC.Insertar(c);
        cc.dispose();
        p = new VPrincipal();
        p.setVisible(true);
    }

    public static void ModificarTablaCampamentos(Campamento c) throws Exception {
        tablaC.Modificar(c);
        cc.dispose();
        CerrarModificarEliminarCamp();
    }
    
    public static void BuscarCampamentos(){
        campamentos = new ArrayList();
           try {
               tablaC.BuscarCampamentos(campamentos);
           } catch (Exception ex) {
               System.out.println("Ha ocurrido un error "+ex.getMessage());
           }
    }
    
    public static JComboBox<String> RellenarComboCamp(JComboBox combo) throws Exception{
        combo.removeAllItems();
        combo.addItem("-----Elegir campamento-----");
            try{
             for(int x=0;x<campamentos.size();x++){
             String nombre = campamentos.get(x).getNombre();
             combo.addItem(nombre);
             }
         }catch(Exception e){
                     System.out.println("ha ocurrido un error "+ e.getMessage());
                     } 
         return combo;    
    }
    
    public static Campamento ExtraerDatosCampamento(String nombre){
        int x;
        for(x=0;x<campamentos.size()&&!campamentos.get(x).getNombre().equals(nombre);x++){}
        return campamentos.get(x);
    }
    
    public static void EliminarCamp(Campamento camp) throws Exception{
        ArrayList<Integer> codigos_personas = tablaCP.BuscarPersonasCamp(camp.getId());
        tablaCP.EliminarCampamento(camp);
        tablaC.EliminarCampamento(camp);
        ComprobarSiHayPersonasSinCampamentos(codigos_personas);
        CerrarModificarEliminarCamp();
    }
    
    public static void AbrirInscribir(){
        BuscarCampamentos();
        if(campamentos.isEmpty()){
            p.NoCampamentos();
        }else{
            p.dispose();
            ip = new InscribirPersona();
            ip.setVisible(true);
        }
       
    }

    public static void BuscarPersona(Persona p,int opcion) throws Exception {
        Persona pe = tablaP.BuscarPersona(p);
        if(opcion==0){
            if(pe==null){
           ip.PersonaNoEncontrada();
            }else{
                ip.PersonaEncontrada(p);
            }
        }else if(opcion==1){
            if(pe==null){
           rp.PersonaNoEncontrada();
            }else{
                rp.PersonaEncontrada(p);
            }
        }  
    }
    
    public static void CerrarInscribir(){
        ip.dispose();
        p = new VPrincipal();
        p.setVisible(true);
    }
    
    public static void InsertarNuevaPersona(Persona pe) throws Exception{
        tablaP.Insertar(pe);
    }
    
    public static int ObtenerCodigoPersona(Persona p) throws Exception{
        return tablaP.BuscarCodigo(p);
    }
    
    public static void InsertarPerEnCamp(Persona p , Campamento c) throws Exception{
        tablaCP.Insertar(p,c);
        CerrarInscribir();
    }
    
    public static void AbrirRetirarPersona(int opcion){
        BuscarCampamentos();
        if(campamentos.isEmpty()){
            p.NoCampamentos();
        }else 
            if(!ComprobarSiHayPersonas()){
                p.NoPersonas();
            }else{
           rp = new RetirarPersona();
            p.dispose();
            if(opcion==1){
                rp.VerInscripciones(opcion);
            }
            rp.setVisible(true); 
        }
        
    }
    
    public static boolean ComprobarSiHayPersonas(){
       try{
         return tablaP.HayPersonas(); 
       }catch(Exception e){
           System.out.println("Problemas al comprobar si hay personas en la base de datos "+e.getMessage());
           return false;
       }
    }
    
    public static void CerrarRetirarPersona(){
        rp.dispose();
        p = new VPrincipal();
        p.setVisible(true);
    }
    
    public static JComboBox<String> RellenarComboCampamentosPersona(JComboBox combo, int id){
        ArrayList<Integer> ids = BuscarIdsDeCampamentosDePersona(id);
        ArrayList<Campamento> camps = BuscarCampamentosSegunIdsConPersonas(ids);
        combo.removeAllItems();
        if(!camps.isEmpty()){
            try{
             for(int x=0;x<camps.size();x++){
             combo.addItem(camps.get(x).getNombre());
             }
         }catch(Exception e){
                     System.out.println("ha ocurrido un error "+ e.getMessage());
                     } 
        }else{
            EliminarPersona(id);
            rp.PersonaEliminada();
            CerrarRetirarPersona();
        }    
        return combo; 
    }
    
    public static ArrayList<Integer> BuscarIdsDeCampamentosDePersona(int id){
        ArrayList<Integer> ids = new ArrayList();
        try{
            ids = tablaCP.BuscarCampamentos(id);
        }catch(Exception e){
            System.out.println("Ha ocurrido un error "+e.getMessage());
        }
        return ids;
    }
    
    public static ArrayList<Campamento> BuscarCampamentosSegunIdsConPersonas(ArrayList<Integer> ids){
        ArrayList<Campamento> camps = new ArrayList();
        try{
            camps = tablaC.BuscarCampamentosPersona(ids);
        }catch(Exception e){
            System.out.println("Ha ocurrido un error "+e.getMessage());
        }
        rp.setCampamentos(camps);
        return camps;
    }
    
    public static void ResetearVentanaRetirar(int opcion){
        rp.dispose();
        rp = new RetirarPersona();
        if(opcion==1){
            rp.VerInscripciones(opcion);
        }
        rp.setVisible(true);
    }   
    
    public static void RetirarPersonaDeCampamento(int id_camp , int id_per) throws Exception{
        tablaCP.RetirarPersona(id_camp,id_per);
        rp.PersonaRetirada();
        if(!ComprobarCampamentosPersona(id_per)){
            EliminarPersona(id_per);
            rp.PersonaEliminada();
        }
        rp.dispose();
        p = new VPrincipal();
        p.setVisible(true);
    }
    
    public static boolean ComprobarCampamentosPersona(int id){
        try{
            if(tablaCP.ComprobarCampamentosDeUnaPersona(id)){
                return true;
            }else{
                return false;
            }  
        }catch(Exception e){
            System.out.print("Ha ocurrido un error al comprobar los campamentos de esta persona"+e.getMessage());
            return true;
        }
    }
    
    public static void EliminarPersona(int id){
            try{
               tablaP.EliminarPersona(id); 
            }catch(Exception e){
                System.out.print("Ha ocurrido un error al intentar eliminar a la persona"+e.getMessage());
            } 
    }
    
    public static void AbrirGenteInscrita(){
        BuscarCampamentos();
        if(campamentos.isEmpty()){
            p.NoCampamentos();
        }else{
          gi = new GenteInscrita();
            p.dispose();
            gi.setVisible(true);  
        }
    }
    
    public static void CerrarGenteInscrita(){
        gi.dispose();
        p = new VPrincipal();
        p.setVisible(true);
    }
    
    public static String BuscarPersonasCampamento(int id_camp) throws Exception{
        ArrayList<Integer> per_codigos = tablaCP.BuscarPersonasCamp(id_camp);
        ArrayList<Persona> personas = tablaP.BuscarListaDePersonas(per_codigos);
        String info="";
        if(personas.isEmpty()){
            return "No hay ninguna persona inscrita en este campamento";
        }else{
            for(int x=0;x<personas.size();x++){
            info = info + personas.get(x).toString()+"\n\n" ;
            }
            return info; 
        }
       
    }
    
    public static boolean ComprobarNumeroDeCampamentosPersona(int id){
        try{
            return tablaCP.ComprobarNumeroDeCampamentos(id);
        }catch(Exception e){
            System.out.print("Ha ocurrido un problema "+e.getMessage());
            return false;
        }
    }
    
    public static void ComprobarSiHayPersonasSinCampamentos(ArrayList<Integer> ids){
        try{
            ArrayList <Integer> ids_eliminar = tablaCP.ComprobarCampamentosDeVariasPersonas(ids);
            tablaP.EliminarVariasPersonas(ids_eliminar);
        }catch(Exception e){
            System.out.print("Ha ocurrido un error al revisar si hay personas sin campamentos "+e.getMessage());
        }
    }
}
