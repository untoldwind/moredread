//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.27 at 09:08:35 PM MESZ 
//


package org.collada._2008._03.colladaschema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for motion_technique_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="motion_technique_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="axis_info" type="{http://www.collada.org/2008/03/COLLADASchema}motion_axis_info_type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="effector_info" type="{http://www.collada.org/2008/03/COLLADASchema}motion_effector_info_type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "motion_technique_type", propOrder = {
    "axisInfo",
    "effectorInfo"
})
public class MotionTechniqueType {

    @XmlElement(name = "axis_info")
    protected List<MotionAxisInfoType> axisInfo;
    @XmlElement(name = "effector_info")
    protected MotionEffectorInfoType effectorInfo;

    /**
     * Gets the value of the axisInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the axisInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAxisInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MotionAxisInfoType }
     * 
     * 
     */
    public List<MotionAxisInfoType> getAxisInfo() {
        if (axisInfo == null) {
            axisInfo = new ArrayList<MotionAxisInfoType>();
        }
        return this.axisInfo;
    }

    /**
     * Gets the value of the effectorInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MotionEffectorInfoType }
     *     
     */
    public MotionEffectorInfoType getEffectorInfo() {
        return effectorInfo;
    }

    /**
     * Sets the value of the effectorInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MotionEffectorInfoType }
     *     
     */
    public void setEffectorInfo(MotionEffectorInfoType value) {
        this.effectorInfo = value;
    }

}
