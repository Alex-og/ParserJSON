package app.interpreter.model;

import java.io.IOException;
import java.io.Reader;

/**
 * @author Oleksandr Haleta
 */

public class ReaderChar {
    private static final int BUFFER_SIZE = 1024;
    private Reader reader;
    private char[] buffer;
    private int index;      // нижний индекс
    private int size;

    public ReaderChar(Reader reader) {
        this.reader = reader;
        buffer = new char[BUFFER_SIZE];
    }

    /**
     * Вернуть символ в нижнем индексе pos и вернуть
     * @return
     */
    public char peek() {
        if (index - 1 >= size) {
            return (char) -1;
        }

        return buffer[Math.max(0, index - 1)];
    }

    /**
     * Вернуть символ в нижнем индексе pos, вернуть pos + 1 и, наконец, вернуть символ
     * @return
     * @throws IOException
     */
    public char next() throws IOException {
        if (!hasMore()) {
            return (char) -1;
        }

        return buffer[index++];
    }

    /**
     Откат подстрочного индекса
     */
    public void back() {
        index = Math.max(0, --index);
    }

    /**
     Судите, закончился ли поток
     */
    public boolean hasMore() throws IOException {
        if (index < size) {
            return true;
        }

        fillBuffer();
        return index < size;
    }

    /**
     Заполните буферный массив
     * @throws IOException
     */
    void fillBuffer() throws IOException {
        int n = reader.read(buffer);
        if (n == -1) {
            return;
        }

        index = 0;
        size = n;
    }
}
