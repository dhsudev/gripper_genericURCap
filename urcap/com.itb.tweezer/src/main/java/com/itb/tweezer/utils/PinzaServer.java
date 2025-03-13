import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;

public class PinzaServer extends WebSocketServer {

    public PinzaServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Cliente conectado desde: " + conn.getRemoteSocketAddress());
        conn.send("Conexión establecida. El servidor está listo.");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Mensaje recibido del cliente: " + message);
        // Aquí podrías procesar el mensaje y responder como lo haría la pinza

        // Simula una respuesta de la pinza, por ejemplo:
        String response = "Pinza recibió el mensaje. Respuesta: " + message;
        conn.send(response);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Cliente desconectado: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) {
        // Crea y arranca el servidor en el puerto 12345
        PinzaServer server = new PinzaServer(12345);
        server.start();
        System.out.println("Servidor WebSocket de la pinza iniciado en el puerto 12345");
    }
}
