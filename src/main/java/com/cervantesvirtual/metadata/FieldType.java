package com.cervantesvirtual.metadata;

/**
 * The types of metadata fields.
 * 
 * @author RCC.
 * @verion 2011.03.10
 */
public enum FieldType {
	MARC_LEADER, MARC_CONTROLFIELD, MARC_DATAFIELD, DC_FIELD, CARD_FIELD;
	
	public static FieldType typeFromXMLTag(String tag) throws MetadataException {
		if (tag.equals("marc:leader")) {
			return MARC_LEADER;
		} else if (tag.equals("marc:controlfield")) {
			return MARC_CONTROLFIELD;
		} else if (tag.equals("marc:datafield")) {
			return MARC_DATAFIELD;
		} else if (tag.equals("dc:field")) {
			return DC_FIELD;
		} else if (tag.equals("card:field")) {
			return CARD_FIELD;
		} else {
			throw new MetadataException("Unsupported field type " + tag);
		}
	}

	public static FieldType typeFromMARCTag(String tag) {
		if (tag.matches("[0-9]{3}[0-9 ]{0,2}")) {
			if (tag.substring(0, 3).compareTo("010") < 0) {
				return MARC_CONTROLFIELD;
			} else {
				return MARC_DATAFIELD;
			}
		} else {
			return MARC_LEADER;
		}
	}
	
	public static boolean isIdentifier(Field field) {
		switch (field.getType()) {
		case DC_FIELD : 
			return ((DCField) field).getDCTag() == DCTerm.IDENTIFIER;	
		case MARC_CONTROLFIELD :
			return field.getTag().equals("001");
		default : 
			return false;
		}
	}
}