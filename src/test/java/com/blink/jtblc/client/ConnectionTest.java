package com.blink.jtblc.client;
import com.blink.jtblc.client.bean.*;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ConnectionTest {
    //4.1 创建Connection对象
    Connection connection = ConnectionFactory.getCollection("ws://ts5.jingtum.com:5020");
    Remote remoteImpl = new Remote(connection);
    //4.3 关闭连接
    @Test
    public void disconnect(){
        ConnectionFactory.close();
    }


}
