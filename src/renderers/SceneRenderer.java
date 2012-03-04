package renderers;

import java.util.Vector;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import octree.OctTree;
import octree.factories.PlaceableOctreeFactory;
import octree.nodes.Placeable;
import renderers.core.Renderer3D;
import renderers.core.ViewFrustrumRenderer;
import renderers.core.ViewPortRenderer;
import editors.input.MouseKeyboardInputListener;

public class SceneRenderer extends Renderer3D {

  private final OctTree sceneTree;
  
  private MouseKeyboardInputListener listener = new MouseKeyboardInputListener();

  public SceneRenderer(Vector<Placeable> scene, int width, int height) {
    
    this.viewFrustrum = new ViewFrustrumRenderer(width, height);
    this.camera = new ViewPortRenderer();

    PlaceableOctreeFactory factory = new PlaceableOctreeFactory();
    sceneTree = factory.createOctree(scene);
  }

  @Override
  public void display(GLAutoDrawable drawable) {
    super.display(drawable);
    GL2 gl = getGL(drawable);
    sceneTree.render(gl);

    camera.setRadius(listener.mouseWheelRotation);
    
    if(listener.bRotating){
      camera.updateTheta((listener.xMovement / (float)viewFrustrum.getWidth()));
      camera.updatePhi((listener.yMovement / (float)viewFrustrum.getHeight()));
    }
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    super.init(drawable);
    GL2 gl = getGL(drawable);
    sceneTree.init(gl);
  }
  
  public MouseKeyboardInputListener getInputListener() {
    return listener;
  }

}
