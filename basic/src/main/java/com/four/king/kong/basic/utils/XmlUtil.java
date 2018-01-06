package com.four.king.kong.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * <p>Description: XML工具类.</p>
 * <p>Copyright: Copyright(c)2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/11/28.</p>
 *
 * @author xxx
 * @version 1.0
 */
public class XmlUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);
    /**
     * 基本XML头
     */
    private static final String BASIC_XMLHEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    /**
     * <p>Description: 将对象直接转换成String类型的 XML输出.</p>
     * <p>Copyright: Copyright(c).</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/11/28.</p>
     *
     * @param obj 对象
     * @return String
     * @author xxx
     * @version 1.0
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            sw.write(BASIC_XMLHEADER);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            logger.error("--[error]-- XmlUtil Exception ", e);
        }
        return sw.toString();
    }

    /**
     * <p>Description: 将对象根据路径转换成xml文件.</p>
     * <p>Copyright: Copyright(c).</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/11/28.</p>
     *
     * @param obj  对象
     * @param path 路径
     * @author xxx
     * @version 1.0
     */
    public static void convertToXml(Object obj, String path) {
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            // 创建输出流
            FileWriter fw = null;
            try {
                fw = new FileWriter(path);
                fw.write(BASIC_XMLHEADER);
            } catch (IOException e) {
                e.printStackTrace();
            }
            marshaller.marshal(obj, fw);
        } catch (JAXBException e) {
            logger.error("--[error]-- XmlUtil Exception ", e);
        }
    }

    /**
     * <p>Description: 将String类型的xml转换成对象 .</p>
     * <p>Copyright: Copyright(c).</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/11/28.</p>
     *
     * @param clazz  类型对象
     * @param xmlStr str
     * @return Object
     * @author xxx
     * @version 1.0
     */
    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            logger.error("--[error]-- XmlUtil Exception ", e);
        }
        return xmlObject;
    }

    /**
     * <p>Description: 将file类型的xml转换成对象.</p>
     * <p>Copyright: Copyright(c).</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/11/28.</p>
     *
     * @param clazz   要转换成的类对象
     * @param xmlPath 文件路径
     * @return Object
     * @author xxx
     * @version 1.0
     */
    public static Object convertXmlFileToObject(Class clazz, String xmlPath) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fr = null;
            try {
                fr = new FileReader(xmlPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            xmlObject = unmarshaller.unmarshal(fr);
        } catch (JAXBException e) {
            logger.error("--[error]-- XmlUtil Exception ", e);
        }
        return xmlObject;
    }
}
