import lombok.Getter;

import java.util.List;

@Getter
public class SubscribeAction {
  private String action = "subscribe";
  private List<String> trades;

  public SubscribeAction(List<String> trades) {
    this.trades = trades;
  }
}
