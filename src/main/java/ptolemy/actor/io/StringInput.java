package ptolemy.actor.io;
import ptolemy.kernel.util.NamedObj;
public interface StringInput {
    public void initialise(NamedObj owner, String id);
    public String getInput(String prompt);
}