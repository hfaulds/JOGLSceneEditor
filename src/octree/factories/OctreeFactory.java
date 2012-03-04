package octree.factories;

import geometry.Box;
import geometry.Vertex;

import java.util.Arrays;
import java.util.Vector;

import octree.OctTree;
import octree.collisions.CollisionDetails;
import octree.nodes.LeafTarget;

public abstract class OctreeFactory<NodeType extends LeafTarget> {

  protected abstract double calcSize(Vector<NodeType> elements, Vertex center);
  protected abstract Vertex calcCenterPoint(Vector<NodeType> elements);
  
  public OctTree createOctree(NodeType[] meshes) {
    return createOctree(new Vector<NodeType>(Arrays.asList(meshes)));
  }

  public OctTree createOctree(Vector<NodeType> meshes) {
    Vertex center = calcCenterPoint(meshes);
    double size = calcSize(meshes, center);

    Box rootSpace = Box.createOctSpace(center, size);
    OctTree root = createOctTree(rootSpace, meshes);

    return root;
  }
  
  @SuppressWarnings("unchecked")
  private OctTree createOctTree(Box space, Vector<NodeType> parentElements) {
    Vector<NodeType> elements = new Vector<NodeType>();
    
    for (NodeType element : parentElements) {
      for (CollisionDetails collision : element.collide(space, 0.1)) {
        if (collision.source instanceof LeafTarget && 
            collision.type == CollisionDetails.COLLISION_FULL) {
          elements.add((NodeType) collision.source);
        }
      }
    }
    
    parentElements.removeAll(elements);

    if (elements.size() > OctTree.MAX_ELEMENTS) {
      OctTree[] subTrees = new OctTree[8];
      Box[] subSpaces = generateSubSpaces(space);

      for (int i = 0; i < 8; i++) {
        subTrees[i] = createOctTree(subSpaces[i], elements);
      }
      
      return new OctTree(space, subTrees, (Vector<LeafTarget>) elements);
    }
    else {
      return new OctTree(space, (Vector<LeafTarget>) elements);
    }
  }
  
  private Box[] generateSubSpaces(Box space) {
    Box[] spaces = new Box[8];
    
    double size = space.size / 2.0;
    int count = 0;
    
    for (int x = -1; x < 2; x += 2) {
      for (int y = -1; y < 2; y += 2) {
        for (int z = -1; z < 2; z += 2) {
          Vertex center = new Vertex(size * x, size * y, size * z).add(space.position);
          spaces[count++] = Box.createOctSpace(center, size);
        }
      }
    }

    return spaces;
  }
}
