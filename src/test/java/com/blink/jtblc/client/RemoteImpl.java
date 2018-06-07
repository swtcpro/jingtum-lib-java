package com.blink.jtblc.client;

import com.blink.jtblc.client.bean.*;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.exceptions.RemoteException;
import com.blink.jtblc.utils.CheckUtils;
import com.blink.jtblc.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteImpl {

    private String server = "";
    //签名默认为false,false需要传入密钥
    private Boolean local_sign = false;

    private Connection conn = null;

    public RemoteImpl(Connection conn) {
        this.conn = conn;
    }

    public RemoteImpl(Connection conn, Boolean local_sign) {
        this.conn = conn;
        this.local_sign = local_sign;
    }

    //4.2  创建连接
    public String subscribe()throws Exception{
        Map params = new HashMap();
        Map message = new HashMap();
        List<String> strs = new ArrayList<String>();
        strs.add("ledger");
        strs.add("server");
        message.put("streams",strs);
        params.put("message",message);
        String msg = this.sendMessage("server",params);
        return msg;
    }

    /**
     * 4.4 请求底层服务器信息
     * @return
     */
    public ServerInfo requestServerInfo() throws Exception{
        ServerInfo ser =new ServerInfo();
        Map params = new HashMap();
//        params.put("command", "server_info");
        String msg = this.sendMessage("server_info",params);
        Map map = JsonUtils.toObject(msg,Map.class);
        if(map.get("status").equals("success")){
            Map result = (Map)map.get("result");
            Map info = (Map)result.get("info");
            ser.setVersion(info.get("build_version").toString());
            ser.setLedgers(info.get("complete_ledgers").toString());
            ser.setNode(info.get("pubkey_node").toString());
            ser.setState(info.get("server_state").toString());
        }
        return ser;
    }

    /**
     * 4.5 获取最新账本信息
     * @return
     * @throws Exception
     */
    public LedgerClosed requestLedgerClosed() throws Exception{
        Map params = new HashMap();
//        params.put("command", "ledger_closed");
        String msg = this.sendMessage("ledger_closed",params);
        Map map = JsonUtils.toObject(msg,Map.class);
        LedgerClosed ledger = new LedgerClosed();
        if(map.get("status").equals("success")){
            Map result = (Map)map.get("result");
            ledger.setLedgerHash(result.get("ledger_hash").toString());
            ledger.setLedgerIndex(result.get("ledger_index").toString());
        }
        return ledger;
    }
    /**
     * 4.6 获取某一账本具体信息
     * 注：整体参数是Object类型，当参数都不填时，默认返回最新账本信息
     * @param ledger_index    井通区块高度
     * @param ledger_hash     井通区块hash(与上面ledger_index二选其一)
     * @param transactions    是否返回账本上的交易记录hash，默认false
     * @return
     */
    public Ledger requestLedger(String ledger_index,String ledger_hash, boolean transactions){
        Map params = new HashMap();
        //校验,并将参数写入message对象
        Map message = new HashMap();
        if(StringUtils.isEmpty(ledger_index) && StringUtils.isEmpty(ledger_hash)) {
            message.put("ledger", new RemoteException("ledger_index and ledger_hash is null"));
        }
        if(!CheckUtils.isNumeric(ledger_index)) {
            message.put("ledger_index", new RemoteException("invalid ledger_index"));
        }
        if(!CheckUtils.isValidHash(ledger_hash)) {
            message.put("ledger_hash", new RemoteException("invalid ledger_hash"));
        }
        params.put("message", message);
        params.put("ledger_index",ledger_index);
        params.put("ledger_hash",ledger_hash);
        params.put("transactions",transactions);
//        params.put("command", "ledger");
        String msg = this.sendMessage("ledger",params);
        Map map = JsonUtils.toObject(msg,Map.class);
        Ledger ledger =  new Ledger();
        if(map.get("status").equals("success")){
            Map result = (Map)map.get("result");
            Map ledgerMap = (Map)result.get("ledger");
            ledger.setAccepted((Boolean)ledgerMap.get("accepted"));
            ledger.setLedgerHash(ledgerMap.get("account_hash").toString());
            ledger.setLedgerIndex(ledgerMap.get("ledger_index").toString());
            ledger.setParentHash(ledgerMap.get("parent_hash").toString());
            ledger.setCloseTimeHuman(ledgerMap.get("close_time_human").toString());
            ledger.setTotalCoins(ledgerMap.get("total_coins").toString());
        }
        return ledger;
    }


    /**
     * 4.7 查询某一交易具体信息
     *
     * @param hash 交易hash
     * @return
     */
    public Account requestTx(String hash) {
        Map params = new HashMap();
        //校验,并将参数写入message对象
        Map message = new HashMap();
        if(!CheckUtils.isValidHash(hash)) {
            message.put("ledger_hash", new RemoteException("invalid tx hash"));
        }
        message.put("transaction", hash);
        params.put("message", message);
        params.put("transaction", hash);
//        params.put("command", "tx");
        String msg = this.sendMessage("tx",params);
        Account account = JsonUtils.toObject(msg,Account.class);
        return account;
    }

    /**
     * 4.8 请求账号信息
     *
     * @param account 井通钱包地址
     * @return
     */
    public AccountInfo requestAccountInfo(String account) {
        String msg =requestAccount("account_info", account, "");
        AccountInfo accountInfo = JsonUtils.toObject(msg,AccountInfo.class);
        return accountInfo;
    }

    /**
     * 4.9 获得账号可接收和发送的货币
     *
     * @param account 井通钱包地址
     * @return
     */
    public AccountTums requestAccountTums(String account) {
        String msg = requestAccount("account_currencies", account, "");
        AccountTums accountTums = JsonUtils.toObject(msg,AccountTums.class);
        return accountTums;
    }

    /**
     * 4.10 获得账号关系
     *
     * @param account 井通钱包地址
     * @param type 关系类型，固定的三个值：trust、authorize、freeze
     * @return
     */
    public AccountRelations requestAccountRelations(String account, String type) {
        String command = "";
        if(!CheckUtils.isValidType("relation",type)) {
            throw new RemoteException("invalid relation type");
        }
        switch (type) {
            case "trust":
                command = "account_lines";
            case "authorize":
            case "freeze":
                command = "account_relation";
        }
        String msg = requestAccount(command, account, type);
        AccountRelations accountRelations = JsonUtils.toObject(msg,AccountRelations.class);
        return accountRelations;
    }

    /**
     * 4.11 获得账号挂单
     *
     * @param account 井通钱包地址
     * @return
     */
    public AccountOffers requestAccountOffers(String account) {
        String msg = requestAccount("account_offers", account, "");
        AccountOffers accountOffers = JsonUtils.toObject(msg,AccountOffers.class);
        return accountOffers;
    }


    /**
     * 获取账号信息
     * inner function
     * @param command
     * @param account
     * @param type
     * @return
     */
    private String requestAccount(String command, String account, String type) {
        Map params = new HashMap();

        //校验,并将参数写入message对象
        Map message = new HashMap();

        if(StringUtils.isNotEmpty(type)) {
            Integer relation_type = null;
            switch (type) {
                case "trustline":
                    relation_type = 0;
                case "authorize":
                    relation_type = 1;
                case "freeze":
                    relation_type = 3;

            }
            message.put("relation_type", relation_type);
        }

        if(!CheckUtils.isValidAddress(account)) {
            message.put("account", new RemoteException("invalid account"));
        }else {
            message.put("account", account);
        }
        //request.selectLedger(ledger);
        //node.js参数peer/limit/marker无相关信息--待完善;
        params.put("message", message);

        params.put("account", account);
       // params.put("command", command);
        String msg = this.sendMessage(command,params);
        return msg;
    }
    /**
     * 工具方法：组装发送前的信息
     *
     * @param command
     * @param data
     * @return
     */
    public String sendMessage(String command, Map<String, Object> data) {
        Map<String, Object> params = new HashMap();
        params.put("command", command);
        params.putAll(data);
        return conn.submit(params);
    }

}
