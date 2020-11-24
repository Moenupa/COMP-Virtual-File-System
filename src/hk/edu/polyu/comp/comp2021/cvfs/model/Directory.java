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

// --Commented out by Inspection START (2020/11/23 22:55):
//    /**
//     * Stores the reference to the current disk.
//     */
//    private Directory currDisk;
// --Commented out by Inspection STOP (2020/11/23 22:55)

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
        this.setSize(SIZE_CONSTANT);
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
        return this.catalog;
    }

    /**
     * Make a new directory in the current directory.
     * First update the size of the current directory, then create the file.
     *
     * @param name The name of the new directory.
     * @return The reference to the new directory.
     */
    public Directory newDir(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid Argument.");
        }
        if (this.getCatalog().get(name) != null) {
            throw new IllegalArgumentException("A file with the same name already exists");
        }
        Directory tmp = new Directory(name, this);
        this.getCatalog().put(name, tmp);
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
        if (!isValidName(name) || type == null || content == null) {
            throw new IllegalArgumentException("Invalid Argument.");
        }
        if (this.getCatalog().get(name) != null) {
            throw new IllegalArgumentException("A file with the same name already exists.");
        }
        Document tmp = new Document(name, this, type, content);
        this.getCatalog().put(name, tmp);
        TraceLogger.getInstance().newLog(TraceLogger.OpType.DEL, tmp, this);
        updateSizeBy(tmp.getSize());
        return tmp;
    }

// --Commented out by Inspection START (2020/11/23 22:55):
//    /**
//     * Delete a file in the current directory and move it to bin.
//     * Print a warning and return if there is no such file.
//     * Then update the size of current directory
//     *
//     * @param name The name of the file to be deleted.
//     */
//    public void mv2bin(String name) {
//        if (this.getCatalog().get(name) == null) {
//            System.out.println("No such document/directory exit.");
//            return;
//        }
//        move((Directory) currDisk.getCatalog().get("Bin"), name, false);
//        updateSizeBy(-this.getCatalog().get(name).getSize());
//        this.getCatalog().remove(name);
//    }
// --Commented out by Inspection STOP (2020/11/23 22:55)

    /**
     * Delete a file in the current directory and move it to bin.
     * Print a warning and return if there is no such file.
     * Then update the size of current directory
     *
     * @param name The name of the file to be deleted.
     */
    public void delete(String name) {
        if (this.getCatalog().get(name) == null) {
            throw new IllegalArgumentException("Error: Can't find " + name + " in this directory.");
        }
        updateSizeBy(-this.getCatalog().get(name).getSize());
        TraceLogger.getInstance().newLog(TraceLogger.OpType.ADD, getCatalog().get(name), this);
        this.getCatalog().remove(name);
    }

