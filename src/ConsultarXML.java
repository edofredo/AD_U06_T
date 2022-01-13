import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XPathQueryService;
/**
 *
 * @author Cristian
 */
public class ConsultarXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        Class driverClass = Class.forName("org.exist.xmldb.DatabaseImpl"); 
	Database bd = (Database) driverClass.newInstance(); 
	DatabaseManager.registerDatabase(bd); 
			
        //Indicación de puerto, usuario y contraseña
        String URL = 
        "xmldb:exist://localhost:8080/exist/xmlrpc/db/empleados";
        String user = "admin";
        String pass = "";
        
        //Creacion de objeto Collection para acceder a la colección
        Collection colec = DatabaseManager.getCollection(URL,user,pass);
		    
        if (colec != null) {
            XPathQueryService servicio = (XPathQueryService) colec
                    .getService("XPathQueryService", "1.0");
            //Almacenamiento del resultado de la consulta en un objeto ResourceSet
            ResourceSet resultado = servicio.query
                    ("for $emp in /empleados/empleado " //selección del nodo
                    + "let $nom:=$emp/nombre/text(), " //valores del nodo a $nom
                    + "$ape:=$emp/apellidos/text(), " //valores del nodo a $ape
                    + "$pue:=$emp/puesto/text(), " //valores del nodo a $pue
                    + "$sal:=$emp/salario/text() " //valores del nodo a $sal
                    + "where salario>1300 " // condicion de salario mayor 1300
                    + "return concat(" // concatenacion de salida de los valores
                    + "'Nombre: ',$nom, "
                    + "' / Apellido: ',$ape, "
                    + "' / Puesto: ',$pue, "
                    + "' / Salario: ',$sal)");
            
            //Recorrido del resultado mediante iterador
            ResourceIterator it = resultado.getIterator();
            while (it.hasMoreResources()) {
                Resource r = it.nextResource();
                System.out.println((String) r.getContent());
            }
            //cierre de la conexión a la colección
            colec.close();
        }
	else{
            System.out.println("La colección no existe");
	} 
    }
}
