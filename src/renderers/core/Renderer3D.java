package renderers.core;

import geometry.Line;
import geometry.Vertex;

import java.awt.Point;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public abstract class Renderer3D implements GLEventListener {
 
  protected ViewPortRenderer camera;
  protected ViewFrustrumRenderer viewFrustrum;

  public ViewPortRenderer getCamera() {
    return this.camera;
  }

  public ViewFrustrumRenderer getViewFrustrum() {
    return this.viewFrustrum;
  }
  
  protected Line unProject(GL2 gl, Point p) {
    return unProject(gl, p.x, p.y);
  }
  
  protected Line unProject(GL2 gl, int x, int y)
  {
    Vertex vert1 = camera.unProject(gl, x, y, 0);
    Vertex vert2 = camera.unProject(gl, x, y, 1);
  
    return new Line(vert1, vert2);
  }

  @Override
  public void display(GLAutoDrawable drawable) {
    GL2 gl = getGL(drawable);
    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity();
    viewFrustrum.render(gl);
    camera.render(gl);
  }
  
  @Override
  public void init(GLAutoDrawable drawable) {
    GL2 gl = getGL(drawable);

    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    gl.glEnable(GL2.GL_DEPTH_TEST);
    gl.glEnable(GL2.GL_CULL_FACE);
    gl.glCullFace(GL2.GL_BACK);
    gl.glShadeModel(GL2.GL_SMOOTH);
    gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    gl.glEnable(GL2.GL_NORMALIZE);

    viewFrustrum.init(gl);
  }
  
  @Override
  public void reshape(
      GLAutoDrawable drawable, 
      int x, int y, int width,
      int height) 
  {
    GL2 gl = getGL(drawable);
    viewFrustrum.reshape(width, height, gl);
  }
  
  @Override
  public void dispose(GLAutoDrawable drawable) {
  }

  protected GL2 getGL(GLAutoDrawable drawable) {
    return drawable.getGL().getGL2();
  }

}
