package net.miniscape.service.translations;

import net.miniscape.translation.Language;
import net.miniscape.translation.Translation;

import java.util.List;

public interface TranslationsService {
    List<Translation> getTranslations();

    Translation getTranslation(Language language);
}