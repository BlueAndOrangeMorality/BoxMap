package de.ranagazoo.boxmap;

//import static de.ranagazoo.box.Box2dBuilder.createBody;
//import static de.ranagazoo.box.Box2dBuilder.createChainShape;
//import static de.ranagazoo.box.Box2dBuilder.createFixtureDef;
//import static de.ranagazoo.box.Box2dBuilder.createStaticBodyDef;

import static de.ranagazoo.boxmap.Config.TS;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import de.ranagazoo.boxmap.Waypoint;

public class BoxMap extends ApplicationAdapter
{

  public static final String TEXTURE_LIBGDX = "data/libgdx.png";
  public static final String TEXTURE_ENTITIES = "data/entities-big.png";
  public static final String TEXTURE_MECHA = "data/mecha32.png";

  public static final String MAP_MAP = "data/map.tmx";
  
  private AssetManager assetManager;

  private OrthographicCamera cameraSprites;
  private OrthographicCamera cameraBox2dDebug;
  private SpriteBatch batch;

  private Texture entitiesBigTexture, mechaTexture;
  //private TextureRegion entityPlayerRegion;
  Sprite playerSprite;

  private Animation<TextureRegion> animation;
  private float stateTime;

  private ShapeRenderer shapeRenderer;
  private Random random;

//  private World world;
  private WorldManager worldManager;
//  private Box2DDebugRenderer debugRenderer;

//  private BoxEntityFactory boxEntityFactory;
//  private BoxEntityFactory2 boxEntityFactory;
  
  private TiledMap map; 
  private TiledMapRenderer mapRenderer;
  
  // My Objects
  private Array<BoxEntity2> boxEntities;
  
//  private ArrayList<Waypoint> waypoints;

  private DebugOutput debugOutput;

  @Override
  public void create()
  {

    assetManager = new AssetManager();
    loadAssets();

    worldManager = new WorldManager();
//    // box2dworld
//    world = new World(new Vector2(0, 0), true);
//    world.setContactListener(new BoxMapContactListener());

//    boxEntityFactory = new BoxEntityFactory();
//    boxEntityFactory = new BoxEntityFactory2(this);
    
    
    
    // Renderer / Cameras
//    debugRenderer = new Box2DDebugRenderer();

    cameraSprites = new OrthographicCamera();
    cameraSprites.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    cameraSprites.update();

    cameraBox2dDebug = new OrthographicCamera();
    cameraBox2dDebug.setToOrtho(false, Gdx.graphics.getWidth() / TS, Gdx.graphics.getHeight() / TS);
    cameraBox2dDebug.update();

    batch = new SpriteBatch();
    shapeRenderer = new ShapeRenderer();

    entitiesBigTexture = assetManager.get(TEXTURE_ENTITIES);
    entitiesBigTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//    entityPlayerRegion = new TextureRegion(entitiesBigTexture, 8 * TS, 1 * TS, TS, TS);
    
    
    playerSprite = new Sprite(new TextureRegion(entitiesBigTexture, 8 * TS, 1 * TS, TS, TS));
    playerSprite.setSize(TS, TS);
    playerSprite.setOrigin(TS / 2, TS / 2);
    
    
    // Random für Random
    random = new Random();

    debugOutput = new DebugOutput();

    mechaTexture = assetManager.get(TEXTURE_MECHA);
    mechaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    TextureRegion[] animationFrames = new TextureRegion[5];
    animationFrames[0] = new TextureRegion(mechaTexture, 0, 384, 64, 64);
    animationFrames[1] = new TextureRegion(mechaTexture, 64, 384, 64, 64);
    animationFrames[2] = new TextureRegion(mechaTexture, 128, 384, 64, 64);
    animationFrames[3] = new TextureRegion(mechaTexture, 192, 384, 64, 64);
    animationFrames[4] = new TextureRegion(mechaTexture, 256, 384, 64, 64);
    animation = new Animation<TextureRegion>(0.1f, animationFrames);

    stateTime = 0f;
    
    map = assetManager.get(MAP_MAP);
    mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / 1f);
    mapRenderer.setView(cameraSprites);
    

