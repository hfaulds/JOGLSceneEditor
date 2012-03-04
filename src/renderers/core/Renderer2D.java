package renderers.core;

import java.awt.Point;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Renderer2D {
  
  /** DOES NOT CLEAR THE BUFFER **/
  public void drawRectangle(GLAutoDrawable drawable, Point tl, Point br) {
    this.init(drawable);
    GL2 gl = getGL(drawable);

    gl.glLoadIdentity();
    gl.glColor3d(1,1,1);
    
    gl.glBegin(GL2.GL_LINE_LOOP);
    gl.glVertex2d(tl.x, tl.y);
    gl.glVertex2d(br.x, tl.y);
    gl.glVertex2d(br.x, br.y);
    gl.glVertex2d(tl.x, br.y);
    gl.glEnd();
  }

  private void init(GLAutoDrawable drawable) {
    GL2 gl = getGL(drawable);
    gl.glMatrixMode (GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(0, drawable.getWidth(), drawable.getHeight(), 0, 0, 1);
    gl.glDisable(GL2.GL_DEPTH_TEST);
    gl.glDisable(GL2.GL_CULL_FACE);
    gl.glMatrixMode (GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
  }
  
  private GL2 getGL(GLAutoDrawable drawable) {
    return drawable.getGL().getGL2();
  }
}
