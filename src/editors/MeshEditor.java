package editors;

import editors.input.MouseKeyboardInputListener;
import geometry.mesh.Mesh;
import geometry.mesh.MeshLoader;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import octree.nodes.StaticMesh;


import material.Material;
import material.MaterialFactory;
import renderers.StaticMeshRenderer;

import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class MeshEditor extends JFrame {

  private static final int DEFAULT_WIDTH = 1024;
  private static final int DEFAULT_HEIGHT = 768;

  public MeshEditor(StaticMesh meshNode) {
    StaticMeshRenderer meshRenderer = new StaticMeshRenderer(meshNode, DEFAULT_WIDTH, DEFAULT_HEIGHT);

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
      
      StaticMesh meshNode = new StaticMesh(mesh, material);
      MeshEditor editor = new MeshEditor(meshNode);
      
      editor.setVisible(true);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
