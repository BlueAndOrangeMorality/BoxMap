package de.ranagazoo.boxmap;

import static de.ranagazoo.boxmap.Config.CATEGORY_MONSTER;
import static de.ranagazoo.boxmap.Config.CATEGORY_MSENSOR;
import static de.ranagazoo.boxmap.Config.CATEGORY_PLAYER;
import static de.ranagazoo.boxmap.Config.MASK_MONSTER;
import static de.ranagazoo.boxmap.Config.MASK_MSENSOR;
import static de.ranagazoo.boxmap.Config.MASK_PLAYER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;

public class BoxEntityFactory2 implements Disposable
{
  public static final int BOXTYPE_PLAYER   = 1;
  public static final int BOXTYPE_ENEMY    = 2;
  public static final int BOXTYPE_OBSTACLE = 3;
  public static final int BOXTYPE_WAYPOINT = 4;
  
  private BoxMap boxMap;
  
  private BodyDef playerBodyDef;
  private BodyDef enemyBodyDef;
  private BodyDef obstacleBodyDef;
  private BodyDef waypointBodyDef;
  
  private FixtureDef playerFixtureDef;
  private FixtureDef enemyFixtureDef;
  private FixtureDef enemyFixtureDefSensor;
  private FixtureDef obstacleFixtureDef;
  private FixtureDef waypointFixtureDef;
  
  private PolygonShape playerPolygonShape;
  private PolygonShape enemyPolygonShape;
  private PolygonShape obstaclePolygonShape;
  private CircleShape circleShape;
  private CircleShape enemyCircleShape;

  public BoxEntityFactory2(BoxMap boxMap)
  {
    this.boxMap = boxMap;
    
    playerBodyDef = new BodyDef();
    enemyBodyDef = new BodyDef();
    obstacleBodyDef = new BodyDef();
    waypointBodyDef = new BodyDef();
    
    playerFixtureDef = new FixtureDef();
    enemyFixtureDef = new FixtureDef();
    enemyFixtureDefSensor = new FixtureDef();
    obstacleFixtureDef = new FixtureDef();
    waypointFixtureDef = new FixtureDef();
    
    playerPolygonShape = new PolygonShape();
    enemyPolygonShape = new PolygonShape();
    obstaclePolygonShape = new PolygonShape();
    circleShape = new CircleShape();
    enemyCircleShape = new CircleShape();
          
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
    
    playerPolygonShape.set(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f});
    playerFixtureDef.shape                = playerPolygonShape;
    
    
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
    
    enemyPolygonShape.set(new float[]{-0.25f, -0.25f, 0, -1, 0.25f, -0.25f, 0.25f, 0.25f, -0.25f, 0.25f});
    enemyFixtureDef.shape                = enemyPolygonShape;
    
    enemyFixtureDefSensor.density        = 0f;
    enemyFixtureDefSensor.friction       = 0.4f;
    enemyFixtureDefSensor.restitution    = 0.1f;
    enemyFixtureDefSensor.filter.categoryBits = CATEGORY_MSENSOR;
    enemyFixtureDefSensor.filter.maskBits = MASK_MSENSOR;
    enemyFixtureDefSensor.isSensor       = true;
        
    enemyCircleShape.setRadius(3);
    enemyFixtureDefSensor.shape          = enemyCircleShape;

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
    
    obstaclePolygonShape.set(new float[]{-2f, -1f, 2f, -1f, 2f, 1f, -2f, 1f});
    obstacleFixtureDef.shape = obstaclePolygonShape;
    
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
  
  //https://www.tutorialspoint.com/design_pattern/factory_pattern.htm
  
  public BoxEntity createEntity(MapProperties mapProperties)
  {
    String type = mapProperties.get("type", String.class);
    float x = mapProperties.get("x", Float.class);
    float y = mapProperties.get("y", Float.class);
    float width = mapProperties.get("width", Float.class);
    float height = mapProperties.get("height", Float.class);
    Float posX = (x + width/2.0f) / Config.TS;
    Float posY = (y + height/2.0f) / Config.TS;
    
    if ("player1".equals(type))
    {
      playerBodyDef.position.set(posX, posY);
      Body playerBody = boxMap.getWorld().createBody(playerBodyDef);
      playerBody.createFixture(playerFixtureDef);
      return new Player(playerBody, boxMap);
    }
    else if ("enemy1".equals(type))
    {
      enemyBodyDef.position.set(posX, posY);
      Body enemyBody = boxMap.getWorld().createBody(enemyBodyDef);
      enemyBody.createFixture(enemyFixtureDef);
      enemyBody.createFixture(enemyFixtureDefSensor);
      return new Enemy(enemyBody, boxMap);
    }
    else if ("obstacle".equals(type))
    {
      obstacleBodyDef.position.set(posX, posY);
      Body obstacleBody = boxMap.getWorld().createBody(obstacleBodyDef);
      obstacleBody.createFixture(obstacleFixtureDef);
      return new Obstacle(obstacleBody, boxMap);
    }
    else if ("waypoint".equals(type))
    {
      waypointBodyDef.position.set(posX, posY);
      Body waypointBody = boxMap.getWorld().createBody(waypointBodyDef);
      waypointBody.createFixture(waypointFixtureDef);
      return new Waypoint(waypointBody, boxMap);
    }
    
    Gdx.app.log("BoxEntityFactory2.createEntity:", "No type given!");    
    return null;
  }

  @Override
  public void dispose()
  {
    enemyPolygonShape.dispose();
    circleShape.dispose();
    
  }

}
