package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;
import ch.erzberger.sharppc.LineNormalizeHelper;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Level;

/**
 * One line of a Basic program.
 * A line consists of a LineNumber, and a list of Statements.
 * The Line also takes care of calculating the length of the line, which becomes part of the binary representation
 * of the LineNumber.
 */
@Log
public class Line extends Token {
    private final LineNumber lineNumber;
    private final List<Statement> statements = new ArrayList<>();
    private final String rawComment;

    public Line(String input, PocketPcDevice device) {
        // Before parsing a line, cleanup the extra white space
        var cleanInput = LineNormalizeHelper.cleanupWhiteSpace(input);
        // Check if this line is a comment
        if (!Character.isDigit(cleanInput.charAt(0))) {
            this.rawComment = input;
            this.lineNumber = null;
            validate();
            setInputMinusToken("");
        } else {
            rawComment = null; // Not a comment
            // First, extract the line number from the start of the String
            this.lineNumber = new LineNumber(cleanInput);
            if (!lineNumber.isValid()) {
                // The line does not start with a line number, and therefore can't be valid
                log.log(Level.WARNING, "Line does not start with a line number: {0}", input);
                setInputMinusToken(cleanInput);
            } else {
                // Line number is valid, split the rest of the line into Statements
                List<String> statementsAsString = split(lineNumber.getInputMinusToken());
                for (String statementAsString : statementsAsString) {
                    Statement statement = new Statement(statementAsString, device);
                    if (!statement.isValid()) {
                        log.log(Level.WARNING, "The line started ok, but this is not a statement: {0}", statementAsString);
                        setInputMinusToken(cleanInput);
                        return; // Abort, the line is invalid and must be ignored.
                    }
                    statements.add(statement);
                }
                // If we arrive here, line number and all statements are valid
                validate();
                setInputMinusToken(""); // Since a line consists of nothing but line number and statements, we are finished
            }
        }
    }

    @Override
    public String getNormalizedRepresentation() {
        if (rawComment != null) {
            return rawComment;
        }
        StringJoiner joiner = new StringJoiner(":");
        for (Statement statement : statements) {
            joiner.add(statement.getNormalizedRepresentation());
        }
        return lineNumber.getNormalizedRepresentation() + joiner.toString().trim();
    }

    @Override
    public String getShortRepresentation() {
        if (rawComment != null) {
            return rawComment;
        }
        StringJoiner joiner = new StringJoiner(":");
        for (Statement statement : statements) {
            joiner.add(statement.getShortRepresentation());
        }
        return lineNumber.getShortRepresentation() + joiner.toString().trim();
    }

    @Override
    public byte[] getBinaryRepresentation() {
        if (rawComment != null) {
            return new byte[0];
        }
        byte[] line = new byte[0];
        for (int numStatement = 0; numStatement < statements.size(); numStatement++) {
            Statement statement = statements.get(numStatement);
            line = appendBytes(line, statement.getBinaryRepresentation());
            if (numStatement < statements.size() - 1) {
                line = appendBytes(line, new byte[]{0x3A}); // Add the colon
            }
        }
        // Add the carriage return
        line = appendBytes(line, new byte[]{0x0D});
        byte[] length = convertIntToTwoByteByteArray(line.length);
        byte[] retVal = lineNumber.getBinaryRepresentation();
        retVal[2] = length[1];
        retVal = appendBytes(retVal, line);
        return retVal;
    }

    /**
     * Special helper to split a line into statements, ignoring the delimiter inside of quotes
     * @param input String to split
     * @return Statements
     */
    private List<String> split(String input) {
        List<String> result = new ArrayList<>();
        int start = 0;
        boolean inQuotes = false;
        for (int current = 0; current < input.length(); current++) {
            if (input.charAt(current) == '\"') inQuotes = !inQuotes; // toggle state
            else if (input.charAt(current) == ':' && !inQuotes) {
                result.add(input.substring(start, current));
                start = current + 1;
            }
        }
        result.add(input.substring(start));
        return result;
    }
}
