package serializers;

import geometry.Vertex;

import java.nio.ByteBuffer;

import com.esotericsoftware.kryo.serialize.SimpleSerializer;

public class VertexSerializer extends SimpleSerializer<Vertex>  {

  @Override
  public Vertex read(ByteBuffer buffer) {
    double x = buffer.getDouble();
    double y = buffer.getDouble();
    double z = buffer.getDouble();
    return new Vertex(x,y,z);
  }

  @Override
  public void write(ByteBuffer buffer, Vertex vertex) {
    buffer.putDouble(vertex.x);
    buffer.putDouble(vertex.y);
    buffer.putDouble(vertex.z);
  }

}
