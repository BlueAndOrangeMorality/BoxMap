package de.ranagazoo.boxmap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;


public class Obstacle implements BoxEntity
{
  private Body groundBody;
 
  public Obstacle(Body body)
  { 
    groundBody = body;
    groundBody.setUserData(this);
  }  
  
  //Obstacles are not rendered, they are drawn "behind" map parts
  public void render(SpriteBatch batch) {}


  @Override
  public void move() {}
 
}
