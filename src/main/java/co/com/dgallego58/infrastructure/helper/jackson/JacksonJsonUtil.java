package co.com.dgallego58.infrastructure.helper.jackson;

import co.com.dgallego58.infrastructure.helper.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JacksonJsonUtil implements JsonUtil {

    private final ObjectMapper mapper;

    public JacksonJsonUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T, E extends Exception> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JacksonJsonUtilException("cannot be deserialized", e);
        }
    }


    @Override
    public String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JacksonJsonUtilException("cannot be serialized", e);
        }
    }

    @Override
    public Map<String, Object> toMap(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new JacksonJsonUtilException("cannot interpret this json as map", e);
        }
    }

    public static class JacksonJsonUtilException extends RuntimeException {
        public JacksonJsonUtilException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
