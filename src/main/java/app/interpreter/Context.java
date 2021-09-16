package app.interpreter;

import app.interpreter.model.expressions.TokenType;

import java.util.*;

/**
 * @author Oleksandr Haleta
 */
public class Context {
    String string;
    Map<String, Object> map;
    int index;

    public Context(String inString) {
        this.string = inString;
        map = new HashMap<>();
        index = 0;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }    

    public void evaluate() {
        String withoutSpaces = string.replaceAll("\\s", "");
        char[] characters = withoutSpaces.toCharArray();
        parse(Arrays.copyOfRange(characters, 1, characters.length - 1));
    }

    private void parse(char[] characters) {
        while (index != characters.length - 1) {
            map.put((String) checkObject(index, characters), checkObject(index, characters));
            //checkObject(0, characters);
        }
        for (Map.Entry<String, Object> m : map.entrySet()) {
            System.out.println(m.getKey() + "--" + m.getValue());
        }
    }

    private Object checkObject(int index, char[] characters) {
        char token = characters[index];
        if (TokenType.isColon(token)) {
            setIndex(getIndex() + 1);
            return checkObject(getIndex(), characters);
        }
        if (TokenType.isComma(token)) {
            setIndex(getIndex() + 1);
            return checkObject(getIndex(), characters);
        }
        if (TokenType.isString(token)) {
            String string = createString(index, characters);
            setIndex(getIndex() + 1);
            return string;

        } else if (TokenType.isBeginObject(token)) {
            return createMap(characters);
        } else if (TokenType.isBeginArray(token)) {
            return createList(characters);
        } else {
            setIndex(getIndex() + 1);
            return checkObject(getIndex(), characters);
        }
    }

    private Object createMap(char[] characters) {
        Map<String, Object> map = new HashMap<>();
        setIndex(getIndex() + 1);
        if (TokenType.isEndObject(characters[getIndex()])) {
            setIndex(getIndex() + 1);
            return map;
        } else {
            while (!TokenType.isEndObject(characters[getIndex()])) {
                if (TokenType.isString(characters[getIndex()])) {
                    String left = (String) checkObject(getIndex(), characters);
                    map.put(left, checkObject(getIndex(), characters));
                } else {
                    setIndex(getIndex() + 1);
                }
            }
            setIndex(getIndex() + 1);
        }
        return map;
    }

    private List<Object> createList(char[] characters) {
        List<Object> list = new ArrayList<>();
        setIndex(getIndex() + 1);
        if (TokenType.isEndArray(characters[getIndex()])) {
            setIndex(getIndex() + 1);
            return list;
        } else if (TokenType.isBeginObject(characters[getIndex()])) {
            list.add(createMap(characters));
        } else {
            while (!TokenType.isEndArray(characters[getIndex()])) {
                Container container = new Container();
                container.setName((String) checkObject(getIndex(), characters));
                container.setBody(checkObject(getIndex(), characters));
                list.add(container);
            }
        }
        return list;
    }

    private String createString(int index, char[] chars) {
        StringBuilder builder = new StringBuilder();
        for (int i = index + 1; i < chars.length; i++) {
            if (TokenType.isString(chars[i])) {
                setIndex(i);
                break;
            }
            builder.append(chars[i]);
        }
        return builder.toString();
    }


    public static void main(String[] args) {
        String s = "{\n" +
                "   \"pageInfo\": {\n" +
                "         \"pageName\": \"abc\",\n" +
                "         \"pagePic\": \"http://example.com/content.jpg\"\n" +
                "    }\n" +
                "    \"posts\": [\n" +
                "         {\n" +
                "              \"post_id\": \"123456789012_123456789012\",\n" +
                "              \"actor_id\": \"1234567890\",\n" +
                "              \"picOfPersonWhoPosted\": \"http://example.com/photo.jpg\",\n" +
                "              \"nameOfPersonWhoPosted\": \"Jane Doe\",\n" +
                "              \"message\": \"Sounds cool. Can't wait to see it!\",\n" +
                "              \"likesCount\": \"2\",\n" +
                "              \"comments\": [],\n" +
                "              \"timeOfPost\": \"1234567890\"\n" +
                "         }\n" +
                "    ]\n" +
                "}";

        String cont = "{\n" +
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

        Context context = new Context(s);
        context.evaluate();
    }

    private void printList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("index: " + i + " -- " + list.get(i));
        }
    }
}

class Container {
    String name;
    Object body;

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", body=" + body +
                '}';
    }
}


