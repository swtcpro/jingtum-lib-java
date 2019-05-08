package com.blink.jtblc.client;

import java.util.List;

import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.client.bean.TransactionInfo;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
import com.blink.jtblc.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MsgConver {
	// can reuse, share globally
	private static ObjectMapper om = new ObjectMapper();
	
	public static void main(String[] args) throws Exception {
		String msg = "{\r\n" + "	\"id\": \"1543292560655611\",\r\n" + "	\"result\": {\r\n"
		        + "		\"engine_result\": \"tesSUCCESS\",\r\n" + "		\"engine_result_code\": 0,\r\n"
		        + "		\"engine_result_message\": \"The transaction was applied. Only final in a validated ledger.\",\r\n"
		        + "		\"tx_blob\": \"120000228000000024000000AA614000000000000001684000000000000014732103725CDCE8DF9A3ECA9C311FDB9FF65F68DC86E41E4876ADAD577F75FFFC7E63667446304402201239BAAA6402C59C5F5E4D735893D747691A230562552F8002F108B0101AA5EB02204C1ABC2360F60168C4824AB37A68E4B53A1B0C4E924869B6EDEA5B76265479208114EB969C12D9CFEA15D46C2B0BABD4659E086BB7EC8314C98F6CA34063D287DA5214717AC1DE6209830DB8F9EA7D2265363934616665346262393833303265333033303330333033303331353335373534E1F1\",\r\n"
		        + "		\"tx_json\": {\r\n" + "			\"Account\": \"j47gDd3ethDU4UJMD2rosg9WrSXeh9bLd1\",\r\n"
		        + "			\"Amount\": \"1\",\r\n" + "			\"Destination\": \"jK4kdiriyxErTfW8wMMjzP25oT2AKLWGfY\",\r\n"
		        + "			\"Fee\": \"20\",\r\n" + "			\"Flags\": 2147483648,\r\n" + "			\"Memos\": [{\r\n"
		        + "				\"Memo\": {\r\n"
		        + "					\"MemoData\": \"65363934616665346262393833303265333033303330333033303331353335373534\"\r\n"
		        + "				}\r\n" + "			}],\r\n" + "			\"Sequence\": 170,\r\n"
		        + "			\"SigningPubKey\": \"03725CDCE8DF9A3ECA9C311FDB9FF65F68DC86E41E4876ADAD577F75FFFC7E6366\",\r\n"
		        + "			\"TransactionType\": \"Payment\",\r\n"
		        + "			\"TxnSignature\": \"304402201239BAAA6402C59C5F5E4D735893D747691A230562552F8002F108B0101AA5EB02204C1ABC2360F60168C4824AB37A68E4B53A1B0C4E924869B6EDEA5B7626547920\",\r\n"
		        + "			\"hash\": \"06EFE3A368AEA85483C04CD875BE5DAB2276F53965916217DF94310C61EEB463\"\r\n" + "		}\r\n" + "	},\r\n"
		        + "	\"status\": \"success\",\r\n" + "	\"type\": \"response\"\r\n" + "}";
		// memos解析丢失
		TransactionInfo bean = JsonUtils.toEntity(msg, TransactionInfo.class);
		System.out.print(bean);
	}
	
	/**
	 * 14 支付
	 *
	 * @param from 发起账号地址
	 * @param to 目标账号地址
	 * @param amount 支付金额对象Amount
	 * @param fromSecret 发起账号私钥
	 * @return
	 */
	public TransactionInfo buildPaymentTx(String from, String to, AmountInfo amount, String fromSecret, List<String> memos) {
		from = "jwZd5nvNTgTUM8UR7FVDFoe3Vb8emvzRdn";
		to = "jQGW9F6vLYX2dQMHv2eZTMRUkEHAEGJSgz";
		String memo = "{\r\n"
		        + "		 \"vc_hetmc\" : \"测试合同名称\",\"vc_chengzkhmc\" : \"承租客户名称\",\"vc_biz\" : \"人民币\",\"dec_hetje\" : \"2000000\",\"dec_shengyje\" : \"100000\",\"dt_qizrq\" :\r\n"
		        + "		 \"2018-11-16\",\"vc_hetzt\" : \"已启动\", \"type\" : \"type1\", \"dt_tijsj\" : \"2018-11-30 08:56:01\"\r\n"
		        + "		 }";
		memos.add(memo);
		fromSecret = "sniR2LfrUAfjpkMz3moYWGp9kDAyG";
		// #区块链服务器地址
		// #货币种类，三到六个字母或20字节的自定义货币
		// blockChain.currency=SWT
		// #货币数量
		// blockChain.basePrice=0.000001
		// #货币发行方
		// blockChain.issuer=
		// 使用签名
		amount = new AmountInfo();
		if (amount == null) {
			amount = new AmountInfo();
			amount.setCurrency("SWT");
			amount.setValue("0.000001");
			amount.setIssuer("");
		}
		// String memo = "支付0.000001SWT";
		String server = "ws://101.200.176.238:5020";
		Connection conn = ConnectionFactory.getCollection(server);
		Remote remote = new Remote(conn);
		Transaction tx = remote.buildPaymentTx(from, to, amount);
		tx.addMemo(memos);
		tx.setSecret(fromSecret);
		tx.setFee(20);
		TransactionInfo bean = tx.submit();
		System.out.println("14 buildPaymentTx:\n" + JsonUtils.toJsonString(bean));
		return bean;
	}
}