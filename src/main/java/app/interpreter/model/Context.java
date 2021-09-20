package app.interpreter.model;

import java.util.*;

/**
 * @author Oleksandr Haleta
 */
public class Context implements AbstractContext {
    String string;
    Map<String, Object> map;
    int index;

    public Context(String inString) {
        this.string = inString;
        map = new HashMap<>();
        index = 0;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    @Override
    public void evaluate() {
        String withoutSpaces = string.replaceAll("\\s", "");
        char[] characters = withoutSpaces.toCharArray();
        parse(Arrays.copyOfRange(characters, 1, characters.length - 1));
    }

    public void parse(char[] characters) {
        while (index != characters.length - 1) {
            map.put((String) checkObject(index, characters), checkObject(index, characters));
        }
        for (Map.Entry<String, Object> m : map.entrySet()) {
            System.out.println(m.getKey() + "--" + m.getValue());
        }
    }

    public Object checkObject(int index, char[] characters) {
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

    public Object createMap(char[] characters) {
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

    public List<Object> createList(char[] characters) {
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

    public String createString(int index, char[] chars) {
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
