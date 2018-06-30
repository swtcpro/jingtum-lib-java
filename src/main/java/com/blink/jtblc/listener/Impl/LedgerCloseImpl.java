package com.blink.jtblc.listener.Impl;

import java.util.HashMap;
import java.util.Map;

import com.blink.jtblc.client.Request;
import com.blink.jtblc.listener.RemoteInter;

public class LedgerCloseImpl implements RemoteInter{



	@Override
	public String submit(Request request) {
		Map params = new HashMap();
		params.put("streams", new String[] {"ledger"});		
		return request.submit(params);
	}

}
