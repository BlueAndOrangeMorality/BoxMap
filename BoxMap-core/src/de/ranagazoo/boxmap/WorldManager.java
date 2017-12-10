package de.ranagazoo.boxmap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class WorldManager implements Disposable
{
  private World world;
  private Box2DDebugRenderer debugRenderer;
  private BoxEntityFactory3 boxEntityFactory;
  
  
  public WorldManager()
  {
    world = new World(new Vector2(0, 0), true);
    world.setContactListener(new BoxMapContactListener());
    debugRenderer = new Box2DDebugRenderer();
    boxEntityFactory = new BoxEntityFactory3();
    
  }
  
  public void loadEntities(TiledMap tiledMap)
  {
    
//    
//    Hier werden die ganzen entities mit world.clearForces();.WorldManager   erzeugt
//    am Besten werden hier die Entities auch schon eingeteilt und dann an das Spiel zurückgeliefert, damit beim rendern da drüberiteriert wird.
//    für render und move
//    
//    quasi den teil hier aus boxmap richtig durchlaufen lassen
//  
//    tiledMap.getLayers().get(1).getObjects().getByType(PolygonMapObject.class);
//    tiledMap.getLayers().get(1).getObjects().getByType(PolylineMapObject.class);
//    tiledMap.getLayers().get(1).getObjects().getByType(RectangleMapObject.class);
//    tiledMap.getLayers().get(1).getObjects().getByType(TiledMapTileMapObject.class);
//    
    
    
    for (MapObject mapObject : tiledMap.getLayers().get(1).getObjects())
    {
      String type = boxEntityFactory.getTypeFromMapObject(mapObject);
      Vector2 pos = boxEntityFactory.getPositionFromMapObject(mapObject);

      Shape shape = boxEntityFactory.getShapeFromMapObject(mapObject);
      
      //Sinnvoll ist ggf., immer nur ein Shape pro Objekt anzunehmen
      //und per Parameter in der Klasse einfach nur den sensorenradius anzugeben.
      //Das ist zumindestens aktuell sinnvoll, solange entities nur ein fixture haben
      
      //---->Das if/else kann entfallen
      
      BodyDef bodyDef = boxEntityFactory.getBodyDefFromMapObject(type);
      FixtureDef fixtureDef = boxEntityFactory.getFixtureDefFromMapObject(type);
         
      bodyDef.position.set(pos);
      Body body = world.createBody(bodyDef);
      body.createFixture(fixtureDef);
      
      
      this.add.type.Entity(playerBody);
        
        
        
//      else if (type.equals(Config.TYPE_ENEMY1))
//      {
//        enemyBodyDef.position.set(getPositionFromMapObject(mapProperties));
//        Body enemyBody = world.createBody(enemyBodyDef);
//        enemyBody.createFixture(enemyFixtureDef);
//        enemyBody.createFixture(enemyFixtureDefSensor);
//        return new Enemy(enemyBody, boxMap);
//      }
//      else if (type.equals(Config.TYPE_OBSTACLE))
//      {
//        obstacleBodyDef.position.set(getPositionFromMapObject(mapProperties));
//        Body obstacleBody = world.createBody(obstacleBodyDef);
//        obstacleFixtureDef.shape = getShapeFromMapObject(mapObject);
//        obstacleBody.createFixture(obstacleFixtureDef);      
//        return new Obstacle(obstacleBody);
//      }
      
      
      
      
      
//      
//      bd getDefaultBodydefFromType(type)
//      
//      array fd = getDefaultFixturedefsdefFromType(type)
//      ---> hier müssen beliebig viele shapes dran
//      
//      world.createBody()
//      return entitiyWenn render und Move?
//      
//      
//      
//      
//      
//      MapProperties mapProperties = mapObject.getProperties();
//      
//      if("waypoint".equals(mapProperties.get("type")))
//        waypoints.add((Waypoint)boxEntityFactory.createEntity(mapObject));
//      else if("player1".equals(mapProperties.get("type")) || "enemy1".equals(mapProperties.get("type")) || "obstacle".equals(mapProperties.get("type"))) 
//        boxEntities.add(boxEntityFactory.createEntity(mapObject));      
    }

    
    
    
    
    
    
    
    
  }
  
  
  
  public void step()
  {
    world.step(1 / 60f, 6, 2);
  }
  
  public void render(Matrix4 combined)
  {
    debugRenderer.render(world, combined);
  }
  
  public BoxEntity createEntity(MapObject mapObject)
  {
//    MapProperties mapProperties = mapObject.getProperties();
//    String type = mapProperties.get("type", String.class);
//      
//    if (type.equals(Config.TYPE_PLAYER1))
//    {
//      playerBodyDef.position.set(getPositionFromMapObject(mapProperties));
//      Body playerBody = world.createBody(playerBodyDef);
//      playerBody.createFixture(playerFixtureDef);
//      return new Player(playerBody, boxMap);
//    }
//    else if (type.equals(Config.TYPE_ENEMY1))
//    {
//      enemyBodyDef.position.set(getPositionFromMapObject(mapProperties));
//      Body enemyBody = world.createBody(enemyBodyDef);
//      enemyBody.createFixture(enemyFixtureDef);
//      enemyBody.createFixture(enemyFixtureDefSensor);
//      return new Enemy(enemyBody, boxMap);
//    }
//    else if (type.equals(Config.TYPE_OBSTACLE))
//    {
//      obstacleBodyDef.position.set(getPositionFromMapObject(mapProperties));
//      Body obstacleBody = world.createBody(obstacleBodyDef);
//      obstacleFixtureDef.shape = getShapeFromMapObject(mapObject);
//      obstacleBody.createFixture(obstacleFixtureDef);      
//      return new Obstacle(obstacleBody);
//    }
////    else if ("waypoint".equals(type))
////    {
////      waypointBodyDef.position.set(getPositionFromMapObject(mapProperties));
////      Body waypointBody = boxMap.getWorld().createBody(waypointBodyDef);
////      waypointBody.createFixture(waypointFixtureDef);
////      return new Waypoint(waypointBody, boxMap);
////    }
//    
//    Gdx.app.log("BoxEntityFactory2.createEntity:", "No type given!");    
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
    if(mapObject.getClass().equals(PolygonMapObject.class))
    {
      Polygon polygon = ((PolygonMapObject)mapObject).getPolygon();
      polygon.setPosition(0, 0);
      polygon.setScale(1f/32f, 1f/32f);
      
      PolygonShape polygonShape = new PolygonShape();
      polygonShape.set(polygon.getTransformedVertices());
      
      return polygonShape;
    }
    else if (mapObject.getClass().equals(RectangleMapObject.class))
    {      
      Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
      rectangle.setPosition(0, 0);
      float hx = (rectangle.x + rectangle.width / 2f) / 32f;
      float hy = (rectangle.y + rectangle.height / 2f) / 32f;
      PolygonShape polygonShape = new PolygonShape();
      polygonShape.setAsBox(hx, hy);
      return polygonShape;
    }
//    else if (mapObject.getClass().equals(TiledMapTileMapObject.class))
    Gdx.app.log("BoxEntityFactory.getShapeFromMapObject: ", "Aktuell nur Obstacles vorgesehen und nur als Rectangle oder Polygon");
    return null;
  }

  
  @Override
  public void dispose()
  {
    world.dispose();
    playerPolygonShape.dispose();
    enemyPolygonShape.dispose();
    obstaclePolygonShape.dispose();
    waypointCircleShape.dispose();
  }
}
