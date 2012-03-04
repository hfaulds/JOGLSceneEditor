package octree.nodes;

import geometry.Box;
import geometry.Vertex;

import javax.media.opengl.GL2;

import octree.nodes.collision_handlers.PlaceableCollisionHandler;
import collisions.CollisionHandler;

public abstract class Placeable implements LeafTarget {

  public final Vertex position;
  protected final Vertex direction;
  public final Box boundingbox;
  
  protected CollisionHandler collisionHandler = new PlaceableCollisionHandler(this);

  public Placeable(Box OctSpace) {
    this(new Vertex(0,0,0), new Vertex(0,0,1), OctSpace);
  }
  
  public Placeable(Vertex position, Vertex direction, Box OctSpace) {
    this.position = position;
    this.direction = direction;
    this.boundingbox = OctSpace;
  }

  @Override
  public CollisionHandler getCollisionHandler() {
    return collisionHandler;
  }
  
  @Override
  public void init(GL2 gl) {}

  public Vertex getCentre() {
    return position;
  }
}