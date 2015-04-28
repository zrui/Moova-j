/**
 * 
 */
package com.zeerui;

import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import spark.Spark;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author zrui
 *
 */
public class SparkFreeMongo {

    /**
     * @param args
     * @throws UnknownHostException 
     */
    public static void main(String[] args) throws UnknownHostException {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloFreeMarker.class, "/");
        
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
        DB testDB = client.getDB("test");
        DBCollection collection = testDB.getCollection("names");
        
        
        Spark.get("/freemarker", (req, res) -> {
            
            StringWriter writer = new StringWriter();
            try {
                Template helloTemplate = configuration.getTemplate("hello.ftl");
                DBObject document = collection.findOne();
                helloTemplate.process(document, writer);
                
            } catch (IOException | TemplateException e) {
                Spark.halt(500);
                e.printStackTrace();
            }
            return writer;
        });

    }

}
