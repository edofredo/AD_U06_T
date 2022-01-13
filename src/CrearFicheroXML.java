import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.modules.XMLResource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Clase que crea un fichero XML dentro de la colección empleados
 * @author Cristian
 */
public class CrearFicheroXML {

    public static void main(String[] args) throws Exception {

        //carga del driver
        Class driverClass = Class.forName("org.exist.xmldb.DatabaseImpl");
        Database bd = (Database) driverClass.newInstance();
        DatabaseManager.registerDatabase(bd);

        //definicion de url, user y password
        String URL
                = "xmldb:exist://localhost:8080/exist/xmlrpc/db/empleados";
        String user = "admin";
        //Aquí pondremos nuestra contraseña
        String pass = "";
        Collection colec = DatabaseManager.getCollection(URL, user, pass);

        //si el objeto Collection logra instanciarse, se permite crear el fichero
        if (colec != null) {

            //Creación de un recurso (XMLResource) 
            Resource documentoXML = (XMLResource) colec.createResource(
                    "empleados.xml", "XMLResource");

            //Creación de fichero usando DOM
            File f = CrearFicheroXML.crearFicheroXML("empleados.xml", "empleados");
            
            //Se añade el fichero al recurso XML
            documentoXML.setContent(f);

            //Se guarda el recurso/fichero XML en la colección
            colec.storeResource(documentoXML);
            System.out.println("Fichero creado");
            colec.close();
            System.out.println("Conexión cerrada");
        } else {
            System.out.println("La colección no existe");
        }
    }
    
    //método que devuelve un fichero XML con etiqueta raíz
    public static File crearFicheroXML(String ficheroXML, String etiquetaXML) 
            throws ParserConfigurationException, TransformerConfigurationException, 
            TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation imp = builder.getDOMImplementation();
        //Creación de la representacion del documento XML con su nodo raiz (etiquetaXML)
        Document documentoXML = imp.createDocument(null, etiquetaXML, null);
        documentoXML.setXmlVersion("1.0");
        
        //Transformacion de Document a Archivo XML final
        //Creación de la fuente
        Source fuente = new DOMSource(documentoXML);
        //Creacion del fichero destino
        File fichXML = new File(ficheroXML);
        //Creación de un flujo hasta el destino
        Result destino = new StreamResult(fichXML);
        //Uso de un transformador para pasar de fuente a destino
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.transform(fuente, destino);
        //devuelve el fichero XMl
        return fichXML;
    }
}


