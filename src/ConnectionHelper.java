import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHelper implements AutoCloseable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ConnectionHelper(ServerSocket server) throws IOException {
        this.socket = server.accept();
        init();
    }

    public ConnectionHelper(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        init();
    }

    private void init() throws IOException {
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
    }

    public void sendMessage(String message) {
        this.out.println(message);
        this.out.flush();
    }

    public String readMessage() {
        try {
            return this.in.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getPort() {
        return this.socket.getPort();
    }


    @Override
    public void close() throws IOException {
        this.socket.close();
        this.in.close();
        this.out.close();
    }
}
