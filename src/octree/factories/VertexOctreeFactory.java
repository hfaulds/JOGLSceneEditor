package octree.factories;

import geometry.Vertex;

import java.util.Vector;

public class VertexOctreeFactory extends OctreeFactory<Vertex> {

  @Override
  protected Vertex calcCenterPoint(Vector<Vertex> points) {
    Vertex centerPoint = new Vertex();
    
    for (Vertex point : points) {
      centerPoint = centerPoint.add(point);
    }
    
    return centerPoint.divide(points.size());
  }
  
  @Override
  protected double calcSize(Vector<Vertex> points, Vertex center) {
    double maxDistance = 0;

    for (Vertex point : points) {
      maxDistance = Math.max(point.x, maxDistance);
      maxDistance = Math.max(point.y, maxDistance);
      maxDistance = Math.max(point.z, maxDistance);
    }

    return maxDistance;
  }
}
