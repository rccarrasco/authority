/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cervantesvirtual.util;

import com.cervantesvirtual.io.SafeFile;
import junit.framework.TestCase;

/**
 *
 * @author rafa
 */
public class TestSafeFile extends TestCase {

    public void testSafeFile() throws Exception {

        SafeFile file = new SafeFile("safe.xml");
        System.out.println(file.getCanonicalPath());
        java.io.PrintWriter writer = new java.io.PrintWriter(file);
        writer.write("Hello");
        writer.close();
    }
}
