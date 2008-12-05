package ptolemy.actor.lib.input;

import ptolemy.actor.TypedIOPort;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.lib.Source;
import ptolemy.actor.parameters.PortParameter;
import ptolemy.data.StringToken;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

import ptolemy.actor.io.IOFactory;
//import ptolemy.actor.io.StringInput;

public class StringInput extends Source {
    public StringInput(CompositeEntity container, String name)
	throws IllegalActionException, NameDuplicationException {
	super(container, name);
	prompt = new PortParameter(this, "prompt");
	prompt.setTypeEquals(BaseType.STRING);
	output.setTypeEquals(BaseType.STRING);
    }

    public PortParameter prompt;

    public void fire() throws IllegalActionException {
	super.fire();

	for (int i = 0; i < trigger.getWidth(); i++) {
            if (trigger.hasToken(i)) {
                trigger.get(i);
            }
        }
	ptolemy.actor.io.StringInput sin = IOFactory.getInstance().getStringInput(this, getName());

	prompt.update();
	String prompt_value = ((StringToken)prompt.getToken()).stringValue();
	System.out.println("got expression " + prompt_value);
	String result = sin.getInput(prompt_value);

	output.send(0, new StringToken(result));
    }
}