//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.27 at 09:19:50 PM MESZ 
//


package org.collada._2005._11.colladaschema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}asset" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}source" maxOccurs="unbounded"/>
 *             &lt;choice>
 *               &lt;sequence>
 *                 &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}sampler" maxOccurs="unbounded"/>
 *                 &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}channel" maxOccurs="unbounded"/>
 *                 &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}animation" maxOccurs="unbounded" minOccurs="0"/>
 *               &lt;/sequence>
 *               &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}animation" maxOccurs="unbounded"/>
 *             &lt;/choice>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}sampler" maxOccurs="unbounded"/>
 *             &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}channel" maxOccurs="unbounded"/>
 *             &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}animation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}animation" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}extra" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "animation")
public class Animation {

    @XmlElementRefs({
        @XmlElementRef(name = "source", namespace = "http://www.collada.org/2005/11/COLLADASchema", type = Source.class),
        @XmlElementRef(name = "animation", namespace = "http://www.collada.org/2005/11/COLLADASchema", type = Animation.class),
        @XmlElementRef(name = "asset", namespace = "http://www.collada.org/2005/11/COLLADASchema", type = Asset.class),
        @XmlElementRef(name = "sampler", namespace = "http://www.collada.org/2005/11/COLLADASchema", type = Sampler.class),
        @XmlElementRef(name = "extra", namespace = "http://www.collada.org/2005/11/COLLADASchema", type = Extra.class),
        @XmlElementRef(name = "channel", namespace = "http://www.collada.org/2005/11/COLLADASchema", type = Channel.class)
    })
    protected List<Object> content;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Animation" is used by two different parts of a schema. See: 
     * line 2451 of file:/Users/junglas/workspaces/w6/moredread/model/io/src/main/xsd/collada_schema_1_4.xsd
     * line 2443 of file:/Users/junglas/workspaces/w6/moredread/model/io/src/main/xsd/collada_schema_1_4.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Asset }
     * {@link Source }
     * {@link Sampler }
     * {@link Channel }
     * {@link Extra }
     * {@link Animation }
     * 
     * 
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
