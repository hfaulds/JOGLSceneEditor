package geometry;


public class Line {

  public final Vertex start, end;

  public Line(Vertex a, Vertex b) {
    this.start = a;
    this.end = b;
  }

  public Vertex direction() {
    return start.subtract(end);
  }
  
  public double distanceTo(Vertex x) {
    double top = start.subtract(end).crossProduct(start.subtract(x)).magnitude();
    double bottom = x.subtract(start).magnitude();

    return top / bottom;
  }

  public boolean collide(Box space, double accuracy) {

    return true;
  }
}
