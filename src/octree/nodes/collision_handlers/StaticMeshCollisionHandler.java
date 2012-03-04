package octree.nodes.collision_handlers;

import octree.nodes.StaticMesh;

public class StaticMeshCollisionHandler extends PlaceableCollisionHandler {
  public StaticMeshCollisionHandler(StaticMesh subject) {
    super(subject);
  }
}
