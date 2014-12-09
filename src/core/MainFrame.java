/*
Changelog
=====================
19 October 2014
    -1600 hours
        *initial creation
20 October 2014
    -0400
        *added rollCredits JButton, this is to temporarily show the credits are working
*/

package core;

import credits.Credits;
import credits.CreditsPanel;
import game.GamePanel;
import instructions.InstructionsPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import settings.Setting;
import settings.SettingsPanel;
import util.XML_240;

/**
 * Java class for creating a custom swing JFrame.
 * @author Jackson
 * @version 1.00
 */
public class MainFrame extends JFrame implements ActionListener
{
    SplashPanel splash;
    CreditsPanel credits;
    GamePanel game;
    InstructionsPanel instructions;
    SettingsPanel settings;
    Credits creditsList;
    Setting music;
    boolean musicOn;
    XML_240 x2;
    PlayerListener playerMoving;
    int HEIGHT = 600, WIDTH = 800;
    /**
    * Constructor for class
    * @author Jackson
    * @version 1.00
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javax.sound.sampled.LineUnavailableException
    */
    public MainFrame () throws UnsupportedAudioFileException, LineUnavailableException
    {
        super ("Game");
        x2 = new XML_240();
        String size = getSavedSize();
        switch(size)
        {
            case "800 x 600":
                this.HEIGHT = 600;
                this.WIDTH = 800;
                break;
            case "1000 x 800":
                this.HEIGHT = 800;
                this.WIDTH = 1000;
                break;
            case "1200 x 1000":
                this.HEIGHT = 1000;
                this.WIDTH = 1200;
                break;
        }
        loadMusic();
        creditsList = new Credits();
        game = new GamePanel(WIDTH, HEIGHT);
        instructions = new InstructionsPanel(WIDTH, HEIGHT);
        settings = new SettingsPanel(WIDTH, HEIGHT);
        credits = new CreditsPanel(creditsList, HEIGHT, WIDTH);
        splash = new SplashPanel(HEIGHT, WIDTH);
        splash.creditsButton.addActionListener(this);
        splash.startGame.addActionListener(this);
        splash.instructionsButton.addActionListener(this);
        splash.settingsButton.addActionListener(this);
        game.back.addActionListener(this);
        instructions.back.addActionListener(this);
        settings.back.addActionListener(this);
        playerMoving = new PlayerListener();
        game.setFocusable(true);
        game.addKeyListener(playerMoving);
        getContentPane().add(splash,"Center");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        refreshSize();
        setVisible(true);
    }

