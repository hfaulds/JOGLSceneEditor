import static org.junit.Assert.assertTrue;
import geometry.Box;
import geometry.Vertex;
import geometry.mesh.Mesh;
import geometry.mesh.MeshLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import octree.OctTree;
import octree.factories.VertexOctreeFactory;

import org.junit.Test;

import renderers.core.ViewPortRenderer;
import serializers.CameraSerializer;
import serializers.OctSpaceSerializer;
import serializers.VertexSerializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;


public class TreeSerializationTest {

  @Test public void WriteAndReadTree() throws FileNotFoundException {    
    Kryo kryo = new Kryo();
    kryo.register(ViewPortRenderer.class, new CameraSerializer());
    kryo.register(Vertex.class, new VertexSerializer());
    //kryo.register(OctLeaf.class, new OctLeafSerializer(kryo));
    //kryo.register(OctBranch.class, new OctTreeSerializer(kryo));
    //kryo.register(NullLeaf.class, new NullLeafSerializer(kryo));
    kryo.register(Box.class, new OctSpaceSerializer(kryo));
   
    File meshFile = new File("assets/meshes/mushroom.obj");
    Mesh mesh = MeshLoader.loadOBJ(meshFile);

    VertexOctreeFactory factory = new VertexOctreeFactory();
    OctTree tree1 = factory.createOctree(mesh.vertices.clone());

    String filename = "assets/scenes/TreeSerializationTest.scene";
    
    ObjectBuffer objectBuffer = new ObjectBuffer(kryo, 64 * 1024);
    objectBuffer.writeObject(new FileOutputStream(filename), tree1);
    
    OctTree tree2 = objectBuffer.readObject(new FileInputStream(filename), OctTree.class);

    boolean equals = tree1.equals(tree2);
    assertTrue(equals);
  }

}
