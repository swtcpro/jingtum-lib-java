package com.blink.jtblc.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blink.jtblc.utils.JsonUtils;

public class HandleProcessTask implements Callable<String> {
	final static Logger logger = LoggerFactory.getLogger(HandleProcessTask.class);
	private Map<String, Object> params;
	private WebSocket webSocket;
	
	public HandleProcessTask(Map<String, Object> params, WebSocket webSocket) {
		this.params = params;
		this.webSocket = webSocket;
	}
	
	@Override
	public String call() throws Exception {
		String uuid = webSocket.send(params);
		while (webSocket.getMessage(uuid) == null && webSocket.getMessage("listener")==null) {
		}
		String result ="";
		if(StringUtils.isNotBlank(uuid)) {
			result = webSocket.getMessageAndClean(uuid);
		}else{
			result = webSocket.getMessageAndClean("listener");
		}
		Map map = JsonUtils.toObject(result, Map.class);
		if (map.get("status").equals("error")) {
			throw new Exception("接口调用异常,异常信息：" + result);
		}
		switch(map.get("type").toString()) {
	        case "ledgerClosed":
	            return this.handleLedgerClosed(map);
	        case "serverStatus":
	            return this.handleServerStatus(map);
	        case "response":
	            this.handleResponse(map);
	            return result;
	        case "transaction":
	        	return this.handleTransaction(map);
	        case "path_find":
	        	return this.handlePathFind(map);
	    }
		return "";
	}
	
	public String handleLedgerClosed(Map map) throws Exception {
		Map _status =new HashMap();
		if(map.get("ledger_index")!=null&&!map.get("ledger_index").toString().equals("0")) {
			_status.put("ledger_index", map.get("ledger_index"));
			_status.put("ledger_time", map.get("ledger_time"));
			_status.put("reserve_base", map.get("reserve_base"));
			_status.put("reserve_inc", map.get("reserve_inc"));
			_status.put("fee_base", map.get("fee_base"));
			_status.put("fee_ref", map.get("fee_ref"));
			Map params = new HashMap();
			params.put("streams", new String[] {"ledger"});
			return this.sendMessage("subscribe", params);
		}
		return "";
	}
	public String handleServerStatus(Map map) throws Exception {
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
	
	public void handleResponse(Map result) throws Exception {
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
	
	public String handleTransaction(Map map) throws Exception {
		//todo 缓冲hash
		Map params = new HashMap();
		params.put("streams", new String[] {"transactions"});
		return this.sendMessage("subscribe", params);
	}
	
	public String handlePathFind(Map map) throws Exception {
		Map params = new HashMap();
		params.put("streams", new String[] {"path_find"});
		return this.sendMessage("subscribe", params);
	}
	
	
	public String sendMessage(String command, Map<String, Object> data) throws Exception{
		Map<String, Object> params = new HashMap();
		params.put("command", command);
		params.putAll(data);
		logger.debug("WebSocket参数： " + JsonUtils.toJsonString(params));
		return webSocket.send(params);
	}
}
