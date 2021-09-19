import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthData {
  private String action = "authenticate";
  private KeyPair data;

  private class KeyPair {
    @JsonProperty("key_id")
    String apiKey;

    @JsonProperty("secret_key")
    String secretKey;

    private KeyPair(String apiKey, String secretKey) {
      this.apiKey = apiKey;
      this.secretKey = secretKey;
    }
  }

  public AuthData(String apiKey, String secretKey) {
    this.data = new KeyPair(apiKey, secretKey);
  }
}
