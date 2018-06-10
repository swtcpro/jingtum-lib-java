package com.blink.jtblc.client;

import com.blink.jtblc.client.bean.*;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RemoteTestWyz {
    //4.1 创建Connection对象
    Connection connection = ConnectionFactory.getCollection("ws://101.200.176.238:5020");
    Remote Remote = new Remote(connection);
    ObjectMapper mapper = new ObjectMapper();

    //4-1 创建连接 获取服务信息及帐本信息
    @Test
    public void requestLedgerInfo() throws Exception{
        LedgerInfo ledgerInfo = Remote.requestLedgerInfo();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ledgerInfo));
    }

    //4-4 请求服务器信息
    @Test
    public void requestServerInfo() throws Exception{
        ServerInfo serverInfo = Remote.requestServerInfo();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(serverInfo));
    }
    //4.5 获取最新账本信息
    @Test
    public void requestLedgerClosed() throws Exception{
        LedgerClosed str = Remote.requestLedgerClosed();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(str));
    }
    //4.6 获取某一账本具体信息
    @Test
    public void requestLedger() throws Exception{
        Ledger str = Remote.requestLedger("","F6F728C3E39F731F3E4994B20AAACACBBFF9751B9D0F6CDA1C7A3D5BB1C502C4",true);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(str));
    }

    //4.7 查询某一交易具体信息 -转换异常 list中有map，map不需要转换
    @Test
    public void requestTx() throws Exception{
        Account str = Remote.requestTx("32E57868F1453BA4F7A47D7DFB75AF2C241101FE346D2864D2790836838C3A26");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(str));
    }

    //    secret=shEWLj5gnZu5d37AQiZ82Gfwvfhpg
//    address=jKSEoGkxkuoyc5ypw99YrmMBk2rSfYp2ht
    //4.8 查询某一交易具体信息
    @Test
    public void requestAccountInfo() throws Exception{
        AccountInfo str = Remote.requestAccountInfo("jK4kdiriyxErTfW8wMMjzP25oT2AKLWGfY");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(str));
    }


    //4.9 获得账号可接收和发送的货币
    @Test
    public void requestAccountTums() throws Exception{
        AccountTums str = Remote.requestAccountTums("jK4kdiriyxErTfW8wMMjzP25oT2AKLWGfY");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(str));
    }


    //4.10 获得账号关系
    @Test
    public void requestAccountRelations() throws Exception{
        AccountRelations str = Remote.requestAccountRelations("jK4kdiriyxErTfW8wMMjzP25oT2AKLWGfY","trust");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(str));
    }

    //4.11 获得账号挂单
    @Test
    public void requestAccountOffers() throws Exception{
        AccountOffers str = Remote.requestAccountOffers("jK4kdiriyxErTfW8wMMjzP25oT2AKLWGfY");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(str));
    }

    //4.12 获得账号交易列表 -转换异常
    @Test
    public void requestAccountTx() throws Exception{
        AccountTx str = Remote.requestAccountTx("jK4kdiriyxErTfW8wMMjzP25oT2AKLWGfY",1);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(str));
    }
    //4.13 获得市场挂单列表
    @Test
    public void requestOrderBook() throws Exception{
        Map gets = new HashMap();
        gets.put("currency", "CNY");
        gets.put("issuer", "jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
        Map pays = new HashMap();
        pays.put("currency", "SWT");
        OrderBook str = Remote.requestOrderBook(gets,pays,1);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(str));
    }

}
