#include <WiFi.h>

#include <ESP32Servo.h>
Servo myservo;


// Configurações da rede Wi-Fi
const char* ssid = "RIBEIRO_PC";         // Insira o SSID da sua rede Wi-Fi
const char* password = "ribeiro3724";        // Insira a senha da sua rede Wi-Fi

// Configurações do servidor TCP
WiFiServer server(12345);                  // Porta do servidor TCP (use a mesma porta usada pelo cliente TCP)

void setup() {
  // Allow allocation of all timers
	ESP32PWM::allocateTimer(0);
	ESP32PWM::allocateTimer(1);
	ESP32PWM::allocateTimer(2);
	ESP32PWM::allocateTimer(3);


  Serial.begin(115200);


  // Conecta-se à rede Wi-Fi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(100);
    Serial.println("Conectando à rede Wi-Fi...");
  }
  Serial.println("Conectado à rede Wi-Fi!");
  Serial.print("Endereço IP: ");
  Serial.println(WiFi.localIP());

  // Inicia o servidor TCP
  server.begin();
  Serial.println("Servidor TCP iniciado, aguardando conexões...");
}

void loop() {
  // Aguarda uma conexão de cliente
  WiFiClient client = server.available();

  if (client) {
    Serial.println("Cliente conectado!");

    while (client.connected()) {
      if (client.available()) {
        // Lê a mensagem enviada pelo cliente
        String message = client.readStringUntil('\n');
        Serial.print("Mensagem recebida: ");
        Serial.println(message);

        
        //PWM
        int valor_PWM = message.toInt();
        if (!myservo.attached()) {
		      myservo.setPeriodHertz(50); // standard 50 hz servo
		      myservo.attach(14, 1000, 2000); // Attach the servo after it has been detatched
	      }
        myservo.write(valor_PWM);
        

        // Responde ao cliente (opcional)
        client.println("Mensagem recebida com sucesso");
      }
    }

    // Fecha a conexão com o cliente
    client.stop();
    Serial.println("Cliente desconectado.");
  }
}
