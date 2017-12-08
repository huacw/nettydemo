package com.phei.netty.protocol.http.xml.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Customer information.
 */
@XmlRootElement(name = "Customer")
@XmlAccessorType(XmlAccessType.NONE)
public class Customer {
	@XmlAttribute
	private long customerNumber;

	/** Personal name. */
	@XmlElement
	private String firstName;

	/** Family name. */
	@XmlElement
	private String lastName;

	/** Middle name(s), if any. */
	private List<String> middleNames;

	public long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(long customerId) {
		this.customerNumber = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement(name = "middleNames")
	public List<String> getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(List<String> middleNames) {
		this.middleNames = middleNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Customer [customerNumber=" + customerNumber + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", middleNames=" + middleNames + "]";
	}

}