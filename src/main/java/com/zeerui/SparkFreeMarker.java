package com.zeerui;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Spark;

public class SparkFreeMarker {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloFreeMarker.class, "/");
        
        Spark.get("/freemarker", (req, res) -> {
            
            StringWriter writer = new StringWriter();
            try {
                Template helloTemplate = configuration.getTemplate("hello.ftl");
                Map<String, Object> helloMap = new HashMap<String, Object>();
                helloMap.put("name", "Freemarker");
                
                helloTemplate.process(helloMap, writer);
                
            } catch (IOException | TemplateException e) {
                Spark.halt(500);
                e.printStackTrace();
            }
            return writer;
        });

    }

}
