/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import org.xmldb.api.*;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;

/**
 *
 * @author Cristian
 */
public class InsertarXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        insertEmp(1,"Cristian","Ade","Desarrollador",1350);
        insertEmp(2,"Francisco","Frailes","Administrativo",1000);
        insertEmp(3,"Maria","Méndez","Desarrollador",1500);
        insertEmp(4,"Melchor","Fuentes","Analista",1300);
        insertEmp(5,"Cristina","Jiménez","Gerente",1700);      
    }    
     
    private static void insertEmp(int cod,String nom, String ape, String puesto, 
            int sal ) throws Exception{
        Class driverClass = Class.forName("org.exist.xmldb.DatabaseImpl"); 
        Database bd = (Database) driverClass.newInstance(); 
        DatabaseManager.registerDatabase(bd); 

        //definicion de url, user y password
        String URL = 
            "xmldb:exist://localhost:8080/exist/xmlrpc/db/empleados";
        String user = "admin";
        //Aquí pondremos nuestra contraseña
        String pass = "";
        Collection colec = DatabaseManager.getCollection(URL,user,pass);
        
        
        
        //si el objeto Collection logra instanciarse, se permite crear la colección
        if(colec!=null){
            XPathQueryService s = (XPathQueryService) colec
                    .getService("XPathQueryService", "1.0");
            
            String insert = "update insert"
                    + "\n<empleado>"
                    + "\n\t<codEmpleado>"+cod+"</codEmpleado>"
                    + "\n\t<nombre>"+nom+"</nombre>"
                    + "\n\t<apellidos>"+ape+"</apellidos>"
                    + "\n\t<puesto>"+puesto+"</puesto>"
                    + "\n\t<salario>"+sal+"</salario>"
                    + "\n</empleado>"
                    + "\n into /empleados";
            try {
                s.query(insert);
                System.out.println("Inserción realizada con éxito");
            } catch (Exception e) {
                System.out.println("Error al insertar: "+e);   
            }
            colec.close();
            System.out.println("Conexión cerrada");
        }
        else{
            System.out.println("La colección no existe");
        }
    
    }
    
}
