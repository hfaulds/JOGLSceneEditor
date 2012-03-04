package transforms;

public abstract class Rotation extends Transform {
  private double angle;
  private String axis;

  public Rotation(double angle, String axis) {
    this.axis = axis;
    reCalculate(angle);
  }

  public double getAngle() {
    return angle;
  }

  public String getAxis() {
    return axis;
  }

  public void reCalculate(double angle) {
    this.angle = angle;
  }
}
