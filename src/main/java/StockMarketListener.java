import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class StockMarketListener extends WebSocketListener {
  private String apiKey;
  private String secretKey;

  public StockMarketListener(String apiKey, String secretKey) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
  }
  @Override
  public void onOpen(WebSocket webSocket, Response response) {
    AuthData auth = new AuthData(apiKey, secretKey);
    String authString = JsonConverter.toJsonString(auth);
    System.out.println("authentication: " + authString);
    webSocket.send(authString);
  }

  @Override
  public void onMessage(WebSocket webSocket, String text) {
    System.out.println("MESSAGE: " + text);
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
