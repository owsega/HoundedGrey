package com.owsega.houndedgrey;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hound.core.model.sdk.ClientMatch;

/**
 *
 */
public class MiscClientMatchFactory {

    public static ClientMatch getLightsOnMatch() {
        // client match 1
        ClientMatch clientMatch1 = new ClientMatch();
        clientMatch1.setExpression("([1/100 (\"can\"|\"could\"|\"will\"|\"would\").\"you\"].[1/10 \"please\"].(\"turn\"|\"switch\"|(1/100 \"flip\")).\"on\".[\"the\"].(\"light\"|\"lights\").[1/20 \"for\".\"me\"].[1/20 \"please\"]) \n" +
                "| \n" +
                "([1/100 (\"can\"|\"could\"|\"will\"|\"would\").\"you\"].[1/10 \"please\"].[100 (\"turn\"|\"switch\"|(1/100 \"flip\"))].[\"the\"].(\"light\"|\"lights\").\"on\".[1/20 \"for\".\"me\"].[1/20 \"please\"]) \n" +
                "| \n" +
                "(((\"i\".(\"want\"|\"like\"))|(((\"i\".[\"would\"])|(\"i'd\")).(\"like\"|\"want\"))).[\"the\"].(\"light\"|\"lights\").[\"turned\"|\"switched\"|(\"to\".\"go\")|(1/100\"flipped\")].\"on\".[1/20\"please\"]) ");

        clientMatch1.setSpokenResponse("Ok, I'm turning the lights on.");
        clientMatch1.setSpokenResponseLong("Ok, I am turning the lights on.");
        clientMatch1.setWrittenResponse("Ok, I'm turning the lights on.");
        clientMatch1.setWrittenResponseLong("Ok, I am turning the lights on.");

        ObjectNode result1Node = JsonNodeFactory.instance.objectNode();
        result1Node.put("Intent", "TURN_LIGHT_ON");
        clientMatch1.setResult(result1Node);
        return clientMatch1;
    }

    public static ClientMatch getLightsOffMatch() {
        ClientMatch clientMatch2 = new ClientMatch();
        clientMatch2.setExpression("([1/100 (\"can\"|\"could\"|\"will\"|\"would\").\"you\"].[1/10 \"please\"].(\"turn\"|\"switch\"|(1/100 \"flip\")).\"off\".[\"the\"].(\"light\"|\"lights\").[1/20 \"for\".\"me\"].[1/20 \"please\"]) \n" +
                "| \n" +
                "([1/100 (\"can\"|\"could\"|\"will\"|\"would\").\"you\"].[1/10 \"please\"].[100 (\"turn\"|\"switch\"|(1/100 \"flip\"))].[\"the\"].(\"light\"|\"lights\").\"off\".[1/20 \"for\".\"me\"].[1/20 \"please\"]) \n" +
                "| \n" +
                "(((\"i\".(\"want\"|\"like\"))|(((\"i\".[\"would\"])|(\"i'd\")).(\"like\"|\"want\"))).[\"the\"].(\"light\"|\"lights\").[\"turned\"|\"switched\"|(\"to\".\"go\")|(1/100\"flipped\")].\"off\".[1/20\"please\"]) ");

        clientMatch2.setSpokenResponse("Ok, I'm turning the lights off.");
        clientMatch2.setSpokenResponseLong("Ok, I am turning the lights off.");
        clientMatch2.setWrittenResponse("Ok, I'm turning the lights off.");
        clientMatch2.setWrittenResponseLong("Ok, I am turning the lights off.");

        final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode result2Node = nodeFactory.objectNode();
        result2Node.put("Intent", "TURN_LIGHT_OFF");
        clientMatch2.setResult(result2Node);
        return clientMatch2;
    }

}
