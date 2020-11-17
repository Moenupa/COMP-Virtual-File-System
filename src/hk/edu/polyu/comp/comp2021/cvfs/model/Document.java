package hk.edu.polyu.comp.comp2021.cvfs.model;

public class Document extends Unit {
    /**
     * The type of the document.
     */
    private DocType type;

    /**
     * The content of the document.
     */
    private String content;

    /**
     * Construct a new document file.
     *
     * @param name    The name of the doc.
     * @param parent  The parent of the doc.
     * @param type    The type of the document.
     * @param content The content of the document.
     */
    public Document(String name, Unit parent, DocType type, String content) {
        super(name, parent);
        this.type = type;
        this.content = content;
        setSize(40 + content.length() * 2);
    }

    /**
     * @return The type of the document.
     */
    public DocType getType() {
        return type;
    }

    /**
     * @return The type of the document.
     */
    public DocType getType() {
        return type;
    }

    /**
     * Format the output
     *
     * @return the formatted output.
     */
    @Override
    public String toString() {
        String str;
        str = "Document " + getName() + '.' + getType() + ", size = " + getSize() + "\n"
                + "\033[32m" + content;
        return str;
    }

    public static void main(String[] args) {
        Document newdoc = new Document("mydoc", null, DocType.TXT, "content");
        System.out.println(newdoc.toString());
    }

    /**
     * Invalid for this type.
     *
     * @param offset Positive if the size increases, vice versa.
     */
    public void updateSizeBy(int offset) {
        System.out.println("Error: Access Denied.");
    }
}
