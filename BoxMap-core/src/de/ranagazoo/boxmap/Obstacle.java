package de.ranagazoo.boxmap;

import static de.ranagazoo.boxmap.Config.TS;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;


public class Obstacle implements BoxEntity
{
  private Sprite bottomSprite; 
  private Body groundBody;
 
  public Obstacle(BoxMap box2dMovement, float posX, float posY, TextureRegion region)
  { 
    groundBody = box2dMovement.getWorld().createBody(box2dMovement.getBoxEntityFactory().getBodyDef(posX, posY));
    groundBody.createFixture(box2dMovement.getBoxEntityFactory().getFixtureDef());
    groundBody.setUserData("userData");
    
    bottomSprite = new Sprite(region);
    bottomSprite.setSize(4f*TS, 2f*TS);
    bottomSprite.setOrigin(TS/2, TS/2);
  }
  
  
  public void render(SpriteBatch batch)
  {
    bottomSprite.setPosition((groundBody.getPosition().x - 2f) * TS, (groundBody.getPosition().y - 1f) * TS);
    bottomSprite.draw(batch);     
  }


  @Override
  public void move() {}
 
}
