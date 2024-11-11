import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPSender {
    public static void main(String[] args) {
        try {
            // Define o endereço IP e a porta do destino
            String tcpIP = "192.168.137.145";  // IP do ESP32 ou servidor de destino
            int tcpPort = 12345;               // Porta do ESP32 ou servidor de destino

            // Cria o socket TCP e conecta ao endereço e porta especificados
            Socket socket = new Socket(tcpIP, tcpPort);

            // Cria o fluxo de saída para enviar a mensagem
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            // Cria o fluxo de entrada para receber a resposta
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Mensagem a ser enviada
            String message = "Hello from PC!";
            writer.println(message);  // Envia a mensagem

            System.out.println("Mensagem enviada: " + message);

            // Aguarda e lê a resposta do servidor
            String response = reader.readLine();
            System.out.println("Resposta recebida: " + response);

            // Fecha os fluxos e o socket
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


