package de.ranagazoo.boxmap;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/*
 * Is loaded from a map object (of type waypointGroup)
 * Contains a list of waypoints and their order
 */
public class WaypointGroup
{
  //Waypoints are doubled here because the rendering expects float[], but it's easier to get the right Vector
  //MAYBE THIS SHOULD BE WAYPOINT-CLASS
  Array<Body> waypoints = new Array<Body>(); 
  float[] waypointsRender = new float[]{};
  
  //Empty Constructor, das ganze sollte hoffentlich später noch überladen werden.
  //Das Ganze macht nur so lange Sinn, wie es nur eine WaypointGroup gibt, aber das gilt auch für WorldManager
  //Kannst du immer noch aufzwirbeln
  public WaypointGroup(){}
  
  
  //Waypoint exisitert nicht mehr
  
  //Hier werden erst alle Punkte um Polygon/Polyline in waypointsRender geschrieben
  //Dann werden daraus Waypoint-Typ-Bodys gemacht, diese in waypoint hinterlegt und jedem this als userObject übergeben
  //So müsste man bei jedem Waypoint an dieses Eltenr-Objekt kommen und umgekehrt
  
  public WaypointGroup(MapObject mapObject, World world, BoxEntityFactory3 boxEntityFactory3)
  {
    if(mapObject.getClass().equals(PolygonMapObject.class))
    {
      Polygon polygon = ((PolygonMapObject)mapObject).getPolygon();
      polygon.setPosition(0, 0);
      polygon.setScale(1f/32f, 1f/32f);     
      waypointsRender = polygon.getTransformedVertices();
    }
    else if(mapObject.getClass().equals(PolylineMapObject.class))
    {
      Polyline polyline = ((PolylineMapObject)mapObject).getPolyline();
      polyline.setPosition(0, 0);
      polyline.setScale(1f/32f, 1f/32f);     
      waypointsRender = polyline.getTransformedVertices();
    }
  
    for (int i = 0; i < waypointsRender.length-1; i++)
    {
      BodyDef bodyDef = boxEntityFactory3.getBodyDefFromMapObject(Config.TYPE_WAYPOINT);
      bodyDef.position.set(new Vector2(waypointsRender[i], waypointsRender[i+1]));
      
      //Hat schon seinen Shape
      FixtureDef fixtureDef = boxEntityFactory3.getFixtureDefFromMapObject(Config.TYPE_WAYPOINT);
      
      Body body = world.createBody(bodyDef);
      body.createFixture(fixtureDef);
      body.setUserData(this);
      waypoints.add(body);    
    }
  }
  
  
  
  public Body get (int index) {
    return waypoints.get(index);
  }  
  
  public int getIndex (Body body) {
    return waypoints.indexOf(body, true);
  }

}
