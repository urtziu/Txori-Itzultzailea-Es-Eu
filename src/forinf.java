import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;

import java.io.File;
import java.io.IOException;
import java.util.List;



public class forinf {

    protected static SpellDictionaryHashMap dictionary = null;
    protected static SpellChecker spellChecker = null;

    static {

        try {

            dictionary =
                new SpellDictionaryHashMap(new
                File(".\\bin\\ES.dic"));
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        spellChecker = new SpellChecker(dictionary);
    }
    public static List<?> getSuggestions(String word,
            int threshold) {
    	
            return spellChecker.getSuggestions(word, threshold);
        }
   
    public static boolean CheckSpell(String word) {
    	
			
			//System.out.println(word +" hitza ondo dago? " + spellChecker.isCorrect(word));
			return spellChecker.isCorrect(word);
		
    	//System.out.println(word +" hitza2 ondo dago? "+ spellChecker.isCorrect("D�a"));
		
    	
        //return spellChecker.getSuggestions(word, threshold);
    }
}