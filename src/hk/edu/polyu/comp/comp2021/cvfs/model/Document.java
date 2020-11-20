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
     * get the level index of this unit;
     */
    @Override
    public int getLevel() {
        Unit temp = this;
        int level = 0;
        while (temp.getParent() != null) {
            temp = temp.getParent();
            level++;
        }
        return level;
    }

    /**
     * Format the output
     *
     * @return the formatted output.
     */
    @Override
    public String toString() {
        String str = "Document " + getName() + '.' + getType() + ", size = " + getSize() + "\n";
        if (content.isEmpty() || content == null)
            str = str + "\033[31m[No Content]\033[0m";
        else
            str = str + "\033[32m" + content + "\033[0m";
        return str;
    }

}
