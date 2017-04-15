import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author NRY
 */

public class About extends GameCanvas  {
    private boolean running;
    private Image about1;

    
    public About () {
        super(true);
        try {
            setFullScreenMode(true);
            about1 = Image.createImage("/images/about.png");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setFullScreenMode(true);
        repaint();
    }
    
    public void clearObject(){
       about1 = null;
    }
    public void paint(Graphics g) {
        g.drawImage(about1, getWidth()/2, getHeight()/2, Graphics.VCENTER| Graphics.HCENTER);
    }
    
    public void pointerPressed(int x, int y) {
        if (x > 190 && x<230 && y > 190 && y < 225){
            Midlet.getInstance().toMainMenu();
    
        }
    }
}
