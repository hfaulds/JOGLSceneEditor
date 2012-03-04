package serializers;

import java.nio.ByteBuffer;

import renderers.core.ViewPortRenderer;

import com.esotericsoftware.kryo.serialize.SimpleSerializer;

public class CameraSerializer extends SimpleSerializer<ViewPortRenderer> {

  @Override
  public ViewPortRenderer read(ByteBuffer buffer) {
    float phi = buffer.getFloat();
    float theta = buffer.getFloat();
    float radius = buffer.getFloat();
    return new ViewPortRenderer(theta, phi, radius);
  }

  @Override
  public void write(ByteBuffer buffer, ViewPortRenderer camera) {
    buffer.putFloat(camera.getPhi());
    buffer.putFloat(camera.getTheta());
    buffer.putFloat(camera.getRadius());
  }

}
