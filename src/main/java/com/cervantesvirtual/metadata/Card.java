/**
 * 
 */
package com.cervantesvirtual.metadata;

/**
 * A bibliographic index card
 * 
 * @author RCC
 * @version 2011.07.12
 */
public class Card extends Record {
	/**
	 * @param record
	 *            a MARC record
	 */
	public Card(Record record) {
		super(MetadataFormat.INDEX, record.id);
		// Fields whose tag starts with 0 will be appended to the end of the
		// record
		java.util.List<Field> delayed = new java.util.ArrayList<Field>(); //
		for (Field field : record.getFields()) {
			if (field.getType() == FieldType.MARC_DATAFIELD) {
				String mtag = field.getTag();
				MARCDataField mfield = (MARCDataField) field;
				CardField cfield = null;
				if (mtag.equals("100") || mtag.equals("110")
						|| mtag.equals("111")) {
					cfield = new CardField("Autor", Transformer.getName(mfield));
				} else if (mtag.equals("700") || mtag.equals("710")
						|| mtag.equals("711")) {
					String tag = Transformer.hasSubfield(mfield, "$e:coaut") ? "Coautor"
							: "Participante";
					cfield = new CardField(tag, Transformer.getName(mfield));
				} else if (mtag.equals("511")) {
					cfield = new CardField("Entrada secundaria",
							Transformer.getTextContent(mfield));
				} else if (mtag.equals("130")) {
					; // ignore creator as Uniform title ?
				} else if (mtag.equals("240") || mtag.equals("243")) {
					cfield = new CardField("Título uniforme",
							Transformer.getTextContent(mfield));
				} else if (mtag.equals("245")) {
					cfield = new CardField("Título",
							Transformer.getTextContent(mfield));
				} else if (mtag.equals("246")) {
					cfield = new CardField("Variante del título",
							Transformer.getTextContent(mfield));
				} else if (mtag.equals("250")) {
					cfield = new CardField("Edición",
							Transformer.getTextContent(mfield));
				} else if (mtag.equals("260")) {
					cfield = new CardField("Publicación",
							Transformer.getTextContent(mfield, "$a $b $c"));
				} else if (mtag.equals("300")) {
					cfield = new CardField("Descripción física",
							Transformer.getTextContent(mfield));
				} else if (mtag.equals("310")) {
					cfield = new CardField("Frecuencia",
							Transformer.getTextContent(mfield));
				} else if (mtag.equals("362")) {
					cfield = new CardField("Fechas de publicación",
							Transformer.getTextContent(mfield));
				} else if (mtag.equals("440") || mtag.equals("490")) {
					cfield = new CardField("Portales",
							Transformer.getTextContent(mfield, "$a"));
				} else if (mtag.startsWith("5")) {
					if (mtag.equals("534")) { // source edition statement
						cfield = new CardField(
								"Nota sobre la edición original",
								Transformer.getTextContent(mfield));
					} else if (mtag.equals("540")) {
						; // Ignore (abierto/cerrado)
					} else {
						cfield = new CardField("Nota",
								Transformer.getTextContent(mfield));
					}
				} else if (mtag.equals("600") || mtag.equals("610")
						|| mtag.equals("611")) {
					cfield = new CardField("Materia - Autor",
							Transformer.getTextContent(mfield, "$a $x $y $t",
									" - "));
				} else if (mtag.equals("630")) {
					cfield = new CardField("Materia - Título",
							Transformer.getTextContent(mfield, "$a $x $y $t",
									" - "));
				} else if (mtag.equals("650")) {
					cfield = new CardField("Materia",
							Transformer.getTextContent(mfield, "$a $x $y $t",
									" - "));
				} else if (mtag.equals("651")) {
					cfield = new CardField("Materia - Nombre geográfico",
							Transformer.getTextContent(mfield, "$a $x $y $t",
									" - "));
				} else if (mtag.equals("655")) {
					cfield = new CardField("Formato",
							Transformer.getTextContent(mfield));
				} else if (mtag.equals("740")) {
					cfield = new CardField("Otro título",
							Transformer.getTextContent(mfield, "$t"));
				} else if (mtag.equals("773")) {
					cfield = new CardField("Fuente",
							Transformer.getTextContent(mfield, "$t"));
				} else if (mtag.equals("017")) {
					delayed.add(new CardField("Derechos", Transformer
							.getTextContent(mfield)));
				} else if (mtag.equals("020")) {
					delayed.add(new CardField("ISBN", Transformer
							.getTextContent(mfield)));
				} else if (mtag.equals("022")) {
					delayed.add(new CardField("ISSN", Transformer
							.getTextContent(mfield)));
				} else if (mtag.equals("041")) {
					String text = Transformer.getTextContent(mfield).trim();
					MARClang[] langs = MARClang.valueOf(text
							.split("\\p{Space}+"));
					String name = MARClang.toString(langs);
					delayed.add(new CardField("Idioma", name));
				} else if (mtag.equals("080")) {
					delayed.add(new CardField("CDU", Transformer
							.getTextContent(mfield)));
				} else if (mtag.startsWith("0") || mtag.startsWith("8")
						|| mtag.startsWith("9")) {
					; // ignore
				} else {
					System.err.println("Tag " + mtag + " not incuded in card");
				}
				if (cfield != null) {
					addField(cfield);
					cfield = null;
				}
			}
		}
		/** language, udc, etc are written at the end **/
		for (Field cfield : delayed) {
			addField(cfield);
		}
	}

	/**
	 * A simple main for debugging purposes
	 */
	public static void main(String[] args) throws Exception {
		for (String arg : args) {
			java.io.File file = new java.io.File(arg);
			org.w3c.dom.Document doc = javax.xml.parsers.DocumentBuilderFactory
					.newInstance().newDocumentBuilder().parse(file);
			Collection cin = new Collection(MetadataFormat.MARC, doc);
			// cin.writeXML(System.out);
			for (Record source : cin.getRecords()) {
				Card card = new Card(source);
				System.out.println(card);
			}
		}
	}

}
