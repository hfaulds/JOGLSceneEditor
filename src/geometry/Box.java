package geometry;

import java.util.Vector;

import javax.media.opengl.GL2;

import octree.collisions.CollisionDetails;

import material.Colour;

import geometry.mesh.Mesh;

public class Box extends Mesh {
  
  public final Colour colour;
  
  public final Vertex position;
  public final double size;

  public Box(Vertex[] vertices, Vertex position) {
    this(vertices, position, new Colour());
  }
  
  public Box(Vertex[] vertices, Vertex position, Colour colour) {
    super("BoundingBox", vertices, new Vertex[0], new Triangle[0]);
    this.size = getSize(vertices, position);
    this.position = position;
    this.colour = colour;
  }
  
  public static Box createOctSpace(Vertex position, double size) {
    Vertex[] vertices = new Vertex[8];
    
    int i = 0;
    for (int x = -1; x < 2; x += 2) {
      for (int y = -1; y < 2; y += 2) {
        for (int z = -1; z < 2; z += 2) {
          vertices[i++] = new Vertex(
              position.x + size*x, 
              position.y + size*y, 
              position.z + size*z
            );
        }
      }
    }
    
    return new Box(vertices, position, new Colour(0, 0, 1));
  }

  public static Box createBoundingBox(Mesh mesh, Vertex position) {
    Vertex max = new Vertex(mesh.vertices[0]);
    Vertex min = new Vertex(mesh.vertices[0]);
    
    for(Vertex vertex : mesh.vertices)
    {
      max.x = Math.max(vertex.x, max.x);
      max.y = Math.max(vertex.y, max.y);
      max.z = Math.max(vertex.z, max.z);
      
      min.x = Math.min(vertex.x, min.x);
      min.y = Math.min(vertex.y, min.y);
      min.z = Math.min(vertex.z, min.z);
    }

    return Box.createBoundingBox(max, min, position);
  }
  
  public static Box createBoundingBox(Vertex max, Vertex min, Vertex position)
  {
    Vertex[] vertices = new Vertex[]{
      new Vertex(max.x, max.y, max.z).add(position),
      new Vertex(min.x, max.y, max.z).add(position),
      new Vertex(max.x, min.y, max.z).add(position),
      new Vertex(min.x, min.y, max.z).add(position),
      new Vertex(max.x, max.y, min.z).add(position),
      new Vertex(min.x, max.y, min.z).add(position),
      new Vertex(max.x, min.y, min.z).add(position),
      new Vertex(min.x, min.y, min.z).add(position)
    };
    
    return new Box(vertices, position, new Colour(0, 1, 0));
  }

  @Override
  public Vector<CollisionDetails> collide(Box space, double accuracy) {
    Vector<CollisionDetails> collisions = new Vector<CollisionDetails>();
    int vertsInside = 0;
    
    for(Vertex vert : vertices) {
      if(space.contains(vert)) {
        vertsInside++;
      }
    }
    
    if(vertsInside == vertices.length) {
      collisions.add(new CollisionDetails(this, CollisionDetails.COLLISION_FULL));
    } else if(vertsInside > 0) {
      collisions.add(new CollisionDetails(this, CollisionDetails.COLLISION_PARTIAL));
    }
    
    return collisions;
  }

  public boolean contains(Vertex point) {
    boolean xInside = contains(point.x, position.x);
    boolean yInside = contains(point.y, position.y);
    boolean zInside = contains(point.z, position.z);

    return xInside && yInside && zInside;
  }

  private boolean contains(double x, double center) {
    boolean bigEnough = x >= (center - size);
    boolean smallEnough = x <= (center + size);
    return (bigEnough && smallEnough);
  }
  
  private double getSize(Vertex[] points, Vertex position) {
    double maxDistance = 0;

    for (Vertex point : points) {
      Vertex vertex = point.subtract(position);
      maxDistance = Math.max(vertex.x, maxDistance);
      maxDistance = Math.max(vertex.y, maxDistance);
      maxDistance = Math.max(vertex.z, maxDistance);
    }

    return maxDistance;
  }
  
  @Override
  public void render(GL2 gl) {
    gl.glPushMatrix();
    gl.glColor3d(colour.r, colour.g, colour.b);
    gl.glBegin(GL2.GL_LINE_LOOP);
    for(int i : new int[]{0,4,5,1,3,7,6,2,0,4,6,2,3,7,5,1,0})
    {
      Vertex vertex = vertices[i];
      gl.glVertex3d(vertex.x, vertex.y, vertex.z);
    }
    gl.glEnd();
    gl.glPopMatrix();
  }
}
