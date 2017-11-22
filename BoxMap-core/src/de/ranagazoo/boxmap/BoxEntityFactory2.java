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

public class BoxEntityFactory2
{
  public static final int BOXTYPE_PLAYER   = 1;
  public static final int BOXTYPE_ENEMY    = 2;
  public static final int BOXTYPE_OBSTACLE = 3;
  public static final int BOXTYPE_WAYPOINT = 4;
  
  private BodyDef playerBodyDef;
  private BodyDef enemyBodyDef;
  private BodyDef obstacleBodyDef;
  private BodyDef waypointBodyDef;
  
  private FixtureDef playerFixtureDef;
  private FixtureDef enemyFixtureDef;
  private FixtureDef enemyFixtureDefSensor;
  private FixtureDef obstacleFixtureDef;
  private FixtureDef waypointFixtureDef;
  
  private PolygonShape polygonShape;
  private CircleShape circleShape;

  public BoxEntityFactory2()
  {
    playerBodyDef = new BodyDef();
    enemyBodyDef = new BodyDef();
    obstacleBodyDef = new BodyDef();
    waypointBodyDef = new BodyDef();
    
    playerFixtureDef = new FixtureDef();
    enemyFixtureDef = new FixtureDef();
    enemyFixtureDefSensor = new FixtureDef();
    obstacleFixtureDef = new FixtureDef();
    waypointFixtureDef = new FixtureDef();
    
    polygonShape = new PolygonShape();
    circleShape = new CircleShape();
  }

  public void defineBodyEntities()
  {
        
    //Player
    playerBodyDef.angularDamping          = 5f;
    playerBodyDef.fixedRotation           = false;
    playerBodyDef.linearDamping           = 5f;
    playerBodyDef.type                    = BodyType.DynamicBody;
    
    playerFixtureDef.density              = 0.5f;
    playerFixtureDef.friction             = 0.4f;
    playerFixtureDef.restitution          = 0.1f;
    playerFixtureDef.filter.categoryBits  = CATEGORY_PLAYER;
    playerFixtureDef.filter.maskBits      = MASK_PLAYER;
    playerFixtureDef.isSensor       = false;
    
    polygonShape.set(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f});
    playerFixtureDef.shape                = polygonShape;
    
    
    //Enemy
    
    enemyBodyDef.angularDamping          = 2;
    enemyBodyDef.fixedRotation           = false;
    enemyBodyDef.linearDamping           = 2;
    enemyBodyDef.type                    = BodyType.DynamicBody;
    
    enemyFixtureDef.density              = 0.2f;
    enemyFixtureDef.friction             = 0.4f;
    enemyFixtureDef.restitution          = 0.1f;
    enemyFixtureDef.filter.categoryBits  = CATEGORY_MONSTER;
    enemyFixtureDef.filter.maskBits      = MASK_MONSTER;
    enemyFixtureDef.isSensor       = false;
    
    polygonShape.set(new float[]{-0.25f, -0.25f, 0, -1, 0.25f, -0.25f, 0.25f, 0.25f, -0.25f, 0.25f});
    playerFixtureDef.shape                = polygonShape;
    
    enemyFixtureDefSensor.density        = 0f;
    enemyFixtureDefSensor.friction       = 0.4f;
    enemyFixtureDefSensor.restitution    = 0.1f;
    enemyFixtureDefSensor.filter.categoryBits = CATEGORY_MSENSOR;
    enemyFixtureDefSensor.filter.maskBits = MASK_MSENSOR;
    enemyFixtureDefSensor.isSensor       = true;
        
    circleShape.setRadius(3);
    enemyFixtureDefSensor.shape          = circleShape;

    //Obstacle
    obstacleBodyDef.angularDamping          = 2f;
    obstacleBodyDef.fixedRotation           = false;
    obstacleBodyDef.linearDamping           = 2f;
    obstacleBodyDef.type                    = BodyType.StaticBody;
    
    obstacleFixtureDef.density              = 0.5f;
    obstacleFixtureDef.friction             = 0.4f;
    obstacleFixtureDef.restitution          = 0.1f;
    obstacleFixtureDef.filter.categoryBits  = Config.CATEGORY_SCENERY;
    obstacleFixtureDef.filter.maskBits      = Config.MASK_SCENERY;
    obstacleFixtureDef.isSensor       = false;
    
    polygonShape.set(new float[]{-2f, -1f, 2f, -1f, 2f, 1f, -2f, 1f});
    obstacleFixtureDef.shape = polygonShape;
    
    //Waypoint
    waypointBodyDef.angularDamping          = 2f;
    waypointBodyDef.fixedRotation           = false;
    waypointBodyDef.linearDamping           = 2f;
    waypointBodyDef.type                    = BodyType.StaticBody;
    
    waypointFixtureDef.density              = 0.2f;
    waypointFixtureDef.friction             = 0.4f;
    waypointFixtureDef.restitution          = 0.1f;
    waypointFixtureDef.isSensor             = true;
    waypointFixtureDef.filter.categoryBits  = Config.CATEGORY_WAYPOINT;
    waypointFixtureDef.filter.maskBits      = Config.MASK_WAYPOINT;
    
    circleShape.setRadius(0.5f);
    waypointFixtureDef.shape = circleShape;
        
     
    
    
  }
  
  public BodyDef getBodyDef(float posX, float posY)
  {
    playerBodyDef.position.set(posX, posY);
    return playerBodyDef;
  }
  
  
  
  TODO
  Typ abfragen beim holen. Oder aus den MapProperties?
      Prüfen, ob das so mit den Shapes geht.
  public FixtureDef getFixtureDef(int boxtype)
  {
    return playerFixtureDef;
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
