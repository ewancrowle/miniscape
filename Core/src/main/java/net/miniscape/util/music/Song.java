package net.miniscape.util.music;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Song {
    NEW_HORIZONS_THEME("new_horizons_theme.midi"),
    BUBBLEGUM_KK("bubblegum_kk.midi");

    public static final String DEFAULT_FILE_LOCATION = "/midi/";
    private final String fileName;

    public Optional<File> getDefaultFile() {
        File file = new File(DEFAULT_FILE_LOCATION, fileName);
        if (file.exists()) {
            return Optional.of(file);
        }
        return Optional.empty();
    }
}