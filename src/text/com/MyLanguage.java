package text.com;

import java.util.ArrayList;
import java.util.List;


public enum MyLanguage
{
    ARABIC("ar", "العربية"), BULGARIAN("bg"), CATALAN("ca"), CHINESE("zh", "中文"), CHINESE_SIMPLIFIED("zh_CN", "简体中文"),
    CHINESE_TRADITIONAL("zh-TW", "正體中文"), CROATIAN("hr"), CZECH("cs"), DANISH("da"), DUTCH("nl"), ENGLISH("en", "English"),
    FILIPINO("tl"), FINNISH("fi"), FRENCH("fr_FR", "Français"), GERMAN("de", "Deutsch"), GREEK("el", "Ελληνικά"), HEBREW("iw", "עברית"), HINDI("hi"),
    INDONESIAN("id"), ITALIAN("it", "Italiano"), JAPANESE("ja", "日本語"), KOREAN("ko", "한국어"), LATVIAN("lv"), LITHUANIAN("lt"), NORWEGIAN("no"),
    POLISH("pl"), PORTUGESE("pt"), ROMANIAN("ro"), RUSSIAN("ru"), SERBIAN("sr"), SLOVAK("sk"), SLOVENIAN("sl"),
    SPANISH("es"), SWEDISH("sv"), UKRANIAN("uk"), VIETNAMESE("vi", "Việt");

    MyLanguage(String language)
    {
        this(language, "");
    }

    MyLanguage(String language, String alias)
    {
        this.lang = language;
        this.alias = alias;
    }
    public static MyLanguage validate(String language)
    {
        for (MyLanguage item : MyLanguage.values())
            if (item.lang.equalsIgnoreCase(language))
                return item;
        return null;
    }
    
    public static String returnLang(String language)
    {
        for (MyLanguage item : MyLanguage.values())
            if (item.lang.equalsIgnoreCase(language))
                return item.name().toString();
        return null;
    }
    
    public static List<String> getAll()
    {
        List<String> langs = new ArrayList<String>();
        for (MyLanguage item : MyLanguage.values()){
            langs.add(item.lang);
        }
         return langs;
    }

    /**
     * Checks a given language is available to use with Google Translate.
     * 
     * @param language The language code to check for.
     * @return true if this language is available to use with Google Translate, false otherwise.
     */
    public static boolean isValidLanguage(String language)
    {
        return (validate(language) != null);
       
    }

    /**
     * Return the global name of Language
     * @return the global name of Language
     */
    @Override
    public String toString() {
        return lang;
    }
    
    public String getAlias() {
        return alias.equals("") ?
                name().toUpperCase().charAt(0)
                + name().toLowerCase().substring(1)
                : alias;
    }
    private String lang, alias;
    
}