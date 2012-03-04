package octree.nodes;

import editors.static_settings.DebugSettings;
import geometry.Box;
import geometry.Vertex;
import geometry.mesh.Mesh;

import javax.media.opengl.GL2;

import material.Material;
import octree.nodes.collision_handlers.StaticMeshCollisionHandler;
import transforms.Transform;
import transforms.Translation;
import collisions.CollisionHandler;

public class StaticMesh extends Placeable {

  public final Material material;
  public final Mesh mesh;
  
  protected CollisionHandler collisionHandler = new StaticMeshCollisionHandler(this);

  private double[] directionMatrix = Transform.inDirection(direction)
      .getMatrix();

  private double[] positionMatrix = new Translation(position).getMatrix();

  public StaticMesh(Mesh mesh, Material material) {
    super(Box.createBoundingBox(mesh, new Vertex(0, 0, 0)));
    this.mesh = mesh;
    this.material = material;
  }

  public StaticMesh(Mesh mesh, Material material, Vertex position,
      Vertex direction) {
    super(position, direction, Box.createBoundingBox(mesh, position));
    this.mesh = mesh;
    this.material = material;
  }

  @Override
  public void init(GL2 gl) {
    material.init(gl);
  }

  @Override
  public void render(GL2 gl) {
    gl.glPushMatrix();
    {
      gl.glMultMatrixd(directionMatrix, 0);
      {
        if (DebugSettings.B_RENDER_BOUNDING_BOXSS) {
          boundingbox.render(gl);
        }

        gl.glMultMatrixd(positionMatrix, 0);
        {
          material.startRender(gl);
          mesh.render(gl);
          material.stopRender(gl);
        }
      }
    }
    gl.glPopMatrix();
  }

  @Override
  public Vertex getCentre() {
    return position.add(mesh.getCentre());
  }
}