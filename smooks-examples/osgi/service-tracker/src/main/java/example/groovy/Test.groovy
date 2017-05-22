package example.activator;

import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.dom.DOMVisitAfter;

public class Test implements DOMVisitAfter {

    public void visitAfter(Element element, ExecutionContext executionContext) 
    {
        println ("In Test.groovy...");
    }

}