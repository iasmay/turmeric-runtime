//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.06.29 at 01:13:31 AM GMT-08:00 
//


package org.ebayopensource.turmeric.common.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PresenceConfig.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PresenceConfig">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Mandatory"/>
 *     &lt;enumeration value="Replaceable"/>
 *     &lt;enumeration value="Removed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PresenceConfig")
@XmlEnum
public enum PresenceConfig {

    @XmlEnumValue("Mandatory")
    MANDATORY("Mandatory"),
    @XmlEnumValue("Replaceable")
    REPLACEABLE("Replaceable"),
    @XmlEnumValue("Removed")
    REMOVED("Removed");
    private final String value;

    PresenceConfig(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PresenceConfig fromValue(String v) {
        for (PresenceConfig c: PresenceConfig.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}