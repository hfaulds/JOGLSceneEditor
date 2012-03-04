package transforms;

public class ZRotation extends Rotation {

  public ZRotation(double angle) {
    super(angle, "Z");
  }

  public void reCalculate(double angle) {
    super.reCalculate(angle);
    double s = Math.sin(angle * Math.PI / 180);
    double c = Math.cos(angle * Math.PI / 180);
    tm[0][0] = c;
    tm[0][1] = -s;
    tm[1][0] = s;
    tm[1][1] = c;
  }
}
