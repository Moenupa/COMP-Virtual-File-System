package hk.edu.polyu.comp.comp2021.cvfs.model;

/**
 * This class implements document stored in the system,
 * and a <code>Docuemnt</code> is a Leaf on the system's storage tree.
 * Uses s constructor, several getter/setter methods, and
 * a <code>toString</code> method to fully implement a document.
 */
public class Document extends Unit {
    /**
     * The type of the document.
     */
    private DocType type;

    /**
     * A reference to the parent directory. Not null except for the disk.
     */
    private Directory parent;

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
        super(name);
        setParent(parent);
        this.type = type;
        this.content = content;
        setSize(SIZE_CONSTANT + content.length() * 2);
    }

    /**
     * @return The type of the document.
     */
    public DocType getType() {
        return type;
    }

    /**
     * @return The reference to the parent.
     */
    @Override
    public Directory getParent() {
        return parent;
    }

    /**
     * Set a new parent to the current unit.
     *
     * @param newParent The new parent of the unit.
     */
    @Override
    public void setParent(Unit newParent) {
        parent = (Directory) newParent;
    }

    /**
     * Format the output
     *
     * @return the formatted output in white.
     */
    @Override
    public String toString() {
        return String.format("%-14s \033[33m%s\033[0m", this.getName() + "." + this.getType(), this.getSize());
    }

}
