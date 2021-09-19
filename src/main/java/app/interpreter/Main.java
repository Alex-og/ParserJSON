package app.interpreter;

import app.interpreter.model.AbstractContext;
import app.interpreter.model.Context;
import app.interpreter.tst.Page;

public class Main {
    public static void main(String[] args) {
        Provider<Page> provider = new Provider<>();
        Page page = provider.getContext(getJson());
    }

    private static String getJson() {
        return  "{\n" +
                "        \"posts\": [\n" +
                "        \"sdff\":[],\n" +
                "        \"post_id\": \"123456789012_123456789012\",\n" +
                "            \"actor_id\": \"1234567890\"\n" +
                "]\n" +
                "        \"person\": [\n" +
                "        \"nameOfPersonWhoPosted\": \"Jane Doe\",\n" +
                "            \"message\": \"Sounds cool. Can't wait to see it!\"\n" +
                "]\n" +
                "    }";
    }
}
