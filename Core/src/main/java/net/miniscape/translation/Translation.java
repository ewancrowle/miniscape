package net.miniscape.translation;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Translation {
    private final Language language;
    private final Map<String, String> strings;
}
