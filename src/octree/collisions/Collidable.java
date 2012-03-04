package octree.collisions;

import geometry.Box;
import geometry.Line;

import java.util.Vector;

import octree.nodes.Placeable;

public interface Collidable {
  public Vector<CollisionDetails> collide(Line line, double accuracy);
  public Vector<CollisionDetails> collide(CollisionFrustrum box, double accuracy);
  public Vector<CollisionDetails> collide(Box space, double accuracy);
  public Vector<CollisionDetails> collide(Placeable placeable, double accuracy);
}
