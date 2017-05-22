package org.dhatim.delivery.nested;

import org.dhatim.SmooksException;
import org.dhatim.cdr.annotation.ConfigParam;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.sax.SAXElement;
import org.dhatim.delivery.sax.SAXVisitBefore;

import java.io.IOException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class OrderItemVisitor implements SAXVisitBefore {

    @ConfigParam
    private String beanId;

    public void visitBefore(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
        Object bean = executionContext.getBeanContext().getBean(beanId);

        if(bean != null) {
            executionContext.getBeanContext().addBean(beanId, bean + "-" + beanId);
        } else {
            executionContext.getBeanContext().addBean(beanId, beanId);
        }
    }
}
