package app.services;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Obtener los recursos necesarios para un servicio web
 */
public class Services implements RestServices{

    public static Services instance;

    /**
     * Constructor
     */
    private Services(){}

    /**
     * Obtener la instancia de la clase segun el patrón de Singleton
     * @return La única instancia de la clase
     */
    public static Services getInstance(){
        if(instance == null){
            instance = new Services();
        }
        return instance;
    }

    @Override
    public String getHeader(String type, String code) {
        return "HTTP/1.1 "+code+"\r\n" +
                "Content-type: text/"+type+"\r\n" +
                "\r\n";
    }

    @Override
    public String getResponse(String path) {
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(fileContent);
    }

    @Override
    public void get(String path, BiFunction<HttpExchange, Map<String, String>, String> handler) {

    }
}
