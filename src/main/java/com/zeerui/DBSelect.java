/**
 * 
 */
package com.zeerui;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import spark.Spark;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author zrui
 *
 */
public class DBSelect {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloFreeMarker.class, "/");
        
        Spark.get("/dbselect", (req, res) -> {
            
            StringWriter writer = new StringWriter();
            try {
                Template dbTemplate = configuration.getTemplate("dbselect.ftl");
                Map<String, Object> dbMap = new HashMap<String, Object>();
                dbMap.put("databases", Arrays.asList("mongodb", "mysql", "postgre", "dynamo"));
                
                
                
                dbTemplate.process(dbMap, writer);
                
            } catch (IOException | TemplateException e) {
                Spark.halt(500);
                e.printStackTrace();
            }
            return writer;
        });
        
        Spark.post("/choose_db", (req, res)->{
            String selection = req.queryParams("db");
            if(selection == null)
                return "db has not been selected";
            else
                return selection;
            
        });
    }

}
