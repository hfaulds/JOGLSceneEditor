package collisions;

import geometry.Box;
import geometry.Frustrum;
import geometry.Line;

import java.util.Vector;

import octree.nodes.Placeable;

public interface CollisionHandler {
  public Vector<Collision> collide(Line line, double accuracy);
  public Vector<Collision> collide(Box space, double accuracy);
  public Vector<Collision> collide(Frustrum frustrum, double accuracy);
  public Vector<Collision> collide(Placeable placeable, double accuracy);
}
