package com.cervantesvirtual.metadata.marc;

import com.cervantesvirtual.marc.authority.CreatorSet;
import com.cervantesvirtual.metadata.Collection;
import com.cervantesvirtual.metadata.MetadataFormat;
import java.io.File;
import junit.framework.TestCase;
import org.w3c.dom.Document;

public class TestCreatorSet extends TestCase {
	public void testCreatorSet() {
		java.net.URL urlin = this.getClass().getResource("/MARCCollection.xml");
		File in = new File(urlin.getFile());
		Document doc = com.cervantesvirtual.xml.DocumentParser
				.parse(in);
		Collection bibliographic = new Collection(MetadataFormat.MARC, doc);
		CreatorSet set = new CreatorSet(bibliographic);
		System.out.println(set);
		assertEquals(1, set.size());
	}
}
