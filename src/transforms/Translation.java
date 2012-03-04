package transforms;

import geometry.Vertex;

public class Translation extends Transform {

  public Translation(double x, double y, double z) {
    tm[0][3] = x;
    tm[1][3] = y;
    tm[2][3] = z;
  }

  public Translation(Vertex v) {
    this(v.x, v.y, v.z);
  }

  public Vertex getVector() {
    return new Vertex(tm[0][3], tm[1][3], tm[2][3]);
  }

  public void reCalculate(Vertex vector) {
    reCalculate(vector.x, vector.y, vector.z);
  }

  public void reCalculate(double x, double y, double z) {
    tm[0][3] = x;
    tm[1][3] = y;
    tm[2][3] = z;
  }
}
