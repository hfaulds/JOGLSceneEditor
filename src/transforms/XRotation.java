package transforms;

public class XRotation extends Rotation {

  public XRotation(double angle) {
    super(angle, "X");
  }

  public void reCalculate(double angle) {
    super.reCalculate(angle);
    double s = Math.sin(angle * Math.PI / 180);
    double c = Math.cos(angle * Math.PI / 180);
    tm[1][1] = c;
    tm[1][2] = -s;
    tm[2][1] = s;
    tm[2][2] = c;
  }
}
