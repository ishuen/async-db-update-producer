import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeResponse {
  @JsonProperty("T")
  private String status;

  @JsonProperty("trades")
  private String[] trades;

  @JsonProperty("quotes")
  private String[] quotes;

  @JsonProperty("bars")
  private String[] bars;

  @JsonProperty("dailyBars")
  private String[] dailyBars;

  @JsonProperty("statuses")
  private String[] statuses;

  @JsonProperty("lulds")
  private String[] lulds;

  public boolean hasSubscribed() {
    return "subscription".equals(status);
  }
}