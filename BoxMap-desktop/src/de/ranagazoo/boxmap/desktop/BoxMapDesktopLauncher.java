package de.ranagazoo.boxmap.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.ranagazoo.boxmap.BoxMap;

public class BoxMapDesktopLauncher
{
  public static void main(String[] arg)
  {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = "BoxMap";
    config.width = 1024;
    config.height = 768;
    new LwjglApplication(new BoxMap(), config);
  }
}
