package ptolemy.actor.io;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ptolemy.kernel.util.NamedObj;

import java.util.HashMap;

public class IOFactory {

    private IOFactory() {
	String swingconfig = System.getProperty("springconfig");
	if (swingconfig == null) {
	    swingconfig = "ptolemy/console/io/console-spring-config.xml";
	}
	appcontext = new ClassPathXmlApplicationContext(swingconfig);
    }

    private ApplicationContext appcontext;

    private static class IOFactorySingletonHolder {
	static IOFactory instance = new IOFactory();
    }

    public static IOFactory getInstance() {
	return IOFactorySingletonHolder.instance;
    }

    public TextOutput getTextOutput(NamedObj owner, String id) {
	TextOutput out = (TextOutput)this.getOutput(owner, id);
	if (out == null) {
	    out = (TextOutput)appcontext.getBean("textoutput");
	    out.initialise(owner, id);
	    this.storeOutput(owner, id, out);
	}
	return out;
    }

    public GraphOutput getGraphOutput(NamedObj owner, String id) {
	return this.getGraphOutput(owner, id, GraphOutput.GraphType.DEFAULT);
    }
    public GraphOutput getGraphOutput(NamedObj owner, String id, 
				      GraphOutput.GraphType type) {
	GraphOutput out = (GraphOutput)this.getOutput(owner, id);
	if (out == null) {
	    out = (GraphOutput)appcontext.getBean("graphoutput");
	    out.initialise(owner, id);
	    out.setType(type);
	    this.storeOutput(owner, id, out);
	}
	return out;
    }

    private Object getOutput(NamedObj owner, String id) {
	HashMap<String, Object> outputs = owners.get(owner);
	if (outputs == null) {
	    return null;
	} else {
	    Object out = outputs.get(id);
	    return out;
	}
    }
	   
    private void storeOutput(NamedObj owner, String id, Object output) {
	HashMap<String, Object> outputs = owners.get(owner);
	if (outputs == null) {
	    outputs = new HashMap<String, Object>();
	}
	outputs.put(id, output);
	owners.put(owner, outputs);
    }

    private HashMap<NamedObj, HashMap<String, Object>> owners = new HashMap<NamedObj, HashMap<String, Object>>();

}