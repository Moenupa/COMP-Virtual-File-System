package hk.edu.polyu.comp.comp2021.cvfs.model;

/**
 * Four different types of a document file.
 */
public enum DocType {
    /**
     * Plain text file.
     */
    TXT("txt"),
    /**
     * Java source code file.
     */
    JAVA("java"),
    /**
     * Hypertext Markup Language file.
     */
    HTML("html"),
    /**
     * Cascading Style Sheets file.
     */
    CSS("css");

    /**
     * To be displayed when a list command is called.
     */
    private final String type;

    /**
     * Link the enum with the text.
     */
    DocType(String type) {
        this.type = type;
    }

    /**
     * Check whether type is valid or not
     *
     * @param type The string to be checked.
     * @return True if the type is valid.
     */
    public static boolean isValidDocType(String type) {
        return type.matches("^(txt|css|java|html)$");
    }

    /**
     * Parse the string and return a DocType. Return null if the string is invalid
     *
     * @param s The string to be parsed
     * @return The DocType of the string.
     */
    public static DocType parse(String s) {
        switch (s) {
            case "txt":
                return TXT;
            case "java":
                return JAVA;
            case "css":
                return CSS;
            case "html":
                return HTML;
            default:
                return null;

        }
    }

    /**
     * Return the text form of the enum
     *
     * @return The text of the enum.
     */
    public String toString() {
        return type;
    }


}
