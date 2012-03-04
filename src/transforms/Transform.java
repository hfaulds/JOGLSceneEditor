package transforms;

import geometry.Vertex;

public class Transform {

  private final int SIZE = 4;
  protected final double[][] tm;

  protected Transform() {
    this.tm = getIdentityMatrix();
  }

  private Transform(double[][] tm) {
    this.tm = tm;
  }

  public Transform(double[] m) {
    this(getIdentityMatrix());
    for (int i = 0; i < SIZE; i++)
      for (int j = 0; j < SIZE; j++)
        tm[j][i] = m[i * SIZE + j];
  }

  public double[] getMatrix() {
    double[] m = new double[SIZE * SIZE];
    for (int i = 0; i < SIZE; i++)
      for (int j = 0; j < SIZE; j++)
        m[i * SIZE + j] = tm[j][i];
    return m;
  }

  public static double[][] getIdentityMatrix() {
    return new double[][] { 
        { 1, 0, 0, 0 }, 
        { 0, 1, 0, 0 }, 
        { 0, 0, 1, 0 },
        { 0, 0, 0, 1 }
        };
  }
  
  
  public static Transform pointToFrom(Vertex position, Vertex target) {
    Vertex direction = position.subtract(target).normalized();
    return inDirection(direction);
  }

  public static Transform inDirection(Vertex direction) {
    
    direction.normalize();
    Vertex x_direction = new Vertex(0,0,1);
    Vertex y_direction = new Vertex(0,0,0);
    Vertex z_direction = direction;
    
    double z = direction.z;
    
    if(z >-0.999999999 && z <0.999999999) {
      x_direction = x_direction.subtract(direction.mutiply(z)).normalized();
      y_direction = direction.crossProduct(x_direction);
    } else {
      x_direction = new Vertex(direction.z, 0, -direction.x);
      y_direction = new Vertex(0,1,0);
    }
    
    double[] rot = new double[]{
        x_direction.x,  x_direction.y,  x_direction.z,          0, 
        y_direction.x,  y_direction.y,  y_direction.z,          0, 
        z_direction.x,  z_direction.y,  z_direction.z,          0, 
        0,              0,              0,                      1
        };
    
    return new Transform(rot);
  }
  
  /*Vec3d x_dir(0.0,0.0,1.0),y_dir;
    real64 d=dir.z;
    
    if(d>-0.999999999 && d<0.999999999){ // to avoid problems with normalize in special cases
                    x_dir=x_dir-dir*d;
                    x_dir.Normalize();
                    y_dir=CrossProd(dir,x_dir);
    }else{
                    x_dir=Vec3d(dir.z,0,-dir.x);
                    y_dir=Vec3d(0,1,0);
    };
  */
}