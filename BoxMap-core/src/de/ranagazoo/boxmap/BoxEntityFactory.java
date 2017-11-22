package de.ranagazoo.boxmap;

import static de.ranagazoo.boxmap.Config.CATEGORY_MONSTER;
import static de.ranagazoo.boxmap.Config.CATEGORY_MSENSOR;
import static de.ranagazoo.boxmap.Config.CATEGORY_PLAYER;
import static de.ranagazoo.boxmap.Config.MASK_MONSTER;
import static de.ranagazoo.boxmap.Config.MASK_MSENSOR;
import static de.ranagazoo.boxmap.Config.MASK_PLAYER;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class BoxEntityFactory
{
  public static final int BOXTYPE_PLAYER   = 1;
  public static final int BOXTYPE_ENEMY    = 2;
  public static final int BOXTYPE_OBSTACLE = 3;
  public static final int BOXTYPE_WAYPOINT = 4;
  
  private BodyDef bodyDef;
  private FixtureDef fixtureDef;
  private FixtureDef fixtureDefSensor;
  private PolygonShape polygonShape;
  private CircleShape circleShape;

  public BoxEntityFactory()
  {
    bodyDef = new BodyDef();
    fixtureDef = new FixtureDef();
    fixtureDefSensor = new FixtureDef();
    polygonShape = new PolygonShape();
    circleShape = new CircleShape();
  }

  public void defineBodyDef(int entityType)
  {
    switch (entityType) {
      case BOXTYPE_PLAYER:
        bodyDef.angularDamping          = 5f;
        bodyDef.fixedRotation           = false;
        bodyDef.linearDamping           = 5f;
        bodyDef.type                    = BodyType.DynamicBody;
        
        fixtureDef.density              = 0.5f;
        fixtureDef.friction             = 0.4f;
        fixtureDef.restitution          = 0.1f;
        fixtureDef.filter.categoryBits  = CATEGORY_PLAYER;
        fixtureDef.filter.maskBits      = MASK_PLAYER;
        fixtureDefSensor.isSensor       = false;
        
        polygonShape.set(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f});
        fixtureDef.shape                = polygonShape;
        break;
        
      case BOXTYPE_ENEMY:
        bodyDef.angularDamping          = 2;
        bodyDef.fixedRotation           = false;
        bodyDef.linearDamping           = 2;
        bodyDef.type                    = BodyType.DynamicBody;
        
        fixtureDef.density              = 0.2f;
        fixtureDef.friction             = 0.4f;
        fixtureDef.restitution          = 0.1f;
        fixtureDef.filter.categoryBits  = CATEGORY_MONSTER;
        fixtureDef.filter.maskBits      = MASK_MONSTER;
        fixtureDefSensor.isSensor       = false;
        
        polygonShape.set(new float[]{-0.25f, -0.25f, 0, -1, 0.25f, -0.25f, 0.25f, 0.25f, -0.25f, 0.25f});
        fixtureDef.shape                = polygonShape;
        
        fixtureDefSensor.density        = 0f;
        fixtureDefSensor.friction       = 0.4f;
        fixtureDefSensor.restitution    = 0.1f;
        fixtureDefSensor.filter.categoryBits = CATEGORY_MSENSOR;
        fixtureDefSensor.filter.maskBits = MASK_MSENSOR;
        fixtureDefSensor.isSensor       = true;

        circleShape.setRadius(3);
        fixtureDefSensor.shape          = circleShape;
        break;
        
      case BOXTYPE_OBSTACLE:
        bodyDef.angularDamping          = 2f;
        bodyDef.fixedRotation           = false;
        bodyDef.linearDamping           = 2f;
        bodyDef.type                    = BodyType.StaticBody;
        
        fixtureDef.density              = 0.5f;
        fixtureDef.friction             = 0.4f;
        fixtureDef.restitution          = 0.1f;
        fixtureDef.filter.categoryBits  = Config.CATEGORY_SCENERY;
        fixtureDef.filter.maskBits      = Config.MASK_SCENERY;
        fixtureDefSensor.isSensor       = false;
        
        polygonShape.set(new float[]{-2f, -1f, 2f, -1f, 2f, 1f, -2f, 1f});
        fixtureDef.shape = polygonShape;
        break;
        
      case BOXTYPE_WAYPOINT:
        bodyDef.angularDamping          = 2f;
        bodyDef.fixedRotation           = false;
        bodyDef.linearDamping           = 2f;
        bodyDef.type                    = BodyType.StaticBody;
        
        fixtureDef.density              = 0.2f;
        fixtureDef.friction             = 0.4f;
        fixtureDef.restitution          = 0.1f;
        fixtureDef.isSensor             = true;
        fixtureDef.filter.categoryBits  = Config.CATEGORY_WAYPOINT;
        fixtureDef.filter.maskBits      = Config.MASK_WAYPOINT;
    
        circleShape.setRadius(0.5f);
        fixtureDef.shape = circleShape;
        break;
    }
    
    
  }
  
  public BodyDef getBodyDef(float posX, float posY)
  {
    bodyDef.position.set(posX, posY);
    return bodyDef;
  }
  
  public FixtureDef getFixtureDef()
  {
    return fixtureDef;
  }

  public FixtureDef getFixtureDefSensor()
  {
    return fixtureDefSensor;
  }
  
  public PolygonShape getPolygonShape()
  {
    return polygonShape;
  }

  public CircleShape getCircleShape()
  {
    return circleShape;
  }

  public void cleanUp()
  {
    polygonShape.dispose();
    circleShape.dispose();
  }

}
