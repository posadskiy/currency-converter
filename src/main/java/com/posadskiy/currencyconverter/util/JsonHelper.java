package com.posadskiy.currencyconverter.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Minimal zero-dependency JSON object parser. Handles the subset of JSON
 * returned by currency-rate provider APIs: objects, strings, numbers,
 * booleans, and null. Arrays are skipped (not used by any provider response).
 */
public final class JsonHelper {

    private JsonHelper() {}

    public static Map<String, Object> parseObject(String json) {
        if (json == null || json.trim().isEmpty()) return Collections.emptyMap();
        Parser p = new Parser(json.trim());
        if (p.peek() != '{') return Collections.emptyMap();
        return p.readObject();
    }

    public static double getDouble(Map<String, Object> map, String key) {
        Object v = map.get(key);
        if (v instanceof Number) return ((Number) v).doubleValue();
        if (v instanceof String) return Double.parseDouble((String) v);
        throw new IllegalArgumentException("Key '" + key + "' is not a number");
    }

    public static String getString(Map<String, Object> map, String key) {
        Object v = map.get(key);
        return v == null ? null : v.toString();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getObject(Map<String, Object> map, String key) {
        Object v = map.get(key);
        if (v instanceof Map) return (Map<String, Object>) v;
        return Collections.emptyMap();
    }

    private static final class Parser {
        private final String src;
        private int pos;

        Parser(String src) {
            this.src = src;
        }

        char peek() {
            skipWhitespace();
            return pos < src.length() ? src.charAt(pos) : 0;
        }

        char advance() {
            skipWhitespace();
            return pos < src.length() ? src.charAt(pos++) : 0;
        }

        void skipWhitespace() {
            while (pos < src.length() && Character.isWhitespace(src.charAt(pos))) pos++;
        }

        Map<String, Object> readObject() {
            advance(); // consume '{'
            Map<String, Object> map = new LinkedHashMap<>();
            if (peek() == '}') { advance(); return map; }
            while (true) {
                String key = readString();
                if (advance() != ':') break;
                Object value = readValue();
                map.put(key, value);
                char next = advance();
                if (next == '}') break;
                if (next != ',') break;
            }
            return map;
        }

        Object readValue() {
            char c = peek();
            if (c == '"') return readString();
            if (c == '{') return readObject();
            if (c == '[') { skipArray(); return null; }
            if (c == 't' || c == 'f') return readBoolean();
            if (c == 'n') { readNull(); return null; }
            return readNumber();
        }

        String readString() {
            advance(); // consume '"'
            StringBuilder sb = new StringBuilder();
            while (pos < src.length()) {
                char c = src.charAt(pos++);
                if (c == '"') return sb.toString();
                if (c == '\\' && pos < src.length()) {
                    char esc = src.charAt(pos++);
                    switch (esc) {
                        case '"': case '\\': case '/': sb.append(esc); break;
                        case 'n': sb.append('\n'); break;
                        case 'r': sb.append('\r'); break;
                        case 't': sb.append('\t'); break;
                        default: sb.append(esc);
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        Number readNumber() {
            skipWhitespace();
            int start = pos;
            while (pos < src.length()) {
                char c = src.charAt(pos);
                if (c == ',' || c == '}' || c == ']' || Character.isWhitespace(c)) break;
                pos++;
            }
            String num = src.substring(start, pos);
            if (num.contains(".") || num.contains("e") || num.contains("E")) {
                return Double.parseDouble(num);
            }
            try {
                return Long.parseLong(num);
            } catch (NumberFormatException e) {
                return Double.parseDouble(num);
            }
        }

        boolean readBoolean() {
            if (src.startsWith("true", pos)) { pos += 4; return true; }
            if (src.startsWith("false", pos)) { pos += 5; return false; }
            throw new IllegalArgumentException("Invalid boolean at position " + pos);
        }

        void readNull() {
            if (src.startsWith("null", pos)) { pos += 4; }
        }

        void skipArray() {
            advance(); // consume '['
            int depth = 1;
            while (pos < src.length() && depth > 0) {
                char c = src.charAt(pos++);
                if (c == '[') depth++;
                if (c == ']') depth--;
                if (c == '"') {
                    while (pos < src.length()) {
                        char sc = src.charAt(pos++);
                        if (sc == '\\') pos++;
                        else if (sc == '"') break;
                    }
                }
            }
        }
    }
}
