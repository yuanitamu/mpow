/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.*;

/**
 * @author NRY
 */
public class Midlet extends MIDlet {
    private Display d;
    private MainCanvas main;
    private MainMenu menu;
    private Help help;
    private About about;
    private static Midlet self;
    
    public Midlet(){
        d = Display.getDisplay(this);
        self = this;
    }
    
    public static Midlet getInstance(){
        return self;
    }
    
    public void toMainMenu(){
        if(menu!=null){
            menu = null;
        }
        menu = new MainMenu();
        d.setCurrent(menu);
    }
    
    public void newGame(){
        if(main!=null){
            main = null;
        }
        main = new MainCanvas();
        main.start();
        d.setCurrent(main);
    }
    
    public void help (){
        if(help!=null){
           help = null;
        }
        help = new Help();
        d.setCurrent(help);
    }
    public void about(){
        if(about!=null){
           about = null;
        }
        about = new About ();
        d.setCurrent(about);
    }
 
    public void startApp() {
        toMainMenu();
    }
    
    public void pauseApp() {
    }
    

    public void destroyApp(boolean unconditional){
        
    }
 
}
