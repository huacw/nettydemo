package com.phei.netty.protocol.http.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * JAXB工具类
 * 
 * @author 华成伍
 * 
 */
public class JAXBUtils {
	/**
	 * 把xml文件转换为JavaBean
	 * 
	 * @param xml
	 *            xml文件
	 * @param clazz
	 *            要转成的类
	 * @return 解析的实例对象
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static Object xmlToBean(String xml, Class<?> clazz) {
		try {
			if (xml == null || "".equals(xml.trim())) {
				throw new RuntimeException("XML文件为空");
			}
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller um = jaxbContext.createUnmarshaller();
			// 把xml文件转换为字符串流
			StringReader reader = new StringReader(xml);
			return um.unmarshal(reader);
		} catch (Exception e) {
			throw new RuntimeException("XML文件格式错误");
		}
	}

	/**
	 * 把xml文件转换为JavaBean
	 * 
	 * @param file
	 *            要解析的xml文件
	 * @param clazz
	 *            要转成的类
	 * @return 解析的实例对象
	 */
	public static Object xmlToBean(File file, Class<?> clazz) {
		try {
			if (file == null) {
				throw new RuntimeException("XML文件不存在");
			}
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller um = jaxbContext.createUnmarshaller();
			return um.unmarshal(file);
		} catch (Exception e) {
			throw new RuntimeException("XML文件格式错误");
		}
	}

	/**
	 * 把xml文件流转换为JavaBean
	 * 
	 * @param is
	 *            xml文件流
	 * @param clazz
	 *            要转成的类
	 * @return 解析的实例对象
	 */
	public static Object xmlToBean(InputStream is, Class<?> clazz) {
		try {
			if (is == null) {
				throw new RuntimeException("XML文件错误");
			}
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller um = jaxbContext.createUnmarshaller();
			return um.unmarshal(is);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("XML文件格式错误");
		}
	}

	/**
	 * 将java对象转换为xml字符串
	 * 
	 * @param obj
	 *            java对象
	 * @return xml字符串
	 */
	public static String beanToXML(Object obj) {
		String xml = "";
		try {
			if (obj != null) {
				JAXBContext context = JAXBContext.newInstance(obj.getClass());
				Marshaller mar = context.createMarshaller();
				StringWriter writer = new StringWriter();
				mar.marshal(obj, writer);
				xml = writer.toString();
			}
		} catch (Exception e) {
			throw new RuntimeException("java对象转换xml错误");
		}
		return xml;
	}

	/**
	 * 把xml文件流转换为JavaBean，带格式验证
	 * 
	 * @param xml
	 *            xml文件流
	 * @param clazz
	 *            要转成的类
	 * @param schemaFile
	 *            验证文件
	 * @return
	 */
	public static Object xmlToBean(InputStream xml, Class<?> clazz,
			File schemaFile) {
		if (schemaFile == null || (!schemaFile.exists())) {
			throw new RuntimeException("未找到验证文件");
		}
		Object obj = null;
		ValidationEventCollector vec = new ValidationEventCollector();

		try {
			SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(schemaFile);

			JAXBContext jc = JAXBContext.newInstance(clazz.getPackage()
					.getName());
			Unmarshaller u = jc.createUnmarshaller();
			u.setSchema(schema);
			u.setEventHandler(vec);

			obj = u.unmarshal(xml);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("XML文件格式错误");
		} finally {
			if (vec != null && vec.hasEvents()) {
				for (ValidationEvent ve : vec.getEvents()) {
					String msg = ve.getMessage();
					ValidationEventLocator vel = ve.getLocator();
					int line = vel.getLineNumber();
					int column = vel.getColumnNumber();
					System.err.println("At line=" + line + ", column=" + column
							+ ": " + msg);
				}
			}
		}

		return obj;
	}
}
