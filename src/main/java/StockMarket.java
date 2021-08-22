import java.io.FileInputStream;
import java.util.Properties;

public class StockMarket {
  public StockMarket() throws Exception{
    String configPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "key.config";
    Properties keyProps = new Properties();
    keyProps.load(new FileInputStream(configPath));
    System.out.println(keyProps.getProperty("api-key"));
  }
}
