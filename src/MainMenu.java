import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.midlet.MIDletStateChangeException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author NRY
 */
public class MainMenu extends Canvas  {

    private Image background;
    private Image mainMenu;

    public MainMenu() {
        try {
            mainMenu = Image.createImage("/images/mainmenu1.png");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setFullScreenMode(true);
        repaint();
    }

    public void clearObject() {
        mainMenu = null;

    }

    protected void paint(Graphics g) {
        g.drawImage(mainMenu, getWidth() / 2, getHeight() / 2, Graphics.VCENTER | Graphics.HCENTER);
    }

    public void pointerPressed(int x, int y) {
        if (x > 50 && x < 175 && y > 45 && y < 80) {
            clearObject();
            Midlet.getInstance().newGame();
        }
        if (x > 50 && x < 175 && y > 110 && y < 140) {
            clearObject();
            Midlet.getInstance().help();
        }
        if (x > 50 && x < 175 && y > 80 && y < 110) {
            clearObject();
            Midlet.getInstance().about();
        }
        if (x > 50 && x < 175 && y > 140 && y < 170) {
           
           
        }
    }

    public void playerUpdate(Player player, String event, Object eventData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
