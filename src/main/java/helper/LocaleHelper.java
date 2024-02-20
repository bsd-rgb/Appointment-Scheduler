package helper;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleHelper {

    public static ResourceBundle getDefaultLocale() {
        return ResourceBundle.getBundle("Nat", Locale.getDefault());
    }

    public boolean isFrench(String language) {
        //temporarily set while I take a break;
        //Need to be able to check the default locale
        //should I include french in the name? Is there a way to dynamically check?
        return true;
    }



   //if
}
