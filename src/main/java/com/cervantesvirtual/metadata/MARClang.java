package com.cervantesvirtual.metadata;

/**
 * MARC codes for language and their Spanish names.
 * 
 * @author RCC
 * @version 20110713
 */
public enum MARClang {
	mul, spa, cat, catBl, val, glg, baq, // languages in Spain
	lat, gre, grc, heb, sem, arc, // ancient
	eng, fre, ger, dut, swe, por, ita, lad, // European 
	sai, cai, ara, chi, rus, syr;  // scarce

	/**
	 * Translation to Spanish
	 */
	public String toString() {
		switch (this) {
		case mul:
			return "varios";
		case spa:
		case catBl:
			return "castellano";
		case cat:
			return "catalán";
		case val:
			return "valenciano";
		case baq:
			return "euskera";
		case glg:
			return "gallego";
		case lat:
			return "latín";
		case gre:
		case grc:
			return "griego";
		case heb:
			return "hebreo";
		case sem:
			return "lengua semítica";
		case arc:
			return "arameo";
		case eng:
			return "inglés";
		case fre:
			return "fancés";
		case ger:
			return "alemán";
		case dut:
			return "holandés";
		case swe:
			return "sueco";
		case por:
			return "portugués";
		case ita:
			return "italiano";
		case lad:
			return "ladino";
		case sai:
			return "lenguas indias de América del Sur";
		case cai:
			return "lenguas indias de América Central";
		case ara:
			return "árabe";
		case chi:
			return "chino";
		case rus:
			return "ruso";
		case syr:
			return "sirio";
		default:
			return toString();
		}
	}

	/**
	 * @return valueOf an input array
	 */
	public static MARClang[] valueOf(String[] array) {
		MARClang[] langs = new MARClang[array.length];
		for (int n = 0; n < array.length; ++n) {
			langs[n] = valueOf(array[n]);
		}
		return langs;
	}

	/**
	 * Translate to String an array of MARClangs
	 * 
	 * @param langs
	 * @return
	 */
	public static String toString(MARClang[] langs) {
		StringBuffer buff = new StringBuffer();
		buff.append(langs[0].toString());
		for (int n = 1; n < langs.length; ++n) {
			buff.append(", " + langs[n]);
		}
		return buff.toString();
	}

}
