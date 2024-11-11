#include <WiFi.h>  // Biblioteca WiFi para o Arduino GIGA R1 WiFi

// Credenciais da rede WiFi
const char* ssid = "batata";
const char* password = "1232145654";

WiFiServer server(12345);

void setup() {
  // Inicializa a comunicação serial
  Serial.begin(115200);
  
  // Conecta ao WiFi
  Serial.print("Conectando ao WiFi...");
  WiFi.begin(ssid, password);
  
  // Espera até que esteja conectado ao WiFi
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  
  // Conexão bem-sucedida
  Serial.println("\nConectado ao WiFi!");
  Serial.print("IP do Arduino: ");
  Serial.println(WiFi.localIP());

  // Inicia o servidor
  server.begin();
}

void loop() {
  // Verifica se há um cliente conectado
  WiFiClient client = server.available();
  
  if (client) {
    Serial.println("Cliente conectado.");
    
    // Aguarda a mensagem do cliente
    while (client.connected()) {
      if (client.available()) {
        // Lê a mensagem enviada pelo cliente (assumindo que é um número)
        String message = client.readStringUntil('\n');
        Serial.print("Mensagem recebida: ");
        Serial.println(message);

        // Converte a mensagem para um número inteiro
        int receivedNumber = message.toInt();

        // Soma 10 ao número recebido
        int result = receivedNumber + 10;
        Serial.print("Resultado (após somar 10): ");
        Serial.println(result);

        // Envia o resultado de volta ao cliente
        client.println(result);  // Envia o número como resposta

        // Sai do loop após enviar a resposta
        break;
      }
    }
    
    // Fecha a conexão com o cliente
    client.stop();
    Serial.println("Cliente desconectado.");
  }
}


