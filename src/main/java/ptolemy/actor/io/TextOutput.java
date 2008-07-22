package ptolemy.actor.io;
import ptolemy.kernel.util.NamedObj;
public interface TextOutput {
    public void initialise(NamedObj owner, String id);
    public void write(String text);
}