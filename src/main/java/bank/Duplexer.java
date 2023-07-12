package bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Duplexer implements AutoCloseable {
    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;

    public Duplexer(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(String message) {
        writer.print(message);
        writer.flush();
    }

    public String read() {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            line = null;
        }
        return line;
    }

    @Override
    public void close() throws Exception {
        socket.close();
        writer.close();
        reader.close();
    }
}
