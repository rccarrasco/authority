package com.cervantesvirtual.metadata;

import com.cervantesvirtual.util.Normalizer;

/**
 * A bibliographic index card
 * 
 * @author RCC
 * @version 2011.07.12
 * 
 */
public class CardField implements Field {
	String tag;
	String value;

	/**
	 * Constructor.
	 * 
	 * @param tag
	 *            the field tag.
	 * @param value
	 *            the field value.
	 */
	public CardField(String tag, String value) {
		this.tag = tag;
		this.value = value;
	}

	/**
	 * Constructor from XML elements
	 */
	public CardField(org.w3c.dom.Node node) {
		tag = node.getAttributes().getNamedItem("tag").getNodeValue();
		value = node.getTextContent().trim();
	}

	/**
	 * Get the field type.
	 */
	public FieldType getType() {
		return FieldType.CARD_FIELD;
	}

	public MetadataFormat getFormat() {
		return MetadataFormat.INDEX;
	}

	/**
	 * Get the field tag.
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Get the field content.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return true if fields are identical.
	 */
	public boolean equals(CardField other) {
		return this.tag == other.tag && this.value.equals(other.value);
	}

	/**
	 * @return true if fields are identical.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		} else {
			return this.equals((DCField) o);
		}
	}

	/**
	 * @return a hash code consistent with equality
	 */
	public int hashCode() {
		return tag.hashCode() ^ 31 * value.hashCode();
	}

	/**
	 * @return XML representation.
	 */
	public String toXML() {
		StringBuilder builder = new StringBuilder();
		builder.append("<card:field tag=\"" + tag + "\" ");
		builder.append(Normalizer.encode(value));
		builder.append("</card:field>");
		return builder.toString();
	}

	/**
	 * @return string representation.
	 */
	public String toString() {
		return tag.toString() + ":\n\t" + value;
	}

}
