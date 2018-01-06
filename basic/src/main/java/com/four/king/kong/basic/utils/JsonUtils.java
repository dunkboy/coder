package com.four.king.kong.basic.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>Description: 将json转换为map .</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public abstract class JsonUtils
{

    /**
     * <p>Description:json转map </p>
     * <p>Copyright:Copyright(c)2016</p>
     * <p>Company: xxx</p>
     * <p>CreateTime:2016年10月25日</p>
     * <p>@author xxx</p>
     *
     * @param jsonStr json字符串
     * @return Map<String, Object>
     */
    public static Map<String, Object> parseJSONToMap(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        // 最外层解析
        JSONObject json = JSONObject.parseObject(jsonStr);
        if (json != null) {
            for (String k : json.keySet()) {
                Object v = json.get(k);
                // 如果内层还是数组的话，继续解析
                if (v instanceof JSONArray) {
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    JSONArray jsonArray = JSONArray.parseArray(v.toString());
                    if (jsonArray != null) {
                        Iterator<Object> it = jsonArray.iterator();
                        if (it != null) {
                            while (it.hasNext()) {
                                JSONObject json2 = (JSONObject) it.next();
                                list.add(parseJSONToMap(json2.toString()));
                            }
                        }
                    }

                    map.put(k, list);
                } else {
                    map.put(k, v);
                }
            }
        }

        return map;
    }

    /**
     * <p>Description: 书写状态 .</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/10.</p>
     *
     * @param os      输出流
     * @param status  状态
     * @param message 信息
     * @author xxx
     * @version 1.0
     */
    public static void writeStatus(OutputStream os, boolean status, String message) {
        try {
            writeStatus(os, status, message, null);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    /**
     * 书写状态
     *
     * @param os      输出流
     * @param status  状态
     * @param message 信息
     * @param props   map对象
     * @throws IOException io异常
     */
    public static void writeStatus(OutputStream os, boolean status, String message, Map<String, String> props)
            throws IOException {
        JsonGenerator jg = Jackson.FACTORY.createGenerator(os, JsonEncoding.UTF8);
        jg.writeStartObject();
        jg.writeBooleanField("status", status);
        jg.writeStringField("message", message);
        if (!CollectionUtils.isEmpty(props)) {
            for (Entry<String, String> entry : props.entrySet()) {
                jg.writeStringField(entry.getKey(), entry.getValue());
            }
        }
        jg.writeEndObject();
        jg.flush();
    }

    /**
     * jackson
     */
    public static final class Jackson {
        /**
         * 初始工厂实例
         */
        public static final JsonFactory FACTORY = new JsonFactory();
        /**
         * 初始mapper实例
         */
        public static final ObjectMapper MAPPER = new ObjectMapper();

        static {
            MAPPER.setSerializationInclusion(Include.NON_NULL);
            MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            MAPPER.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//            MAPPER.registerModule(new H3M().enable(H3M.FT.FORCE_LAZY_LOADING));
        }

        /**
         * 获取JsonGenerator
         *
         * @param content 内容
         * @return JsonGenerator 对象
         */
        public static JsonGenerator createGenerator(File content) {
            try {
                return FACTORY.createGenerator(content, JsonEncoding.UTF8);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 获取JsonGenerator
         *
         * @param os 输出流
         * @return JsonGenerator 对象
         */
        public static JsonGenerator createGenerator(OutputStream os) {
            try {
                return FACTORY.createGenerator(os, JsonEncoding.UTF8);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 获取JsonGenerator
         *
         * @param writer 书写对象
         * @return JsonGenerator 对象
         */
        public static JsonGenerator createGenerator(Writer writer) {
            try {
                return FACTORY.createGenerator(writer);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 获取JsonGenerator
         *
         * @param in 输入流
         * @return JsonGenerator 对象
         */
        public static JsonParser createParser(InputStream in) {
            try {
                return FACTORY.createParser(in);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 获取JsonGenerator
         *
         * @param content 内容
         * @return JsonGenerator 对象
         */
        public static JsonParser createParser(String content) {
            try {
                return FACTORY.createParser(content);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 书写属性值
         *
         * @param os    输出流
         * @param value 属性
         */
        public static void writeValue(OutputStream os, Object value) {
            try {
                MAPPER.writeValue(os, value);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 书写属性值
         *
         * @param content           内容
         * @param jsonTypeReference 对象
         * @param <T>               泛型类
         * @return T 泛型对象
         */
        public static <T> T readValue(String content, TypeReference<T> jsonTypeReference) {
            try {
                return MAPPER.readValue(content, jsonTypeReference);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 读取属性值
         *
         * @param content   内容
         * @param valueType 属性类型
         * @param <T>       泛型类
         * @return T 泛型对象
         */
        public static <T> T readValue(String content, Class<T> valueType) {
            try {
                return MAPPER.readValue(content, valueType);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 读取属性值
         *
         * @param content      内容
         * @param valueType    属性类型
         * @param defaultValue 默认值
         * @param <T>          泛型类
         * @return T 泛型对象
         */
        public static <T> T readValue(String content, Class<T> valueType, T defaultValue) {
            try {
                return MAPPER.readValue(content, valueType);
            } catch (IOException e) {
                return defaultValue;
            }
        }

        /**
         * 读取属性值
         *
         * @param valueType 属性类型
         * @param src       文件
         * @param <T>       泛型类
         * @return T 泛型对象
         */
        public static <T> T readValue(File src, Class<T> valueType) {
            try {
                return MAPPER.readValue(src, valueType);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 读取属性值
         *
         * @param is        输入流
         * @param valueType 属性类型
         * @param <T>       泛型类
         * @return T 泛型对象
         */
        public static <T> T readValue(InputStream is, Class<T> valueType) {
            try {
                return MAPPER.readValue(is, valueType);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 读取属性值
         *
         * @param is           输入流
         * @param valueType    属性类型
         * @param defaultValue 默认值
         * @param <T>          泛型类
         * @return T 泛型对象
         */
        public static <T> T readValue(InputStream is, Class<T> valueType, T defaultValue) {
            try {
                return MAPPER.readValue(is, valueType);
            } catch (IOException e) {
                return defaultValue;
            }
        }

        /**
         * 书写为字节
         *
         * @param value 属性
         * @return byte[] 字节数组
         */
        public static byte[] writeValueAsBytes(Object value) {
            try {
                return MAPPER.writeValueAsBytes(value);
            } catch (JsonProcessingException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 书写为字符
         *
         * @param value 属性
         * @return string 字符串
         */
        public static String writeValueAsString(Object value) {
            try {
                return MAPPER.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 书写属性
         *
         * @param jg    对象
         * @param name  名称
         * @param value 值
         */
        public static void writeField(JsonGenerator jg, String name, String value) {
            if (value != null) {
                try {
                    jg.writeStringField(name, value);
                } catch (IOException e) {
                    throw new JsonException(e);
                }
            }
        }

        /**
         * 书写属性
         *
         * @param jg    对象
         * @param name  名称
         * @param value 值
         */
        public static void writeField(JsonGenerator jg, String name, Double value) {
            if (value != null) {
                try {
                    jg.writeNumberField(name, value);
                } catch (IOException e) {
                    throw new JsonException(e);
                }
            }
        }

        /**
         * 书写属性
         *
         * @param jg    对象
         * @param name  名称
         * @param value 值
         */
        public static void writeField(JsonGenerator jg, String name, Float value) {
            if (value != null) {
                try {
                    jg.writeNumberField(name, value);
                } catch (IOException e) {
                    throw new JsonException(e);
                }
            }
        }

        /**
         * 书写属性
         *
         * @param jg    对象
         * @param name  名称
         * @param value 值
         */
        public static void writeField(JsonGenerator jg, String name, Long value) {
            if (value != null) {
                try {
                    jg.writeNumberField(name, value);
                } catch (IOException e) {
                    throw new JsonException(e);
                }
            }
        }

        /**
         * 书写属性
         *
         * @param jg    对象
         * @param name  名称
         * @param value 值
         */
        public static void writeField(JsonGenerator jg, String name, Integer value) {
            if (value != null) {
                try {
                    jg.writeNumberField(name, value);
                } catch (IOException e) {
                    throw new JsonException(e);
                }
            }
        }

        /**
         * 书写属性
         *
         * @param jg      对象
         * @param name    名称
         * @param value   值
         * @param pattern 模板
         */
        public static void writeField(JsonGenerator jg, String name, Date value, String pattern) {
            if (value != null) {
                try {
                    jg.writeStringField(name, new SimpleDateFormat(pattern).format(value));
                } catch (IOException e) {
                    throw new JsonException(e);
                }
            }
        }
    }

    /**
     * fast类
     */
    public static final class Fast extends JSON
    {
        /**
         * 读取属性值
         *
         * @param content   内容
         * @param valueType 属性类型
         *                  * @param <T> 泛型类
         * @return T 泛型对象
         */
        public static <T> T readValue(File content, Class<T> valueType) {
            InputStream is = null;
            try {
                return readValue(is = FileUtils.openInputStream(content), valueType);
            } catch (IOException e) {
                throw new JsonException(e);
            } finally {
                IOUtils.closeQuietly(is);
            }
        }

        /**
         * 读取属性值
         *
         * @param is        输入流
         * @param valueType 属性类型
         * @param <T>       泛型类
         * @return T 泛型对象
         */
        public static <T> T readValue(InputStream is, Class<T> valueType) {
            try {
                return new DefaultJSONParser(IOUtils.toString(is)).parseObject(valueType);
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        /**
         * 书写属性值
         *
         * @param content 内容
         * @param value   属性值
         */
        public static void writeValue(File content, Object value) {
            OutputStream os = null;
            try {
                writeValue(os = FileUtils.openOutputStream(content), value);
            } catch (IOException e) {
                throw new JsonException(e);
            } finally {
                IOUtils.closeQuietly(os);
            }
        }

        /**
         * 书写属性值
         *
         * @param os    输出流
         * @param value 属性值
         */
        public static void writeValue(OutputStream os, Object value) {
            OutputStreamWriter osw = null;
            JSONWriter jw = null;
            try {
                (jw = new JSONWriter(osw = new OutputStreamWriter(os, ByteUtils.CHARSET_NAME))).writeValue(value);
            } catch (UnsupportedEncodingException e) {
                throw new JsonException(e);
            } finally {
                // IOUtils.closeQuietly(jw);
                try {
                    if (jw != null) {
                        jw.close();
                    }
                } catch (IOException ignored) {
                    System.out.println();
                }
                IOUtils.closeQuietly(osw);
            }
        }

        /**
         * 选择
         *
         * @param json  文件
         * @param xpath 路径
         * @param clazz class对象
         * @param <T>   泛型类
         * @return T 泛型对象
         */
        public static <T> T select(JSONObject json, String xpath, Class<T> clazz) {
            if (json == null) {
                return null;
            }
            if (StringUtils.contains(xpath, ".")) {
                String name = StringUtils.substringBefore(xpath, ".");
                json = json.getJSONObject(name);
                xpath = StringUtils.substringAfter(xpath, ".");
                return select(json, xpath, clazz);
            } else {
                return json.getObject(xpath, clazz);
            }
        }
    }

    /**
     * 状态内部类
     */
    @JsonAutoDetect(creatorVisibility = Visibility.NONE, fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE)
    public static class Status {
        /**
         * 返回前台失败状态码
         */
        public static final int FALSE = 0;
        /**
         * 返回前台成功状态码
         */
        public static final int TRUE = 1;

        @JsonProperty("errcode")
        private int errcode;
        @JsonProperty("message")
        private String message;
        @JsonProperty("data")
        private Object data;

        /**
         * 构造方法
         */
        public Status() {
        }

        /**
         * 构造方法
         *
         * @param success 是否成功
         */
        public Status(boolean success) {
            this(BooleanUtils.isTrue(success) ? TRUE : FALSE, null, null);
        }

        /**
         * 构造方法
         *
         * @param success 是否成功
         * @param message 信息
         */
        public Status(boolean success, String message) {
            this(BooleanUtils.isTrue(success) ? TRUE : FALSE, message, null);
        }

        /**
         * 构造方法
         *
         * @param success 是否成功
         * @param message 信息
         * @param data    数据对象
         */
        public Status(boolean success, String message, Object data) {
            this(BooleanUtils.isTrue(success) ? TRUE : FALSE, message, data);
        }

        /**
         * 构造方法
         *
         * @param errcode 状态码
         */
        public Status(int errcode) {
            this(errcode, null, null);
        }

        /**
         * 构造方法
         *
         * @param errcode 状态码
         * @param message 信息
         */
        public Status(int errcode, String message) {
            this(errcode, message, null);
        }

        /**
         * 构造方法
         *
         * @param errcode 状态码
         * @param data    数据对象
         */
        public Status(int errcode, Object data) {
            this(errcode, "", data);
        }

        /**
         * 构造方法
         *
         * @param errcode 状态码
         * @param message 信息
         * @param data    数据对象
         */
        public Status(int errcode, String message, Object data) {
            this.errcode = errcode;
            this.message = message;
            this.data = data;
        }

        public int getErrcode() {
            return errcode;
        }

        public void setErrcode(int errcode) {
            this.errcode = errcode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    /**
     * json异常
     *
     * @author xxx
     */
    public static final class JsonException extends RuntimeException {

        private static final long serialVersionUID = -3734539964869141033L;

        /**
         * 构造
         *
         * @param cause 原因
         */
        public JsonException(Throwable cause) {
            super(cause);
        }
    }
}
