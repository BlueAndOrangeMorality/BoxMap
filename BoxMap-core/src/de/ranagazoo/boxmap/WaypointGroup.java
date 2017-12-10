package de.ranagazoo.boxmap;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

/*
 * Is loaded from a map object (of type waypointGroup)
 * Contains a list of waypoints and their order
 */
public class WaypointGroup
{
  //Waypoints are doubled here because the rendering expects float[], but it's easier to get the right Vector
  //MAYBE THIS SHOULD BE WAYPOINT-CLASS
  Array<Waypoint> waypoints = new Array<Waypoint>(); 
  float[] waypointsRender = new float[]{};
  
  public WaypointGroup(MapObject mapObject)
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
    
  }
  
  public void createWaypoints()
  {
    if("waypoint".equals(mapProperties.get("type")))
      waypoints.add((Waypoint)boxEntityFactory.createEntity(mapObject));
    
    waypointBodyDef.position.set(new Vector2(waypointsRender[i], waypointsRender[i+1]));
    Body waypointBody = boxMap.getWorld().createBody(waypointBodyDef);
    waypointBody.createFixture(waypointFixtureDef);
    return new Waypoint(waypointBody, boxMap);
    
    
    
    
    
    for (int i = 0; i < waypointsRender.length-1; i++)
    {
      waypoints.add(new Vector2(waypointsRender[i], waypointsRender[i+1]));
    }
    
  }
  
  
  public Vector2 get (int index) {
    return waypoints.get(index);
  }  
  
  public int getIndex (Vector2 object) {
    return waypoints.indexOf(object, true);
  }

}
