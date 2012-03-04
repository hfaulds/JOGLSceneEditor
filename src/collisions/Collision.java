package collisions;



public class Collision {
  private enum COLLISION {
    PARTIAL, FULL
  }

  public static final COLLISION COLLISION_PARTIAL = COLLISION.PARTIAL;
  public static final COLLISION COLLISION_FULL = COLLISION.FULL;
  
  public final Collidable source;
  public final COLLISION type;

  public Collision(Collidable source, COLLISION type) {
    this.source = source;
    this.type = type;
  }
}
