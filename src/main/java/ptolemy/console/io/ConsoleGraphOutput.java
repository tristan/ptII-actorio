package ptolemy.console.io;

import ptolemy.actor.io.GraphOutput;
import ptolemy.kernel.util.NamedObj;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ConsoleGraphOutput extends GraphOutput {

    private String name;

    private Integer X_SIZE = 70;
    private Integer Y_SIZE = 10;

    public void setWidth(Integer width) {
	this.X_SIZE = width;
    }
    public void setHeight(Integer height) {
	this.Y_SIZE = height;
    }

    public ConsoleGraphOutput() {}

    public void initialise(NamedObj owner, String id) {
	name = owner.getDisplayName() + "-" + id;
    }

    public void reset() {
    }
    public void add(String set, Double x, Double y) {
	Set s = sets.get(set);
	if (s == null) {
	    s = new Set();
	    sets.put(set, s);
	}
	s.add_new_point(x, y);
    }

    public void wrapup() {

	Double max_x_point;
	Double min_x_point;
	Double max_y_point;
	Double min_y_point;

	System.out.println();

	char[][] graph = new char[X_SIZE][Y_SIZE];
	for (int i = 0; i < X_SIZE; i++) {
	    for (int j = 0; j < Y_SIZE; j++) {
		graph[i][j] = ' ';
	    }
	}
	int set_no = 0;
	char[] set_chars = { 'o', 'x', '+', '-', '=' };
	for (Set s: this.sets.values()) {
	    Double ox = (this.X_SIZE-1) / (s.get_max_x_point() - s.get_min_x_point());
	    Double oy = (this.Y_SIZE-1) / (s.get_max_y_point() - s.get_min_y_point());

	    for (Point p: s.getPoints()) {
		Double px = p.getX();
		Double py = p.getY();
		Integer pos_x = (int)((px - s.get_min_x_point()) * ox);
		Integer pos_y = (int)((py - s.get_min_y_point()) * oy);
		if (graph[pos_x][pos_y] != ' ' && graph[pos_x][pos_y] != set_chars[set_no]) {
		    graph[pos_x][pos_y] = '#';
		} else {
		    graph[pos_x][pos_y] = set_chars[set_no];
		}
	    }
	    set_no++;
	}
	
	//System.out.println("max-y: " + this._max_y_point);
	for (int i = 0 ; i < this.Y_SIZE ; i++) {
	    System.out.print("- ");
	    for (int j = 0; j < this.X_SIZE; j++) {
		char p = graph[j][(Y_SIZE-1)-i];
		System.out.print(p);
	    }
	    System.out.println();
	}
	/*
	System.out.print("  |");
	for (int i = 0; i < this.X_SIZE; i++) {
	    System.out.print('|');
	}
	
	System.out.println("\nmin-y: " + 
			   this._min_y_point + 
			   "\tmin-x: " + 
			   this._min_x_point + 
			   "\tmax-x: " + 
			   this._max_x_point );
	*/
    }

    private Map<String, Set> sets = new HashMap<String, Set>();

    private class Set {
	public Set() {
	}

	
	/**
	 * Gets the value of _max_x_point
	 *
	 * @return the value of _max_x_point
	 */
	public final Double get_max_x_point() {
	    return this._max_x_point;
	}

	/**
	 * Gets the value of _min_x_point
	 *
	 * @return the value of _min_x_point
	 */
	public final Double get_min_x_point() {
	    return this._min_x_point;
	}

	/**
	 * Gets the value of _max_y_point
	 *
	 * @return the value of _max_y_point
	 */
	public final Double get_max_y_point() {
	    return this._max_y_point;
	}

	/**
	 * Gets the value of _min_y_point
	 *
	 * @return the value of _min_y_point
	 */
	public final Double get_min_y_point() {
	    return this._min_y_point;
	}

	public final void add_new_point(Double x, Double y) {
	    Point p = new Point(x, y);
	    this.points.add(p);
	    if (x > this._max_x_point) {
		this._max_x_point = x;
	    }
	    if (x < this._min_x_point) {
		this._min_x_point = x;
	    }
	    if (y > this._max_y_point) {
		this._max_y_point = y;
	    }
	    if (y < this._min_y_point) {
		this._min_y_point = y;
	    }
	}

	public final List<Point> getPoints() {
	    return points;
	}

	private List<Point> points = new ArrayList<Point>();
	private Double _max_x_point = Double.NEGATIVE_INFINITY;
	private Double _min_x_point = Double.POSITIVE_INFINITY;
	private Double _max_y_point = Double.NEGATIVE_INFINITY;
	private Double _min_y_point = Double.POSITIVE_INFINITY;

    }

    private class Point {
	public Point(Double x, Double y) {
	    this._x = x;
	    this._y = y;
	}

	private Double _x;
	private Double _y;

	public Double getX() { return this._x; }
	public Double getY() { return this._y; }

    }
}