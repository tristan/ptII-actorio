package ptolemy.console.io;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import ptolemy.actor.io.StringInput;
import ptolemy.kernel.util.NamedObj;

public class ConsoleStringInput implements StringInput {

    private String name;
    private Thread readingThread;

    public ConsoleStringInput() {}

    public void initialise(NamedObj owner, String id) {
	name = owner.getDisplayName() + "-" + id;
    }

    public String getInput(String prompt) {
	readingThread = Thread.currentThread();
	System.out.print(prompt + ": ");
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	try {
	    // in the case that the workflow is stopped, we need to stop trying
	    // to read from system.in. since we can't break out of readLine without
	    // closing System.in (which we shouldn't do) we have to only call
	    // readLine when there is something to be read.
	    while (!br.ready()) {
		readingThread.sleep(500);
	    }
	    String r = br.readLine();
	    return r;
	} catch (IOException e) {
	} catch (InterruptedException e) {}
	return "";
    }

    public void stop() {
	readingThread.interrupt();
    }
}