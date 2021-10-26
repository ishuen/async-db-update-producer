import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonConverterTest {

  @Test
  public void authToJsonString(){
    AuthAction authAction = new AuthAction("api-key", "secret-key");
    String output = JsonConverter.toJsonString(authAction);
    assertEquals("{\"action\":\"auth\",\"key\":\"api-key\",\"secret\":\"secret-key\"}", output);
  }

  @Test
  public void oneBrandToJsonString(){
    SubscribeAction subscribeAction = new SubscribeAction(List.of("brand1"));
    String output = JsonConverter.toJsonString(subscribeAction);
    assertEquals("{\"action\":\"subscribe\",\"trades\":[\"brand1\"]}", output);
  }

  @Test
  public void multiBrandToJsonString(){
    SubscribeAction subscribeAction = new SubscribeAction(List.of("brand1", "brand2", "brand3"));
    String output = JsonConverter.toJsonString(subscribeAction);
    assertEquals("{\"action\":\"subscribe\",\"trades\":[\"brand1\",\"brand2\",\"brand3\"]}", output);
  }
}
