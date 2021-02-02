package net.miniscape.service.translations;

import com.google.gson.Gson;
import net.miniscape.translation.Language;
import net.miniscape.translation.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TranslationsServiceImpl implements TranslationsService {
    private static final Gson GSON = new Gson();
    private final Logger logger = LoggerFactory.getLogger(TranslationsService.class);

    private List<Translation> translations = new ArrayList<>();

    public TranslationsServiceImpl() {
        translations = getTranslations();
    }

    @Override
    public List<Translation> getTranslations() {
        List<Translation> translations = new ArrayList<>();
        for (Language language : Language.values()) {
            Translation translation = getTranslation(language);
            if (translation != null) {
                translations.add(translation);
            }
        }
        return translations;
    }

    @Override
    public Translation getTranslation(Language language) {
        String fileName = language.name().toLowerCase() + ".json";
        try {
            Map<String, String> strings = GSON.fromJson(new FileReader(fileName), Map.class);
            if (strings != null) {
                return new Translation(language, strings);
            }
        } catch (FileNotFoundException e) {
            logger.warn("Language file missing: " + fileName);
        }
        return null;
    }
}
