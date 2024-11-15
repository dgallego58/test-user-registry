package co.com.dgallego58.infrastructure.helper;

import java.util.Map;

public interface JsonUtil {

    <T, E extends Exception> T fromJson(String json, Class<T> clazz) throws E;

    <E extends Exception> String toJson(Object obj) throws E;

    Map<String, Object> toMap(String json);
}
