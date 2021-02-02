package net.miniscape.service.translations;

import net.miniscape.translation.Language;
import net.miniscape.translation.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TranslationsController {
    @Autowired
    private TranslationsService service;

    @GetMapping(value = "/translations")
    public ResponseEntity<List<Translation>> getTranslations() {
        return new ResponseEntity(service.getTranslations(), HttpStatus.OK);
    }
}