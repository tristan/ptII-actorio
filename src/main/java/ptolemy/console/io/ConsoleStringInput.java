package ptolemy.console.io;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import ptolemy.actor.io.StringInput;
import ptolemy.kernel.util.NamedObj;

public class ConsoleStringInput implements StringInput {

    private String name;

    public ConsoleStringInput() {}

    public void initialise(NamedObj owner, String id) {
	name = owner.getDisplayName() + "-" + id;
    }

    public String getInput(String prompt) {
	System.out.print(prompt + ": ");
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	try {
	    String r = br.readLine();
	    return r;
	} catch (IOException e) {
	    return "";
	}
    }
}