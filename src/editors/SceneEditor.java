package editors;

import editors.input.MouseKeyboardInputListener;
import geometry.Vertex;
import geometry.mesh.Mesh;
import geometry.mesh.MeshLoader;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import octree.nodes.Placeable;
import octree.nodes.StaticMesh;
import renderers.SceneRenderer;


import material.Material;
import material.MaterialFactory;

import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class SceneEditor extends JFrame {

  private static final int DEFAULT_WIDTH = 1024;
  private static final int DEFAULT_HEIGHT = 768;

  public SceneEditor(Vector<Placeable> scene) {
    SceneRenderer meshRenderer = new SceneRenderer(scene, DEFAULT_WIDTH, DEFAULT_HEIGHT);

    this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    GLProfile glProfile = GLProfile.getDefault();
    GLCapabilities glCapabilities = new GLCapabilities(glProfile);
    
    GLCanvas canvas = new GLCanvas(glCapabilities);
    canvas.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    
    MouseKeyboardInputListener listener = meshRenderer.getInputListener();
    canvas.addMouseMotionListener(listener);
    canvas.addMouseWheelListener(listener);
    canvas.addMouseListener(listener);
    canvas.addKeyListener(listener);
    canvas.addGLEventListener(meshRenderer);
    
    FPSAnimator animator = new FPSAnimator(canvas, 60);
    animator.add(canvas);
    animator.start();
    this.add(canvas);

  }

  public static void main(String... args) {
    try {
      GLProfile.initSingleton(true);

      File meshFile = new File("assets/meshes/mushroom2.obj");
      File textureFile = new File("assets/textures/mushroom.jpg");

      Mesh mesh = MeshLoader.loadOBJ(meshFile);
      Material material = MaterialFactory.createMaterial(textureFile);
      
      Vector<Placeable> scene = new Vector<Placeable>();
      for(int i=-1; i < 2; i+=2)
      {
        for(int j=-5; j < 5; j ++)
        {
          StaticMesh staticMesh = new StaticMesh(mesh, material, new Vertex(i,j,j), new Vertex(0,0,1));
          scene.add(staticMesh);
        }
      }
      SceneEditor editor = new SceneEditor(scene);
      
      editor.setVisible(true);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}