package com.blink.jtblc.client;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blink.jtblc.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test2 {
	// can reuse, share globally
	private static ObjectMapper om = new ObjectMapper();
	
	public static void main(String[] args) throws Exception {

		System.out.println(java.net.URLEncoder.encode("https://blog.csdn.net/whaosy/article/details/8776197"));
		 BigInteger bn=new BigInteger("02FE64E0C20F0058F22F3742EDC15F49F318C04F88B130742C68BAF3B1C89FD167",16);
	   System.out.println(bn.toByteArray().length);
		String json = "{" + 
				"	    \"LedgerEntryType\": [1, 1]," + 
				"	    \"TransactionType\": [1, 2]," + 
				"	    \"Flags\": [2, 2]," + 
				"	    \"SourceTag\": [2, 3]," + 
				"	    \"Sequence\": [2, 4]," + 
				"	    \"PreviousTxnLgrSeq\": [2, 5]," + 
				"	    \"LedgerSequence\": [2, 6]," + 
				"	    \"CloseTime\": [2, 7]," + 
				"	    \"ParentCloseTime\": [2, 8]," + 
				"	    \"SigningTime\": [2, 9]," + 
				"	    \"Expiration\": [2, 10]," + 
				"	    \"TransferRate\": [2, 11]," + 
				"	    \"WalletSize\": [2, 12]," + 
				"	    \"OwnerCount\": [2, 13]," + 
				"	    \"DestinationTag\": [2, 14]," + 
				"	    \"HighQualityIn\": [2, 16]," + 
				"	    \"HighQualityOut\": [2, 17]," + 
				"	    \"LowQualityIn\": [2, 18]," + 
				"	    \"LowQualityOut\": [2, 19]," + 
				"	    \"QualityIn\": [2, 20]," + 
				"	    \"QualityOut\": [2, 21]," + 
				"	    \"StampEscrow\": [2, 22]," + 
				"	    \"BondAmount\": [2, 23]," + 
				"	    \"LoadFee\": [2, 24]," + 
				"	    \"OfferSequence\": [2, 25]," + 
				"	    \"FirstLedgerSequence\": [2, 26]," + 
				"	    \"LastLedgerSequence\": [2, 27]," + 
				"	    \"TransactionIndex\": [2, 28]," + 
				"	    \"OperationLimit\": [2, 29]," + 
				"	    \"ReferenceFeeUnits\": [2, 30]," + 
				"	    \"ReserveBase\": [2, 31]," + 
				"	    \"ReserveIncrement\": [2, 32]," + 
				"	    \"SetFlag\": [2, 33]," + 
				"	    \"ClearFlag\": [2, 34]," + 
				"	    \"IndexNext\": [3, 1]," + 
				"	    \"IndexPrevious\": [3, 2]," + 
				"	    \"BookNode\": [3, 3]," + 
				"	    \"OwnerNode\": [3, 4]," + 
				"	    \"BaseFee\": [3, 5]," + 
				"	    \"ExchangeRate\": [3, 6]," + 
				"	    \"LowNode\": [3, 7]," + 
				"	    \"HighNode\": [3, 8]," + 
				"	    \"EmailHash\": [4, 1]," + 
				"	    \"LedgerHash\": [5, 1]," + 
				"	    \"ParentHash\": [5, 2]," + 
				"	    \"TransactionHash\": [5, 3]," + 
				"	    \"AccountHash\": [5, 4]," + 
				"	    \"PreviousTxnID\": [5, 5]," + 
				"	    \"LedgerIndex\": [5, 6]," + 
				"	    \"WalletLocator\": [5, 7]," + 
				"	    \"RootIndex\": [5, 8]," + 
				"	    \"AccountTxnID\": [5, 9]," + 
				"	    \"BookDirectory\": [5, 16]," + 
				"	    \"InvoiceID\": [5, 17]," + 
				"	    \"Nickname\": [5, 18]," + 
				"	    \"Amendment\": [5, 19]," + 
				"	    \"TicketID\": [5, 20]," + 
				"	    \"Amount\": [6, 1]," + 
				"	    \"Balance\": [6, 2]," + 
				"	    \"LimitAmount\": [6, 3]," + 
				"	    \"TakerPays\": [6, 4]," + 
				"	    \"TakerGets\": [6, 5]," + 
				"	    \"LowLimit\": [6, 6]," + 
				"	    \"HighLimit\": [6, 7]," + 
				"	    \"Fee\": [6, 8]," + 
				"	    \"SendMax\": [6, 9]," + 
				"	    \"MinimumOffer\": [6, 16]," + 
				"	    \"JingtumEscrow\": [6, 17]," + 
				"	    \"DeliveredAmount\": [6, 18]," + 
				"	    \"PublicKey\": [7, 1]," + 
				"	    \"MessageKey\": [7, 2]," + 
				"	    \"SigningPubKey\": [7, 3]," + 
				"	    \"TxnSignature\": [7, 4]," + 
				"	    \"Generator\": [7, 5]," + 
				"	    \"Signature\": [7, 6]," + 
				"	    \"Domain\": [7, 7]," + 
				"	    \"FundCode\": [7, 8]," + 
				"	    \"RemoveCode\": [7, 9]," + 
				"	    \"ExpireCode\": [7, 10]," + 
				"	    \"CreateCode\": [7, 11]," + 
				"	    \"MemoType\": [7, 12]," + 
				"	    \"MemoData\": [7, 13]," + 
				"	    \"MemoFormat\": [7, 14]," + 
				"	    \"Account\": [8, 1]," + 
				"	    \"Owner\": [8, 2]," + 
				"	    \"Destination\": [8, 3]," + 
				"	    \"Issuer\": [8, 4]," + 
				"	    \"Target\": [8, 7]," + 
				"	    \"RegularKey\": [8, 8]," + 
				"	    \"undefined\": [15, 1]," + 
				"	    \"TransactionMetaData\": [14, 2]," + 
				"	    \"CreatedNode\": [14, 3]," + 
				"	    \"DeletedNode\": [14, 4]," + 
				"	    \"ModifiedNode\": [14, 5]," + 
				"	    \"PreviousFields\": [14, 6]," + 
				"	    \"FinalFields\": [14, 7]," + 
				"	    \"NewFields\": [14, 8]," + 
				"	    \"TemplateEntry\": [14, 9]," + 
				"	    \"Memo\": [14, 10]," + 
				"	    \"SigningAccounts\": [15, 2]," + 
				"	    \"TxnSignatures\": [15, 3]," + 
				"	    \"Signatures\": [15, 4]," + 
				"	    \"Template\": [15, 5]," + 
				"	    \"Necessary\": [15, 6]," + 
				"	    \"Sufficient\": [15, 7]," + 
				"	    \"AffectedNodes\": [15, 8]," + 
				"	    \"Memos\": [15, 9]," + 
				"	    \"CloseResolution\": [16, 1]," + 
				"	    \"TemplateEntryType\": [16, 2]," + 
				"	    \"TransactionResult\": [16, 3]," + 
				"	    \"TakerPaysCurrency\": [17, 1]," + 
				"	    \"TakerPaysIssuer\": [17, 2]," + 
				"	    \"TakerGetsCurrency\": [17, 3]," + 
				"	    \"TakerGetsIssuer\": [17, 4]," + 
				"	    \"Paths\": [18, 1]," + 
				"	    \"Indexes\": [19, 1]," + 
				"	    \"Hashes\": [19, 2]," + 
				"	    \"Amendments\": [19, 3]" + 
				"	}";
		Map newMap = 	JsonUtils.toObject(json, Map.class);
		Map aaMap = new HashMap<>();
		Map bbMap = new HashMap<>();
		bbMap.put(1, "LedgerEntryType");
		aaMap.put(1, bbMap);
		// json = om.writeValueAsString(aaMap);
		System.out.println(json);
		
		List list = new ArrayList<String>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(list.subList(1, list.size()));
		System.out.println("aa".equals(aaMap.get("aaa")));
		System.out.println(Map.class);
		TestClass t = new TestClass();
		TestClass t1 = new TestClass();
		t1.setName("t1");
		TestClass t2 = new TestClass();
		t2.setName("t2");
		TestClass t3 = new TestClass();
		t3.setName("t3");
		t.setList(new ArrayList<>());
		t.getList().add(t1);
		t.getList().add(t2);
		t.getList().add(t3);
		Map params = new HashMap<>();
		params.put("result", t);
		params.put("status", "success");
		String jsonStr = om.writeValueAsString(params);
		System.out.println(jsonStr);
		TestClass res = JsonUtils.toEntity(jsonStr, TestClass.class);
		jsonStr = om.writeValueAsString(res);
		System.out.println(jsonStr);
		// java数组转换成json串
		String simpleArray[] = { "英国", "法国", "德国" };
		String arrJson = JsonUtils.toJsonString(simpleArray);
		System.out.println(arrJson);
		// ["英国","法国","德国"]
		// 简单list转换成json串
		List simpleList = new ArrayList();
		simpleList.add("中国");
		simpleList.add("美国");
		simpleList.add("俄罗斯");
		String listJson = JsonUtils.toJsonString(simpleList);
		System.out.println(listJson);
		// ["中国","美国","俄罗斯"]
		// 简单Map转换成json串
		Map simpleMap = new HashMap();
		simpleMap.put("name", "张三");
		simpleMap.put("birthday", "1983");
		String mapJson = JsonUtils.toJsonString(simpleMap);
		System.out.println(mapJson);
		// {"birthday":"1983","name":"张三"}
		// 复杂List转换成json串
		List<Object> fuzaList = new ArrayList<>();
		fuzaList.add(simpleMap);
		fuzaList.add(simpleArray);
		fuzaList.add(simpleList);
		String fuzaListJson = JsonUtils.toJsonString(fuzaList);
		System.out.println(fuzaListJson);
		// [{"birthday":"1983","name":"张三"},["英国","法国","德国"],["中国","美国","俄罗斯"]]
		// 复杂Map转成json串
		Map fuzaMap = new HashMap();
		fuzaMap.put("count", 100);
		fuzaMap.put("page_size", 10);
		fuzaMap.put("data", fuzaList);
		String json2 = JsonUtils.toJsonString(fuzaMap);
		System.out.println(json2);
		// {"page_size":10,"count":100,"data":[{"birthday":"1983","name":"张三"},["英国","法国","德国"],["中国","美国","俄罗斯"]]}
		// json传还原成java数组
		simpleArray = JsonUtils.toObject(arrJson, String[].class);
		System.out.println(simpleArray);
		for (int i = 0; i < simpleArray.length; i++) {
			System.out.println(simpleArray[i]);// 英国 法国 德国
		}
		// json还原成list
		simpleList = JsonUtils.toObject(listJson, List.class);
		System.out.println(simpleList);
		// [中国, 美国, 俄罗斯]
		// json还原成简单map
		simpleMap = JsonUtils.toObject(mapJson, Map.class);
		System.out.println(simpleMap);
		// {birthday=1983, name=张三}
		// json还原成复杂List
		fuzaList = JsonUtils.toObject(fuzaListJson, List.class);
		simpleMap = (Map) fuzaList.get(0);
		System.out.println(simpleMap);
		// {birthday=1983, name=张三}
		simpleList = (List) fuzaList.get(1);// array -> list
		System.out.println(simpleList);
		// [英国, 法国, 德国]
		simpleList = (List) fuzaList.get(2);
		System.out.println(simpleList);
		// [中国, 美国, 俄罗斯]
		// json还原成复杂map
		fuzaMap = JsonUtils.toObject(json2, Map.class);
		System.out.println(fuzaMap);
		// {page_size=10, count=100, data=[{birthday=1983, name=张三}, [英国, 法国, 德国], [中国, 美国, 俄罗斯]]}
		String ledger_index = "8488670";
		String ledger_hash = "";
		boolean transactions = true;
	}
}