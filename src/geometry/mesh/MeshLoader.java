package geometry.mesh;

import geometry.Triangle;
import geometry.Vertex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Vector;


public class MeshLoader {

  private final static String FOLDER_PATH = "assets/meshes/";

  public static Mesh loadOBJ(String filename) throws FileNotFoundException {
    String fullpath = FOLDER_PATH + filename;
    return loadOBJ(new File(fullpath));
  }

  public static Mesh loadOBJ(File file) throws FileNotFoundException {
    return loadOBJ(new BufferedReader(new FileReader(file)), file.getName());
  }

  private static Mesh loadOBJ(BufferedReader file, String filename)
      throws FileNotFoundException {
    Vector<Vertex> vertices = new Vector<Vertex>();
    Vector<Triangle> triangles = new Vector<Triangle>();
    Vector<Vertex> uvwCoords = new Vector<Vertex>();

    try {
      for(String line = file.readLine(); line != null; line = file.readLine()) {
        StringTokenizer st = new StringTokenizer(line, "\t ");

        if (st.hasMoreTokens()) {
          
          String data = st.nextToken();
          
          if (data.equalsIgnoreCase("v")) {
            
            /**New Vertex**/
            vertices.add(new Vertex(
                nextDouble(st), 
                nextDouble(st),
                nextDouble(st)
                ));
            
          } else if (data.equalsIgnoreCase("vn")) {
            
            /**New Normal**/
            throw new RuntimeException("TODO");
            
          } else if (data.equalsIgnoreCase("vt")) {
            
            /**New Texture Coordinate**/
            uvwCoords.add(new Vertex(
                nextDouble(st), 
                nextDouble(st), 
                nextDouble(st)
                ));
            
          } else if (data.equalsIgnoreCase("f")) {
            
            /**New Triangle**/
            int[] vertIndexes = new int[3];
            Vertex[] localuvws = new Vertex[3];
            
            for(int i=0; i < 3; i++)
            {
              StringTokenizer subTokenizer = new StringTokenizer(st.nextToken(), "/");
              
              vertIndexes[i] = nextInt(subTokenizer);
              localuvws[i] = uvwCoords.get(nextInt(subTokenizer));
            }
            
            triangles.add(new Triangle(
                vertIndexes[0], localuvws[0], 
                vertIndexes[1], localuvws[1], 
                vertIndexes[2], localuvws[2])
            );
          }
        }
      }

      file.close();

      return Mesh.fromVectors(filename, vertices, triangles);

    } catch (Exception e) {
      System.out.println(e);
      return null;
    }

  }

  private static int nextInt(StringTokenizer tokenizer) {
    return Integer.parseInt(tokenizer.nextToken()) - 1;
  }

  private static double nextDouble(StringTokenizer st) {
    return Double.parseDouble(st.nextToken());
  }
}