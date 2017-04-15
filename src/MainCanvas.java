
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

public class MainCanvas extends GameCanvas implements Runnable, PlayerListener {

    private boolean running;
    private Image background;
    private Image help;
    private TiledLayer hole[];
    private Sprite jamur;
    private Sprite bom;
    private Sprite mud;
    private final int seqMuncul[] = {5, 4, 3, 2, 1, 0, 1, 2, 3, 4, 5},
            seqHitted[] = {6, 7, 8, 9, 10, 11}, bomMuncul[] = {0,1,2,3,4,5}, bomHitted[] = {4},
            summonTime = 1, summonTimeb = 5;
    private int nextSummon;
    private boolean isHitted;
    private int score;
    private int hitBom;
    private int maxJamur, countJamur, countBom, maxBom = 1;
    private Image levelCleared;
    private Image gameover;
    private TiledLayer cup[];
    private TiledLayer life[];
    private Player soundHit;
    
    public MainCanvas() {
        super(true);
        try {
            setFullScreenMode(true);
            background = Image.createImage("/images/hole.png");
//            initSoundMain();
            initHole();
            initJamur();
            initLife();
            initbom();
            initSoundHit();
            nextSummon = summonTime;
            nextSummon = summonTimeb;
            score = 0;
            maxJamur = 8;
            countJamur = 0;

            countBom = 0;
            hitBom = 0;
            gameover = Image.createImage("/images/gameover.png");
            levelCleared = Image.createImage("/images/succes.png");
            initCup();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public void clearObject() {
        background = null;
        clearHole();
        clearJamur();
        clearBom();
        levelCleared = null;
        clearCup();
        clearLife();
        soundHit = null;
    }

    public void initSoundHit() {
        InputStream hit = getClass().getResourceAsStream("/sounds/hitted.wav");

        try {
            soundHit = Manager.createPlayer(hit, "audio/X-wav");
            soundHit.addPlayerListener(this);
            soundHit.setLoopCount(1);
            soundHit.realize();
            VolumeControl vc = (VolumeControl) soundHit.getControl("VolumeControl");
            vc.setLevel(100);
        } catch 
                (IOException ex) {
        } catch (MediaException ex) {
        }
    }
//        public void initSoundMain(){
//        InputStream main = getClass().getResourceAsStream("/sounds/main.wav");
//        
//        try {
//            soundMain = Manager.createPlayer(main, "audio/X-wav");
//            soundMain.addPlayerListener(this);
//            soundMain.setLoopCount(3);
//            soundMain.realize();
//            VolumeControl vc = (VolumeControl)soundMain.getControl("VolumeControl");
//            vc.setLevel(100);
//        } catch (IOException ex) {
//            
//        } catch (MediaException ex) {
//        }
//    }
   
    public void initCup() throws IOException {
        Image img = Image.createImage("/images/cup.png");
        cup = new TiledLayer[3];
        int length = cup.length;
        for (int i = 0; i < length; i++) {
            cup[i] = new TiledLayer(1, 1, img, 30, 30);
            cup[i].setCell(0, 0, 1);
            cup[i].setVisible(false);
        }
        cup[0].setPosition(160, 70);
        cup[1].setPosition(cup[0].getX() + cup[0].getWidth(), cup[0].getY());
        cup[2].setPosition(cup[1].getX() + cup[1].getWidth(), cup[1].getY());
    }

    public void initLife() throws IOException {
        Image img = Image.createImage("/images/lifenew.png");
        life = new TiledLayer[3];
        int length = life.length;
        for (int i = 0; i < length; i++) {
            life[i] = new TiledLayer(1, 1, img, 17, 17);
            life[i].setCell(0, 0, 1);
            life[i].setVisible(true);
        }
        life[0].setPosition(170, 15);
        life[1].setPosition(life[0].getX() + life[0].getWidth(), life[0].getY());
        life[2].setPosition(life[1].getX() + life[1].getWidth(), life[1].getY());
    }

    public void drawCup(Graphics g) {
        cup[0].setVisible(true);
        if (score >= 40) {
            cup[1].setVisible(true);
        }
        if (score >= 60) {
            cup[2].setVisible(true);
        }
        int length = cup.length;
        for (int i = 0; i < length; i++) {
            cup[i].paint(g);
        }
    }

    public void drawLife(Graphics g) {
        if (hitBom == 1) {
            life[0].setVisible(false);
        }
        if (hitBom == 2) {
            life[1].setVisible(false);
        }
        if (hitBom == 3) {
            life[2].setVisible(false);

        }

        int length = life.length;
        for (int i = 0; i < length; i++) {
            life[i].paint(g);
        }
    }

    public void clearCup() {
        int length = cup.length;
        for (int i = 0; i < length; i++) {
            cup[i] = null;
        }
    }

    public void clearLife() {
        int length = life.length;
        for (int i = 0; i < length; i++) {
            life[i] = null;
        }
    }

    public void initJamur() throws IOException {
        Image img = Image.createImage("/images/mushy.png");
        jamur = new Sprite(img, 66, 79);
        jamur.setFrameSequence(seqMuncul);
        jamur.setVisible(false);
    }

    public void summonJamur() {
        Random r = new Random();
        int numbHole = r.nextInt(9);
        jamur.setPosition(hole[numbHole].getX() + 9, (hole[numbHole].getY() + (hole[numbHole].getHeight() / 2)) - jamur.getHeight());
        jamur.setFrameSequence(seqMuncul);
        jamur.setVisible(true);
        isHitted = false;
    }

    public void hitJamur() {
        isHitted = true;
        jamur.setFrameSequence(seqHitted);
        score += 10;
        try {
            soundHit.start();
            initSoundHit();
        } catch (MediaException ex) {
            ex.printStackTrace();
        }
    }

    public void drawJamur(Graphics g) {
        jamur.paint(g);
    }

    public void drawBom(Graphics g) {
        bom.paint(g);
    }

    public void initbom() throws IOException {
        Image bomb = Image.createImage("/images/wow.png");
        bom = new Sprite(bomb, 66, 79);
        bom.setFrameSequence(bomMuncul);
        bom.setVisible(false);
    }

    public void summonBom() {
        Random r = new Random();
        int numbHole = r.nextInt(9);
        bom.setPosition(hole[numbHole].getX() + 9, (hole[numbHole].getY() + (hole[numbHole].getHeight() / 2)) - bom.getHeight());
        bom.setFrameSequence(bomMuncul);
        bom.setVisible(true);
        isHitted = false;
    }

    public void hitBom() {
        isHitted = true;
        bom.setFrameSequence(bomHitted);
        hitBom += 1;
    }

    public void moveJamur() {
        if (countJamur < maxJamur) {
            if (nextSummon > 0) {
                nextSummon--;
            } else {
                if (!jamur.isVisible()) {
                    summonJamur();
                } else {
                    jamur.nextFrame();
                    if (jamur.getFrame() == 0) {
                        nextSummon = summonTime;
                        jamur.setVisible(false);
                        countJamur++;

                    }
                }

            }
        }

    }

    public void callBom() {
        if (countJamur > 2 && countJamur % 2 == 0) {
            if (countJamur < maxJamur) {
                if(countBom<maxBom){
                jamur.setVisible(false);
                if (nextSummon > 0) {
                    nextSummon--;
                } else {
                    if (!bom.isVisible()) {
                        summonBom();
                    } else {
                        bom.nextFrame();
                        if (bom.getFrame() == 0) {
                            nextSummon = summonTimeb;
                            bom.setVisible(true);
                            countBom++;
                        }
                    }
                }
            }
        }
    }
    }
    public void clearJamur() {
        jamur = null;
    }

    public void clearBom() {
        bom = null;
    }

    public void initHole() throws IOException {
        Image img = Image.createImage("/images/hole.png");
        hole = new TiledLayer[9];
        int length = hole.length;
        for (int i = 0; i < length; i++) {
            hole[i] = new TiledLayer(1, 1, img, 74, 13);
            hole[i].setCell(0, 0, 1);
            hole[i].setVisible(false);
        }
        hole[0].setPosition(35, 80);
        hole[1].setPosition(160, 80);
        hole[2].setPosition(280, 80);
        hole[3].setPosition(35, 145);
        hole[4].setPosition(160, 145);
        hole[5].setPosition(280, 145);
        hole[6].setPosition(35, 210);
        hole[7].setPosition(160, 210);
        hole[8].setPosition(280, 210);
    }

    public void start() {
        running = true;
        Thread t = new Thread(this);
        t.start();
    }

    public void drawHole(Graphics g) {
        int length = hole.length;
        for (int i = 0; i < length; i++) {
            hole[i].paint(g);
        }
    }

    public void clearHole() {
        int length = hole.length;
        for (int i = 0; i < length; i++) {
            hole[i] = null;
        }
    }

    public void stop() {
        running = false;
    }

    public void draw(Graphics g) {
        g.setColor(0x000000);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(background, 0, 0, 0);
        drawHole(g);
        drawJamur(g);
        drawLife(g);
        drawBom(g);
        drawBom(g);

        g.setColor(0xFFFF00);
        g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        g.drawString("Score : " + String.valueOf(score), 10, 10, 0);
        g.drawString(countJamur + "/" + maxJamur, 390, 10, Graphics.TOP | Graphics.RIGHT);
        if (countJamur == maxJamur) {
            g.drawImage(levelCleared, getWidth() / 2, getHeight() / 2, Graphics.VCENTER | Graphics.HCENTER);
            g.setColor(0x7F5217);
            g.drawString(String.valueOf(score), getWidth() / 2, (getHeight() / 2) + 30, Graphics.TOP | Graphics.HCENTER);
            drawCup(g);
            
        }
        if (hitBom == 3) {
            g.drawImage(gameover, getWidth() / 2, getHeight() / 2, Graphics.VCENTER | Graphics.HCENTER);
            
        }
    }

    public void run() {
        Graphics g = getGraphics();
        while (running) {
            moveJamur();
            callBom();
            draw(g);
            flushGraphics();
            try {
                Thread.sleep(1000 / 30);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void pointerPressed(int x, int y) {
        if (countJamur == maxJamur && hitBom == 3) {
            stop();
            clearObject();
            Midlet.getInstance().toMainMenu();
        } else {
            if (x > jamur.getX() && x < jamur.getX() + jamur.getWidth() && y > jamur.getY() && y < jamur.getY() + jamur.getHeight() && !isHitted) {
                hitJamur();
            }
            if (x > bom.getX() && x < bom.getX() + bom.getWidth() && y > bom.getY() && y < bom.getY() + bom.getHeight() && !isHitted) {
                hitBom();
            }

        }
    }

    public void playerUpdate(Player player, String event, Object eventData) {
        //do nothing
    }
}
