package net.miniscape.translation;

import lombok.Getter;
import net.miniscape.util.guice.Manager;

import java.util.Map;

@Manager(name = "Translation Manager")
public class TranslationManager {
    @Getter
    private Map<Language, Map<String, String>> translations;

    public String get(String key, Language language) {
        Map<String, String> strings = translations.get(language);

        if (strings == null) {
            return key;
        }

        if (!strings.containsKey(key) || strings.get(key) == "") {
            if (language == Language.ENGLISH) {
                return key;
            }
            return get(key, Language.ENGLISH);
        }

        return strings.get(key);
    }
}