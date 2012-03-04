package geometry;

import java.util.Vector;

import javax.media.opengl.GL2;

import material.Colour;
import octree.nodes.LeafTarget;
import octree.nodes.Placeable;
import collisions.Collision;
import collisions.CollisionHandler;

public class Vertex implements CollisionHandler, LeafTarget {
  public Colour colour = new Colour();
  public int renderSize = 5;
  
  public double x, y, z;

  public Vertex() {
    this(0,0,0);
  }

  public Vertex(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public Vertex(Vertex vertex) {
    this.x = vertex.x;
    this.x = vertex.y;
    this.x = vertex.z;
  }

  public double[] toDouble() {
    return new double[]{x,y,z};
  }
  
  public float[] toFloat() {
    return new float[]{(float) x,(float) y,(float) z};
  }

  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + "]";
  }
  
  public double distanceTo(Vertex vert) {
    double x = Math.pow(vert.x - this.x, 2);
    double y = Math.pow(vert.y - this.y, 2);
    double z = Math.pow(vert.z - this.z, 2);
    return Math.sqrt(x + y + z);
  }

  public Vertex add(Vertex vert) {
    double x = this.x + vert.x;
    double y = this.y + vert.y;
    double z = this.z + vert.z;
    return new Vertex(x, y, z);
  }

  public Vertex subtract(Vertex vert) {
    double x = this.x - vert.x;
    double y = this.y - vert.y;
    double z = this.z - vert.z;
    return new Vertex(x, y, z);
  }

  public Vertex mutiply(double n) {
    double x = this.x * n;
    double y = this.y * n;
    double z = this.z * n;
    return new Vertex(x,y,z);
  }

  public Vertex divide(double n) {
    double x = this.x / n;
    double y = this.y / n;
    double z = this.z / n;
    return new Vertex(x,y,z);
  }

  public Vertex crossProduct(Vertex vert) {
    double x = this.y * vert.z - this.z * vert.y;
    double y = this.z * vert.x - this.x * vert.z;
    double z = this.x * vert.y - this.y * vert.x;
    return new Vertex(x, y, z);
  }

  public double dotProduct(Vertex vert) {
    double x = this.x * vert.x;
    double y = this.y * vert.y;
    double z = this.z * vert.z;
    return (x + y + z);
  }
  
  public double magnitude() {
    double a = Math.pow(this.x, 2);
    double b = Math.pow(this.y, 2);
    double c = Math.pow(this.z, 2);
    return Math.sqrt(a + b + c);
  }

  public Vertex normalized() {
    if(this.magnitude() != 0)
      return this.divide(this.magnitude());
    else
      return new Vertex(this);
  }

  public void normalize() {
    double mag = this.magnitude();
    this.x /= mag;
    this.y /= mag;
    this.z /= mag;
  }
  
  @Override
  public Vector<Collision> collide(Line line, double accuracy) {
    Vector<Collision> collisions = new Vector<Collision>();
    
    if (line.distanceTo(this) < accuracy) {
      collisions.add(new Collision(this, Collision.COLLISION_FULL));
    }

    return collisions;
  }
  
  @Override
  public Vector<Collision> collide(Frustrum box, double accuracy) {
    Vector<Collision> collisions = new Vector<Collision>();
    
    if(box.contains(this, accuracy))
    {
      collisions.add(new Collision(this, Collision.COLLISION_FULL));
    }
    
    return collisions;
  }

  @Override
  public Vector<Collision> collide(Placeable placeable, double accuracy) {
    // TODO Auto-generated method stub
    throw new RuntimeException("TODO");
  }

  @Override
  public Vector<Collision> collide(Box space, double accuracy) {
    Vector<Collision> collisions = new Vector<Collision>();
    
    if(space.contains(this))
      collisions.add(new Collision(this, Collision.COLLISION_FULL));
    
    return collisions;
  }

  @Override
  public void render(GL2 gl) {
    gl.glPushMatrix();
    
    gl.glPointSize(renderSize);
    gl.glColor3d(colour.r, colour.g, colour.b);
    
    gl.glBegin(GL2.GL_POINTS);
    gl.glVertex3d(x, y, z);
    gl.glEnd();
    
    gl.glPopMatrix();
  }

  @Override
  public CollisionHandler getCollisionHandler() {
    return this;
  }

  @Override
  public void init(GL2 gl) {}

  @Override
  public boolean equals(Object object)
  {
    if(object instanceof Vertex)
    {
      Vertex compare = (Vertex)object;
      if(x != compare.x)
        return false;
      if(y != compare.y)
        return false;
      if(z != compare.z)
          return false;
      return true;
    }else{
      return false;
    }
  }
}
