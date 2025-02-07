package app;
import java.io.IOException;

/**
 * Clase para correr el servidor HttpServer
 */
public class App {

    /**
     * Iniciar el programa
     * @param args args
     * @throws IOException Por si algo sale mal en el proceso
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.getInstance();

        server.setStaticFiles("src/main/resources/");
        server.initializeRoute("/pi", (req, resp) -> String.valueOf(Math.PI));
        server.initializeRoute("/hello", (req, res) -> "Hello " + req.getValue("name"));
        server.run(args);
    }

}
