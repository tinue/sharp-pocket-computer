package ch.erzberger.sharppc.sharpbasic;

/**
 * Top-level token, represents the Basic program is its entirety
 */
public class Program extends Token{

    public Program(String name) {
    }

    @Override
    public String getNormalizedRepresentation() {
        return "";
    }
}
