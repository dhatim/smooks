package org.dhatim.smooks.scripting.groovy;

import groovy.xml.XmlUtil;
import groovy.xml.dom.DOMCategory;
import groovy.xml.DOMBuilder;

import org.dhatim.container.ExecutionContext;
import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.SmooksException;
import org.dhatim.javabean.context.BeanContext;

import org.dhatim.delivery.DomModelCreator;
import org.dhatim.delivery.DOMModel;
import org.dhatim.delivery.dom.DOMVisitBefore;
import org.dhatim.delivery.dom.DOMVisitAfter;
import org.dhatim.delivery.dom.serialize.Serializer;
import org.dhatim.xml.*;
import org.dhatim.io.NullWriter;

import org.dhatim.delivery.sax.SAXVisitBefore;
import org.dhatim.delivery.sax.SAXVisitAfter;
import org.dhatim.delivery.sax.SAXElement;

import java.io.IOException;
import org.w3c.dom.*;
import java.util.Map;

${imports}

<#if visitBefore>
class ${visitorName} implements DOMVisitBefore, SAXVisitBefore {

    private SmooksResourceConfiguration config;

	public void setConfiguration(SmooksResourceConfiguration config) {
		this.config = config;
	}

    public void visitBefore(Element element, ExecutionContext executionContext) {
        Document document = element.getOwnerDocument();
        Map nodeModels = DOMModel.getModel(executionContext).getModels();

        def getBean = { beanId ->
            executionContext.getBeanContext().getBean(beanId);
        }

        ${visitorScript}
    }

    public void visitBefore(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
        Map nodeModels = DOMModel.getModel(executionContext).getModels();

        def getBean = { beanId ->
            executionContext.getBeanContext().getBean(beanId);
        }

        ${visitorScript}
    }
}
<#else>
class ${visitorName} implements DOMVisitAfter, SAXVisitBefore, SAXVisitAfter {

    private SmooksResourceConfiguration config;
    private DomModelCreator modelCreator;
    private boolean format = false;
    private boolean isWritingFragment = false;

	public void setConfiguration(SmooksResourceConfiguration config) {
		this.config = config;

		if(config.getBoolParameter("createDOMFragment", true)) {
		    modelCreator = new DomModelCreator();
		}
		format = config.getBoolParameter("format", false);
		isWritingFragment = config.getBoolParameter("writeFragment", false);
	}

    public void visitAfter(Element element, ExecutionContext executionContext) {
        visitAfter(element, executionContext, null);
    }

    public void visitAfter(Element element, ExecutionContext executionContext, Writer writer) {
        Document document = element.getOwnerDocument();
        Map nodeModels = DOMModel.getModel(executionContext).getModels();

        def getBean = { beanId ->
            executionContext.getBeanContext().getBean(beanId);
        }
        def writeFragment = { outNode ->
            if(outNode.getNodeType() == Node.ELEMENT_NODE) {
                Serializer.recursiveDOMWrite((Element) outNode, writer);
            } else if(outNode.getNodeType() == Node.DOCUMENT_NODE) {
                Serializer.recursiveDOMWrite(outNode.getDocumentElement(), writer);
            } else {
                throw new SmooksException("Call to 'writeFragment' with a non Document/Element Node.  Node type: " + outNode.getClass().getName());
            }
        }

        ${visitorScript}
    }

    // visitBefore is required purely for setting up the model creator...
    public void visitBefore(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
        if(modelCreator != null) {
            if(isWritingFragment) {
                Writer currentWriter = element.getWriter(this);
                // If fragment writing is on, we want to block output to the
                // output stream...
                element.setWriter(new NullWriter(currentWriter), this);
            }

            modelCreator.visitBefore(element, executionContext);
        }
    }

    public void visitAfter(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
        if(modelCreator != null) {
            Document fragmentDoc = modelCreator.popCreator(executionContext);
            Element fragmentElement = fragmentDoc.getDocumentElement();

            if(isWritingFragment) {
                Writer writer = element.getWriter(this);
                if(writer instanceof NullWriter) {
                    // Reset the writer...
                    writer = ((NullWriter)writer).getParentWriter();
                    element.setWriter(writer, this);
                }

                visitAfter(fragmentElement, executionContext, writer);
            } else {
                visitAfter(fragmentElement, executionContext);
            }
        } else {
            Map nodeModels = DOMModel.getModel(executionContext).getModels();

            def getBean = { beanId ->
                executionContext.getBeanContext().getBean(beanId);
            }

            ${visitorScript}
        }
    }
}
</#if>
