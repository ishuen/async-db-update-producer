public class Producer {
  public static void main(String[] argv) throws Exception {
    StockMarket stockClient = new StockMarket();
    stockClient.connect();
  }
}
