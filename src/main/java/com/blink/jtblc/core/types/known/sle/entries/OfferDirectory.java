package com.blink.jtblc.core.types.known.sle.entries;

import com.blink.jtblc.core.coretypes.Issue;
import com.blink.jtblc.core.coretypes.IssuePair;

public class OfferDirectory extends DirectoryNode {
    public IssuePair issuePair() {
        return new IssuePair(takerPaysIssue(), takerGetsIssue());
    }

    public Issue takerGetsIssue() {
        // TODO: remove wrapper
        return Issue.from160s(takerGetsCurrency(), takerGetsIssuer());
    }
    public Issue takerPaysIssue() {
        // TODO: remove wrapper
        return Issue.from160s(takerPaysCurrency(), takerPaysIssuer());
    }

}
