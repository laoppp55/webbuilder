/*
 * TestHTTP.java
 *
 * Created on August 18, 2003, 9:52 PM
 */

package com.heaton.bot.test;
import com.heaton.bot.*;

/**
 *
 * @author  jheaton
 */
public class TestHTTP {
    /** Creates a new instance of TestHTTP */
    static public void test() {
        try
        {
            HTTPSocket http = new HTTPSocket();
            System.out.print("HTTP Test:");
            http.setUser("y2301");
            http.setPassword("tinghsin");
            http.send("http://www.cnewsbank.com",null);
            String result = http.getBody();
            if( !result.trim().equals("Bot testbed") )
            {
                System.out.println("Failed to get correct response:" + result );
            }

            // now test an error
            try
            {
                http.send("http://www.jeffheaton.com/test/bogus.html",null);
            }
            catch(HTTPException e)
            {
                System.out.println( e );
            }
            System.out.println("Success");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
