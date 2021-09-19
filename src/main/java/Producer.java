import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
  private final static String QUEUE_NAME = "queue";
  public static void main(String[] argv) throws Exception {
    StockMarket stockClient = new StockMarket();
    stockClient.connect();
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("localhost");
    try (Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()) {
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      String message = "Hello World!";
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
      System.out.println(" [x] Sent '" + message + "'");
    }
  }
}
