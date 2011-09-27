//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.27 at 09:08:35 PM MESZ 
//


package org.collada._2008._03.colladaschema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sampler_behavior_enum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sampler_behavior_enum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CONSTANT"/>
 *     &lt;enumeration value="CYCLE"/>
 *     &lt;enumeration value="CYCLE_RELATIVE"/>
 *     &lt;enumeration value="GRADIENT"/>
 *     &lt;enumeration value="OSCILLATE"/>
 *     &lt;enumeration value="UNDEFINED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "sampler_behavior_enum")
@XmlEnum
public enum SamplerBehaviorEnum {

    CONSTANT,
    CYCLE,
    CYCLE_RELATIVE,
    GRADIENT,
    OSCILLATE,
    UNDEFINED;

    public String value() {
        return name();
    }

    public static SamplerBehaviorEnum fromValue(String v) {
        return valueOf(v);
    }

}
