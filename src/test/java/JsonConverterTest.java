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
  public void oneCompanyToJsonString(){
    SubscribeAction subscribeAction = new SubscribeAction(List.of("brand1"));
    String output = JsonConverter.toJsonString(subscribeAction);
    assertEquals("{\"action\":\"subscribe\",\"trades\":[\"brand1\"]}", output);
  }

  @Test
  public void multiCompanyToJsonString(){
    SubscribeAction subscribeAction = new SubscribeAction(List.of("brand1", "brand2", "brand3"));
    String output = JsonConverter.toJsonString(subscribeAction);
    assertEquals("{\"action\":\"subscribe\",\"trades\":[\"brand1\",\"brand2\",\"brand3\"]}", output);
  }

  @Test
  public void StringToAuthResponse() throws Exception {
    String authResponseString = "[{\"T\":\"success\",\"msg\":\"authenticated\"}]";
    AuthResponse[] authResponse = JsonConverter.toObject(authResponseString, AuthResponse[].class);
    AuthResponse expected = new AuthResponse();
    expected.setStatus("success");
    expected.setMessage("authenticated");
    assertEquals(expected.getStatus(), authResponse[0].getStatus());
    assertEquals(expected.getMessage(), authResponse[0].getMessage());
  }

  @Test
  public void StringToAuthResponseWithExtra() throws Exception {
    String authResponseString = "[{\"T\":\"success\",\"msg\":\"authenticated\", \"additional\":\"none\"}]";
    AuthResponse[] authResponse = JsonConverter.toObject(authResponseString, AuthResponse[].class);
    AuthResponse expected = new AuthResponse();
    expected.setStatus("success");
    expected.setMessage("authenticated");
    assertEquals(expected.getStatus(), authResponse[0].getStatus());
    assertEquals(expected.getMessage(), authResponse[0].getMessage());
  }

  @Test
  public void StringToSubscribeResponse() throws Exception {
    String subscribeResponseString = "[{\"T\":\"subscription\",\"trades\":[\"AAPL\"],\"quotes\":[],\"bars\":[],\"dailyBars\":[],\"statuses\":[],\"lulds\":[]}]";
    SubscribeResponse[] subscribeResponse = JsonConverter.toObject(subscribeResponseString, SubscribeResponse[].class);
    SubscribeResponse expected = new SubscribeResponse();
    expected.setStatus("subscription");
    expected.setTrades(new String[]{"AAPL"});
    expected.setQuotes(new String[]{});
    assertEquals(expected.getStatus(), subscribeResponse[0].getStatus());
    assertEquals(expected.getTrades()[0], subscribeResponse[0].getTrades()[0]);
    assertEquals(expected.getQuotes().length, subscribeResponse[0].getQuotes().length);
  }
}
