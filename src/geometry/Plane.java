package geometry;

public class Plane {

  public final Vertex point;
  public final Vertex normal;

  public Plane(Vertex point, Vertex normal) {
    this.point = point;
    this.normal = normal;
  }
  
  public double distanceFrom(Vertex vertex) {
    return this.normal.dotProduct(vertex.subtract(this.point));
  }
}
