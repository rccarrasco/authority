package com.cervantesvirtual.metadata;

import java.io.File;

import org.w3c.dom.Document;

import junit.framework.TestCase;

public class TestTransformer extends TestCase {
	
	public void testTransformer() throws Exception {
		java.net.URL urlin = this.getClass().getResource("/MARCRecord.xml");
		 System.out.println(urlin);
	    File in = new File(urlin.getFile());
		//File out = new File("DCRecord.txt"); 
	   
		Document doc = javax.xml.parsers.DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(in);
		Collection cin = new Collection(MetadataFormat.MARC, doc);
		//cin.writeXML(System.out);
		for (Record source : cin.getRecords()) {
			Record target = Transformer.transform(source);
			System.out.println(target.toXML());
		}
	}
	
}
