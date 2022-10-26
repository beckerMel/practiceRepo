/**
 * This class represents a simple projectile, which implements the Particle interface.
 * Each SimpleProjectile has an x and y coordinate that represent its initial horizontal
 * and vertical positions respectively, an initial horizontal velocity, and an initial vertical
 * velocity.
 */
public class SimpleProjectile implements Particle {
  private final float gravity = 9.81f;
  private float initPosX;
  private float initPosY;
  private float initVelX;
  private float initVelY;

  /**
   * Construct a simpleProjectile object that has the provided initial x and y coordinates
   * as its starting position, and the provided values for its initial horizontal and vertical
   * velocities, respectively.
   *
   * @param initPosX  the x coordinate of the simple projectile's initial position.
   * @param initPosY the y coordinate of the simple projectile's initial position.
   * @param initVelX the simple projectile's initial horizontal velocity.
   * @param initVelY the simple projectile's initial vertical velocity.
   */
  public SimpleProjectile(float initPosX, float initPosY, float initVelX, float initVelY) {
    this.initPosX = initPosX;
    this.initPosY = initPosY;
    this.initVelX = initVelX;
    this.initVelY = initVelY;
  }

  @Override
  public String getState(float time) {
    float posX = initPosX;
    float posY = initPosY;

    // if time <= 0, the position doesn't change; no calculations needed
    if (time > 0) {

      // if the projectile has no vertical velocity, it will never stray from initial vertical
      // position and we only care about horizontal displacement
      if (initVelY == 0) {
        posX = (float) (posX + initVelX * time);
      } else {
        // stopTime: time beyond which particle returns to initial vertical position
        // and stops moving entirely; derived from final velocity equation
        float stopTime = 2 * (initVelY / gravity);
        if (time < stopTime) {
          posX = (float) (posX + initVelX * time);

          // multiply by -1 because gravity is a downward force and therefore treated as negative
          posY = (float) (posY + initVelY * time + 0.5 * (-1) * gravity * Math.pow(time, 2));
        } else {
          // no need to calculate posY as projectile has returned to initial vertical
          // position
          posX = (float) (posX + initVelX * stopTime);
        }
      }
    }

    String soln = String.format("At time %1.2f: position is (%2.2f,%3.2f)", time, posX, posY);
    return soln;
  }
}