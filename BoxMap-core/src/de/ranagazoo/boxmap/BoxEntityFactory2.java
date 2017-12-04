package de.ranagazoo.boxmap;

import static de.ranagazoo.boxmap.Config.CATEGORY_MONSTER;
import static de.ranagazoo.boxmap.Config.CATEGORY_MSENSOR;
import static de.ranagazoo.boxmap.Config.CATEGORY_PLAYER;
import static de.ranagazoo.boxmap.Config.MASK_MONSTER;
import static de.ranagazoo.boxmap.Config.MASK_MSENSOR;
import static de.ranagazoo.boxmap.Config.MASK_PLAYER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
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
    
    //Player
    playerBodyDef = new BodyDef();
    playerBodyDef.angularDamping          = 5f;
    playerBodyDef.fixedRotation           = false;
    playerBodyDef.linearDamping           = 5f;
    playerBodyDef.type                    = BodyType.DynamicBody;
    
    playerFixtureDef = new FixtureDef();
    playerFixtureDef.density              = 0.5f;
    playerFixtureDef.friction             = 0.4f;
    playerFixtureDef.restitution          = 0.1f;
    playerFixtureDef.filter.categoryBits  = CATEGORY_PLAYER;
    playerFixtureDef.filter.maskBits      = MASK_PLAYER;
    playerFixtureDef.isSensor             = false;
    
    //Enemy   
    enemyBodyDef = new BodyDef();
    enemyBodyDef.angularDamping          = 2;
    enemyBodyDef.fixedRotation           = false;
    enemyBodyDef.linearDamping           = 2;
    enemyBodyDef.type                    = BodyType.DynamicBody;
    
    enemyFixtureDef = new FixtureDef();
    enemyFixtureDef.density              = 0.2f;
    enemyFixtureDef.friction             = 0.4f;
    enemyFixtureDef.restitution          = 0.1f;
    enemyFixtureDef.filter.categoryBits  = CATEGORY_MONSTER;
    enemyFixtureDef.filter.maskBits      = MASK_MONSTER;
    enemyFixtureDef.isSensor             = false;
    
    enemyFixtureDefSensor = new FixtureDef();
    enemyFixtureDefSensor.density        = 0f;
    enemyFixtureDefSensor.friction       = 0.4f;
    enemyFixtureDefSensor.restitution    = 0.1f;
    enemyFixtureDefSensor.filter.categoryBits = CATEGORY_MSENSOR;
    enemyFixtureDefSensor.filter.maskBits = MASK_MSENSOR;
    enemyFixtureDefSensor.isSensor       = true;
        
    
    //Obstacle
    obstacleBodyDef = new BodyDef();
    obstacleBodyDef.angularDamping          = 2f;
    obstacleBodyDef.fixedRotation           = false;
    obstacleBodyDef.linearDamping           = 2f;
    obstacleBodyDef.type                    = BodyType.StaticBody;
    
    obstacleFixtureDef = new FixtureDef();
    obstacleFixtureDef.density              = 0.5f;
    obstacleFixtureDef.friction             = 0.4f;
    obstacleFixtureDef.restitution          = 0.1f;
    obstacleFixtureDef.filter.categoryBits  = Config.CATEGORY_SCENERY;
    obstacleFixtureDef.filter.maskBits      = Config.MASK_SCENERY;
    obstacleFixtureDef.isSensor             = false;
    
    //Waypoint
    waypointBodyDef = new BodyDef();
    waypointBodyDef.angularDamping          = 2f;
    waypointBodyDef.fixedRotation           = false;
    waypointBodyDef.linearDamping           = 2f;
    waypointBodyDef.type                    = BodyType.StaticBody;
    
    waypointFixtureDef = new FixtureDef();
    waypointFixtureDef.density              = 0.2f;
    waypointFixtureDef.friction             = 0.4f;
    waypointFixtureDef.restitution          = 0.1f;
    waypointFixtureDef.isSensor             = true;
    waypointFixtureDef.filter.categoryBits  = Config.CATEGORY_WAYPOINT;
    waypointFixtureDef.filter.maskBits      = Config.MASK_WAYPOINT;

    
    playerPolygonShape = new PolygonShape();
    playerPolygonShape.set(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f});
    playerFixtureDef.shape = playerPolygonShape;
    
    enemyCircleShape = new CircleShape();
    enemyCircleShape.setRadius(3);
    enemyFixtureDefSensor.shape = enemyCircleShape;
    
    enemyPolygonShape = new PolygonShape();
    enemyPolygonShape.set(new float[]{-0.25f, -0.25f, 0, -1, 0.25f, -0.25f, 0.25f, 0.25f, -0.25f, 0.25f});
    enemyFixtureDef.shape = enemyPolygonShape;
    
    obstaclePolygonShape = new PolygonShape();
    obstaclePolygonShape.set(new float[]{-2f, -1f, 2f, -1f, 2f, 1f, -2f, 1f});
    obstacleFixtureDef.shape = obstaclePolygonShape;
    
    circleShape = new CircleShape();
    circleShape.setRadius(0.5f);
    waypointFixtureDef.shape = circleShape;
  }
  
  //https://www.tutorialspoint.com/design_pattern/factory_pattern.htm
  
  public BoxEntity createEntity(MapObject mapObject)
  {
    MapProperties mapProperties = mapObject.getProperties();
    String type = mapProperties.get("type", String.class);

    getShapeFromMapObject(mapObject);
    
    
    
    if ("player1".equals(type))
    {
      playerBodyDef.position.set(getPositionFromMapObject(mapProperties));
      Body playerBody = boxMap.getWorld().createBody(playerBodyDef);
      playerBody.createFixture(playerFixtureDef);
      return new Player(playerBody, boxMap);
    }
    else if ("enemy1".equals(type))
    {
      enemyBodyDef.position.set(getPositionFromMapObject(mapProperties));
      Body enemyBody = boxMap.getWorld().createBody(enemyBodyDef);
      enemyBody.createFixture(enemyFixtureDef);
      enemyBody.createFixture(enemyFixtureDefSensor);
      return new Enemy(enemyBody, boxMap);
    }
    else if ("obstacle".equals(type))
    {
      obstacleBodyDef.position.set(getPositionFromMapObject(mapProperties));
      Body obstacleBody = boxMap.getWorld().createBody(obstacleBodyDef);
      
//      if(mapObject.getClass().equals(PolygonMapObject.class))
//        obstacleFixtureDef.shape = getShapeFromMapObject(mapObject);
//      if(mapObject.getClass().equals(RectangleMapObject.class))
        obstacleFixtureDef.shape = getShapeFromMapObject(mapObject);
        
        

      
      obstacleBody.createFixture(obstacleFixtureDef);      
      return new Obstacle(obstacleBody, boxMap);
    }
    else if ("waypoint".equals(type))
    {
      waypointBodyDef.position.set(getPositionFromMapObject(mapProperties));
      Body waypointBody = boxMap.getWorld().createBody(waypointBodyDef);
      waypointBody.createFixture(waypointFixtureDef);
      return new Waypoint(waypointBody, boxMap);
    }
    
    Gdx.app.log("BoxEntityFactory2.createEntity:", "No type given!");    
    return null;
  }
  
  private Vector2 getPositionFromMapObject(MapProperties mapProperties)
  {
    float x = mapProperties.get("x", Float.class);
    float y = mapProperties.get("y", Float.class);
    float width = mapProperties.get("width", Float.class);
    float height = mapProperties.get("height", Float.class);
    Float posX = (x + width/2.0f) / Config.TS;
    Float posY = (y + height/2.0f) / Config.TS;
    return new Vector2(posX, posY);
  }
  
  
  
  public Shape getShapeFromMapObject(MapObject mapObject)
  {
    
//    Gdx.app.log("BoxEntityFactory 216:  mapObject.class: ", ""+mapObject.getClass());
    if (mapObject.getClass().equals(TiledMapTileMapObject.class))
    {
      //Gdx.app.log("BoxEntityFactory 230:  Warum bin ich hier?: ", "");
      return null;
    }
    else if (mapObject.getClass().equals(RectangleMapObject.class))
    {
      Gdx.app.log("BoxEntityFactory 228:  ich bin im Rectangledings: ", ""+mapObject.getClass());
      PolygonShape polygonShape = new PolygonShape();
      RectangleMapObject rectangleMapObject = (RectangleMapObject)mapObject;
      Rectangle rectangle = rectangleMapObject.getRectangle();
      rectangle.setPosition(0, 0);

      float hx = (rectangle.x + rectangle.width / 2f) / 32f;
      float hy = (rectangle.y + rectangle.height / 2f) / 32f;
      
      polygonShape.setAsBox(hx, hy);
      return polygonShape;
    }
    else if(mapObject.getClass().equals(PolygonMapObject.class))
    {
      PolygonShape polygonShape = new PolygonShape();
      PolygonMapObject polygonMapObject = (PolygonMapObject)mapObject;
      Polygon polygon = polygonMapObject.getPolygon();
      polygon.setPosition(0, 0);
      polygon.setScale(1f/32f, 1f/32f);
      polygonShape.set(polygon.getTransformedVertices());
      
      return polygonShape;
    }
    Gdx.app.log("BoxEntityFactory 230:  UND Warum bin ich hier?: ", "");
    
    return null;
  }

  @Override
  public void dispose()
  {
    enemyPolygonShape.dispose();
    circleShape.dispose();
    
  }

}
