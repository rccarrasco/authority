package com.cervantesvirtual.metadata;

/**
 * A metadata field.
 * 
 * @author RCC.
 * @verion 2011.03.10
 */
public interface Field {

    /**
     * Get the field format
     */
    public MetadataFormat getFormat();

    /**
     * Get the field type.
     */
    public FieldType getType();

    /**
     * Get the field tag.
     */
    public String getTag();

    /**
     * Get the field content.
     */
    public String getValue();

    /**
     * @return XML representation
     */
    public String toXML();

    /**
     * @return string representation.
     */
    @Override
    public String toString();

    /**
     * @return true if subfields are identical.
     */
    @Override
    public boolean equals(Object o);

    /**
     * @return a hash code consistent with equality.
     */
    @Override
    public int hashCode();
}