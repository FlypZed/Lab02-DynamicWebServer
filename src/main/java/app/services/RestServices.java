package app.services;

import com.sun.net.httpserver.HttpExchange;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * Interfaz que implementa el diseño de un servicio rest
 */
public interface RestServices {
    /**
     * Obtener el Header de un recurso solicitado
     * @param type el tipo o extensión del recurso
     * @param code Codigo de HTTP
     * @return El header del recurso
     */
    public String getHeader(String type, String code);

    /**
     * Obtener el contenido o cuerpo del recurso solicitado
     * @param path El path o dirección de donde se encuentra el recurso
     * @return El contenido del recurso
     */
    public String getResponse(String path);

    /**
     * Permite definir servicios REST usando funciones lambada
     * @param path El Path o direccion de donde se encuentra el recurso
     * @param handler Una funcion con dos paramentreos esperados req y res
     */
    public void get(String path, BiFunction<HttpExchange, Map<String, String>, String> handler);

}