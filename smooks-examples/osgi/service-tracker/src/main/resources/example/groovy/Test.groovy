package example.groovy;

import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.sax.SAXVisitAfter;
import org.dhatim.delivery.sax.SAXElement;

public class Test implements SAXVisitAfter {

    public void visitAfter(Element element, ExecutionContext executionContext) 
    {
        println ("In Test.groovy..." + k)
    }

}