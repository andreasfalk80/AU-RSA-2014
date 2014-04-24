/*
 * Copyright 2014 Henrik Baerbak Christensen, Aarhus University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
 
package cs.rsa.ts14dist.appserver; 
 
import java.io.IOException; 
 
import org.json.simple.*; 
import org.slf4j.*; 
 
import cs.rsa.ts14dist.client.Connector; 
 
import com.rabbitmq.client.*; 
import com.rabbitmq.client.AMQP.BasicProperties; 
 
import java.util.UUID; 
 
/** Implementation of the connection between client and
 * server that uses RabbitMQ's RPC mechanism.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
 
public class RabbitMQRPCConnector implements Connector { 
 
  private Connection connection; 
  private Channel channel; 
  private String replyQueueName; 
  private QueueingConsumer consumer; 
  private String requestQueueName = "rpc_queue"; 
   
  Logger logger = LoggerFactory.getLogger(RabbitMQRPCConnector.class); 
 
  /** Create the connector using the indicated RabbitMQ
   * instance(s) as messaging queue.
   * 
   * @param hostname
   * @throws IOException
   */
  public RabbitMQRPCConnector(String hostname) throws IOException { 
    ConnectionFactory factory = new ConnectionFactory(); 
    factory.setHost(hostname); 
    connection = factory.newConnection(); 
    channel = connection.createChannel(); 
 
    replyQueueName = channel.queueDeclare().getQueue();  
    consumer = new QueueingConsumer(channel); 
    channel.basicConsume(replyQueueName, true, consumer); 
     
    logger.info("Opening connector on MQ at IP= "+hostname); 
  } 
 
  @Override 
  public JSONObject sendRequestAndBlockUntilReply(JSONObject payload) throws IOException { 
    String response = null; 
    String corrId = UUID.randomUUID().toString(); 
 
    BasicProperties props = new BasicProperties 
        .Builder() 
    .correlationId(corrId) 
    .replyTo(replyQueueName) 
    .build(); 
 
    String message = payload.toJSONString(); 
 
    logger.trace("Send request: "+ message); 
    channel.basicPublish("", requestQueueName , props, message.getBytes()); 
 
    while (true) { 
      QueueingConsumer.Delivery delivery = null; 
      try { 
        delivery = consumer.nextDelivery(); 
      } catch (ShutdownSignalException e) { 
        logger.error("ShutdownException: "+ e.getMessage()); 
        e.printStackTrace(); 
      } catch (ConsumerCancelledException e) { 
        logger.error("ConsumerCancelledException: "+ e.getMessage()); 
        e.printStackTrace(); 
      } catch (InterruptedException e) { 
        logger.error("InterruptedException: "+ e.getMessage()); 
        e.printStackTrace(); 
      } 
      if (delivery.getProperties().getCorrelationId().equals(corrId)) { 
        response = new String(delivery.getBody(),"UTF-8"); 
        logger.trace("Retrieved reply: " + response); 
        break; 
      } 
    } 
 
    JSONObject reply = (JSONObject) JSONValue.parse(response); 
    return reply; 
  } 
} 
