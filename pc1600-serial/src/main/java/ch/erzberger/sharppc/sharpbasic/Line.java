package ch.erzberger.sharppc.sharpbasic;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * One line of a Basic program
 */
@Log public class Line extends Token{
    private final LineNumber lineNumber;
    private final List<Statement> statements = new ArrayList<>();

    public Line(String input) {
        this.lineNumber = new LineNumber(input);
        if (lineNumber.isValid()) {
            // Split the rest of the line into statements
            String[] statementsAsString = lineNumber.getInputMinusToken().split(":");
            for (String statementAsString : statementsAsString) {
                Statement statement = new Statement(statementAsString);
                if (!statement.isValid()) {
                    return; // The line will remain invalid
                }
                statements.add(statement);
            }
        }
        // If we arrive here, line number and all statements are valid
        validate();
        setInputMinusToken(""); // Since a line consists of nothing but line number and statements, we are finished
    }

    @Override
    public String getNormalizedRepresentation() {
        StringJoiner joiner = new StringJoiner(":");
        for (Statement statement : statements) {
            joiner.add(statement.getNormalizedRepresentation());
        }
        return lineNumber.getNormalizedRepresentation() + joiner.toString();
    }
}
