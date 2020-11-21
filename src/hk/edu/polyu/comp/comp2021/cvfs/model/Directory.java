package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.HashMap;
import java.util.Map;

/**
 *  This class implement directory that can stored documents or other directory
 *  to form a file system's storage tree with disk as root.
 *  This class also provids methods for revision, move, copy, delete, list, search and so on.
 */
public class Directory extends Unit {
    /**
     * The contents in the directory.
     */
    private final Map<String, Unit> catalog = new HashMap<>();

    /**
     * Stores the reference to the current disk.
     */
    private Directory currDisk;

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
        return "\033[32m" + "├" + this.getName() + " " + this.getSize() + "\033[0m";
    }

    /**
     * @return The catalog of the current directory.
     */
    public Map<String, Unit> getCatalog() {
        return this.catalog;
    }

    /**
     * Make a new directory in the current directory.
     * Print a warning and return if the argument is invalid.
     * First update the size of the current directory, then create the file.
     *
     * @param name The name of the new directory.
     * @return The reference to the new directory.
     */
    public Directory newDir(String name) {
        if (!isValidName(name)) {
            //TODO: Change Error message into exception throws. No need to add the "Error:" prefix and color change. No return statement needed.
            throw new IllegalArgumentException("Error: Invalid Argument.");
        }
        if (this.getCatalog().get(name) != null) {
            throw new IllegalArgumentException("A directory with the same name already exists");
        }
        this.getCatalog().put(name, new Directory(name, this));
        updateSizeBy(this.getCatalog().get(name).getSize());
        return (Directory) this.getCatalog().get(name);
    }

    /**
     * Make a new document in the directory.
     * Print a warning and return if one of the arguments is invalid.
     * First update the size of the current directory, then create the file.
     *
     * @param name    The name of the document.
     * @param type    The type of the document.
     * @param content The content of the document.
     * @return The reference to the new Document.
     */
    public Document newDoc(String name, DocType type, String content) {
        if (!isValidName(name) || type == null || content == null) {
            throw new IllegalArgumentException("Error: Invalid Argument.");
        }
        if (this.getCatalog().get(name) != null) {
            throw new IllegalArgumentException("A document with the same name already exists");
        }
        this.getCatalog().put(name, new Document(name, this, type, content));
        updateSizeBy(this.getCatalog().get(name).getSize());
        return (Document) this.getCatalog().get(name);
    }

    /**
     * Delete a file in the current directory and move it to bin.
     * Print a warning and return if there is no such file.
     * Then update the size of current directory
     *
     * @param name The name of the file to be deleted.
     */
    public void mv2bin(String name) {
        //TODO need modify!!!!
        if (this.getCatalog().get(name) == null) {
            System.out.println("\033[31m" + "Error: No such document/directory exit." + "\033[0m");
            return;
        }
        move((Directory) currDisk.getCatalog().get("Bin"), name,false);
        updateSizeBy(-this.getCatalog().get(name).getSize());
        this.getCatalog().remove(name);
    }

    /**
     * Delete a file in the current directory and move it to bin.
     * Print a warning and return if there is no such file.
     * Then update the size of current directory
     *
     * @param name The name of the file to be deleted.
     */
    public void delete(String name) {
        if (this.getCatalog().get(name) == null) {
            throw new IllegalArgumentException("Error: Can't find "+name+" in this directory.");
        }
        updateSizeBy(-this.getCatalog().get(name).getSize());
        this.getCatalog().remove(name);
    }

    /**
     * Move a document/directory to another directory.
     *
     * @param other Another directory.
     * @param name The moving document/directory.
     * @param delete If move set ture, if copy set false.
     */
    public void move(Directory other, String name, boolean delete) {
        if (this.getCatalog().get(name) == null) {
            throw new IllegalArgumentException("Error: Can't find "+name+" in this directory.");
        }

        if (other.getCatalog().get(name) != null) {
            throw new IllegalArgumentException("A file with the same name already exists in the destination directory");
        }

        if (this.getCatalog().get(name) instanceof Directory) {
            Directory tempDir = (Directory) this.getCatalog().get(name);
            other.getCatalog().put(name, tempDir);
        }
        else {
            Document tempDoc = (Document) this.getCatalog().get(name);
            other.getCatalog().put(name, tempDoc);
        }
        updateSizeBy(this.getCatalog().get(name).getSize());
        if(delete)
            this.delete(name);
    }

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
            throw new IllegalArgumentException("Error: Can't find "+oldName+" in this directory.");
        }
        if (!isValidName(newName)) {
            throw new IllegalArgumentException("Error: Invalid Argument.");
        }
        if (this.getCatalog().get(newName) != null ) {
            throw new IllegalArgumentException("A file with the same new name already exists in this directory");
        }
        Unit renamedItem;
        renamedItem = this.getCatalog().get(oldName);
        this.getCatalog().get(oldName).setName(newName);
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
            System.out.println("\033[31m" + "There is no files/directories in current directory!" + "\033[0m");
            return;
        }
        System.out.print("\033[45m" + "Total size: ");
        System.out.println(this.getSize() + "\033[0m");
        for (String name : this.getCatalog().keySet()) {
            System.out.println(this.getCatalog().get(name));
        }
    }


    /**
     * Recursively list the files in the directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     */
    public void down_rList() {
        if (this.getCatalog().isEmpty()) {
            System.out.println("\033[31m" + "There is no files/directories in current directory!" + "\033[0m");
            return;
        }
        down_rList(this, 0);
    }

    /**
     * Recursively list the files in the directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     *
     * @param currDir The currDir of each recursive level.
     * @param level The level of each recursive.
     */
    public void down_rList(Directory currDir, int level) {
        for (String name : currDir.getCatalog().keySet()) {
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            System.out.println(currDir.getCatalog().get(name));
        }
    }

    /**
     * Recursively list the files from disk(root) to this directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     */
    public void up_rList() {
        if (this.getParent() == null) {
            System.out.println("\033[31m" + "You are already in the root directory！" + "\033[0m");
            return;
        }
        up_rList(this);
    }

    /**
     * Recursively list the files from disk(root) to this directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     *
     * @param currDir The current Directory of each recursive level.
     */
    public void up_rList(Directory currDir) {
        if (currDir.getParent() == null) {
            System.out.println("\033[32m" + currDir.getName() + "\033[0m");
            return;
        }

        up_rList(currDir.getParent());

        Directory parent = currDir.getParent();
        for (String name : parent.getCatalog().keySet()) {
            if (!name.equals(currDir.getName())) {
                for (int i = 0; i < currDir.getLevel(); i++) {
                    System.out.print("\t");
                }
                System.out.println(parent.getCatalog().get(name));
            }
        }
        for (int i = 0; i < currDir.getLevel(); i++) {
            System.out.print("\t");
        }

        if (currDir.getLevel() == this.getLevel()) {
            System.out.println("├" + currDir.getName() + " " + currDir.getSize() + "(Current Directory)");
            for (String name : this.getCatalog().keySet()) {
                for (int i = 0; i < currDir.getLevel() + 1; i++) {
                    System.out.print("\t");
                }
                System.out.println(this.getCatalog().get(name));
            }

        } else
            System.out.println("\033[32m" + "├" + currDir.getName() + " " + currDir.getSize() + "\033[0m");
    }

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
        for (Unit unit : getCatalog().values()) {
            if (criName.check(unit)) {
                System.out.println(unit);
            }
        }
    }

    /**
     * A rList with a filter.
     *
     * @param criName The filter.
     */
    public void rSearch(Criterion criName) {
        if (this.getCatalog().isEmpty()) {
            System.out.println("\033[31m" + "There is no files/directories in current directory!" + "\033[0m");
            return;
        }
        rSearch(this,0,criName);
    }

    /**
     * A rList with a filter.
     *
     * @param criName The filter.
     * @param currDir The current Directory of each recursive level.
     * @param level The level of each recursive.
     */
    public static void rSearch(Directory currDir, int level, Criterion criName) {
        for (Unit unit : currDir.getCatalog().values()) {
            if (criName.check(unit)) {
                for (int i = 0; i < level; i++) System.out.print("\t");
                System.out.println(unit.toString());
            }
        }
    }

    /**
     *
     * @param args blah~blah~
     */
    public static void main(String[] args) {
        final int CAPACITY =  999999;
        Directory root = new Disk(CAPACITY);
        Directory desktop = root.newDir("Desktop");
        Directory documents = root.newDir("Documents");
        Directory downloads = root.newDir("Downloads");

        Directory oop = desktop.newDir("OOP");
        Directory nlp = desktop.newDir("NLP");
        Directory cv = desktop.newDir("CV");
        Document notes = desktop.newDoc("notes", DocType.TXT, "This is the papers.css in /Desktop/NLP");

        Directory gpProj = oop.newDir("gpProj");
        Document requirements = gpProj.newDoc("requirements", DocType.TXT, "This is the assignment.txt in /Desktop/OOP/gpProj/");
        Document example = gpProj.newDoc("example", DocType.JAVA, "This is the example.java in /Desktop/OOP/gpProj/");
        Document outline = oop.newDoc("outline", DocType.HTML, "This is the outline.html in /Desktop/OOP/");

        Directory data = nlp.newDir("data");
        Document embedding = data.newDoc("embedding", DocType.TXT, "This is the embedding.txt in /Desktop/NLP/data/");
        Document train = data.newDoc("train", DocType.TXT, "This is the train.txt in /Desktop/NLP/data/");
        Document test = data.newDoc("test", DocType.TXT, "This is the test.txt in /Desktop/NLP/data/");
        Document paper = data.newDoc("papers", DocType.CSS, "This is the papers.css in /Desktop/NLP");

        Document ToAplus = documents.newDoc("ToAplus", DocType.TXT, "This is the notes.txt in /Documents/");

        Directory download1 = downloads.newDir("download1");
        Directory download2 = downloads.newDir("download2");

        root.down_rList();

    }

    /**
     * Recursively update the size of the directory by a certain number.
     * First update the size of parent, then the current directory.
     *
     * @param offset Positive if the size increases, vice versa.
     */
    public void updateSizeBy(int offset) {
        setSize(getSize() + offset);
        getParent().updateSizeBy(offset);
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
}