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
package org.dhatim.delivery.sax;

import org.dhatim.container.ExecutionContext;
import org.dhatim.SmooksException;

import java.io.IOException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class SAXVisitAfterAndChildrenVisitor implements SAXVisitAfter, SAXVisitChildren {

    public static boolean visited = false;
    public static boolean onChildText = false;
    public static boolean onChildElement = false;

    public void visitAfter(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
        visited = true;
    }

    public void onChildText(SAXElement element, SAXText childText, ExecutionContext executionContext) throws SmooksException, IOException {
        onChildText = true;
    }

    public void onChildElement(SAXElement element, SAXElement childElement, ExecutionContext executionContext) throws SmooksException, IOException {
        onChildElement = true;
    }

    public static void reset() {
        visited = false;
        onChildElement = false;
        onChildText = false;
    }
}