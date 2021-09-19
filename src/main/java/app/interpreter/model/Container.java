package app.interpreter.model;

public class Container {
    String name;
    Object body;

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public Object getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", body=" + body +
                '}';
    }
}
