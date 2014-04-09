package com.cervantesvirtual.metadata;

import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A MARC datafield
 * 
 * @author RCC.
 * @version 2011.03.30
 */
public class MARCDataField implements Field {

    protected String tag;
    protected char ind1;
    protected char ind2;
    protected List<MARCSubfield> subfields;

    /**
     * Default constructor
     */
    public MARCDataField(String tag) {
        this.tag = tag;
        subfields = new java.util.ArrayList<MARCSubfield>();
        ind1 = ind2 = ' ';
    }

    /**
     * Full constructor
     */
    public MARCDataField(String tag, char ind1, char ind2,
            List<MARCSubfield> subfields) {
        this.tag = tag;
        this.ind1 = ind1;
        this.ind2 = ind2;
        this.subfields = subfields;
    }

    /**
     * Copy constructor.
     * 
     * @param other another MARCDataField
     */
    public MARCDataField(MARCDataField other) {
        this.tag = other.tag;
        this.ind1 = other.ind1;
        this.ind2 = other.ind2;
        this.subfields = new java.util.ArrayList<MARCSubfield>(other.subfields);
    }

    /**
     * Get an attribute from XML element
     */
    private String getAttributeValue(Node node, String name) {
        String value = null;
        org.w3c.dom.NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            Node attnode = attributes.getNamedItem(name);
            if (attnode != null) {
                value = attnode.getTextContent();
            }
        }
        return value;
    }

    /**
     * Constructor from XML nodes
     */
    public MARCDataField(Node field) {
        NodeList children = field.getChildNodes();
        String attvalue;
        tag = getAttributeValue(field, "tag");
        attvalue = getAttributeValue(field, "ind1");
        ind1 = (attvalue == null) ? ' ' : attvalue.charAt(0);
        attvalue = getAttributeValue(field, "ind2");
        ind2 = (attvalue == null) ? ' ' : attvalue.charAt(0);
        subfields = new java.util.ArrayList<MARCSubfield>();
        for (int n = 0; n < children.getLength(); ++n) {
            Node child = children.item(n);
            // avoid text nodes with comments, whitespace, line breaks, etc.
            if (child.getNodeType() != Node.TEXT_NODE
                    && child.getNodeType() != Node.COMMENT_NODE) {
                MARCSubfield subfield = new MARCSubfield(child);
                subfields.add(subfield);
            }
        }
    }

    /**
     * Constructor from string
     * 
     * @param heading the field tag and optional indicators.
     * @param content the field value
     */
    public MARCDataField(String heading, String value) {
        tag = heading.substring(0, 3);
        ind1 = (heading.length() > 3) ? heading.charAt(3) : ' ';
        ind2 = (heading.length() > 4) ? heading.charAt(4) : ' ';
        subfields = new java.util.ArrayList<MARCSubfield>();
        if (value.length() > 0) {
            String[] subtexts = value.split("[$]", 0);
            for (String subtext : subtexts) {
                if (subtext.length() > 0) { // redundant?
                    MARCSubfield subfield = new MARCSubfield("$" + subtext);
                    subfields.add(subfield);
                }
            }
        }
    }

    /**
     * Add a subfield.
     */
    public void addSubfield(MARCSubfield subfield) {
        subfields.add(subfield);
    }

    /**
     * Get the field type.
     */
    @Override
    public FieldType getType() {
        return FieldType.MARC_DATAFIELD;
    }

    /**
     * Get the field format.
     */
    @Override
    public MetadataFormat getFormat() {
        return MetadataFormat.MARC;
    }

    /**
     * Get the field tag.
     */
    @Override
    public String getTag() {
        return tag;
    }

    /**
     * Get indicator 1
     */
    public char getInd1() {
        return ind1;
    }

    /**
     * Get indicator 2
     */
    public char getInd2() {
        return ind2;
    }

    /**
     * Get all the field subfields.
     */
    public List<MARCSubfield> getSubfields() {
        return subfields;
    }

    /**
     * Get the field content.
     */
    @Override
    public String getValue() {
        StringBuilder builder = new StringBuilder();
        for (MARCSubfield subfield : subfields) {
            builder.append(subfield);
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    /**
     * @return XML representation
     */
    @Override
    public String toXML() {
        StringBuilder builder = new StringBuilder();
        builder.append("<marc:datafield tag=\"").append(tag).append("\" ");
        builder.append("ind1=\"").append(ind1).append("\" ");
        builder.append("ind2=\"").append(ind2).append("\">");
        if (subfields.size() > 0) {
            builder.append(subfields.get(0).toXML());
            for (int n = 1; n < subfields.size(); ++n) {
                builder.append(subfields.get(n).toXML());
            }
        }
        builder.append("</marc:datafield>");
        return builder.toString();
    }

    /**
     * @return string representation
     */
    @Override
    public String toString() {
        return tag + ind1 + ind2 + " " + getValue();
    }

    /**
     * @return true if DataFields are identical to each other.
     */
    public boolean equals(MARCDataField other) {
        return this.tag.equals(other.tag) && this.ind1 == other.ind1
                && this.ind2 == other.ind2
                && this.subfields.equals(other.subfields);
    }

    /**
     * @return true if DataFields are identical to each other.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        } else {
            return this.equals((MARCDataField) o);
        }
    }

    /**
     * @return a hash code consistent with equality
     */
    @Override
    public int hashCode() {
        return tag.hashCode() ^ 31 * subfields.hashCode();
    }
}