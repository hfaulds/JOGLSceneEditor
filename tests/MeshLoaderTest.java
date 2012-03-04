import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;


import org.junit.Test;

import geometry.Triangle;
import geometry.Vertex;
import geometry.mesh.Mesh;
import geometry.mesh.MeshLoader;


public class MeshLoaderTest {

  @Test
  public void CanLoadTestBox() throws FileNotFoundException {
    File meshFile = new File("assets/meshes/test-cube.obj");

    Mesh mesh = MeshLoader.loadOBJ(meshFile);
    
    
    Vertex[] vertices = new Vertex[]{
        new Vertex(-1 , -1,  1),
        new Vertex(-1 , -1, -1),
        new Vertex( 1 , -1, -1),
        new Vertex( 1 , -1,  1),
        new Vertex(-1 ,  1,  1),
        new Vertex( 1 ,  1,  1),
        new Vertex( 1 ,  1, -1),
        new Vertex(-1 ,  1, -1)
    };
    
    Vertex[] uvs = new Vertex[]{
        new Vertex(1, 0, 0),
        new Vertex(1, 1, 0),
        new Vertex(0, 1, 0),
        new Vertex(0, 0, 0)
    };
    
    Triangle[] triangles = new Triangle[]{
        new Triangle(0, uvs[0], 1, uvs[1], 2, uvs[2]),
        new Triangle(2, uvs[2], 3, uvs[3], 0, uvs[0]),
        new Triangle(4, uvs[3], 5, uvs[0], 6, uvs[1]),
        new Triangle(6, uvs[1], 7, uvs[2], 4, uvs[3]),
        new Triangle(0, uvs[3], 3, uvs[0], 5, uvs[1]),
        new Triangle(5, uvs[1], 4, uvs[2], 0, uvs[3]),
        new Triangle(3, uvs[3], 2, uvs[0], 6, uvs[1]),
        new Triangle(6, uvs[1], 5, uvs[2], 3, uvs[3]),
        new Triangle(2, uvs[3], 1, uvs[0], 7, uvs[1]),
        new Triangle(7, uvs[1], 6, uvs[2], 2, uvs[3]),
        new Triangle(1, uvs[3], 0, uvs[0], 4, uvs[1]),
        new Triangle(4, uvs[1], 7, uvs[2], 1, uvs[3])
    };
    
    assertTrue(mesh.vertices.length == vertices.length);
    
    for(int i=0; i < vertices.length; i++) {
      Vertex vertex1 = vertices[i];
      Vertex vertex2 = mesh.vertices[i];
      assertTrue(vertex1.toString().equals(vertex2.toString()));
    }
    
    assertTrue(mesh.triangles.length == triangles.length);
    
    for(int i=0; i < vertices.length; i++) {
      Triangle tri1 = triangles[i];
      Triangle tri2 = mesh.triangles[i];
      assertTrue(tri1.equals(tri2));
    }
  }

}
