package com.posadskiy.currencyconverter.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonHelperTest {

    @Test
    void parseObject_simpleKeyValue() {
        Map<String, Object> map = JsonHelper.parseObject("{\"USD_EUR\":0.85}");
        assertThat(JsonHelper.getDouble(map, "USD_EUR")).isEqualTo(0.85);
    }

    @Test
    void parseObject_multipleKeys() {
        Map<String, Object> map = JsonHelper.parseObject(
            "{\"success\":true,\"result\":2.0}"
        );
        assertThat(map.get("success")).isEqualTo(true);
        assertThat(JsonHelper.getDouble(map, "result")).isEqualTo(2.0);
    }

    @Test
    void parseObject_nestedObject() {
        Map<String, Object> map = JsonHelper.parseObject(
            "{\"rates\":{\"RUB\":2,\"BYN\":4}}"
        );
        Map<String, Object> rates = JsonHelper.getObject(map, "rates");
        assertThat(JsonHelper.getDouble(rates, "RUB")).isEqualTo(2.0);
        assertThat(JsonHelper.getDouble(rates, "BYN")).isEqualTo(4.0);
    }

    @Test
    void parseObject_stringValues() {
        Map<String, Object> map = JsonHelper.parseObject(
            "{\"rates\":{\"RUB\":\"65.5\",\"BYN\":\"3.2\"}}"
        );
        Map<String, Object> rates = JsonHelper.getObject(map, "rates");
        assertThat(JsonHelper.getDouble(rates, "RUB")).isEqualTo(65.5);
        assertThat(JsonHelper.getDouble(rates, "BYN")).isEqualTo(3.2);
    }

    @Test
    void parseObject_nullAndEmpty() {
        assertThat(JsonHelper.parseObject(null)).isEmpty();
        assertThat(JsonHelper.parseObject("")).isEmpty();
        assertThat(JsonHelper.parseObject("   ")).isEmpty();
        assertThat(JsonHelper.parseObject("not-json")).isEmpty();
    }

    @Test
    void parseObject_nullValue() {
        Map<String, Object> map = JsonHelper.parseObject("{\"key\":null}");
        assertThat(map.get("key")).isNull();
    }

    @Test
    void parseObject_emptyObject() {
        Map<String, Object> map = JsonHelper.parseObject("{}");
        assertThat(map).isEmpty();
    }

    @Test
    void parseObject_escapedStrings() {
        Map<String, Object> map = JsonHelper.parseObject("{\"msg\":\"hello\\nworld\"}");
        assertThat(JsonHelper.getString(map, "msg")).isEqualTo("hello\nworld");
    }

    @Test
    void parseObject_integerValue() {
        Map<String, Object> map = JsonHelper.parseObject("{\"count\":42}");
        assertThat(JsonHelper.getDouble(map, "count")).isEqualTo(42.0);
    }

    @Test
    void getObject_missingKey_returnsEmpty() {
        Map<String, Object> map = JsonHelper.parseObject("{\"a\":1}");
        assertThat(JsonHelper.getObject(map, "missing")).isEmpty();
    }

    @Test
    void getString_returnsNull_forMissingKey() {
        Map<String, Object> map = JsonHelper.parseObject("{\"a\":1}");
        assertThat(JsonHelper.getString(map, "missing")).isNull();
    }

    @Test
    void getDouble_throwsForMissingKey() {
        Map<String, Object> map = JsonHelper.parseObject("{\"a\":1}");
        assertThrows(IllegalArgumentException.class, () -> JsonHelper.getDouble(map, "missing"));
    }

    @Test
    void parseObject_withWhitespace() {
        Map<String, Object> map = JsonHelper.parseObject("  { \"key\" : \"value\" , \"num\" : 1.5 }  ");
        assertThat(JsonHelper.getString(map, "key")).isEqualTo("value");
        assertThat(JsonHelper.getDouble(map, "num")).isEqualTo(1.5);
    }

    @Test
    void parseObject_complexNested() {
        String json = "{\"success\":true,\"query\":{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":1},\"info\":{\"quote\":0.85},\"result\":0.85}";
        Map<String, Object> map = JsonHelper.parseObject(json);
        assertThat(JsonHelper.getDouble(map, "result")).isEqualTo(0.85);
        Map<String, Object> query = JsonHelper.getObject(map, "query");
        assertThat(JsonHelper.getString(query, "from")).isEqualTo("USD");
    }
}
