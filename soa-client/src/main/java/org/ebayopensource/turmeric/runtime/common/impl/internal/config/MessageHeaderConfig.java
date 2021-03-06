/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.runtime.common.impl.internal.config;

import javax.xml.namespace.QName;



/**
 * <p>Java class for MessageHeaderConfig complex type.
 * 
 * 
 * 
 * 
 */
public class MessageHeaderConfig {

    protected String javaTypeName;
    protected QName xmlTypeName;
    protected QName xmlElementName;

    /**
     * Gets the value of the javaTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJavaTypeName() {
        return javaTypeName;
    }

    /**
     * Sets the value of the javaTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJavaTypeName(String value) {
        this.javaTypeName = value;
    }

    /**
     * Gets the value of the xmlTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getXmlTypeName() {
        return xmlTypeName;
    }

    /**
     * Sets the value of the xmlTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setXmlTypeName(QName value) {
        this.xmlTypeName = value;
    }

    /**
     * Gets the value of the xmlElementName property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getXmlElementName() {
        return xmlElementName;
    }

    /**
     * Sets the value of the xmlElementName property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setXmlElementName(QName value) {
        this.xmlElementName = value;
    }

}