// --Commented out by Inspection START (2020/11/23 22:56):
//    /**
//     * Move a document/directory to another directory.
//     *
//     * @param other  Another directory.
//     * @param name   The moving document/directory.
//     * @param delete If move set ture, if copy set false.
//     */
//    public void move(Directory other, String name, boolean delete) {
//        if (this.getCatalog().get(name) == null) {
//            throw new IllegalArgumentException("Can't find " + name + " in this directory.");
//        }
//
//        if (other.getCatalog().get(name) != null) {
//            throw new IllegalArgumentException("A file with the same name already exists in the destination directory");
//        }
//
//        if (this.getCatalog().get(name) instanceof Directory) {
//            Directory tempDir = (Directory) this.getCatalog().get(name);
//            other.getCatalog().put(name, tempDir);
//        } else {
//            Document tempDoc = (Document) this.getCatalog().get(name);
//            other.getCatalog().put(name, tempDoc);
//        }
//        updateSizeBy(this.getCatalog().get(name).getSize());
//        if (delete)
//            this.delete(name);
//    }
// --Commented out by Inspection STOP (2020/11/23 22:56)

    /**
     * Rename a file in the current directory.
     * Print a warning and return is there is no such file OR
     * the new name is invalid OR
     * There exists some file with the same name.
     *
     * @param oldName The old name of the file.
     * @param newName The new name of the file.
     */
    public void rename(String oldName, String newName) {
        if (this.getCatalog().get(oldName) == null) {
            throw new IllegalArgumentException("Can't find " + oldName + " in this directory.");
        }
        if (!isValidName(newName)) {
            throw new IllegalArgumentException("Invalid new name " + newName);
        }
        if (this.getCatalog().get(newName) != null) {
            throw new IllegalArgumentException("A file with the same new name already exists in this directory");
        }
        Unit renamedItem;
        renamedItem = this.getCatalog().get(oldName);
        renamedItem.setName(newName);
        TraceLogger.getInstance().newLog(TraceLogger.OpType.REN, oldName, newName);
        this.getCatalog().remove(oldName);
        this.getCatalog().put(newName, renamedItem);

    }

    /**
     * List all files in the directory and report the total number and size of files listed.
     * For each document, list the name, type, and size.
     * For each directory, list the name and size.
     */
    public void list() {
        if (this.getCatalog().isEmpty()) {
            System.out.println("\033[31m" + "Warning: No files/folders in the current direcotry" + "\033[0m");
            return;
        }
        System.out.println("\033[4m" + this);
        for (Unit unit : getCatalog().values()) {
            System.out.println(" ╞═ " + unit);
        }
    }

    /**
     * Recursively list the files in the directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     */
    public void down_rList() {
        if (this.getCatalog().isEmpty()) {
            System.out.println("\033[31m" + "Warning: No files/folders in the current direcotry" + "\033[0m");
            return;
        }
        System.out.println("\033[4m" + this);
        down_rList(this, 0);
    }

    /**
     * Recursively list the files in the directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     *
     * @param currDir The currDir of each recursive level.
     * @param level   The level of each recursive.
     */
    public void down_rList(Directory currDir, int level) {
        for (Unit unit : currDir.getCatalog().values()) {
            for (int i = 0; i < level; i++)
                System.out.print("\t");
            System.out.println(" ╞═ " + unit);
            if (unit instanceof Directory)
                down_rList((Directory) unit, level + 1);
        }
    }

//    /**
//     * Recursively list the files from disk(root) to this directory.
//     * Use indentation to indicate the level of each line.
//     * Report the total number and size of files listed.
//     */
//    public void up_rList() {
//        if (this.getParent() == null) {
//            System.out.println("\033[31m" + "Warning: Already in the root directory！" + "\033[0m");
//            return;
//        }
//        up_rList(this);
//    }

//    /**
//     * Recursively list the files from disk(root) to this directory.
//     * Use indentation to indicate the level of each line.
//     * Report the total number and size of files listed.
//     *
//     * @param currDir The current Directory of each recursive level.
//     */
    /*
    public void up_rList(Directory currDir) {
        if (currDir.getParent() == null) {
            System.out.println("\033[32m" + currDir.getName() + "\033[0m");
            return;
        }

        up_rList(currDir.getParent());

        Directory parent = currDir.getParent();
        parent.getCatalog().forEach((name, unit) ->
                {
                    if (!name.equals(currDir.getName())) {
                        for (int i = 0; i < currDir.getLevel(); i++)
                            System.out.print("\t");
                        System.out.println(unit);
                    }
                }
        );

        for (int i = 0; i < currDir.getLevel(); i++) {
            System.out.print("\t");
        }

        if (currDir.getLevel() == this.getLevel()) {
            System.out.println(currDir.toString() + "(Current Directory)");
            for (Unit unit : getCatalog().values()) {
                for (int i = 0; i < currDir.getLevel() + 1; i++)
                    System.out.print("\t");
                System.out.println(unit);
            }
        } else {
            System.out.println(currDir.toString());
        }
    }
*/
    /**
     * A list with a filter.
     *
     * @param criName The filter.
     */
    public void search(Criterion criName) {
        if (getCatalog().isEmpty()) {
            System.out.println("\033[31m" + "There are no files in current directory!" + "\033[0m");
            return;
        }
        System.out.println("\033[4m" + this);
        for (Unit unit : getCatalog().values())
            if (criName.check(unit))
                System.out.println(unit);
    }

    /**
     * A rList with a filter.
     *
     * @param criName The filter.
     */
    public void rSearch(Criterion criName) {
        if (this.getCatalog().isEmpty()) {
            System.out.println("\033[31m" + "There are no files/directories in current directory!" + "\033[0m");
            return;
        }
        System.out.println("\033[4m" + this);
        rSearch(this, criName);
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
