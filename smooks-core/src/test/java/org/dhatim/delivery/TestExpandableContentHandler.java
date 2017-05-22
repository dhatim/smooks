package org.dhatim.delivery;

import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.cdr.SmooksConfigurationException;
import org.dhatim.delivery.dom.serialize.DefaultSerializationUnit;
import org.dhatim.delivery.dom.DOMElementVisitor;
import org.dhatim.container.ExecutionContext;
import org.w3c.dom.Element;

import java.util.List;
import java.util.ArrayList;

/**
 * @author
 */
public class TestExpandableContentHandler implements DOMElementVisitor, ConfigurationExpander {

    public void setConfiguration(SmooksResourceConfiguration resourceConfig) throws SmooksConfigurationException {
    }

    public List<SmooksResourceConfiguration> expandConfigurations() {

        List<SmooksResourceConfiguration> expansionConfigs = new ArrayList<SmooksResourceConfiguration>();

        expansionConfigs.add(new SmooksResourceConfiguration("a", Assembly1.class.getName()));
        expansionConfigs.add(new SmooksResourceConfiguration("b", Processing1.class.getName()));        
        expansionConfigs.add(new SmooksResourceConfiguration("c", DefaultSerializationUnit.class.getName()));

        return expansionConfigs;
    }

    public void visitBefore(Element element, ExecutionContext executionContext) {
    }

    public void visitAfter(Element element, ExecutionContext executionContext) {
    }
}
