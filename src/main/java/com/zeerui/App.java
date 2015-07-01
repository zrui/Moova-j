package com.zeerui;

import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Spark;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws UnknownHostException
    {
        Spark.get("/hello", (req, res) -> "Hello World");
        
        Spark.get("/spark", (req, res) ->{
           return "Spark bark"; 
        });
        
        Spark.get("/echo/:thing", (req, res)->{
           return req.params(":thing");
        });
        
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
