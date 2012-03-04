package serializers;

import geometry.Box;
import geometry.Vertex;

import java.nio.ByteBuffer;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serialize.SimpleSerializer;

public class OctSpaceSerializer extends SimpleSerializer<Box> {

  private Kryo kryo;
  
  public OctSpaceSerializer(Kryo kryo) {
    this.kryo = kryo;
  }

  @Override
  public Box read(ByteBuffer buffer) {
    Vertex center = kryo.readObject(buffer, Vertex.class);
    Double size = buffer.getDouble();
    return Box.createOctSpace(center, size);
  }

  @Override
  public void write(ByteBuffer buffer, Box space) {
    kryo.writeObject(buffer, space.position);
    buffer.putDouble(space.size);
  }

}
