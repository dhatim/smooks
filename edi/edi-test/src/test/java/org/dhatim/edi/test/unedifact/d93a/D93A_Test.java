/*
 * Milyn - Copyright (C) 2006 - 2010
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (version 2.1) as published by the Free Software
 * Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * http://www.gnu.org/licenses/lgpl.txt
 */
package org.dhatim.edi.test.unedifact.d93a;

import java.io.File;
import java.io.IOException;
import org.dhatim.edi.test.EdifactDirTestHarness;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class D93A_Test {

    private static EdifactDirTestHarness d03bHarness = new EdifactDirTestHarness(new File("src/test/resources/d93a.zip"), "DESADV", "INVOIC", "ORDERS");

    @Test
    public void test_DESADV_java() throws IOException {
        d03bHarness.assertJavaReadWriteOK(getClass().getResourceAsStream("DESADV.edi"));
    }

    @Test
    public void test_DESADV_xml() throws IOException, SAXException {
        d03bHarness.assertXMLOK(getClass().getResourceAsStream("DESADV.edi"), getClass().getResourceAsStream("DESADV.xml"));
    }

    @Test
    public void test_ORDERS_java() throws IOException {
        d03bHarness.assertJavaReadWriteOK(getClass().getResourceAsStream("ORDERS.edi"));
    }

    @Test
    public void test_ORDERS_xml() throws IOException, SAXException {
        d03bHarness.assertXMLOK(getClass().getResourceAsStream("ORDERS.edi"), getClass().getResourceAsStream("ORDERS.xml"));
    }

}
