package octree.nodes;

import geometry.Box;
import geometry.Line;
import geometry.Vertex;

import java.util.Vector;

import javax.media.opengl.GL2;

import octree.collisions.CollisionDetails;
import octree.collisions.CollisionFrustrum;

public abstract class Placeable implements LeafTarget {

  public final Vertex position;
  protected final Vertex direction;
  public final Box boundingbox;

  public Placeable(Box OctSpace) {
    this(new Vertex(0,0,0), new Vertex(0,0,1), OctSpace);
  }
  
  public Placeable(Vertex position, Vertex direction, Box OctSpace) {
    this.position = position;
    this.direction = direction;
    this.boundingbox = OctSpace;
  }

  @Override
  public Vector<CollisionDetails> collide(Line line, double accuracy) {
    return boundingbox.collide(line, accuracy);
  }

  @Override
  public Vector<CollisionDetails> collide(CollisionFrustrum box, double accuracy) {
    return boundingbox.collide(box, accuracy);
  }
  
  @Override
  public Vector<CollisionDetails> collide(Placeable mesh, double accuracy) {
    return boundingbox.collide(mesh, accuracy);
  }

  @Override
  public Vector<CollisionDetails> collide(Box space, double accuracy) {
    Vector<CollisionDetails> collisions = boundingbox.collide(space, accuracy);
    
    if(collisions.size() > 0) {
      collisions.add(new CollisionDetails(this, collisions.get(0).type));
    }
      
    return collisions;
  }
  
  @Override
  public void init(GL2 gl) {}

  public Vertex getCentre() {
    return position;
  }
}