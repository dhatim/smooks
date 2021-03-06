package org.dhatim.delivery;

import org.dhatim.delivery.dom.DOMElementVisitor;
import org.dhatim.delivery.dom.Phase;
import org.dhatim.delivery.dom.VisitPhase;
import org.dhatim.container.ExecutionContext;
import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.cdr.SmooksConfigurationException;
import org.w3c.dom.Element;

/**
 * @author
 */
@Phase(VisitPhase.ASSEMBLY)
public class Assembly1 implements DOMElementVisitor {
    public void visitBefore(Element element, ExecutionContext executionContext) {
    }

    public void visitAfter(Element element, ExecutionContext executionContext) {
    }

    public void setConfiguration(SmooksResourceConfiguration resourceConfig) throws SmooksConfigurationException {
    }
}
