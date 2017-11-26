package de.ranagazoo.boxmap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Waypoint implements BoxEntity
{
  private Body waypointBody;

  public Waypoint(Body body, BoxMap box2dMovement)
  {
    waypointBody = body;
    waypointBody.setUserData(box2dMovement.getWaypoints().size());
  }
//  public Waypoint(BoxMap box2dMovement, MapProperties mapProperties)
//  {
//    float x = mapProperties.get("x", Float.class);
//    float y = mapProperties.get("y", Float.class);
//    float width = mapProperties.get("width", Float.class);
//    float height = mapProperties.get("height", Float.class);
//    
//    float posX = (x + width/2.0f) / Config.TS;
//    float posY = (y + height/2.0f) / Config.TS;
//    
//    waypointBody = box2dMovement.getWorld().createBody(box2dMovement.getBoxEntityFactory().getBodyDef(posX, posY));
//    waypointBody.createFixture(box2dMovement.getBoxEntityFactory().getFixtureDef());
//    waypointBody.setUserData(box2dMovement.getWaypoints().size());
//  }
  
  public Vector2 getPosition()
  {
    return waypointBody.getPosition();
  }

  @Override
  public void move()
  {
  }

  @Override
  public void render(SpriteBatch batch)
  {
  }
}
