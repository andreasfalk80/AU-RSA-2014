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
 
import org.json.simple.*; 
import org.slf4j.*; 
 
import com.rabbitmq.client.*; 
import com.rabbitmq.client.AMQP.BasicProperties; 
 
/** The Daemon that runs on the server side of the RabbitMQ,
 * responsible for fetching all messages in the queue,
 * forwarding them to the server request handler, and returning
 * the replies using Rabbit's RPC queue technique.
 * 
 * To execute, you have to spawn a thread that takes
 * an instance of this Runnable as parameter.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class RabbitMQDaemon implements Runnable { 
  private static final String RPC_QUEUE_NAME = "rpc_queue"; 
   
  private String hostname; 
  private ServerRequestHandler serverRequestHandler; 
   
  Logger logger = LoggerFactory.getLogger(RabbitMQDaemon.class); 
 
  public RabbitMQDaemon(String iPofMQ, ServerRequestHandler serverRequestHandler) { 
    hostname = iPofMQ; 
    this.serverRequestHandler = serverRequestHandler; 
  } 
 
  @Override 
  public void run() { 
    Connection connection = null; 
    Channel channel = null; 
    try { 
      ConnectionFactory factory = new ConnectionFactory(); 
      logger.info("Starting RabbitMQDaemon, MQ IP = "+hostname); 
      factory.setHost(hostname); 
   
      connection = factory.newConnection(); 
      channel = connection.createChannel(); 
       
      channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null); 
   
      channel.basicQos(1); 
   
      QueueingConsumer consumer = new QueueingConsumer(channel); 
      channel.basicConsume(RPC_QUEUE_NAME, false, consumer); 
   
      while (true) { 
        String response = null; 
         
        // Block and fetch next msg from queue 
        QueueingConsumer.Delivery delivery = consumer.nextDelivery(); 
         
        BasicProperties props = delivery.getProperties(); 
        BasicProperties replyProps = new BasicProperties 
                                         .Builder() 
                                         .correlationId(props.getCorrelationId()) 
                                         .build(); 
         
        try { 
          String message = new String(delivery.getBody(),"UTF-8"); 
          logger.trace("Received msg: " + message); 
           
          // Convert the message to a JSON request object 
          JSONObject request = (JSONObject) JSONValue.parse(message); 
 
          // Delegate to the server request handler for handling the 
          // request and computing an answer 
          JSONObject reply = serverRequestHandler.handleRequest(request); 
           
          // Convert the answer back into a string for replying to client 
          response = reply.toJSONString(); 
        } 
        catch (Exception e) { 
          logger.error(" Exception in MQDAemon run()/while true] " + e.toString()); 
          response = "wrong"; 
        } 
        finally {   
          logger.trace("Returning answer: " + response); 
          channel.basicPublish( "", props.getReplyTo(), replyProps, response.getBytes("UTF-8")); 
          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); 
          logger.trace("Answer acknowledged."); 
        } 
      } 
    } 
    catch  (Exception e) { 
      logger.error("Exception in daemon's outer loop: nested exception" + e.getMessage()); 
      e.printStackTrace(); 
    } 
    finally { 
      if (connection != null) { 
        try { 
          connection.close(); 
        } 
        catch (Exception ignore) {} 
      } 
    }                
  } 
} 
 
