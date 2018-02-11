package cc.tantibus.json;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

/**
 * 0da - 30.01.2018.
 */
class JsonParser {
    private byte stackIndex;
    private final JsonCase[] stack;

    private final StringBuilder accumulator;

    private boolean string;
    private boolean number;
    private boolean special;

    JsonStruct result() {
        return ((JsonArray) stack[stackIndex].getInner()).get(0);
    }

    private JsonParser() {
        this.stack = new JsonCase[256];
        this.stackIndex = 0;
        this.stack[0] = JsonCase.array();
        this.string = false;
        this.special = false;
        this.number = false;
        this.accumulator = new StringBuilder();
    }

    JsonParser(CharSequence json) {
        this();
        init(json.chars());
    }

    JsonParser(InputStream is) {
        this();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        init(reader.lines().flatMapToInt(String::chars));

    }

    private void init(IntStream stream) {
        stream.forEach(token -> tokenize((char) token));
    }

    private void add(JsonStruct value) {
        stack[stackIndex].push(value);
    }

    private void push(JsonCase value) {
        add(value.getInner());
        stackIndex++;
        stack[stackIndex] = value;
    }

    private void pop() {
        stackIndex--;
    }


    private void tokenize(char token) {

        if (string) {
            string(token); return;
        }
        if (number) {
            number(token); return;
        }
        if (Character.isWhitespace(token)) return;

        switch (token) {
            case '{': push(JsonCase.object()); break;
            case '}': pop(); break;
            case '[': push(JsonCase.array()); break;
            case ']': pop(); break;
            case ',': /*nothing*/ break;
            case ':': /*nothing*/ break;

            case '"': string = true; break;

            case '-': case '0': case '1':
            case '2': case '3': case '4':
            case '5': case '6': case '7':
            case '8': case '9': number(token); break;

            case 'f': add(JsonStruct.FALSE); break;
            case 't': add(JsonStruct.TRUE); break;
            case 'n': add(JsonStruct.NULL); break;
            default: /*skip letters from: null, false, true ( u l a s e r )*/ break;
        }

    }


    private void string(char c) {

        if (c == '\\' && !special) {
            special = true;
            return;
        }

        //todo unicode chars
        if (special) {
            special = false;
            switch (c) {
                case '\\': accumulator.append('\\'); break;
                case 'b': accumulator.append('\b'); break;
                case 'f': accumulator.append('\f'); break;
                case 'n': accumulator.append('\n'); break;
                case 'r': accumulator.append('\r'); break;
                case 't': accumulator.append('\t'); break;
                case '"': accumulator.append('"'); break;
                case '/': accumulator.append('/'); break;
                case 'u': accumulator.append("\\u"); break;
            }
            return;
        }

        string = c != '"';

        if (string) accumulator.append(c);
        else {
            add(new JsonString(accumulator.toString()));
            accumulator.setLength(0);
        }
    }

    private void number(char token) {
        number = Character.isDigit(token) | token == '.' | token == 'e' | token == 'E' | token == '+' | token == '-';
        if (number) accumulator.append(token);
        else {
            add(new JsonNumber(accumulator.toString()));
            accumulator.setLength(0);
            tokenize(token);
        }
    }
}
