//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.27 at 09:08:35 PM MESZ 
//


package org.collada._2008._03.colladaschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 			The instance_node element declares the instantiation of a COLLADA node resource.
 * 			
 * 
 * <p>Java class for instance_node_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="instance_node_type">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.collada.org/2008/03/COLLADASchema}instance_with_extra_type">
 *       &lt;attribute name="proxy" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "instance_node_type")
public class InstanceNodeType
    extends InstanceWithExtraType
{

    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String proxy;

    /**
     * Gets the value of the proxy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxy() {
        return proxy;
    }

    /**
     * Sets the value of the proxy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxy(String value) {
        this.proxy = value;
    }

}
