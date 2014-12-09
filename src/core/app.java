/*
Changelog
=====================
19 October 2014
    -1400 hours
        *initial creation
*/
package core;

import credits.Credits;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Main class for the project.
 * @author Jackson
 * @version 1.00
 */
public class app {

    /**
     * @param args the command line arguments
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException {
        
        MainFrame mjf = new MainFrame();
        mjf.setLocationRelativeTo(null);
        
    }
    
}
