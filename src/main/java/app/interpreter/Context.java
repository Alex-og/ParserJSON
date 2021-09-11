package app.interpreter;

import app.interpreter.model.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class Context {
    /*Stack<Pair<String, String>> stack;
    private String inString;
    private boolean flag;

    public Context(String inString) {
        stack = new Stack<>();
        this.inString = inString;
    }*/

   /* public void evaluate() {
        String str = inString.substring(1, inString.length() - 1).trim();
        //parseString(str);
    }*/

    /*private boolean isSimple(String s) {
        Pattern p = Pattern.compile("\\{\\[");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    private void parseString(String str) {
        while (!flag) {
            String left = getLeftSideBeforeColon(str.trim());
            String newString = str.substring(left.length() + 3);
            String right = getRightSideAfterColon(*//*str.substring(left.length() + 3)*//* newString.trim());
            Pair<String, String> pair = new Pair<>(left, right);
            stack.push(pair);
        }
        *//*System.out.println(left);
        System.out.println(right);*//*
    }

    private void parsePair(String st) {

    }

    private String getLeftSideBeforeColon(String string) {
        String str = string.substring(0, string.indexOf(':'));
        String left = str.replaceAll("\"", "").trim();
        inString = string.substring(str.length() + 1);
        return left;
    }

    private String getRightSideAfterColon(String string) {
        String str;
        if (isSimple(string)) {
            str = string.substring(string.indexOf(':') + 1, string.indexOf(',')).trim();
        } else if (string.startsWith("{")){
            str = string.substring(string.indexOf('{') + 1, string.indexOf('}')).trim();
        } else {
            str = string.substring(string.indexOf('[') + 1, string.lastIndexOf(']')).trim();
        }
        if (isSimple(str)) {
            inString = string.substring(str.length() + 1).trim();
            String[] arr = str.split("\\n");
            for (String s : arr) {
                parseString(s);
            }
            return str.replaceAll("\"", "").trim();
        } else if (str.startsWith("{")) {
            parseString(str.substring(1));
        } else if (str.trim().startsWith("[")) {
            parseString(str.trim().substring(1));
        }
        return "";
    }*/

    /////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    "pageInfo": {
         "pageName": "abc",
         "pagePic": "http://example.com/content.jpg"
    }
    "posts": [
         {
              "post_id": "123456789012_123456789012",
              "actor_id": "1234567890",
              "picOfPersonWhoPosted": "http://example.com/photo.jpg",
              "nameOfPersonWhoPosted": "Jane Doe",
              "message": "Sounds cool. Can't wait to see it!",
              "likesCount": "2",
              "comments": [],
              "timeOfPost": "1234567890"
         }
    ]
     */
    private String input;
    List<String> listOfTokens;
    Map<String, Object> mapAllObjects;

    public Context(String input) {
        this.input = input;
        listOfTokens = splitByTokens(input);
        mapAllObjects = new HashMap<>();
    }

    public String getInput() {
        return input;
    }

    public Map<String, Object> getMapAllObjects() {
        return mapAllObjects;
    }


    private void ddd() {
        //printList(listOfTokens);

        if (isSquareNearestToCenter()) {
            createListFromJsonArray();
        } else {

        }


        for (Map.Entry<String, Object> entry : mapAllObjects.entrySet()) {
            System.out.println(entry.getKey() + " -- " + entry.getValue());
        }


        //System.out.println(listOfTokens.get(7));
        /*byte[] b = listOfTokens.get(15).getBytes();
        System.out.println(Arrays.toString(b));*/

        //printTokens();
    }

    private boolean isSquareNearestToCenter() {
        return TokenType.BEGIN_ARRAY.getTokenCount() >= TokenType.BEGIN_OBJECT.getTokenCount();
    }


    private JsonObject createObject() {
        return null;
    }

    private int currentBeginTokenIndex;
    private int currentEndTokenIndex;

    private List<String> createListFromJsonArray() {
        int counter = TokenType.BEGIN_ARRAY.getTokenCount();
        String tknBegin = TokenType.BEGIN_ARRAY.getTokenType();
        String tknEnd = TokenType.END_ARRAY.getTokenType();
        List<String> newList = new ArrayList<>();
        String newListName = null;
        for (int i = 0; i < listOfTokens.size(); i++) {
            if (listOfTokens.get(i).equals(tknBegin)) {
                counter--;
                if (counter == 0) {
                    //currentBeginTokenIndex = i;
                    newListName = listOfTokens.get(i - 2);
                    if (listOfTokens.get(i + 1).equals(tknEnd)) {
                        listOfTokens.remove(listOfTokens.get(i));
                        listOfTokens.remove(listOfTokens.get(i + 1));
                        TokenType.BEGIN_ARRAY.decreaseTokenCount();
                        TokenType.END_ARRAY.decreaseTokenCount();
                        //currentEndTokenIndex = i + 1;
                    } else {
                        newList = fillList(i);
                    }
                }
            }
        }
        mapAllObjects.put(newListName, newList);
        return newList;
    }

    private String getNameNewList() {
        String name = listOfTokens.get(currentBeginTokenIndex - 2);
        listOfTokens.remove(currentBeginTokenIndex - 1);
        listOfTokens.remove(currentBeginTokenIndex - 2);
        currentBeginTokenIndex = 0;
        return name;
    }

    private void removeListItems(List<Integer> list) {
        //Iterator<String> iterator = list.iterator();
        /*while (iterator.hasNext()) {
                iterator.remove();
            }*/
        //currentBeginTokenIndex = 0;
        //currentEndTokenIndex = 0;
        for (int x = 0; x < listOfTokens.size(); x++) {
            for (int i = 0; i < list.size(); i++) {
                if (x == list.get(i)) {
                    listOfTokens.remove(list.get(i));
                }
            }
        }

    }

    private List<String> fillList(int n) {
        List<String> lst = new ArrayList<>();
        List<Integer> forRemove = new ArrayList<>();

        for (int i = n + 1; i < listOfTokens.size(); i++) {
            if (listOfTokens.get(i).equals(TokenType.END_ARRAY.getTokenType())) {
                //currentEndTokenIndex = i;
                listOfTokens.remove(i);
                TokenType.END_ARRAY.decreaseTokenCount();
                break;
            }
            lst.add(listOfTokens.get(i));
            forRemove.add(i);
            //listOfTokens.remove(i);
        }
        listOfTokens.remove(n);
        TokenType.BEGIN_ARRAY.decreaseTokenCount();
        removeListItems(forRemove);
        return lst;
    }

    private List<String> splitByTokens(String s) {
        List<String> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(s, "{[:,]}", true);
        while (tokenizer.hasMoreElements()) {
            list.add(tokenizer.nextToken().trim());
        }
        countTokens(list);
        return list;
    }

    private void countTokens(List<String> list) {
        for (TokenType token : TokenType.values()) {
            for (int i = 0; i < list.size(); i++) {
                if (token.getTokenType().equals(list.get(i))) {
                    token.increaseTokenCount();
                }
            }
        }
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
        /*Context context = new Context(s);
        context.evaluate();
        for (Pair pair : context.stack) {
            System.out.println(pair.getLeft() + " --- " + pair.getRight());
        }*/

        Context context = new Context(s);
        context.ddd();
    }

    private void printTokens() {
        for (TokenType token : TokenType.values()) {
            System.out.println(token.name() + " " + token.getTokenType() + " " + token.getTokenCount());
        }
    }

    private void printList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("index: " + i + " -- " + list.get(i));
        }
    }
}
