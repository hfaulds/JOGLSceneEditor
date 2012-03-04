package renderers.core;

import geometry.Vertex;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class ViewPortRenderer {

  private GLU glu = new GLU();

  private Vertex eyePosition = new Vertex(0,0,0);
  private Vertex upVector = new Vertex(0,0,0);

  private float theta;
  private float phi;
  private float radius;

  private boolean bAnglesChanged = true;

  private double[] modelMatrix  = new double[16];
  private double[] projectionMatrix = new double[16];
  private int[] viewportMatrix = new int[4];

  private boolean bMatricesChanged = true;

  public ViewPortRenderer() {
    this(-0.7f, 0.5f, 5.0f);
  }

  public ViewPortRenderer(float theta, float phi, float radius) {
    this.theta = theta;
    this.phi = phi;
    this.radius = radius;

    calcEyePosition();
  }

  public void render(GL2 gl) {
    if (bAnglesChanged)
    {
      calcEyePosition();
      glu.gluLookAt(
          eyePosition.x, eyePosition.y, eyePosition.z, 
          0.0f, 0.0f, 0.0f, 
          upVector.x, upVector.y, upVector.z)
          ;
    }
  }

  public Vertex unProject(GL2 gl, int x, int y, double depth) {
    if(bMatricesChanged)
      updateMatrices(gl);
    
    double wcoord1[] = new double[4];
    y = viewportMatrix[3] - y - 1;
    
    glu.gluUnProject(
        x, y, depth, 
        modelMatrix, 0, 
        projectionMatrix, 0, 
        viewportMatrix, 0, 
        wcoord1, 0
        );
    
    return new Vertex(wcoord1[0], wcoord1[1], wcoord1[2]);
  }
  
  public void updateMatrices(GL2 gl) {
    gl.glGetIntegerv(GL2.GL_VIEWPORT, viewportMatrix, 0);
    gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelMatrix, 0);
    gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projectionMatrix, 0);
    bMatricesChanged  = false;
  }

  public void updateTheta(float f) {
    theta -= f;
    bAnglesChanged = true;
  }

  public void updatePhi(float f) {
    phi += f;
    bAnglesChanged = true;
  }
  
  public void setRadius(float f) {
    radius = f;
    bAnglesChanged = true;
  }

  private void calcEyePosition() {
    float cy = (float) Math.cos(theta);
    float sy = (float) Math.sin(theta);
    float cz = (float) Math.cos(phi);
    float sz = (float) Math.sin(phi);

    eyePosition.x = radius * cy * cz;
    eyePosition.y = radius * sz;
    eyePosition.z = -radius * sy * cz;

    upVector.x = -cy * sz;
    upVector.y = cz;
    upVector.z = sy * sz;

    if (upVector.y < 0) {
      upVector.x = -upVector.x;
      upVector.y = -upVector.y;
      upVector.z = -upVector.z;
    }
    
    bAnglesChanged = false;
    bMatricesChanged = true;
  }

  // Getters
  public Vertex getEyePosition() {
    return new Vertex(eyePosition);
  }
  
  public float getTheta() {
    return theta;
  }

  public float getPhi() {
    return phi;
  }

  public float getRadius() {
    return radius;
  }
}
