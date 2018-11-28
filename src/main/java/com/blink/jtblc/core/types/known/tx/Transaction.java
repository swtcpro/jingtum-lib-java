package com.blink.jtblc.core.types.known.tx;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Hex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.blink.jtblc.core.coretypes.AccountID;
import com.blink.jtblc.core.coretypes.Amount;
import com.blink.jtblc.core.coretypes.Blob;
import com.blink.jtblc.core.coretypes.STArray;
import com.blink.jtblc.core.coretypes.STObject;
import com.blink.jtblc.core.coretypes.hash.HalfSha512;
import com.blink.jtblc.core.coretypes.hash.Hash256;
import com.blink.jtblc.core.coretypes.hash.prefixes.HashPrefix;
import com.blink.jtblc.core.coretypes.uint.UInt16;
import com.blink.jtblc.core.coretypes.uint.UInt32;
import com.blink.jtblc.core.enums.TransactionFlag;
import com.blink.jtblc.core.fields.Field;
import com.blink.jtblc.core.formats.TxFormat;
import com.blink.jtblc.core.serialized.BytesList;
import com.blink.jtblc.core.serialized.enums.TransactionType;
import com.blink.jtblc.core.types.known.tx.signed.SignedTransaction;
import com.blink.jtblc.crypto.ecdsa.IKeyPair;
import com.blink.jtblc.utils.HashUtils;
import com.blink.jtblc.utils.JsonUtils;

public class Transaction extends STObject {
	public static final boolean CANONICAL_FLAG_DEPLOYED = true;
	public static final UInt32 CANONICAL_SIGNATURE = new UInt32(TransactionFlag.FullyCanonicalSig);
	
	public Transaction(TransactionType type) {
		setFormat(TxFormat.formats.get(type));
		put(Field.TransactionType, type);
	}
	
	public SignedTransaction sign(String secret) {
		SignedTransaction signed = SignedTransaction.fromTx(this);
		signed.sign(secret);
		return signed;
	}
	
	public SignedTransaction sign(IKeyPair keyPair) {
		SignedTransaction signed = SignedTransaction.fromTx(this);
		signed.sign(keyPair);
		return signed;
	}
	
	public TransactionType transactionType() {
		return transactionType(this);
	}
	
	public Hash256 signingHash() {
		HalfSha512 signing = HalfSha512.prefixed256(HashPrefix.txSign);
		toBytesSink(signing, new FieldFilter() {
			@Override
			public boolean evaluate(Field a) {
				return a.isSigningField();
			}
		});
		return signing.finish();
	}
	
	public byte[] signingData() {
		BytesList bl = new BytesList();
		bl.add(HashPrefix.txSign.bytes);
		toBytesSink(bl, new FieldFilter() {
			@Override
			public boolean evaluate(Field a) {
				return a.isSigningField();
			}
		});
		return bl.bytes();
	}
	
	public void setCanonicalSignatureFlag() {
		UInt32 flags = get(UInt32.Flags);
		if (flags == null) {
			flags = CANONICAL_SIGNATURE;
		} else {
			flags = flags.or(CANONICAL_SIGNATURE);
		}
		put(UInt32.Flags, flags);
	}
	
	public UInt32 flags() {
		return get(UInt32.Flags);
	}
	
	public UInt32 sourceTag() {
		return get(UInt32.SourceTag);
	}
	
	public UInt32 sequence() {
		return get(UInt32.Sequence);
	}
	
	public UInt32 lastLedgerSequence() {
		return get(UInt32.LastLedgerSequence);
	}
	
	public UInt32 operationLimit() {
		return get(UInt32.OperationLimit);
	}
	
	public Hash256 previousTxnID() {
		return get(Hash256.PreviousTxnID);
	}
	
	public Hash256 accountTxnID() {
		return get(Hash256.AccountTxnID);
	}
	
	public Amount fee() {
		return get(Amount.Fee);
	}
	
	public Blob signingPubKey() {
		return get(Blob.SigningPubKey);
	}
	
	public Blob txnSignature() {
		return get(Blob.TxnSignature);
	}
	
	public AccountID account() {
		return get(AccountID.Account);
	}
	
	public void transactionType(UInt16 val) {
		put(Field.TransactionType, val);
	}
	
	public void flags(UInt32 val) {
		put(Field.Flags, val);
	}
	
	public void sourceTag(UInt32 val) {
		put(Field.SourceTag, val);
	}
	
	public void sequence(UInt32 val) {
		put(Field.Sequence, val);
	}
	
	public void lastLedgerSequence(UInt32 val) {
		put(Field.LastLedgerSequence, val);
	}
	
	public void operationLimit(UInt32 val) {
		put(Field.OperationLimit, val);
	}
	
	public void previousTxnID(Hash256 val) {
		put(Field.PreviousTxnID, val);
	}
	
	public void accountTxnID(Hash256 val) {
		put(Field.AccountTxnID, val);
	}
	
	public void fee(Amount val) {
		put(Field.Fee, val);
	}
	
	public void signingPubKey(Blob val) {
		put(Field.SigningPubKey, val);
	}
	
	public void txnSignature(Blob val) {
		put(Field.TxnSignature, val);
	}
	
	public void account(AccountID val) {
		put(Field.Account, val);
	}
	
	// 新增方法
	public void memos(STArray val) {
		put(Field.Memos, val);
	}
	
	public void addMemo(String memo) {
		JSONArray memos = new JSONArray();
		JSONObject memoObj = new JSONObject();
		JSONObject memoData = new JSONObject();
		try {
			memoData.put("MemoData", Hex.encode(memo.getBytes("UTF-8")));
			// memoData.put("MemoData", memo);
			memoObj.put("Memo", memoData);
			memos.put(memoObj);
			put(STArray.Memos, STArray.translate.fromJSONArray(memos));
			//System.out.println("------------" + Hex.toHexString(memo.getBytes("UTF-8")));
			//System.out.println(JsonUtils.toJsonString(this.fields));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// addMemo = function(memo) {
	// if (typeof memo !== 'string') {
	// this.tx_json.memo_type = new TypeError('invalid memo type');
	// return this;
	// }
	// if (memo.length > 2048) {
	// this.tx_json.memo_len = new TypeError('memo is too long');
	// return this;
	// }
	// var _memo = {};
	// _memo.MemoData = __stringToHex(utf8.encode(memo));
	// this.tx_json.Memos = (this.tx_json.Memos || []).concat({Memo: _memo});
	// };
	public Hash256 hash() {
		return get(Hash256.hash);
	}
	
	public AccountID signingKey() {
		byte[] pubKey = HashUtils.SHA256_RIPEMD160(signingPubKey().toBytes());
		return AccountID.fromAddressBytes(pubKey);
	}
}