    //Erzeuge Entities aus der Map
    boxEntities = new Array<BoxEntity2>();
    boxEntities.addAll(worldManager.loadEntities(map));
    
    
    
//    waypoints = new ArrayList<Waypoint>();
    
    
    
    
    
    
    
//    
//    map.getLayers().get(1).getObjects().getByType(PolygonMapObject.class);
//    map.getLayers().get(1).getObjects().getByType(PolylineMapObject.class);
//    map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class);
//    map.getLayers().get(1).getObjects().getByType(TiledMapTileMapObject.class);
//    
//    
//    
//    for (MapObject mapObject : map.getLayers().get(1).getObjects())
//    {
//      
//      
//      MapProperties mapProperties = mapObject.getProperties();
//      
//      if("waypoint".equals(mapProperties.get("type")))
//        waypoints.add((Waypoint)boxEntityFactory.createEntity(mapObject));
//      else if("player1".equals(mapProperties.get("type")) || "enemy1".equals(mapProperties.get("type")) || "obstacle".equals(mapProperties.get("type"))) 
//        boxEntities.add(boxEntityFactory.createEntity(mapObject));      
//    }

    
//    // Jedem Enemy initial einen Waypoint zuweisen
//    for (BoxEntity2 boxEntity : boxEntities)
//    {
//      if (boxEntity.getClass() == Enemy.class)
//        ((Enemy) boxEntity).setCurrentTargetId(getRandomWaypointIndex(0));
//    }

    // TODO Box2dChainShape funktioniert nicht mit 1.9.7

    // Border, könnte man auch noch auslagern
    // Shape tempShape;
    // FixtureDef tempFixtureDef = null;
    //
    // borderBody = createBody(world, createStaticBodyDef(2, false, 2, new
    // Vector2(16, 2)), "userData");
    // tempShape = createChainShape(new Vector2[]{new Vector2(-15f, -1f), new
    // Vector2(15f, -1f), new Vector2(15f, 21f), new Vector2(-15f, 21f), new
    // Vector2(-15f, -1f)});

    // tempFixtureDef = createFixtureDef(0.0f, 0.4f, 0.1f, tempShape,
    // CATEGORY_SCENERY, MASK_SCENERY);
    // borderBody.createFixture(tempFixtureDef);
    // tempShape.dispose();

  }

  @Override
  public void render()
  {
    if (Gdx.input.isKeyPressed(Keys.ESCAPE))
      Gdx.app.exit();

    // Move all entities
    for (BoxEntity2 boxEntity : boxEntities)
    {
      boxEntity.move();
    }

    
    
    Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    mapRenderer.render();
    
    batch.setProjectionMatrix(cameraSprites.combined);
    batch.begin();

    
    
    
    
    
    BoxEntities rendern sich nicht mehr selber!
    Sie liefern Position, Drehung, Typ(Player/Entity) und Status(Attack/Idle) zurück.
    Gerendert wird hier anhand der Werte
    
    
    Die Elemente sollten nie die Hauptklasse kriegen
    Bei Enemy wird das schwieriger, hier muss beim Scannen des entities die Position im ContactListener übergeben werden
    und beim zurück-zum-Wegpunkt muss auf die Wegpunktliste verwiesen werden, die am Besten schon im Entity enthalten ist
    
    
    
    
    
    
    
    
    
//    
//    
//    Anders herum, rendern passiert hier, hol nur das Body für Pos und Drehung!
//    //Render all entities
//    for (BoxEntity2 boxEntity : boxEntities)
//    {
//      boxEntity.render(batch);
//    }
    
    //Aktuell werden nur Player und Enemies gerendert
    for (BoxEntity2 boxEntity : boxEntities)
    {
      Vector2 position = boxEntity.getBody().getPosition();
      float angle = boxEntity.getBody().getAngle();
      String type = boxEntity.getType();
      
      if(type.equals(Config.TYPE_PLAYER1))
      {
        playerSprite.setPosition((position.x - 0.5f) * TS, (position.y - 0.5f) * TS);
        playerSprite.setRotation(MathUtils.radiansToDegrees * angle);
        playerSprite.draw(batch);
      }
      else if (type.equals(Config.TYPE_ENEMY1)) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion)animation.getKeyFrame(stateTime, true);
        Sprite s = new Sprite(currentFrame);
        s.setPosition((position.x - 1) * TS, (position.y - 1) * TS);
        s.setRotation(MathUtils.radiansToDegrees * angle + 180);
        s.draw(batch);            
      }
    } 
    
    
    
    //Render debug messages
    debugOutput.render(batch);

    batch.end();

    shapeRenderer.setProjectionMatrix(cameraSprites.combined);
    shapeRenderer.begin(ShapeType.Line);

    
    //shapeRenderer.polygon(floatarray from Wayypoints);    
    
    
