import java.util.Scanner;  
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class senderPWM {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Coloca aqui o valor de PWM (0-255): ");
            // Mensagem a ser enviada 
            String message = scanner.nextLine();  

            try {
                // Define o endereço IP e a porta do destino
                String tcpIP = "192.168.137.180";  // IP do ESP32 ou servidor de destino
                int tcpPort = 12345;              // Porta do ESP32 ou servidor de destino

                // Cria o socket TCP e conecta ao endereço e porta especificados
                Socket socket = new Socket(tcpIP, tcpPort);

                // Cria o fluxo de saída para enviar a mensagem
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(outputStream, true);
                
                // Envia a mensagem
                writer.println(message);  
                System.out.println("Mensagem enviada: " + message);

                // Cria o fluxo de entrada para receber a resposta
                InputStream inputStream = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                
                // Lê a resposta do Arduino
                String response = reader.readLine();
                System.out.println("Resposta do Arduino: " + response);

                // Fecha o fluxo e o socket
                writer.close();
                reader.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

