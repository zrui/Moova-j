package com.zeerui;

import spark.Spark;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Spark.get("/hello", (req, res) -> "Hello World");
        
        Spark.get("/spark", (req, res) ->{
           return "Spark bark"; 
        });
        
        Spark.get("/echo/:thing", (req, res)->{
           return req.params(":thing");
        });
    }
}
