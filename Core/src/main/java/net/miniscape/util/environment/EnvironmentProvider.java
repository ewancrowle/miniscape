package net.miniscape.util.environment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnvironmentProvider {
    private String envVar;

    public String get() {
        String value = System.getenv(envVar);
        if (value == null) {
            throw new IllegalStateException("No " + envVar + " environment variable was provided.");
        }
        return value;
    }
}
