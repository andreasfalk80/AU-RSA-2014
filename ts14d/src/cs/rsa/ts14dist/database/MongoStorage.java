package cs.rsa.ts14dist.database;

import java.net.UnknownHostException;

import org.slf4j.*;

import com.mongodb.*;

public class MongoStorage implements Storage {

  private DBCollection coll;
  private Mongo mongoInstance;
  
  Logger logger = LoggerFactory.getLogger(MongoStorage.class); 

  public MongoStorage(String mongoAddress, String databaseName) {
    mongoInstance = null;
    try {
      mongoInstance = new MongoClient(mongoAddress);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (MongoException e) {
      e.printStackTrace();
    }

    mongoInstance.setWriteConcern(WriteConcern.SAFE);
    // get handle to the required database
    DB db = mongoInstance.getDB( databaseName );

    // get a collection object to work with
    coll = db.getCollection("timesag");
    
    logger.info("MongoDB connection opened to "+ mongoAddress);

    // Register a shutdown hook to close the mongo 
    // connection
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        logger.info("Closing Mongo DB");
        mongoInstance.close();
      }});
  }

  @Override
  public BasicDBObject getDocumentFor(String userName) {
    // Define a Mongo query
    BasicDBObject query = new BasicDBObject();
    // set criteria on pid
    query.put("user", userName);

    BasicDBObject result = (BasicDBObject) coll.findOne(query);
    
    logger.trace("Mongo/getDocumentFor - returns "+ result);
    
    return result;
  }

  @Override
  public void updateDocument(String user, BasicDBObject document) {
    // The API of the Storage is a bit unfortunate, as Mongo
    // has special method for either inserting a new document
    // or updating an existing; therefore the code becomes
    // a bit clumsy...
    BasicDBObject original = getDocumentFor(user);
    if ( original == null ) {
      coll.insert(document);
      logger.trace("Mongo/inserting new document for user "+user);
    } else {
      // To overwrite an existing document you have
      // to use the save method and ensure the
      // new document has the same id...
      document.put("_id", original.get("_id"));
      coll.save(document);
      logger.trace("Mongo/updating existing document for user "+user);
    }
  }

}
