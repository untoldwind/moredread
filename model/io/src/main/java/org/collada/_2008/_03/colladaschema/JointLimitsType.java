//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.27 at 09:08:35 PM MESZ 
//


package org.collada._2008._03.colladaschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for joint_limits_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="joint_limits_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="min" type="{http://www.collada.org/2008/03/COLLADASchema}minmax_type" minOccurs="0"/>
 *         &lt;element name="max" type="{http://www.collada.org/2008/03/COLLADASchema}minmax_type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "joint_limits_type", propOrder = {
    "min",
    "max"
})
public class JointLimitsType {

    protected MinmaxType min;
    protected MinmaxType max;

    /**
     * Gets the value of the min property.
     * 
     * @return
     *     possible object is
     *     {@link MinmaxType }
     *     
     */
    public MinmaxType getMin() {
        return min;
    }

    /**
     * Sets the value of the min property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinmaxType }
     *     
     */
    public void setMin(MinmaxType value) {
        this.min = value;
    }

    /**
     * Gets the value of the max property.
     * 
     * @return
     *     possible object is
     *     {@link MinmaxType }
     *     
     */
    public MinmaxType getMax() {
        return max;
    }

    /**
     * Sets the value of the max property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinmaxType }
     *     
     */
    public void setMax(MinmaxType value) {
        this.max = value;
    }

}
