package org.dhatim.general;

import org.dhatim.delivery.sax.SAXElementVisitor;
import org.dhatim.delivery.sax.SAXElement;
import org.dhatim.delivery.sax.SAXText;
import org.dhatim.delivery.sax.SAXVisitAfter;
import org.dhatim.container.ExecutionContext;
import org.dhatim.SmooksException;

import java.io.IOException;

/**
 * @author
 */
public class SAXVisitor implements SAXVisitAfter {

    public static boolean match = false;

    public void visitAfter(SAXElement saxElement, ExecutionContext executionContext) throws SmooksException, IOException {
        match = true;
    }
}
