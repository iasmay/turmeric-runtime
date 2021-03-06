//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.07 at 12:06:52 PM GMT+05:30 
//


package com.ebay.marketplace.search.v1.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 						This is the simple Distribution request assuming
 * 						the aggregate function as 'COUNT', and with a
 * 						single field to get
 * 						distribution for. It also
 * 						assumes descending order sort by supply.
 * 					
 * 
 * <p>Java class for SimpleCountDistributionRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SimpleCountDistributionRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ebay.com/marketplace/search/v1/services}AbstractDistributionRequestBase">
 *       &lt;sequence>
 *         &lt;element name="field" type="{http://www.ebay.com/marketplace/search/v1/services}Field"/>
 *         &lt;element name="sortOrder" type="{http://www.ebay.com/marketplace/search/v1/services}SortOrder"/>
 *         &lt;element name="sortType" type="{http://www.ebay.com/marketplace/search/v1/services}DistributionSortType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SimpleCountDistributionRequest", propOrder = {
    "field",
    "sortOrder",
    "sortType"
})
public class SimpleCountDistributionRequest
    extends AbstractDistributionRequestBase
{

    @XmlElement(required = true)
    protected Field field;
    @XmlElement(required = true)
    protected SortOrder sortOrder;
    protected DistributionSortType sortType;

    /**
     * Gets the value of the field property.
     * 
     * @return
     *     possible object is
     *     {@link Field }
     *     
     */
    public Field getField() {
        return field;
    }

    /**
     * Sets the value of the field property.
     * 
     * @param value
     *     allowed object is
     *     {@link Field }
     *     
     */
    public void setField(Field value) {
        this.field = value;
    }

    /**
     * Gets the value of the sortOrder property.
     * 
     * @return
     *     possible object is
     *     {@link SortOrder }
     *     
     */
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the value of the sortOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link SortOrder }
     *     
     */
    public void setSortOrder(SortOrder value) {
        this.sortOrder = value;
    }

    /**
     * Gets the value of the sortType property.
     * 
     * @return
     *     possible object is
     *     {@link DistributionSortType }
     *     
     */
    public DistributionSortType getSortType() {
        return sortType;
    }

    /**
     * Sets the value of the sortType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistributionSortType }
     *     
     */
    public void setSortType(DistributionSortType value) {
        this.sortType = value;
    }

}
