package app.services;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase Request para manejar los parámetros de consulta (query params) en las solicitudes HTTP.
 */
public class Request {
    private final Map<String, String> queryParams = new HashMap<>();

    /**
     * Constructor que procesa la URL y extrae los parámetros de consulta.
     * @param url La URL de la solicitud
     */
    public Request(String url){
        if(url.contains("?")){
            String query = url.split("\\?")[1];
            for (String param : query.split("&")) {
                String[] keyValue = param.split("=");
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : "";
                queryParams.putIfAbsent(key, value);
            }
        }
    }

    /**
     * Obtiene el valor de un parámetro de consulta dado su clave.
     * @param key Clave del parámetro de consulta
     * @return Valor asociado a la clave o "World" si no existe
     */
    public String getValue(String key) {
        return queryParams.getOrDefault(key, "World");
    }
}
