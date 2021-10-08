import com.fasterxml.jackson.annotation.JsonProperty;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.LinkedList;
import java.util.List;

public class StockMarketListener extends WebSocketListener {
  private String apiKey;
  private String secretKey;
  private boolean isAuthenticated = false;

  public StockMarketListener(String apiKey, String secretKey) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
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
    if (isAuthenticated) {
      // push message to the queue
      return;
    }
    try {
      AuthResponse[] authResponse = JsonConverter.toObject(text, AuthResponse[].class);
      if (authResponse[0].isAuthenticated()) {
        isAuthenticated = true;
        List<String> brandList = new LinkedList<>();
        brandList.add("AAPL");
        SubscribeAction subscribeAction = new SubscribeAction(brandList);
        String subscribeString = JsonConverter.toJsonString(subscribeAction);
        webSocket.send(subscribeString);
      }
    } catch (Exception e) {
      System.out.println("Authentication failed.");
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
