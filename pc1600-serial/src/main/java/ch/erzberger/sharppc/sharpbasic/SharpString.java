package ch.erzberger.sharppc.sharpbasic;

public class SharpString extends Token{
    private final String sharpString;
    public SharpString(String input) {
        String foundString = findSubstring(input, "^\\\".*\\\"");
        if (foundString == null) {
            // Not a String
            this.sharpString=null;
            setInputMinusToken(input);
        } else {
            setInputMinusToken(input.substring(foundString.length()));
            this.sharpString = foundString.replace("\"", "");
            validate();
        }
    }
    @Override
    public String getNormalizedRepresentation() {
        return "\"" + sharpString + "\"";
    }
}
