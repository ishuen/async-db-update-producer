import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {
  @JsonProperty("T")
  private String status;

  @JsonProperty("msg")
  private String message;

  public boolean isAuthenticated() {
    return "success".equals(status) && "authenticated".equals(message);
  }
}
