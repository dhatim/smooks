/*
	Milyn - Copyright (C) 2006 - 2010

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License (version 2.1) as published by the Free Software
	Foundation.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

	See the GNU Lesser General Public License for more details:
	http://www.gnu.org/licenses/lgpl.txt
*/
package org.dhatim.edi.test.unedifact.d03b;

import java.io.File;
import java.io.IOException;
import javax.xml.transform.stream.StreamSource;
import org.custommonkey.xmlunit.XMLUnit;
import org.dhatim.edi.test.EdifactDirTestHarness;
import org.dhatim.io.StreamUtils;
import org.dhatim.payload.JavaResult;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class D03B_Test {

    private static EdifactDirTestHarness d03bHarness = new EdifactDirTestHarness(new File("src/test/resources/d03b.zip"), "PAXLST");

    @Test
    public void test_PAXLST_test_fragment_split() throws IOException, SAXException {
        JavaResult result = new JavaResult();

        d03bHarness.smooksFilterSource("/org/dhatim/edi/test/unedifact/d03b/smooks-unedifact-split.xml", new StreamSource(getClass().getResourceAsStream("PAXLST.edi")), result);

        String expectedXML = StreamUtils.readStreamAsString(getClass().getResourceAsStream("PAXLST_frag.xml"));
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.compareXML(expectedXML, (String) result.getBean("PAXLST_frag"));
    }
}
