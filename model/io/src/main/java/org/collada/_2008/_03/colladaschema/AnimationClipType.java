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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 			The animation_clip element defines a section of the animation curves to be used together as 
 * 			an animation clip.
 * 			
 * 
 * <p>Java class for animation_clip_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="animation_clip_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="asset" type="{http://www.collada.org/2008/03/COLLADASchema}asset_type" minOccurs="0"/>
 *         &lt;element name="instance_animation" type="{http://www.collada.org/2008/03/COLLADASchema}instance_with_extra_type" maxOccurs="unbounded"/>
 *         &lt;element name="instance_formula" type="{http://www.collada.org/2008/03/COLLADASchema}instance_formula_type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="extra" type="{http://www.collada.org/2008/03/COLLADASchema}extra_type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="start" type="{http://www.collada.org/2008/03/COLLADASchema}float_type" default="0.0" />
 *       &lt;attribute name="end" type="{http://www.collada.org/2008/03/COLLADASchema}float_type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "animation_clip_type", propOrder = {
    "asset",
    "instanceAnimation",
    "instanceFormula",
    "extra"
})
public class AnimationClipType {

    protected AssetType asset;
    @XmlElement(name = "instance_animation", required = true)
    protected List<InstanceWithExtraType> instanceAnimation;
    @XmlElement(name = "instance_formula")
    protected List<InstanceFormulaType> instanceFormula;
    protected List<ExtraType> extra;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String name;
    @XmlAttribute
    protected Double start;
    @XmlAttribute
    protected Double end;

    /**
     * Gets the value of the asset property.
     * 
     * @return
     *     possible object is
     *     {@link AssetType }
     *     
     */
    public AssetType getAsset() {
        return asset;
    }

    /**
     * Sets the value of the asset property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssetType }
     *     
     */
    public void setAsset(AssetType value) {
        this.asset = value;
    }

    /**
     * Gets the value of the instanceAnimation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instanceAnimation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstanceAnimation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstanceWithExtraType }
     * 
     * 
     */
    public List<InstanceWithExtraType> getInstanceAnimation() {
        if (instanceAnimation == null) {
            instanceAnimation = new ArrayList<InstanceWithExtraType>();
        }
        return this.instanceAnimation;
    }

    /**
     * Gets the value of the instanceFormula property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instanceFormula property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstanceFormula().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstanceFormulaType }
     * 
     * 
     */
    public List<InstanceFormulaType> getInstanceFormula() {
        if (instanceFormula == null) {
            instanceFormula = new ArrayList<InstanceFormulaType>();
        }
        return this.instanceFormula;
    }

    /**
     * Gets the value of the extra property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extra property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtra().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtraType }
     * 
     * 
     */
    public List<ExtraType> getExtra() {
        if (extra == null) {
            extra = new ArrayList<ExtraType>();
        }
        return this.extra;
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

    /**
     * Gets the value of the start property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getStart() {
        if (start == null) {
            return  0.0D;
        } else {
            return start;
        }
    }

    /**
     * Sets the value of the start property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setStart(Double value) {
        this.start = value;
    }

    /**
     * Gets the value of the end property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEnd(Double value) {
        this.end = value;
    }

}
