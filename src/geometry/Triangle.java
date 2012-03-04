package geometry;

public class Triangle implements Cloneable {
  public final int[] vertexIndices = new int[3];
  public final Vertex[] textureUVW = new Vertex[3];

  public Triangle(
      int v0, Vertex uvw0, 
      int v1, Vertex uvw1,
      int v2, Vertex uvw2) 
  {
    vertexIndices[0] = v0;
    vertexIndices[1] = v1;
    vertexIndices[2] = v2;
    textureUVW[0] = uvw0;
    textureUVW[1] = uvw1;
    textureUVW[2] = uvw2;
  }

  public double[] getTextureUV(int i) {
    return textureUVW[i].toDouble();
  }

  public int getVertexIndex(int v) {
    return vertexIndices[v];
  }

  @Override
  public boolean equals(Object object)
  {
    if(object instanceof Triangle)
    {
      Triangle compare = (Triangle) object;
      
      for(int i=0; i < vertexIndices.length; i++)
        if(vertexIndices[i] != compare.vertexIndices[i])
          return false;
      
      for(int i=0; i < textureUVW.length; i++)
          if(!textureUVW[i].equals(compare.textureUVW[i]))
            return false;
      
      return true;
    }
    else
    {
      return false;
    }
  }
}
