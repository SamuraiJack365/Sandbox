/*
Changelog
=====================
19 October 2014
    -1400 hours
        *initial creation
        *created contributors for Jack, Michelle, and Nick
        *put in code to populate the contributors from and XML document
*/
package credits;

import java.util.ArrayList;
import util.XML_240;

/**
 * Java class for creating the credits in our IST 240 final project game.
 * @author Jackson
 * @version 1.00
 */
public class Credits {
    
    /**
     * Creates multiple contributors based on an XML document.
     */
    Contributor jack, michelle, nick;
    ArrayList<String> compiledCredits;
    public Credits()
    {
        /* ====================================================
         * This section creates the new instances of the contributors and the XML_240 class
         * ====================================================
         */
        compiledCredits = new ArrayList();
        jack = new Contributor();
        michelle = new Contributor();
        nick = new Contributor();   
        XML_240 x2 = new XML_240();
        
        /* ====================================================
         * This section reads from the Credits.xml to give values to the contributors.
         * ====================================================
         */
        x2.openReaderXML("Credits.xml");
        jack = (Contributor)x2.ReadObject();
        michelle = (Contributor)x2.ReadObject();
        nick = (Contributor)x2.ReadObject();
        x2.closeReaderXML();
    }
    
    public void compileCredits()
    {
        compiledCredits.add("Credits");
        compiledCredits.add(jack.getName());
        compiledCredits.add("Contributions:");
        for(int i =0; i < jack.getContribution().size(); i++)
        {
            compiledCredits.add(jack.getContribution(i));
        }
        compiledCredits.add(michelle.getName());
        compiledCredits.add("Contributions:");
        for(int i =0; i < michelle.getContribution().size(); i++)
        {
            compiledCredits.add(michelle.getContribution(i));
        }
        compiledCredits.add(nick.getName());
        compiledCredits.add("Contributions:");
        for(int i =0; i < nick.getContribution().size(); i++)
        {
            compiledCredits.add(nick.getContribution(i));
        }
    }
    
    
}
