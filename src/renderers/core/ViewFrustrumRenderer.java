package renderers.core;

import javax.media.opengl.GL2;

public class ViewFrustrumRenderer {
  private static final float DEFAULT_FOV = 60.0f;
  private static final float DEFAULT_NEAR_CLIP = 0.1f;
  private static final float DEFAULT_FAR_CLIP = 100.0f;

  private int width, height;
  private final float fov;
  private final float nearClip, farClip;

  public ViewFrustrumRenderer(int width, int height, float fov, float nearClip,
      float farClip) {
    this.width = width;
    this.height = height;

    this.fov = fov;
    this.nearClip = nearClip;
    this.farClip = farClip;
  }

  public ViewFrustrumRenderer(int width, int height) {
    this(width, height, DEFAULT_FOV, DEFAULT_NEAR_CLIP, DEFAULT_FAR_CLIP);
  }

  public void init(GL2 gl) {
    float fAspect = (float) width / height;
    float top = (float) Math.tan(Math.toRadians(fov * 0.5)) * nearClip;
    float bottom = -top;
    float left = fAspect * bottom;
    float right = fAspect * top;

    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glFrustum(left, right, bottom, top, nearClip, farClip);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
  
  }
  
  public void render(GL2 gl) {
    float fAspect = (float) width / height;
    float top = (float) Math.tan(Math.toRadians(fov * 0.5)) * nearClip;
    float bottom = -top;
    float left = fAspect * bottom;
    float right = fAspect * top;
    
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glFrustum(left, right, bottom, top, nearClip, farClip);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
  }

  public void reshape(int width, int height, GL2 gl) {
    this.width = width;
    this.height = height;
    init(gl);
  }

  public float getFov() {
    return fov;
  }

  public float getNearClip() {
    return nearClip;
  }

  public float getFarClip() {
    return farClip;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
