package octree.nodes;

import geometry.Box;
import geometry.Vertex;

import javax.media.opengl.GL2;

import material.Colour;

public class DirectionalLight extends Placeable {

  private static final Box DEFAULT_BOUNDING_BOX = 
      Box.createBoundingBox(new Vertex(-0.5, -0.5, -0.5), new Vertex(0.5, 0.5, 0.5), new Vertex(0,0,0));
  
  public final float lightCutoffAngle;
  public final Colour lightColour;
  public final int lightID;

  public DirectionalLight(int lightID, Vertex position,
      Vertex direction, float lightCutoffAngle, Colour lightColour) {
    
    super(position, direction, DEFAULT_BOUNDING_BOX);
    
    this.lightID = lightID;
    this.lightCutoffAngle = lightCutoffAngle;
    this.lightColour = lightColour;
  }

  @Override
  public void render(GL2 gl) {
    gl.glPushMatrix();
    gl.glLightfv(lightID, GL2.GL_POSITION, position.toFloat(), 0);
    gl.glLightfv(lightID, GL2.GL_SPOT_DIRECTION, direction.toFloat(), 0);
    gl.glLightf(lightID, GL2.GL_SPOT_CUTOFF, lightCutoffAngle);

    gl.glLightfv(lightID, GL2.GL_DIFFUSE, lightColour.toFloat(), 0);
    gl.glLightfv(lightID, GL2.GL_SPECULAR, lightColour.toFloat(), 0);
    gl.glPopMatrix();
  }

}
