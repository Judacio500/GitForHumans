package auxiliars.utils;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class PropertyReader 
{

    private static Map<String, Properties> files = new HashMap<>();

    private PropertyReader() 
    {
        throw new IllegalStateException("Cannot Instantiate this Class");
    }

    public static String getProperty(String fileName, String key) 
    {
        
        if (!files.containsKey(fileName)) 
            {
            
            try 
            {
                // Obtenemos el class path de nuestra instancia
                InputStream inpt = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
                
                // por si no conseguimos el class path
                if(inpt == null)
                {
                    throw new RuntimeException("Error: Could not find " + fileName + " in Resources.");
                }

                // creamos una nueva instancia de Properties
                // Properties crea un diccionario con los datos del archivo properties que trackea

                Properties prop = new Properties();
                
                // Cargamos el archivo a la variable segun nuestro class path
                prop.load(inpt);
                
                // Cerramos el stream al class path
                inpt.close();
                
                // Añadimos nuestro diccionario del archivo a nuestro diccionario de archivos 
                // un hash de hashes
                files.put(fileName, prop);

            } 
            catch (IOException e) 
            {   
                System.err.println("Error: Reading File \"" + fileName +"\"");
                e.printStackTrace(); // Esto imprime el error en la consola para debugging
                
                // Como falló, regresamos null para que quien llamó al método sepa que no se pudo
                return null; 
            }
        }

        // Resto del flujo del metodo
        // Si llegamos aqui estamos 100% seguros de que guardamos esos datos por lo tanto...

        // Recuperamos el diccionario del archivo con su llave en nuestro hash de hashes
        Properties selectedFile = files.get(fileName);

        // Extraemos el dato del archivo de nuestro interes 
        return selectedFile.getProperty(key);
    }
}