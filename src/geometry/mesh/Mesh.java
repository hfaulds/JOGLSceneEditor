package geometry.mesh;

import geometry.Box;
import geometry.Line;
import geometry.Triangle;
import geometry.Vertex;

import java.util.Vector;

import javax.media.opengl.GL2;

import octree.collisions.Collidable;
import octree.collisions.CollisionDetails;
import octree.collisions.CollisionFrustrum;
import octree.nodes.Placeable;

public class Mesh implements Collidable {
  public final String name;
  
  public final Vertex[] vertices;
  public final Vertex[] normals;
  public final Triangle[] triangles;

  public Mesh(String name, Vertex[] vertices, Vertex[] normals, Triangle[] triangles) {
    this.name = name;
    this.vertices = vertices;
    this.normals = normals;
    this.triangles = triangles;
  }

  public static Mesh fromVectors(String name, Vector<Vertex> vertices, Vector<Triangle> triangles) {
    
    Vertex[] newVertices = new Vertex[vertices.size()];
    
    for(int i=0; i < vertices.size(); i++) {
      newVertices[i] = vertices.get(i);
    }

    Triangle[] newTriangles = new Triangle[triangles.size()];
    
    for(int i=0; i < triangles.size(); i++) {
      newTriangles[i] = triangles.get(i);
    }
    
    Vertex[] newNormals = createSmoothNormals(newVertices,newTriangles);
    
    return new Mesh(name, newVertices, newNormals, newTriangles);
  }

  public static Vertex[] createSmoothNormals(Vertex[] vertices, Triangle[] triangles) {
    Vertex[] normals = new Vertex[vertices.length];
    
    for (int i=0; i < normals.length ; i++) {
      normals[i] = new Vertex(0,0,0);
    }

    for (Triangle triangle : triangles) {
      Vertex v0 = vertices[triangle.getVertexIndex(0)];
      Vertex v1 = vertices[triangle.getVertexIndex(1)];
      Vertex v2 = vertices[triangle.getVertexIndex(2)];
      
      Vertex avec = v0.subtract(v1);
      Vertex bvec = v0.subtract(v2);
      
      Vertex normalpart = avec.crossProduct(bvec);
      
      for (int index : triangle.vertexIndices) {
        normals[index].add(normalpart);
      }
    }

    for (Vertex normal : normals) {
      normal.normalize();
    }
    
    return normals;
  }
  
  public void render(GL2 gl) {
    gl.glBegin(GL2.GL_TRIANGLES);
    
    for (Triangle triangle : triangles) {
      for (int i = 0; i < 3; i++) {
        
        gl.glTexCoord2dv(triangle.getTextureUV(i), 0);
        
        int vertexIndex = triangle.getVertexIndex(i);
        
        Vertex vertex = vertices[vertexIndex];
        gl.glVertex3dv(vertex.toDouble(), 0);
        
        Vertex normal = normals[vertexIndex];
        gl.glNormal3dv(normal.toDouble(), 0);
      }
    }
    
    gl.glEnd();
  }


  @Override
  public Vector<CollisionDetails> collide(Line line, double accuracy) {
    Vector<CollisionDetails> collisions = new Vector<CollisionDetails>();

    for (Vertex vertex : vertices) {
      collisions.addAll(vertex.collide(line, accuracy));
    }
    
    if(collisions.size() > 0) {
      collisions.add(new CollisionDetails(this,CollisionDetails.COLLISION_FULL));
    }
    
    return collisions;
  }

  @Override
  public Vector<CollisionDetails> collide(CollisionFrustrum box, double accuracy) {
    Vector<CollisionDetails> collisions = new Vector<CollisionDetails>();

    boolean partial = false;
    boolean collides = false;
    
    for (Vertex vertex : vertices) {
        Vector<CollisionDetails> vertexCollisions = vertex.collide(box, accuracy);
        
        collisions.addAll(vertexCollisions);
        
        if(vertexCollisions.size() > 0)
        {
          collides = true;
        } else {
          partial = true;
        }
    }

    if(collides) {
      if(partial) {
        collisions.add(new CollisionDetails(this, CollisionDetails.COLLISION_PARTIAL));
      } else {
        collisions.add(new CollisionDetails(this, CollisionDetails.COLLISION_FULL));
      }
    }

    return collisions;
  }

  @Override
  public Vector<CollisionDetails> collide(Placeable box, double accuracy) {
    // TODO Auto-generated method stub
    throw new RuntimeException("TODO");
  }
  
  public Vertex getCentre() {
    int xCenter = 0, yCenter = 0, zCenter = 0;

    for (Vertex vert : vertices) {
      xCenter += vert.x;
      yCenter += vert.y;
      zCenter += vert.z;
    }

    return new Vertex(xCenter, yCenter, zCenter).divide(vertices.length);
  }

  @Override
  public Vector<CollisionDetails> collide(Box space, double accuracy) {
    return null;
  }
}