    /**
     * Loads whether music is turned on or off in the settings from file.
     * The after loading that it sets the boolean for it.
     */
    public void loadMusic()
    {
        x2.openReaderXML("Options.xml");
        x2.ReadObject();
        x2.ReadObject();
        music = (Setting) x2.ReadObject();
        x2.closeReaderXML();
        if(music.getSettingValue().equalsIgnoreCase("on"))
        {
            musicOn = true;
        }
        else
        {
            musicOn = false;
        }
    }
    /**
     * Returns the currently set resolution by reading it from the settings XML file.
     * @return size
     */
    public String getSavedSize()
    {
        XML_240 x2 = new XML_240();
        x2.openReaderXML("Options.xml");
        Setting res = (Setting)x2.ReadObject();
        x2.closeReaderXML();
        String size = res.getSettingValue();
        return size;
    }
    /**
     * Changes the size of the window to the current resolution.
     * Also updates the width and height in all of the panels.
     * The panel width and heights are used for positioning of components.
     */
    public void refreshSize()
    {
        String size = settings.options.resolution.getSettingValue();
        switch(size)
        {
            case "800 x 600":
                this.setSize (new Dimension(800, 600));
                WIDTH = 800;
                HEIGHT = 600;
                //settings panel
                settings.width = 800;
                settings.height = 600;
                //splash panel
                splash.WIDTH = 800;
                splash.HEIGHT = 600;
                //credits panel
                credits.width = 600;
                credits.height = 550;
                //game panel
                game.width = 800;
                game.height = 600;
                //instructions panel
                instructions.width = 800;
                instructions.height = 600;
                break;
            case "1000 x 800":
                WIDTH = 1000;
                HEIGHT = 800;
                //settings panel
                settings.width = 1000;
                settings.height = 800;
                //splash panel
                splash.WIDTH = 1000;
                splash.HEIGHT = 800;
                //credits panel
                credits.width = 800;
                credits.height = 750;
                //game panel
                game.width = 1000;
                game.height = 800;
                //instructions panel
                instructions.width = 1000;
                instructions.height = 800;
                this.setSize (new Dimension(1000, 800));
                break;
            case "1200 x 1000":
                WIDTH = 1200;
                HEIGHT = 1000;
                //settings panel
                settings.width = 1200;
                settings.height = 1000;
                //splash panel
                splash.WIDTH = 1200;
                splash.HEIGHT = 1000;
                //credits panel
                credits.width = 1000;
                credits.height = 950;
                //game panel
                game.width = 1200;
                game.height = 1000;
                //instructions panel
                instructions.width = 1200;
                instructions.height = 1000;
                this.setSize (new Dimension(1200, 1000));
                break;
        }
        this.setLocationRelativeTo(null);
    }
    /**
     * ActionPerformed listener for the class, currently removes current panel and adds credits panel upon click of a button
     * @param e ActionEvent for the listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        loadMusic();
        if(obj == splash.creditsButton) 
        { 
            credits.resetBounds();
            try {
                credits = new CreditsPanel(new Credits(), HEIGHT, WIDTH);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            credits.back.addActionListener(this);
            if(musicOn)
            {
                splash.getMusic().stopSound();
                credits.getMusic().startSound();
            }
            else
            {
                splash.getMusic().stopSound();
            }
            replacePanel(splash, credits);
        }
        if(obj == splash.startGame)
        {
            game.loadSettings();
            game.resetBounds();
            game.setDifficulty();
            if(musicOn)
            {
                splash.getMusic().stopSound();
                game.getMusic().startSound();
            }
            else
            {
                splash.getMusic().stopSound();
            }
            replacePanel(splash,game);
            game.requestFocus();
            game.resetGame();
            game.tim.start();
        }
        if(obj == splash.instructionsButton)
        {
            instructions.resetBounds();
            instructions.resolution.setSettingValue(settings.options.resolution.getSettingValue());
            replacePanel(splash,instructions);
        }
        if(obj == splash.settingsButton)
        {
            settings.resetBounds();
            replacePanel(splash,settings);
        }
        if(obj == game.back)
        {
            splash.resetBounds();
            game.tim.stop();
            game.resetGame();
            game.penaltyHit = false;
            game.penaltyT.stop();
            game.penalty = 10;
            game.penaltyL.setText("Penalty Timer: " + game.penalty);
            game.playerMoving = true;
            if(musicOn)
            {
                splash.getMusic().startSound();
                game.getMusic().stopSound();
            }
            else
            {
                game.getMusic().stopSound();
            }
            replacePanel(game,splash);
        }
        if(obj == credits.back)
        {
            splash.resetBounds();
            if(musicOn)
            {
                credits.getMusic().stopSound();
                splash.getMusic().startSound();
            }
            else
            {
                credits.getMusic().stopSound();
            }
            replacePanel(credits,splash);
        }
        if(obj == instructions.back)
        {
            splash.resetBounds();
            replacePanel(instructions,splash);
        }
        if(obj == settings.back)
        {
            refreshSize();
            splash.resetBounds();
            if(!musicOn)
            {
                splash.getMusic().stopSound();
            }
            else
            {
                splash.getMusic().startSound();
            }
            replacePanel(settings,splash);
            splash.resolution.setSettingValue(settings.options.resolution.getSettingValue());
            splash.repaint();
        }
    }
    /**
     * Replaces the current panel with p2
     * @param p1 panel to be removed
     * @param p2 panel to be added
     */
    public void replacePanel(JPanel p1, JPanel p2)
    {
            this.remove(p1);
            this.add(p2);
            this.revalidate();
            this.repaint();
    }
    
    class PlayerListener implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent ke) {
            
        }

        @Override
        public void keyPressed(KeyEvent ke) 
        {
            if(!game.penaltyHit)
            {
                game.penaltyT.start();
                game.playerMoving = false;
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) 
        {
            game.penaltyHit = false;
            game.penaltyT.stop();
            game.penalty = 10;
            game.penaltyL.setText("Penalty Timer: " + game.penalty);
            game.playerMoving = true;
        }
        
    }
}
