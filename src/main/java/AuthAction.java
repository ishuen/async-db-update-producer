import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthAction {
  private String action = "auth";
  @JsonProperty("key")
  private String apiKey;

  @JsonProperty("secret")
  private String secretKey;

  public AuthAction(String apiKey, String secretKey) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
  }
}
