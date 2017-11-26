package de.ranagazoo.boxmap;

import static de.ranagazoo.boxmap.Config.TS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Player implements BoxEntity
{
  private Body playerBody;
  private Sprite playerSprite;

  public Player(Body body, BoxMap boxMap)
  {
    this.playerBody = body;
    this.playerBody.setUserData(this);
//    float x = mapProperties.get("x", Float.class);
//    float y = mapProperties.get("y", Float.class);
//    float width = mapProperties.get("width", Float.class);
//    float height = mapProperties.get("height", Float.class);
//    
//    Float posX = (x + width/2.0f) / Config.TS;
//    Float posY = (y + height/2.0f) / Config.TS;
//    
////    playerBody = box2dMovement.getWorld().createBody(box2dMovement.getBoxEntityFactory().getBodyDef(posX, posY));
//    playerBody = box2dMovement.getWorld().createBody(box2dMovement.getBoxEntityFactory().getBodyDef(BoxEntityFactory2.BOXTYPE_PLAYER, posX, posY));
////    playerBody.createFixture(box2dMovement.getBoxEntityFactory().getFixtureDef());
//    playerBody.createFixture(box2dMovement.getBoxEntityFactory().getFixtureDef(BoxEntityFactory2.BOXTYPE_PLAYER));
//    playerBody.setUserData("userData");
//    
    
    playerSprite = new Sprite(boxMap.getEntityPlayerRegion());
    playerSprite.setSize(TS, TS);
    playerSprite.setOrigin(TS / 2, TS / 2);
  }
//  public Player(BoxMap box2dMovement, MapProperties mapProperties)
//  {
//    float x = mapProperties.get("x", Float.class);
//    float y = mapProperties.get("y", Float.class);
//    float width = mapProperties.get("width", Float.class);
//    float height = mapProperties.get("height", Float.class);
//    
//    Float posX = (x + width/2.0f) / Config.TS;
//    Float posY = (y + height/2.0f) / Config.TS;
//    
////    playerBody = box2dMovement.getWorld().createBody(box2dMovement.getBoxEntityFactory().getBodyDef(posX, posY));
//    playerBody = box2dMovement.getWorld().createBody(box2dMovement.getBoxEntityFactory().getBodyDef(BoxEntityFactory2.BOXTYPE_PLAYER, posX, posY));
////    playerBody.createFixture(box2dMovement.getBoxEntityFactory().getFixtureDef());
//    playerBody.createFixture(box2dMovement.getBoxEntityFactory().getFixtureDef(BoxEntityFactory2.BOXTYPE_PLAYER));
//    playerBody.setUserData("userData");
//    
//    
//    playerSprite = new Sprite(box2dMovement.getEntityPlayerRegion());
//    playerSprite.setSize(TS, TS);
//    playerSprite.setOrigin(TS / 2, TS / 2);
//  }

  
  

  public void move()
  {
    if (Gdx.input.isKeyPressed(Keys.A))
      playerBody.applyLinearImpulse(new Vector2(-1.2f, 0), playerBody.getPosition(), true);
    if (Gdx.input.isKeyPressed(Keys.D))
      playerBody.applyLinearImpulse(new Vector2(1.2f, 0), playerBody.getPosition(), true);
    if (Gdx.input.isKeyPressed(Keys.W))
      playerBody.applyLinearImpulse(new Vector2(0, 1.2f), playerBody.getPosition(), true);
    if (Gdx.input.isKeyPressed(Keys.S))
      playerBody.applyLinearImpulse(new Vector2(0, -1.2f), playerBody.getPosition(), true);
  }

  public void render(SpriteBatch batch)
  {
    playerSprite.setPosition((playerBody.getPosition().x - 0.5f) * TS, (playerBody.getPosition().y - 0.5f) * TS);
    playerSprite.setRotation(MathUtils.radiansToDegrees * playerBody.getAngle());
    playerSprite.draw(batch);
  }

  public Body getBody()
  {
    return this.playerBody;
  }
}
