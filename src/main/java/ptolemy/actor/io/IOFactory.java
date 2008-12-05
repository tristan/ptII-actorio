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
	TextOutput out = (TextOutput)this.getIODevice(owner, id);
	if (out == null) {
	    out = (TextOutput)appcontext.getBean("textoutput");
	    out.initialise(owner, id);
	    this.storeIODevice(owner, id, out);
	}
	return out;
    }

    public GraphOutput getGraphOutput(NamedObj owner, String id) {
	return this.getGraphOutput(owner, id, GraphOutput.GraphType.DEFAULT);
    }
    public GraphOutput getGraphOutput(NamedObj owner, String id, 
				      GraphOutput.GraphType type) {
	GraphOutput out = (GraphOutput)this.getIODevice(owner, id);
	if (out == null) {
	    out = (GraphOutput)appcontext.getBean("graphoutput");
	    out.initialise(owner, id);
	    out.setType(type);
	    this.storeIODevice(owner, id, out);
	}
	return out;
    }

    public StringInput getStringInput(NamedObj owner, String id) {
	StringInput sin = (StringInput)this.getIODevice(owner, id);
	if (sin == null) {
	    sin = (StringInput)appcontext.getBean("stringinput");
	    sin.initialise(owner, id);
	    this.storeIODevice(owner, id, sin);
	}
	return sin;
    }

    private Object getIODevice(NamedObj owner, String id) {
	HashMap<String, Object> iodevices = owners.get(owner);
	if (iodevices == null) {
	    return null;
	} else {
	    Object iodevice = iodevices.get(id);
	    return iodevice;
	}
    }
	   
    private void storeIODevice(NamedObj owner, String id, Object iodevice) {
	HashMap<String, Object> iodevices = owners.get(owner);
	if (iodevices == null) {
	    iodevices = new HashMap<String, Object>();
	}
	iodevices.put(id, iodevice);
	owners.put(owner, iodevices);
    }

    private HashMap<NamedObj, HashMap<String, Object>> owners = new HashMap<NamedObj, HashMap<String, Object>>();

}