import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.LinkedList;
import java.util.List;

public class StockMarketListener extends WebSocketListener {
  private String apiKey;
  private String secretKey;
  private ConnectionFactory connectionFactory;
  private boolean isAuthenticated = false;
  private boolean hasSubscribed = false;
  private final static String QUEUE_NAME = "queue";

  public StockMarketListener(String apiKey, String secretKey) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
    connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("localhost");
  }
  @Override
  public void onOpen(WebSocket webSocket, Response response) {
    AuthAction auth = new AuthAction(apiKey, secretKey);
    String authString = JsonConverter.toJsonString(auth);
    System.out.println("authentication: " + authString);
    webSocket.send(authString);
  }

  @Override
  public void onMessage(WebSocket webSocket, String text) {
    System.out.println("MESSAGE: " + text);
    if (isAuthenticated && hasSubscribed) {
      try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
          channel.queueDeclare(QUEUE_NAME, false, false, false, null);
          channel.basicPublish("", QUEUE_NAME, null, text.getBytes());
          System.out.println("[x] Sent: " + text);
      } catch (Exception e) {
        System.out.println("PUSH FAILED: " + e.getMessage());
      }
    } else if (isAuthenticated) {
      try {
        SubscribeResponse[] subscribeResponse = JsonConverter.toObject(text, SubscribeResponse[].class);
        if (subscribeResponse[0].hasSubscribed()) {
          hasSubscribed = true;
        }
      } catch (Exception e) {
        System.out.println("Subscription failed: " + e.getMessage());
      }
    } else {
      try {
        AuthResponse[] authResponse = JsonConverter.toObject(text, AuthResponse[].class);
        if (authResponse[0].isAuthenticated()) {
          isAuthenticated = true;
          List<String> brandList = List.of("AAPL");
          SubscribeAction subscribeAction = new SubscribeAction(brandList);
          String subscribeString = JsonConverter.toJsonString(subscribeAction);
          webSocket.send(subscribeString);
        }
      } catch (Exception e) {
        System.out.println("Authentication failed: " + e.getMessage());
      }
    }
  }

  @Override
  public void onClosing(WebSocket webSocket, int code, String reason) {
    webSocket.close(1000, null);
    System.out.println("CLOSE: " + code + " " + reason);
  }

  @Override
  public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    t.printStackTrace();
  }

}
