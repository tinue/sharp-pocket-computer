package ch.erzberger.sharppc.sharpbasic;

import lombok.Getter;

@Getter
public class BasicKeyword {
    private final String name;
    private final String abbreviation;
    private final int code;

    public BasicKeyword(String name, String abbreviation, int code) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.code = code;
    }

    public BasicKeyword(String name, int code) {
        this(name, name, code);
    }

    public String getAbbreviation() {
        String candidate = abbreviation + ".";
        // If the abbreviation plus the dot is not actually shorter, then return the original.
        if (candidate.length() >= name.length()) {
            return name;
        } else {
            return candidate;
        }
    }
}
