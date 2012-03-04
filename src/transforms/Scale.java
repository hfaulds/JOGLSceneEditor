package transforms;

import geometry.Vertex;

public class Scale extends Transform {
  public Scale(double x, double y, double z) {
    tm[0][0] = x;
    tm[1][1] = y;
    tm[2][2] = z;
  }

  public Vertex getVector() {
    return new Vertex(tm[0][0], tm[1][1], tm[2][2]);
  }
}
