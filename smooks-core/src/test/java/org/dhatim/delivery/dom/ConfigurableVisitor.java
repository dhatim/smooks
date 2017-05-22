package org.dhatim.delivery.dom;

import org.dhatim.delivery.dom.DOMElementVisitor;
import org.dhatim.container.ExecutionContext;
import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.cdr.SmooksConfigurationException;
import org.w3c.dom.Element;

/**
 * @author
 */
public class ConfigurableVisitor implements DOMElementVisitor {
    public void visitBefore(Element element, ExecutionContext executionContext) {
        element.setAttribute("visitedby-" + getClass().getSimpleName(), "true");
    }

    public void visitAfter(Element element, ExecutionContext executionContext) {
    }

    public void setConfiguration(SmooksResourceConfiguration resourceConfig) throws SmooksConfigurationException {
    }
}
