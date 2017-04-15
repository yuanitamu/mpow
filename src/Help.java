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

public class Help extends GameCanvas  {
    private boolean running;
    private Image help1;
    private Image help2;
    private Image help3;
    private Image help4;
    
    public Help () {
        super(true);
        try {
            setFullScreenMode(true);
            help1 = Image.createImage("/images/help1.png");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setFullScreenMode(true);
        repaint();
    }
    public void help2 (Graphics g){
        try {
            help2 = Image.createImage("/images/help2.png");
            g.drawImage(help2, getWidth()/2, getHeight()/2, Graphics.VCENTER| Graphics.HCENTER);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
        
    }
    public void help3 (){
        try {
            help3 = Image.createImage("/images/help3.png");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
        
    }
    public void help4 (){
        try {
            help4 = Image.createImage("/images/help4.png");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
        
    }
    public void clearObject(){
       help1 = null;
    }
    public void paint(Graphics g) {
        g.drawImage(help1, getWidth()/2, getHeight()/2, Graphics.VCENTER| Graphics.HCENTER);
    }
    
    public void pointerPressed(int x, int y) {
        if (x > 190 && x<230 && y > 190 && y < 230){
            Midlet.getInstance().toMainMenu();
        }
    }
}
