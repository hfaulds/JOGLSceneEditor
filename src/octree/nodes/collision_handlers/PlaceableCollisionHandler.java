package octree.nodes.collision_handlers;

import geometry.Box;
import geometry.Frustrum;
import geometry.Line;

import java.util.Vector;

import octree.nodes.Placeable;
import collisions.Collision;
import collisions.CollisionHandler;

public class PlaceableCollisionHandler implements CollisionHandler {

  private final Placeable subject;

  public PlaceableCollisionHandler(Placeable subject) {
    this.subject = subject;
  }
  
  @Override
  public Vector<Collision> collide(Line line, double accuracy) {
    return subject.boundingbox.collide(line, accuracy);
  }

  @Override
  public Vector<Collision> collide(Frustrum box, double accuracy) {
    return subject.boundingbox.collide(box, accuracy);
  }
  
  @Override
  public Vector<Collision> collide(Placeable mesh, double accuracy) {
    return subject.boundingbox.collide(mesh, accuracy);
  }

  @Override
  public Vector<Collision> collide(Box space, double accuracy) {
    Vector<Collision> collisions = subject.boundingbox.collide(space, accuracy);
    
    if(collisions.size() > 0) {
      collisions.add(new Collision(subject, collisions.get(0).type));
    }
      
    return collisions;
  }
}