//    for (int i = 0; i < waypoints.size() - 1; i++)
//    {
//      shapeRenderer.line(waypoints.get(i).getPosition().scl(32), waypoints.get(i + 1).getPosition().scl(32));
//    }
//    shapeRenderer.line(waypoints.get(waypoints.size() - 1).getPosition().scl(32), waypoints.get(0).getPosition().scl(32));

    shapeRenderer.end();

    worldManager.step();
    worldManager.render(cameraBox2dDebug.combined);
//    world.step(1 / 60f, 6, 2);

    //debugRenderer.render(world, cameraBox2dDebug.combined);
  }

  @Override
  public void dispose()
  {
    batch.dispose();
    shapeRenderer.dispose();
    assetManager.dispose();
//    world.dispose();
//    boxEntityFactory.dispose();
    worldManager.dispose();
  }

//  public Vector2 getPlayerPosition()
//  {
//    for (BoxEntity2 boxEntity : boxEntities)
//    {
//      if (boxEntity.getClass() == Player.class)
//        return ((Player) boxEntity).getBody().getPosition();
//    }
//    return new Vector2(0, 0);
//  }

//  // Return a temp random index, but not the given one
//  public int getRandomWaypointIndex(int notThisOne)
//  {
//    int temp = random.nextInt(waypoints.size());
//    while (notThisOne == temp)
//    {
//      temp = random.nextInt(waypoints.size());
//    }
//
//    return temp;
//  }

//  public int getNextWaypointIndex(int currentone)
//  {
//    int temp = currentone + 1;
//    if (temp >= waypoints.size())
//      temp = 0;
//    return temp;
//  }

  /*
   * Siehe MapbObjetcs  SSSSS
   * 
   ** @param type class of the objects we want to retrieve
   * @param fill collection to put the returned objects in
   * @return array filled with all the objects in the collection matching type 
  public <T extends MapObject> Array<T> getByType (Class<T> type, Array<T> fill) {
    fill.clear();
    for (int i = 0, n = objects.size; i < n; i++) {
      MapObject object = objects.get(i);
      if (ClassReflection.isInstance(type, object)) {
        fill.add((T)object);
      }
    }
    return fill;
  }
   */
  
  
  
//  Avoid Enums, use final variables
//
//  Avoid Iterators, for-loops are faster
//
//  Avoid Global Static classes at all costs, their runtime differs from normal code
//
//   
  
  
//  public Waypoint getWaypoint(int parameter)
//  {
//    return waypoints.get(parameter);
//  }

  public void loadAssets()
  {
    assetManager.load(TEXTURE_LIBGDX, Texture.class);
    assetManager.load(TEXTURE_ENTITIES, Texture.class);
    assetManager.load(TEXTURE_MECHA, Texture.class);
    assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
    assetManager.load(MAP_MAP, TiledMap.class);
    assetManager.finishLoading();
  }

  public AssetManager getAssetManager()
  {
    return assetManager;
  }

//  public World getWorld()
//  {
//    return world;
//  }

  public Animation<TextureRegion> getAnimation()
  {
    return animation;
  }

//  public TextureRegion getEntityPlayerRegion()
//  {
//    return entityPlayerRegion;
//  }
//  
//  public ArrayList<Waypoint> getWaypoints()
//  {
//    return waypoints;
//  }

//  public BoxEntityFactory2 getBoxEntityFactory()
//  {
//    return boxEntityFactory;
//  }
//  public BoxEntityFactory getBoxEntityFactory()
//  {
//    return boxEntityFactory;
//  }
}
