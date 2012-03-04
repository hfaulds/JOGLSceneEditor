package renderers.core;

import javax.media.opengl.GL2;

public interface GLRenderable {
  public void render(GL2 gl);
  public void init(GL2 gl);
}
