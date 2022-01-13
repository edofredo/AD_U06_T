
import java.io.File;
import org.xmldb.api.*;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
public class CrearColeccion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //carga del driver
        Class driverClass = Class.forName("org.exist.xmldb.DatabaseImpl");
        Database bd = (Database) driverClass.newInstance();
        DatabaseManager.registerDatabase(bd);

        //definicion de url, user y password
        String URL
                = "xmldb:exist://localhost:8080/exist/xmlrpc/db";
        String user = "admin";
        //Aquí pondremos nuestra contraseña
        String pass = "";
        Collection colec = DatabaseManager.getCollection(URL, user, pass);

        //si el objeto Collection logra instanciarse, se permite crear la colección
        if (colec != null) {
            CollectionManagementService mSer
                    = (CollectionManagementService) colec
                            .getService("CollectionManagementService", "1.0");
            mSer.createCollection("empleados");
            System.out.println("Colección creada con éxito");
            colec.close();
            System.out.println("Conexión cerrada");
        
        } else {
            System.out.println("La colección no existe");
        }

    }

}
        
        
        
        
        
    
    

