import java.util.Scanner;  
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class senderPWM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Coloca aqui o valor de PWM (0-255): ");
        // Mensagem a ser enviada 
        String message = scanner.nextLine();  

        try {
            // Define o endereço IP e a porta do destino
            String tcpIP = "192.168.137.3";  // IP do ESP32 ou servidor de destino
            int tcpPort = 12345;            // Porta do ESP32 ou servidor de destino

            // Cria o socket TCP e conecta ao endereço e porta especificados
            Socket socket = new Socket(tcpIP, tcpPort);

            // Cria o fluxo de saída para enviar a mensagem
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            
            
            //int message = 10;
            writer.println(message);  // Envia a mensagem

            System.out.println("Mensagem enviada: " + message);

            // Fecha o fluxo e o socket
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


