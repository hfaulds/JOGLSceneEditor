package renderers;

import editors.input.MouseKeyboardInputListener;
import geometry.Frustrum;
import geometry.Line;
import geometry.Vertex;

import java.awt.Point;
import java.util.Vector;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import material.Colour;
import octree.OctTree;
import octree.factories.VertexOctreeFactory;
import octree.nodes.StaticMesh;
import renderers.core.Renderer2D;
import renderers.core.Renderer3D;
import renderers.core.ViewFrustrumRenderer;
import renderers.core.ViewPortRenderer;
import collisions.Collision;

public class StaticMeshRenderer extends Renderer3D {

  private final StaticMesh meshNode;
  private final OctTree meshVertTree;

  private Vector<Vertex> selectedVertices = new Vector<Vertex>();
  private MouseKeyboardInputListener listener = new MouseKeyboardInputListener();

  private Renderer2D renderer2D = new Renderer2D();
  
  public StaticMeshRenderer(StaticMesh meshNode, int width, int height) {
    this.meshNode = meshNode;
    this.viewFrustrum = new ViewFrustrumRenderer(width, height);
    this.camera = new ViewPortRenderer();

    VertexOctreeFactory factory = new VertexOctreeFactory();
    meshVertTree = factory.createOctree(meshNode.mesh.vertices);
  }

  @Override
  public void display(GLAutoDrawable drawable) {
    super.display(drawable);
    GL2 gl = getGL(drawable);
    meshNode.render(gl);
    meshVertTree.render(gl);

    camera.setRadius(listener.mouseWheelRotation);
    if (listener.bSelecting) {
      Point start = listener.selectionStart;
      Point end = listener.selectionEnd;
      
      if(!listener.bContinousSelection)
      {
        clearSelection();
      }
        
      if (Math.abs(Point.distance(start.x, start.y, end.x, end.y)) < 10) {
        selectSingleVertex(gl, end);
      } else {
        selectMultipleVertices(drawable, start, end);
      }
    }else if(listener.bRotating){
      camera.updateTheta((listener.xMovement / (float)viewFrustrum.getWidth()));
      camera.updatePhi((listener.yMovement / (float)viewFrustrum.getHeight()));
    }
  }

  private void selectMultipleVertices(GLAutoDrawable drawable, Point start, Point end) {
    GL2 gl = getGL(drawable);
    int topx = Math.max(start.x, end.x);
    int topy = Math.max(start.y, end.y);
    int bottomx = Math.min(start.x, end.x);
    int bottomy = Math.min(start.y, end.y);
    
    Line line1 = unProject(gl, topx, topy);
    Line line2 = unProject(gl, topx, bottomy);
    Line line3 = unProject(gl, bottomx, bottomy);
    Line line4 = unProject(gl, bottomx, topy);
    
    Frustrum collisionFrustrum = new Frustrum(line1, line2,line3, line4);
    
    Vector<Collision> collisions = meshVertTree.collide(collisionFrustrum, 0);
    
    for (Collision collision : collisions) {
      if(collision.source instanceof Vertex) {
        selectVert((Vertex)collision.source);
      }
    }
    
    renderer2D.drawRectangle(drawable, new Point(topx,topy), new Point(bottomx,bottomy));
    super.init(drawable);
  }
  
  private void selectSingleVertex(GL2 gl, Point mousePos) {
    Line collisionline = unProject(gl, mousePos);
    Vector<Collision> collisions = meshVertTree.collide(collisionline, 0.5);
    
    Vertex closestVertex = null;
    int currentDistance = 9999;
    
    for(Collision collision : collisions) {
      if(collision.source instanceof Vertex) {
        Vertex vertex = (Vertex) collision.source;
        double distance = vertex.distanceTo(camera.getEyePosition());
        if(distance < currentDistance) {
          closestVertex = vertex;
        }
      }
    }

    if (closestVertex != null) {
      selectVert(closestVertex);
    }
  }

  private void selectVert(Vertex vert) {
    selectedVertices.add(vert);
    vert.colour = new Colour(1, 0, 0, 0);
  }

  public void clearSelection() {
    for (Vertex vert : selectedVertices)
      vert.colour = new Colour(1, 1, 1, 1);

    selectedVertices.clear();
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    super.init(drawable);
    GL2 gl = getGL(drawable);
    meshNode.init(gl);
  }

  public MouseKeyboardInputListener getInputListener() {
    return listener ;
  }

}
