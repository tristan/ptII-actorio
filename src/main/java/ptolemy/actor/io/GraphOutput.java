package ptolemy.actor.io;
import ptolemy.kernel.util.NamedObj;
public abstract class GraphOutput {

    public abstract void initialise(NamedObj owner, String id);
    public abstract void reset();
    public abstract void add(String set, Double x, Double y);
    public abstract void wrapup();

    public void setType(GraphType type) {
	if (type == GraphType.DEFAULT) {
	    this._type = GraphType.XY_GRAPH;
	} else {
	    this._type = type;
	}
    }

    public GraphType getType() {
	return _type;
    }

    public enum GraphType {
	XY_GRAPH, BAR_GRAPH, PIE_GRAPH, DEFAULT
	    }

    protected GraphType _type = GraphType.XY_GRAPH;
}