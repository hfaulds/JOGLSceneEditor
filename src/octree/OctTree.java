package octree;

import editors.static_settings.DebugSettings;
import geometry.Box;
import geometry.Frustrum;
import geometry.Line;

import java.util.Vector;

import javax.media.opengl.GL2;

import octree.nodes.LeafTarget;
import octree.nodes.Placeable;
import renderers.core.GLRenderable;
import collisions.Collidable;
import collisions.Collision;
import collisions.CollisionHandler;

public class OctTree implements Collidable, CollisionHandler, GLRenderable {

  public static final int MAX_ELEMENTS = 1;

  private final Box space;
  
  private final OctTree[] subTrees;
  private final Vector<LeafTarget> elements;

  public OctTree(Box space, OctTree[] subTrees) {
    this(space, subTrees, new Vector<LeafTarget>());
  }

  public OctTree(Box space, Vector<LeafTarget> elements) {
    this(space, new OctTree[0], elements);
  }

  public OctTree(Box space, OctTree[] subTrees, Vector<LeafTarget> elements) {
    this.space = space;
    this.subTrees = subTrees;
    this.elements = elements;
  }

  @Override
  public Vector<Collision> collide(Line line, double accuracy) {
    Vector<Collision> collisions = new Vector<Collision>();

    if (line.collide(space, accuracy)) {
      collisions.add(new Collision(this, Collision.COLLISION_FULL));

      for (LeafTarget element : elements) {
        collisions.addAll(element.getCollisionHandler().collide(line, accuracy));
      }

      for (OctTree subTree : subTrees) {
        collisions.addAll(subTree.collide(line, accuracy));
      }
    }

    return collisions;
  }

  @Override
  public Vector<Collision> collide(Frustrum frustrum,
      double accuracy) {
    Vector<Collision> collisions = new Vector<Collision>();

    if (frustrum.collide(space, accuracy)) {
      collisions.add(new Collision(this, Collision.COLLISION_FULL));
      
      for (LeafTarget element : elements) {
        collisions.addAll(element.getCollisionHandler().collide(frustrum, accuracy));
      }

      for (OctTree subTree : subTrees) {
        collisions.addAll(subTree.collide(frustrum, accuracy));
      }
    }

    return collisions;
  }

  @Override
  public Vector<Collision> collide(Placeable box, double accuracy) {
    // TODO Auto-generated method stub
    throw new RuntimeException("TODO");
  }

  @Override
  public Vector<Collision> collide(Box space, double accuracy) {
    // TODO Auto-generated method stub
    throw new RuntimeException("TODO");
  }
  
  public OctTree[] getElements() {
    return subTrees;
  }

  @Override
  public void init(GL2 gl) {
    for (LeafTarget element : elements)
      element.init(gl);

    for (OctTree subTree : subTrees)
      subTree.init(gl);
  }

  @Override
  public void render(GL2 gl) {
    if(DebugSettings.B_RENDER_OCTREE)
      space.render(gl);

    for (LeafTarget element : elements)
      element.render(gl);

    for (OctTree subTree : subTrees)
      subTree.render(gl);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof OctTree) {
      OctTree compare = (OctTree) object;
      if (!space.equals(compare.space))
        return false;

      for (int i = 0; i < subTrees.length; i++)
        if (!subTrees[i].equals(compare.subTrees[i]))
          return false;

      return true;
    } else {
      return false;
    }
  }

  @Override
  public CollisionHandler getCollisionHandler() {
    return this;
  }
}
