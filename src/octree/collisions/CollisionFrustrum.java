package octree.collisions;

import geometry.Box;
import geometry.Line;
import geometry.Plane;
import geometry.Vertex;

public class CollisionFrustrum {
  
  public final Plane[] planes = new Plane[4];
  
  public CollisionFrustrum(Line line0, Line line1, Line line2, Line line3)
  {
    Vertex direction0 = line0.direction();
    Vertex direction1 = line1.direction();
    Vertex direction2 = line2.direction();
    Vertex direction3 = line3.direction();
    
    Vertex normal0 = direction0.crossProduct(direction1);
    Vertex normal1 = direction1.crossProduct(direction2);
    Vertex normal2 = direction2.crossProduct(direction3);
    Vertex normal3 = direction3.crossProduct(direction0);
    
    planes[0] = new Plane(line0.start, normal0);
    planes[1] = new Plane(line1.start, normal1);
    planes[2] = new Plane(line2.start, normal2);
    planes[3] = new Plane(line3.start, normal3);
  }
  
  public boolean contains(Vertex vertex, double accuracy)
  {
    for (Plane plane : planes) {
      if(!vertexInfrontOfplane(plane, vertex, accuracy))
        return false;
    }
    return true;
  }

  public boolean vertexInfrontOfplane(Plane plane, Vertex vertex, double accuracy) {
    return plane.distanceFrom(vertex) < accuracy;
  }

  public boolean collide(Box space, double accuracy)
  {
    for (Plane plane : planes)
      if(!anyInfrontOfPlane(space, accuracy, plane))
        return false;
    
    return true;
  }

  private boolean anyInfrontOfPlane(Box space, double accuracy, Plane plane) {
    
    for(Vertex point : space.vertices)
       if(vertexInfrontOfplane(plane, point, accuracy))
         return true;
    
    return false;
  }
}
