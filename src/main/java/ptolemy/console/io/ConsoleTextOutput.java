package ptolemy.console.io;

import ptolemy.actor.io.TextOutput;
import ptolemy.kernel.util.NamedObj;

public class ConsoleTextOutput implements TextOutput {

    private String name;
    private Boolean _el = true;

    public ConsoleTextOutput() {}

    public void initialise(NamedObj owner, String id) {
	name = owner.getDisplayName() + "-" + id;
    }

    public void write(String text) {
	String[] ta = text.split("\n");
	for (int i = 0; i < ta.length; i++) {
	    String toprint = "";
	    if (this._el) {
		toprint += this.name + ": ";
	    }
	    toprint += ta[i];
	    if (i+1 < ta.length-1 || text.endsWith("\n")) {
		toprint += "\n";
		this._el = true;
	    } else {
		this._el = false;
	    }
	    System.out.print(toprint);
	}
	if (ta.length == 0 && text.equals("\n")) {
	    this._el = true;
	    System.out.print("\n");
	}
    }
}