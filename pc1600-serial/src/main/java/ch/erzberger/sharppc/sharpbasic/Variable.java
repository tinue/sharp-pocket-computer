package ch.erzberger.sharppc.sharpbasic;

/**
 * Represents the name of a variable. A variable name must start with a capital letter.
 * The 2nd digit can be another capital letter, or a single digit number.
 * Any letter or number afterwards is part of the variable name, but will be ignored.
 * Therefore, "A1" and "A1A" are the same variable.
 *
 * A String variable has a dollar at the end.
 */
public class Variable extends Token{
    private final String variableName;
    public Variable(String input) {
        String foundVariableName = findSubstring(input, "^[A-Z][A-Z0-9]*\\$?");
        if (foundVariableName == null) {
            // Not a Variable name
            this.variableName=null;
            setInputMinusToken(input);
        } else {
            setInputMinusToken(input.substring(foundVariableName.length()));
            this.variableName = foundVariableName;
            validate();
        }
    }
    @Override
    public String getNormalizedRepresentation() {
        return variableName;
    }
}
