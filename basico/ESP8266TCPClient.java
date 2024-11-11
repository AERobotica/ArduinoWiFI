import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ESP8266TCPClient {
    public static void main(String[] args) {
        String espIp = "192.168.137.233"; // Substitua pelo IP do seu ESP8266
        int port = 12345; // Mesma porta configurada no ESP8266
        
        try {
            // Conecta ao ESP8266 usando TCP
            Socket socket = new Socket(espIp, port);
            System.out.println("Conectado ao ESP8266");

            // Configura a saída para o ESP8266
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Digite a mensagem 'Mensagem secreta' para obter uma resposta do ESP8266.");
            
            while (true) {
                System.out.print("> ");
                String mensagem = scanner.nextLine();

                // Verifica se o usuário deseja encerrar a conexão
                if (mensagem.equalsIgnoreCase("sair")) {
                    break;
                }
                
                // Envia a mensagem para o ESP8266
                out.println(mensagem);
                System.out.println("Mensagem enviada: " + mensagem);

                // Se a mensagem for "Mensagem secreta", aguarda a resposta do ESP8266
                if (mensagem.equals("Mensagem secreta")) {
                    String resposta = in.readLine();
                    if (resposta != null) {
                        System.out.println("Resposta do ESP8266: " + resposta);
                    } else {
                        System.out.println("Erro: Nenhuma resposta recebida.");
                    }
                } else {
                    System.out.println("Mensagem incorreta. Conexão encerrada.");
                    break;
                }
            }

            // Fecha a conexão e os recursos
            socket.close();
            scanner.close();
            System.out.println("Conexão fechada");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
