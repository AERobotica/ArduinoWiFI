#include <ESP8266WiFi.h>  // Inclui a biblioteca ESP8266WiFi

// Configurações de rede Wi-Fi
const char* ssid = "RedeMarcelino";   // Substitua pelo nome da sua rede Wi-Fi
const char* password = "1597133144";  // Substitua pela senha da sua rede Wi-Fi

// Configura o servidor TCP na porta 12345
WiFiServer server(12345);

void setup() {
  Serial.begin(115200);

  // Conecta ao Wi-Fi
  Serial.println("Tentando conectar ao Wi-Fi...");
  WiFi.begin(ssid, password);

  // Aguarda conexão até que o WiFi se conecte (com timeout de 10 segundos)
  int tentativa = 0;
  while (WiFi.status() != WL_CONNECTED && tentativa < 20) {  // 20 tentativas de 500ms = 10s
    delay(500);
    Serial.print(".");
    tentativa++;
  }

  // Verifica o status da conexão
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("\nConectado ao Wi-Fi");
    Serial.print("Endereço IP: ");
    Serial.println(WiFi.localIP());

    // Inicia o servidor
    server.begin();
    Serial.println("Servidor TCP iniciado na porta 12345");
  } else {
    Serial.println("\nFalha ao conectar ao Wi-Fi.");
    Serial.println("Verifique o SSID e a senha, e se a rede é 2,4 GHz.");
  }
}

void loop() {
  // Verifica se um cliente está conectado
  WiFiClient client = server.available();
  if (client) {
    Serial.println("Novo cliente conectado");

    // Mantém a comunicação enquanto o cliente está conectado
    while (client.connected()) {
      if (client.available()) {
        // Lê a mensagem do cliente
        String message = client.readStringUntil('\n');
        Serial.println("Mensagem recebida: " + message);

        // Verifica se a mensagem é "Mensagem secreta"
        if (message == "Mensagem secreta") {
          // Prepara a resposta e envia para o cliente
          String response = "ESP recebeu a mensagem secreta!";
          client.println(response);  // Envia a resposta
          Serial.println("Resposta enviada: " + response);
        } else {
          // Se a mensagem não for "Mensagem secreta", desconecta o cliente
          client.stop();
          Serial.println("Mensagem incorreta. Conexão encerrada.");
        }

        // Limpa o buffer do cliente
        client.flush();
      }
    }

    // Fecha a conexão após o cliente ser desconectado
    client.stop();
    Serial.println("Cliente desconectado");
  }
}
