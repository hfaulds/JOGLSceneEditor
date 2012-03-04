package geometry.mesh;

import geometry.Triangle;
import geometry.Vertex;

import java.util.Vector;


public class MeshPlaneFactory {

  public static Mesh createMeshPlane(double width, double depth, int widthSegments, int depthSegments) {

    Vector<Vertex> vertices = createPlaneVertices(width, depth, widthSegments, depthSegments);
    Vector<Triangle> triangles = createPlaneTris(widthSegments, depthSegments);
    
    return Mesh.fromVectors("Plane", vertices, triangles);
  }

  private static Vector<Vertex> createPlaneVertices(double width, double depth,
      int widthSegments, int depthSegments) {
    double startX = width / -2.0;
    double startZ = depth / -2.0;

    double stepX = width / widthSegments;
    double stepZ = depth / depthSegments;

    Vector<Vertex> vertices = new Vector<Vertex>();

    for (int x = 0; x < widthSegments + 1; x++) {
      for (int z = 0; z < depthSegments + 1; z++) {
        
        vertices.add(new Vertex(
            startX + stepX * x, 
            0.0, 
            startZ + stepZ * z
            ));
      }
    }

    return vertices;
  }

  private static Vector<Triangle> createPlaneTris(int widthSegments, int depthSegments) {
    
    Vector<Triangle> triangles = new Vector<Triangle>();
    int zVertexCount = depthSegments + 1;

    for (int x = 0; x < widthSegments; x++) {
      for (int z = 0; z < depthSegments; z++) {
        //Define vertex indices of the square about to be constructed
        int tlVertIndex = x * zVertexCount + z;
        int trVertIndex = tlVertIndex + 1;
        int blVertIndex = tlVertIndex + zVertexCount;
        int brVertIndex = blVertIndex + 1;
        
        triangles.add(new Triangle(
                tlVertIndex, 
                calcUVCoord(widthSegments, depthSegments, x, z), 
                trVertIndex, 
                calcUVCoord(widthSegments, depthSegments, x, z + 1),
                blVertIndex, 
                calcUVCoord(widthSegments, depthSegments, x + 1, z)
              ));

        triangles.add(new Triangle(trVertIndex, 
                calcUVCoord(widthSegments, depthSegments, x, z + 1), 
                brVertIndex, 
                calcUVCoord(widthSegments, depthSegments, x + 1, z + 1), 
                blVertIndex,
                calcUVCoord(widthSegments, depthSegments, x + 1, z)
                ));
      }
    }
    return triangles;
  }

  private static Vertex calcUVCoord(int widthSegments, int depthSegments, double x, double z) {
    double uvx = 1 - (x / widthSegments);
    double uvz = (z / depthSegments);
    return new Vertex(uvx, uvz, 0);
  }

}