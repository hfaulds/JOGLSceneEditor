package octree.nodes;


import geometry.Box;
import geometry.Vertex;
import geometry.mesh.Mesh;

import java.util.Vector;

import javax.media.opengl.GL2;

import material.Material;
import octree.collisions.CollisionDetails;
import transforms.Transform;
import transforms.Translation;

public class StaticMesh extends Placeable {

  public final Material material;
  public final Mesh mesh;

  private double[] directionMatrix = 
      Transform.inDirection(direction).getMatrix();
  
  private double[] positionMatrix = 
      new Translation(position).getMatrix();
  
  public StaticMesh(Mesh mesh, Material material) {
    super(Box.createBoundingBox(mesh, new Vertex(0,0,0)));
    this.mesh = mesh;
    this.material = material;
  }
  
  public StaticMesh(Mesh mesh, Material material, Vertex position, Vertex direction) {
    super(position, direction, Box.createBoundingBox(mesh, position));
    this.mesh = mesh;
    this.material = material;
  }

  @Override
  public Vector<CollisionDetails> collide(Box space, double accuracy) {
    Vector<CollisionDetails> collisions = super.collide(space, accuracy);
    
    //TODO
    /*if(collisions.size() == 0) {
      return collisions;
    } else {
      return mesh.collide(space, accuracy);
    }*/
    return collisions;
  }
  
  @Override
  public void init(GL2 gl) {
    material.init(gl);
  }

  @Override
  public void render(GL2 gl) {
    gl.glPushMatrix();
    
    gl.glMultMatrixd(directionMatrix, 0);
    boundingbox.render(gl);
    gl.glMultMatrixd(positionMatrix, 0);
    
    material.startRender(gl);
    mesh.render(gl);
    material.stopRender(gl);
    gl.glPopMatrix();
  }

  @Override
  public Vertex getCentre() {
    return position.add(mesh.getCentre());
  }
}