import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHelper implements Closeable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ConnectionHelper(ServerSocket server) {
        try {
            this.socket = server.accept();
            this.in = createReader();
            this.out = createWriter();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public ConnectionHelper(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private PrintWriter createWriter() {
        try {
            return new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private BufferedReader createReader() {
        try {
            return new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void writeLine(String message) {
        this.out.println(message);
    }

    public String readLine() {
        try {
            return this.in.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
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
