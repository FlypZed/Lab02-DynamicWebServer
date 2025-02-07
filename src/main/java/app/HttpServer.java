package app;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import app.services.Request;
import app.services.Response;
import app.services.Services;

/**
 * Clase HttpServer que levanta un servidor web en el puerto 35000.
 * Permite manejar rutas dinámicas y servir archivos estáticos.
 */
public class HttpServer {

    private static HttpServer instance;
    private static String staticFolder = "";
    private static final Map<String, BiFunction<Request, Response, String>> routes = new HashMap<>();

    /**
     * Obtiene la instancia única del servidor (Patrón Singleton).
     * @return Instancia de HttpServer
     */
    public static HttpServer getInstance() {
        if (instance == null){
            instance = new HttpServer();
        }
        return instance;
    }

    /**
     * Registra una nueva ruta con su respectivo handler.
     * @param path Ruta a registrar
     * @param handler Función que maneja la solicitud y genera una respuesta
     */
    public void initializeRoute(String path, BiFunction<Request, Response, String> handler){
        routes.put(path, handler);
    }

    /**
     * Define la carpeta donde se almacenan los archivos estáticos.
     * @param folder Ruta de la carpeta de archivos estáticos
     */
    public void setStaticFiles(String folder){
        staticFolder = folder;
    }

    /**
     * Inicia el servidor y escucha peticiones en el puerto 35000.
     * @param args Argumentos de la línea de comandos
     * @throws IOException Si ocurre un error al iniciar el servidor
     */
    public void run(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while(running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine, outputLine = "";
            boolean first_line = true;
            String request = "/app";

            while ((inputLine = in.readLine()) != null) {
                if (first_line) {
                    request = inputLine.split(" ")[1];
                    first_line = false;
                }
                if (!in.ready()) {
                    break;
                }
            }

            if (request.startsWith("/app")) {

                request = request.replace("/app", "");
                String cleanRequest = request.split("\\?")[0];

                outputLine = routes.getOrDefault(cleanRequest, (req, res) -> {
                    Services ps = Services.getInstance();
                    return ps.getResponse("src/main/resources/404.html");
                }).apply(new Request(request), new Response());

                Services ps = Services.getInstance();
                String header = ps.getHeader("html", "200 OK");
                outputLine = header + outputLine;

            } else {
                outputLine = executeService(request);
            }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * Ejecuta un servicio solicitado a través de la ruta /app/.
     * @param serviceName Nombre del servicio o recurso a ejecutar
     * @return Respuesta HTTP con el recurso solicitado o error 404 si no se encuentra
     */
    private String executeService(String serviceName) {
        Services ps = Services.getInstance();
        try {
            String type = serviceName.split("\\.")[1]; // Extrae la extensión del archivo
            String header = ps.getHeader(type, "200 OK");
            String body = ps.getResponse(staticFolder + serviceName);
            return header + body;
        } catch (RuntimeException e) {
            String header = ps.getHeader("html", "404 Not Found");
            String body = ps.getResponse("src/main/resources/404.html");
            return header + body;
        }
    }
}
