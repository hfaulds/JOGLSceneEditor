package transforms;

public class YRotation extends Rotation {

  public YRotation(double angle) {
    super(angle, "Y");
  }

  public void reCalculate(double angle) {
    super.reCalculate(angle);
    double s = Math.sin(angle * Math.PI / 180);
    double c = Math.cos(angle * Math.PI / 180);
    tm[0][0] = c;
    tm[0][2] = s;
    tm[2][0] = -s;
    tm[2][2] = c;
  }
}
