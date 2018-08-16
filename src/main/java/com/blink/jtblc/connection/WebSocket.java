package com.blink.jtblc.connection;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.blink.jtblc.listener.Impl.LedgerCloseImpl;
import com.blink.jtblc.listener.Impl.TransactionsImpl;
import com.blink.jtblc.utils.JsonUtils;
import org.apache.commons.lang3.RandomUtils;
import org.java_websocket.WebSocket.READYSTATE;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocket extends WebSocketClient {
    final static Logger logger = LoggerFactory.getLogger(WebSocket.class);
    private volatile Map<String, String> results = new HashMap<String, String>();
    private volatile String status = "";

    private volatile Map<String,String> transationList = new HashMap<String, String>();

    public WebSocket(URI serverURI) {
        super(serverURI, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        status = "open";
        logger.info("已连接");
    }

    @Override
    public void onMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(message, Map.class);
            if (map.get("id") == null) {
                switch(map.get("type").toString()) {
                    case "ledgerClosed":
                        this.handleLedgerClosed(map);
                        break;
                    case "serverStatus":
                        this.handleServerStatus(map);
                        break;
                    case "transaction":
                        this.handleTransaction(map);
                        break;
                    case "path_find":
                        this.handlePathFind(map);
                        break;
                }
            } else {
                results.put(map.get("id").toString(), message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLedgerClosed(Map map) {
        Map _status =new HashMap();
        if(map.get("ledger_index")!=null&&!map.get("ledger_index").toString().equals("0")) {
            _status.put("ledger_index", map.get("ledger_index"));
            _status.put("ledger_time", map.get("ledger_time"));
            _status.put("reserve_base", map.get("reserve_base"));
            _status.put("reserve_inc", map.get("reserve_inc"));
            _status.put("fee_base", map.get("fee_base"));
            _status.put("fee_ref", map.get("fee_ref"));
            new LedgerCloseImpl(JsonUtils.toJsonString(map)).run();
        }
    }
    public String handleServerStatus(Map map) {
        Map _status =new HashMap();
        _status.put("load_base", map.get("load_base"));
        _status.put("load_factor", map.get("load_factor"));
        if(map.get("pubkey_node")!=null) {
            _status.put("pubkey_node", map.get("pubkey_node"));
        }
        _status.put("server_status", map.get("server_status"));

        String[] onlineStates = new String[] {"syncing", "tracking", "proposing", "validating", "full", "connected"};

        for(int i =0;i<onlineStates.length;i++) {
            if(map.get("server_status").equals(onlineStates[i])) {
                //todo
            }
        }
        return "";
    }
    public void handleResponse(Map result)  {
        if(((Map)result.get("result")).get("server_status")!=null) {
            Map map =(Map)result.get("result");
            Map _status =new HashMap();
            _status.put("load_base", map.get("load_base"));
            _status.put("load_factor", map.get("load_factor"));
            if(map.get("pubkey_node")!=null) {
                _status.put("pubkey_node", map.get("pubkey_node"));
            }
            _status.put("server_status", map.get("server_status"));

            String[] onlineStates = new String[] {"syncing", "tracking", "proposing", "validating", "full", "connected"};

            for(int i =0;i<onlineStates.length;i++) {
                if(map.get("server_status").equals(onlineStates[i])) {
                    //todo
                }
            }
        }

    }

    public void handleTransaction(Map map) {
        //todo 缓冲hash
        new TransactionsImpl(JsonUtils.toJsonString(map)).run();
    }

    public String handlePathFind(Map map){
        return JsonUtils.toJsonString(map);
    }



    @Override
    public void onClose(int code, String reason, boolean remote) {
    	 if(code == CloseFrame.NORMAL) {
         	logger.info("已离线，主动关闭连接");
         }else {
         	logger.error("已离线，被动关闭连接，重新连接");
         	this.reconnect();
 			logger.error("重新连接websocket，重连结果【" + this.getReadyState() + "】");
         }
    }

    @Override
    public void onError(Exception ex) {
    	 logger.error(ex.getMessage(), ex);
         //连接断开导致异常时，直接重新连接
         if(this.getReadyState() != READYSTATE.OPEN) {
 			this.reconnect();
 			logger.error("重新连接websocket，重连结果【" + this.getReadyState() + "】");
 		}
    }

    public String getMessage(String sessionId) {
        return results.get(sessionId);
    }

    public String getMessageAndClean(String sessionId) {
        String msg = results.get(sessionId);
        results.remove(sessionId);
        return msg;
    }

    public String send(Map<String, Object> params) throws Exception {
        Double end = Math.pow(10, 3);
        String requestId = String.format(System.currentTimeMillis() + "%0" + 3 + "d", RandomUtils.nextInt(0, end.intValue() - 1));
        params.put("id", requestId);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(params);
        this.send(json);
        return requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
