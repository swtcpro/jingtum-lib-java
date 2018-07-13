package com.blink.jtblc.core.coretypes;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.blink.jtblc.core.coretypes.hash.Hash160;

/**
 * Represents a currency/issuer pair
 */
public class Issue implements Comparable<Issue> {

    public static final Issue SWT = fromString("SWT");
    final Currency currency;
    final AccountID issuer;

    public Issue(Currency currency, AccountID issuer) {
        this.currency = currency;
        this.issuer = issuer;
    }

    public static Issue fromString(String pair) {
        String[] split = pair.split("/");
        return fromStringPair(split);
    }

    private static Issue fromStringPair(String[] split) {
        if (split.length == 2) {
            return new Issue(Currency.fromString(split[0]), AccountID.fromString(split[1]));
        } else if (split[0].equals("SWT")) {
            return new Issue(Currency.SWT, AccountID.SWT_ISSUER);
        } else {
            throw new RuntimeException("Issue string must be XRP or $currency/$issuer");
        }
    }

    /**
     * See {@link com.blink.jtblc.core.fields.Field#TakerGetsCurrency}
     * See {@link com.blink.jtblc.core.fields.Field#TakerGetsIssuer}
     *
     * TODO: better handling of Taker(Gets|Pays)(Issuer|Curency)
     *       maybe special subclasses of AccountID / Currency
     *       respectively?
     */
    public static Issue from160s(Hash160 currency, Hash160 issuer) {
        return new Issue(new Currency(currency.bytes()),
                new AccountID(issuer.toBytes()));
    }

    public Currency currency() {
        return currency;
    }

    public AccountID issuer() {
        return issuer;
    }

    @Override
    public String toString() {
        if (isNative()) {
            return "XRP";
        } else {
            return String.format("%s/%s", currency, issuer);
        }
    }

    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        o.put("currency", currency);
        if (!isNative()) {
            o.put("issuer", issuer);
        }
        return o;
    }

    public Amount amount(BigDecimal value) {
        return new Amount(value, currency, issuer, isNative());
    }

    public boolean isNative() {
        return this == SWT || currency.equals(Currency.SWT);
    }

    public Amount amount(Number value) {
        return new Amount(BigDecimal.valueOf(value.doubleValue()), currency, issuer, isNative());
    }

    @Override
    public int compareTo(Issue o) {
        int ret = issuer.compareTo(o.issuer);
        if (ret != 0) {
            return ret;
        }
        ret = currency.compareTo(o.currency);
        return ret;
    }

    public Amount roundedAmount(BigDecimal amount) {
        return amount(Amount.roundValue(amount, isNative()));
    }
}
