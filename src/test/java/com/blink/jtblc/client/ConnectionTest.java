package com.blink.jtblc.client;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ConnectionTest {
    @Test
    public void connection(){
        Connection connection = ConnectionFactory.getCollection("ws://ts5.jingtum.com:5020");
        Map map =new HashMap<String,String>();
        map.put("iphone","x");
        String message = connection.submit(map);
        System.out.println(message+"===");

    }

}
