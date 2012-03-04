package octree.factories;

import geometry.Vertex;

import java.util.Vector;

import octree.nodes.Placeable;

public class PlaceableOctreeFactory extends OctreeFactory<Placeable> {
  
  @Override
  protected Vertex calcCenterPoint(Vector<Placeable> elements) {
    Vertex center = new Vertex();

    for (Placeable element : elements) {
      center = center.add(element.getCentre());
    }

    return center.divide(elements.size());
  }
  
  @Override
  protected double calcSize(Vector<Placeable> elements, Vertex center) {
    double maxDistance = 0;

    for (Placeable element : elements) {
      for (Vertex vert : element.boundingbox.vertices) {
        maxDistance = Math.max(Math.abs(center.x - vert.x), maxDistance);
        maxDistance = Math.max(Math.abs(center.y - vert.y), maxDistance);
        maxDistance = Math.max(Math.abs(center.z - vert.z), maxDistance);
      }
    }

    return maxDistance;
  }
}
