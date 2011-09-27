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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 			The definition of the convex_mesh element is identical to the mesh element with the exception that 
 * 			instead of a complete description (source, vertices, polygons etc.), it may simply point to another 
 * 			geometry to derive its shape. The latter case means that the convex hull of that geometry should 
 * 			be computed and is indicated by the optional "convex_hull_of" attribute.
 * 			
 * 
 * <p>Java class for convex_mesh_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="convex_mesh_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="source" type="{http://www.collada.org/2008/03/COLLADASchema}source_type" maxOccurs="unbounded"/>
 *         &lt;element name="vertices" type="{http://www.collada.org/2008/03/COLLADASchema}vertices_type"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="lines" type="{http://www.collada.org/2008/03/COLLADASchema}lines_type"/>
 *           &lt;element name="linestrips" type="{http://www.collada.org/2008/03/COLLADASchema}linestrips_type"/>
 *           &lt;element name="polygons" type="{http://www.collada.org/2008/03/COLLADASchema}polygons_type"/>
 *           &lt;element name="polylist" type="{http://www.collada.org/2008/03/COLLADASchema}polylist_type"/>
 *           &lt;element name="triangles" type="{http://www.collada.org/2008/03/COLLADASchema}triangles_type"/>
 *           &lt;element name="trifans" type="{http://www.collada.org/2008/03/COLLADASchema}trifans_type"/>
 *           &lt;element name="tristrips" type="{http://www.collada.org/2008/03/COLLADASchema}tristrips_type"/>
 *         &lt;/choice>
 *         &lt;element name="extra" type="{http://www.collada.org/2008/03/COLLADASchema}extra_type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="convex_hull_of" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "convex_mesh_type", propOrder = {
    "source",
    "vertices",
    "linesOrLinestripsOrPolygons",
    "extra"
})
public class ConvexMeshType {

    protected List<SourceType> source;
    protected VerticesType vertices;
    @XmlElements({
        @XmlElement(name = "linestrips", type = LinestripsType.class),
        @XmlElement(name = "tristrips", type = TristripsType.class),
        @XmlElement(name = "triangles", type = TrianglesType.class),
        @XmlElement(name = "trifans", type = TrifansType.class),
        @XmlElement(name = "polygons", type = PolygonsType.class),
        @XmlElement(name = "lines", type = LinesType.class),
        @XmlElement(name = "polylist", type = PolylistType.class)
    })
    protected List<Object> linesOrLinestripsOrPolygons;
    protected List<ExtraType> extra;
    @XmlAttribute(name = "convex_hull_of")
    @XmlSchemaType(name = "anyURI")
    protected String convexHullOf;

    /**
     * Gets the value of the source property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the source property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceType }
     * 
     * 
     */
    public List<SourceType> getSource() {
        if (source == null) {
            source = new ArrayList<SourceType>();
        }
        return this.source;
    }

    /**
     * Gets the value of the vertices property.
     * 
     * @return
     *     possible object is
     *     {@link VerticesType }
     *     
     */
    public VerticesType getVertices() {
        return vertices;
    }

    /**
     * Sets the value of the vertices property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerticesType }
     *     
     */
    public void setVertices(VerticesType value) {
        this.vertices = value;
    }

    /**
     * Gets the value of the linesOrLinestripsOrPolygons property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the linesOrLinestripsOrPolygons property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLinesOrLinestripsOrPolygons().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LinestripsType }
     * {@link TristripsType }
     * {@link TrianglesType }
     * {@link TrifansType }
     * {@link PolygonsType }
     * {@link LinesType }
     * {@link PolylistType }
     * 
     * 
     */
    public List<Object> getLinesOrLinestripsOrPolygons() {
        if (linesOrLinestripsOrPolygons == null) {
            linesOrLinestripsOrPolygons = new ArrayList<Object>();
        }
        return this.linesOrLinestripsOrPolygons;
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
     * Gets the value of the convexHullOf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConvexHullOf() {
        return convexHullOf;
    }

    /**
     * Sets the value of the convexHullOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConvexHullOf(String value) {
        this.convexHullOf = value;
    }

}
