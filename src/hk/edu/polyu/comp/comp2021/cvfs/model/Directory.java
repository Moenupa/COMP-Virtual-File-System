package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implement directory that can stored documents or other directory
 * to form a file system's storage tree with disk as root.
 * This class also provids methods for revision, move, copy, delete, list, search and so on.
 */
public class Directory extends Unit {
    /**
     * The contents in the directory.
     */
    private final Map<String, Unit> catalog = new HashMap<>();
    private static final String noFileWarningMessage
            = "\033[31mWarning: No files/folders in the current direcotry\033[0m";
    private static final String unchangedRenameWarningMessage
            = "\033[31mWarning: File name unchanged during rename.\033[0m";

    /**
     * A reference to the parent directory. Not null except for the disk.
     */
    private Directory parent;

    /**
     * Construct a new directory.
     *
     * @param name   The name of the directory.
     * @param parent The parent of this directory.
     */
    public Directory(String name, Unit parent) {
        super(name);
        setParent(parent);
        setSize(SIZE_CONSTANT);
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
     * @return the formatted output in green.
     */
    @Override
    public String toString() {
        return String.format("\033[32m%-14s \033[33m%d\033[0m", getName(), getSize());
    }

    /**
     * @return The catalog of the current directory.
     */
    public Map<String, Unit> getCatalog() {
        return catalog;
    }

    /**
     * Make a new directory in the current directory.
     * First update the size of the current directory, then create the file.
     *
     * @param name The name of the new directory.
     * @return The reference to the new directory.
     */
    public Directory newDir(String name) {
        if (catalog.get(name) != null)
            throw new IllegalArgumentException("A file with the same name already exists");
        Directory tmp = new Directory(name, this);
        catalog.put(name, tmp);
        TraceLogger.getInstance().newLog(TraceLogger.OpType.DEL, tmp, this);
        updateSizeBy(tmp.getSize());
        return tmp;
    }

    /**
     * Make a new document in the directory.
     * First update the size of the current directory, then create the file.
     *
     * @param name    The name of the document.
     * @param type    The type of the document.
     * @param content The content of the document.
     * @return The reference to the new Document.
     */
    public Document newDoc(String name, DocType type, String content) {
        if (catalog.get(name) != null)
            throw new IllegalArgumentException("A file with the same name already exists.");
        Document tmp = new Document(name, this, type, content);
        catalog.put(name, tmp);
        TraceLogger.getInstance().newLog(TraceLogger.OpType.DEL, tmp, this);
        updateSizeBy(tmp.getSize());
        return tmp;
    }

    /**
     * Delete a file in the current directory and move it to bin.
     * Print a warning and return if there is no such file.
     * Then update the size of current directory
     *
     * @param name The name of the file to be deleted.
     */
    public void delete(String name) {
        if (catalog.get(name) == null) {
            throw new IllegalArgumentException("Can't find " + name + " in this directory.");
        }
        updateSizeBy( - catalog.get(name).getSize());
        TraceLogger.getInstance().newLog(TraceLogger.OpType.ADD, catalog.get(name), this);
        catalog.remove(name);
    }

    /**
     * Rename a file in the current directory.
     * Print a warning and return is there is no such file OR
     * There exists some file with the same name.
     *
     * @param oldName The old name of the file.
     * @param newName The new name of the file.
     */
    public void rename(String oldName, String newName) {
        if (catalog.get(oldName) == null)
            throw new IllegalArgumentException("Can't find " + oldName + " in this directory.");
        if (catalog.get(newName) != null)
            throw new IllegalArgumentException("A file with the same new name already exists in this directory");

        if (newName.equals(oldName)) {
            System.out.println(unchangedRenameWarningMessage);
        }

        Unit renamedItem = catalog.get(oldName);
        renamedItem.setName(newName);
        TraceLogger.getInstance().newLog(TraceLogger.OpType.REN, oldName, newName);
        catalog.remove(oldName);
        catalog.put(newName, renamedItem);

    }

    /**
     * List all files in the directory and report the total number and size of files listed.
     * For each document, list the name, type, and size.
     * For each directory, list the name and size.
     */
    public void list() {
        if (catalog.isEmpty()) {
            System.out.println(noFileWarningMessage);
            return;
        }
        System.out.println("\033[4m" + this);
        for (Unit unit : catalog.values()) {
            System.out.println(" ╞═ " + unit);
        }
    }

    /**
     * Recursively list the files in the directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     */
    public void rList() {
        if (catalog.isEmpty()) {
            System.out.println(noFileWarningMessage);
            return;
        }
        System.out.println("\033[4m" + this);
        rList(this, 0);
    }

    /**
     * Recursively list the files in the directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     *
     * @param currDir The currDir of each recursive level.
     * @param level   The level of each recursive.
     */
    public void rList(Directory currDir, int level) {
        for (Unit unit : currDir.getCatalog().values()) {
            for (int i = 0; i < level; i++)
                System.out.print("\t");
            System.out.println(" ╞═ " + unit);
            if (unit instanceof Directory)
                rList((Directory) unit, level + 1);
        }
    }

    /**
     * A list with a filter.
     *
     * @param criterion The filter.
     */
    public void search(Criterion criterion) {
        if (catalog.isEmpty()) {
            System.out.println(noFileWarningMessage);
            return;
        }
        System.out.println("\033[4m" + this);
        for (Unit unit : catalog.values())
            if (criterion.check(unit))
                System.out.println(unit);
    }

    /**
     * A rList with a filter.
     *
     * @param criterion The filter.
     */
    public void rSearch(Criterion criterion) {
        if (catalog.isEmpty()) {
            System.out.println(noFileWarningMessage);
            return;
        }
        System.out.println("\033[4m" + this);
        rSearch(this, criterion);
    }
    
    /**
     * A rList with a filter.
     *
     * @param criName The filter.
     * @param currDir The current Directory of each recursive level.
     */
    public static void rSearch(Directory currDir, Criterion criName) {
        for (Unit unit : currDir.getCatalog().values()) {
            if (criName.check(unit))
                System.out.println(unit);
            if (unit instanceof Directory)
                rSearch((Directory) unit, criName);
        }
    }
    
    /**
     * Recursively update the size of the directory by a certain number.
     * First update the size of parent, then the current directory.
     *
     * @param offset Positive if the size increases, vice versa.
     */
    public void updateSizeBy(int offset) {
        getParent().updateSizeBy(offset);
        setSize(getSize() + offset);
    }

    /**
     * Get the full path of the current directory.
     *
     * @return The StringBuilder containing the full path
     */
    public StringBuilder getPath() {
        StringBuilder str = getParent().getPath();
        str.append(':').append(getName());
        return str;
    }
}
