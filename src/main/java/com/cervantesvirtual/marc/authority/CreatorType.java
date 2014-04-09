package com.cervantesvirtual.marc.authority;

public enum CreatorType {
	PERSONAL, CORPORATE, MEETING;

	public static CreatorType type(String tag) {
		if (tag.matches("^[17][01][01]")) {
			if (tag.endsWith("00")) {
				return CreatorType.PERSONAL;
			} else if (tag.endsWith("10")) {
				return CreatorType.CORPORATE;
			} else if (tag.endsWith("11")) {
				return CreatorType.MEETING;
			}
		} else {
			System.err.println("CreatorType: wrong tag <" + tag + ">");
		}
		return null;
	}
}