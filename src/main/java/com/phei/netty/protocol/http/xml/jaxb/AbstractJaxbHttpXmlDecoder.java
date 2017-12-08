/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phei.netty.protocol.http.xml.jaxb;

import java.io.StringReader;
import java.nio.charset.Charset;

import com.phei.netty.protocol.http.xml.JAXBUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * Jaxb的解码器
 * 
 * @author sea
 *
 * @param <T>
 */
public abstract class AbstractJaxbHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {

	private StringReader reader;
	private Class<?> clazz;
	private boolean isPrint;
	private final static String CHARSET_NAME = "UTF-8";
	private final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

	protected AbstractJaxbHttpXmlDecoder(Class<?> clazz) {
		this(clazz, false);
	}

	protected AbstractJaxbHttpXmlDecoder(Class<?> clazz, boolean isPrint) {
		this.clazz = clazz;
		this.isPrint = isPrint;
	}

	protected Object decode0(ChannelHandlerContext arg0, ByteBuf body) throws Exception {
		String content = body.toString(UTF_8);
		if (isPrint)
			System.out.println("The body is : " + content);
		return JAXBUtils.xmlToBean(content, clazz);
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to
	 * forward to the next {@link ChannelHandler} in the {@link ChannelPipeline}
	 * .
	 * 
	 * Sub-classes may override this method to change behavior.
	 */
	@Skip
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 释放资源
		if (reader != null) {
			reader.close();
			reader = null;
		}
	}
}
