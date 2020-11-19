package hk.edu.polyu.comp.comp2021.cvfs.model;
import javax.print.Doc;
import java.util.HashMap;
import java.util.Map;
//import hk.edu.polyu.comp.comp2021.cvfs .model.DocType;

public class Directory extends Unit {
    /**
     * The contents in the directory.
     */
    private final Map<String, Unit> catalog = new HashMap<>();
    Disk currDisk;

    /**
     * Construct a new directory.
     *
     * @param name   The name of the directory.
     * @param parent The parent of this directory.
     */
    public Directory(String name, Unit parent) {
        super(name, parent);
        this.setSize(40);
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
     */
    public Directory newDir(String name) {
        if (!isValidName(name)) {
            System.out.println("\033[31m" + "Error: Invalid Argument." + "\033[0m");
            return null;
        }
        if (this.getCatalog().get(name) != null) {
            System.out.println("\033[31m" + "A file with the same name already exists" + "\033[0m");
            return null;
        }
        this.getCatalog().put(name, new Directory(name, this));
        updateSizeBy(this, this.getCatalog().get(name).getSize());
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
     */
    public Document newDoc(String name, DocType type, String content) {
        if (!isValidName(name) || type == null || content == null) {
            System.out.println("\033[31m" + "Error: Invalid Argument." + "\033[0m");
            return null;
        }
        if (this.getCatalog().get(name) != null) {
            System.out.println("\033[31m" + "A file with the same name already exists" + "\033[0m");
            return null;
        }
        this.getCatalog().put(name, new Document(name, this, type, content));
        updateSizeBy(this, this.getCatalog().get(name).getSize());
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
        updateSizeBy(this, -this.getCatalog().get(name).getSize());
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
        updateSizeBy(this, -this.getCatalog().get(name).getSize());
        if (this.getCatalog().get(name) == null) {
            System.out.println("\033[31m" + "Error: No such document/directory exit." + "\033[0m");
            return;
        }
        this.getCatalog().remove(name);
    }

    /**
     * Move a document/directory to another directory.
     *
     * @param other Another directory.
     * @param name The moving document/directory.
     * @
     */
    public void move(Directory other, String name, boolean delete) {
        if (this.getCatalog().get(name) == null) {
            System.out.println("\033[31m" + "Error: No such document/directory exist." + "\033[0m");
            return;
        }

        if (other.getCatalog().get(name) != null) {
            System.out.println("\033[31m" + "Error: The target directory contains a file with the same name, please rename the file you want to move or delete the file with the same name in the target directory." + "\033[0m");
            return;
        }

        if (this.getCatalog().get(name) instanceof Directory) {
            Directory tempDir = (Directory) this.getCatalog().get(name);
            other.getCatalog().put(name, tempDir);
        }
        else {
            Document tempDoc = (Document) this.getCatalog().get(name);
            other.getCatalog().put(name, tempDoc);
        }
        updateSizeBy(other, this.getCatalog().get(name).getSize());
        if (delete) {
            updateSizeBy(this, -this.getCatalog().get(name).getSize());
            this.getCatalog().remove(name);
        }
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
            System.out.println("\033[31m" + "Error: No such document/directory exist." + "\033[0m");
            return;
        }
        if (this.getCatalog().get(newName) != null ) {
            System.out.println("\033[31m" + "Error: The document/directory whose same is same with the new name has exist." + "\033[0m");
            return;
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
            if (this.getCatalog().get(name) instanceof Directory)
                System.out.println("\033[32m" + "├" + name + " " + this.getCatalog().get(name).getSize() + "\033[0m");
            if (this.getCatalog().get(name) instanceof Document)
                System.out.println("\033[30m" + "├" + name + " " + ((Document) this.getCatalog().get(name)).getType() + " " + this.getCatalog().get(name).getSize() + "\033[0m");
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
    public void down_rList(Directory currDir, int level) {
        for (String name : currDir.getCatalog().keySet()) {
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            if (currDir.getCatalog().get(name) instanceof Document)
                System.out.println("\033[30m" + "├" + name + " " + ((Document) currDir.getCatalog().get(name)).getType() + " " + currDir.getCatalog().get(name).getSize() + "\033[0m");
            if (currDir.getCatalog().get(name) instanceof Directory) {
                System.out.println("\033[32m" + "├" + name + " " + currDir.getCatalog().get(name).getSize() + "\033[0m");
                down_rList((Directory) currDir.getCatalog().get(name), level + 1);
            }
        }
    }

    /**
     * Recursively list the files from disk(root) to this directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     */
    public void up_rList() {
        if (this.getParent() == null) {
            System.out.println("You are already in the root directory！");
            return;
        }
        up_rList(this);
    }
    public void up_rList(Directory currDir) {
        if (currDir.getParent() == null) {
            System.out.println("\033[32m" + currDir.getName() + "\033[0m");
            return;
        }

        up_rList((Directory) currDir.getParent());

        Directory parent = ((Directory) currDir.getParent());
        for (String name : parent.getCatalog().keySet()) {
            if (!name.equals(currDir.getName())) {
                for (int i = 0; i < currDir.getLevel(); i++) {
                    System.out.print("\t");
                }
                if (parent.getCatalog().get(name) instanceof Directory)
                    System.out.println("\033[32m" + "├" + name + " " + parent.getCatalog().get(name).getSize() + "\033[0m");
                if (parent.getCatalog().get(name) instanceof Document)
                    System.out.println("\033[30m" + "├" + name + " " + ((Document)parent.getCatalog().get(name)).getType() + " " + parent.getCatalog().get(name).getSize() + "\033[0m");
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
                if (this.getCatalog().get(name) instanceof Directory)
                    System.out.println("\033[32m" + name + " " + this.getCatalog().get(name).getSize() + "\033[0m");
                if (this.getCatalog().get(name) instanceof Document)
                    System.out.println("\033[30m" + name + " " + ((Document) this.getCatalog().get(name)).getType() + " " + this.getCatalog().get(name).getSize() + "\033[0m");
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
        if (this.getCatalog().isEmpty()) {
            System.out.println("\033[31m" + "There is no files in current directory!" + "\033[0m");
            return;
        }
        for (String name : this.getCatalog().keySet()) {
            if (criName.check(this.getCatalog().get(name))) {
                if (this.getCatalog().get(name) instanceof Directory)
                    System.out.println("\033[32m" + name + " " + this.getCatalog().get(name).getSize() + "\033[0m");
                if (this.getCatalog().get(name) instanceof Document)
                    System.out.println("\033[30m" + name + " " + ((Document) this.getCatalog().get(name)).getType() + " " + this.getCatalog().get(name).getSize() + "\033[0m");
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
    public void rSearch(Directory currDir, int level, Criterion criName) {
        for (String name : currDir.getCatalog().keySet()) {
            if (criName.check(this.getCatalog().get(name))) {
                for (int i = 0; i < level; i++) {
                    System.out.print("\t");
                }
                if (currDir.getCatalog().get(name) instanceof Document)
                    System.out.println("\033[30m" + "├" + name + " " + ((Document) currDir.getCatalog().get(name)).getType() + " " + currDir.getCatalog().get(name).getSize() + "\033[0m");
                if (currDir.getCatalog().get(name) instanceof Directory) {
                    System.out.println("\033[32m" + "├" + name + " " + currDir.getCatalog().get(name).getSize() + "\033[0m");
                    rSearch((Directory) currDir.getCatalog().get(name), level + 1, criName);
                }
            }
        }
    }

    public static void main(String[] args) {
        Disk root = new Disk(999999);
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
}

















































