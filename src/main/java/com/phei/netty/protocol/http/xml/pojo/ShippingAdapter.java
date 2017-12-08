package com.phei.netty.protocol.http.xml.pojo;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 枚举转换
 * 
 * @author sea
 *
 */
public class ShippingAdapter extends XmlAdapter<String, Shipping> {

	@Override
	public Shipping unmarshal(String v) throws Exception {
		try {
			return Shipping.valueOf(v);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String marshal(Shipping v) throws Exception {
		if (v == null) {
			return "";
		}
		return v.name();
	}

}
