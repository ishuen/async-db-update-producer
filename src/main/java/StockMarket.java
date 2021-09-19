import okhttp3.*;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class StockMarket {
  private String baseUrl;
  private String apiKey;
  private String secretKey;

  public StockMarket() throws Exception{
    String configPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "key.config";
    Properties keyProps = new Properties();
    keyProps.load(new FileInputStream(configPath));
    apiKey = keyProps.getProperty("api-key");
    secretKey = keyProps.getProperty("secret-key");
    baseUrl = keyProps.getProperty("ws-base");
  }

  public void connect() {
    StockMarketListener listener = new StockMarketListener(apiKey, secretKey);
    OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(1000,  TimeUnit.MILLISECONDS)
            .build();
    Request request = new Request.Builder()
            .url(baseUrl)
            .build();
    WebSocket ws = client.newWebSocket(request, listener);

    // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
    client.dispatcher().executorService().shutdown();
  }

}
