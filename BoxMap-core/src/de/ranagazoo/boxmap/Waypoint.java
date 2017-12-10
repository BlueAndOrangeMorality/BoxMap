package de.ranagazoo.boxmap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Waypoint
{
  private Body waypointBody;

  public Waypoint(Body body, BoxMap box2dMovement)
  {
    waypointBody = body;
    waypointBody.setUserData(this);
  }
  
  public Vector2 getPosition()
  {
    return waypointBody.getPosition();
  }
}
