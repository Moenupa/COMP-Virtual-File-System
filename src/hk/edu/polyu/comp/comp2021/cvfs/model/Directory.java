package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.HashMap;
import java.util.Map;
//import hk.edu.polyu.comp.comp2021.cvfs .model.DocType;

public class Directory extends Unit {
    /**
     * The contents in the directory.
     */
    private final Map<String, Unit> catalog = new HashMap<>();

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

    //public void addItem

    /**
     * Make a new directory in the current directory.
     * Print a warning and return if the argument is invalid.
     * First update the size of the current directory, then create the file.
     *
     * @param name The name of the new directory.
     */
    public void newDir(String name) {
        if (!isValidName(name)) {
            System.out.println("Error: Invalid Argument.");
            return;
        }
        this.catalog.put(name, new Directory(name, this));
        updateSizeBy(this, this.catalog.get(name).getSize());
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
    public void newDoc(String name, DocType type, String content) {
        if (!isValidName(name) || type == null || content == null) {
            System.out.println("Error: Invalid Argument.");
            return;
        }
        this.catalog.put(name, new Document(name, this, type, content));
        updateSizeBy(this, this.catalog.get(name).getSize());
    }

    /**
     * Delete a file in the current directory.
     * Print a warning and return if there is no such file.
     * Then update the size of current directory
     *
     * @param name The name of the file to be deleted.
     */
    public void delete(Disk currDisk, String name) {
        if (this.catalog.get(name) == null) {
            System.out.println("No such document/directory exit.");
            return;
        }
        move((Directory) currDisk.getCatalog().get("Bin"), name,false);
        updateSizeBy(this, -this.catalog.get(name).getSize());
        this.catalog.remove(name);
    }

    /**
     * Move a document/directory to another directory.
     *
     * @param other Another directory.
     * @param name The moving document/directory.
     * @
     */
    public void move(Directory other, String name, boolean delete) {
        if (this.catalog.get(name) == null) {
            System.out.println("No such document/directory exit.");
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
        updateSizeBy(other, this.catalog.get(name).getSize());
        if (delete) {
            updateSizeBy(this, -this.catalog.get(name).getSize());
            this.catalog.remove(name);
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
        if (this.catalog.get(oldName) == null) {
            System.out.println("No such document/directory exit.");
            return;
        }
        Unit renamedItem;
        renamedItem = this.catalog.get(oldName);
        this.catalog.get(oldName).setName(newName);
        this.catalog.remove(oldName);
        this.catalog.put(newName, renamedItem);

    }

    /**
     * List all files in the directory and report the total number and size of files listed.
     * For each document, list the name, type, and size.
     * For each directory, list the name and size.
     */
    public void list() {
        if (this.getCatalog().isEmpty()) {
            System.out.println("\033[31m" + "There is no files in current directory!");
            return;
        }
        System.out.print("\033[45m" + "Total size: ");
        System.out.println(this.getSize() + "\033[0m");
        for (String name : this.catalog.keySet()) {
            if (this.catalog.get(name) instanceof Directory)
                System.out.println("\033[32m" + "├" + name + " " + this.catalog.get(name).getSize());
            if (this.catalog.get(name) instanceof Document)
                System.out.println("\033[30m" + "├" + name + " " + ((Document) this.catalog.get(name)).getType() + " " + this.catalog.get(name).getSize());
        }
    }

    /**
     * Recursively list the files in the directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     */
    public void down_rList(Directory currDir, int level) {
        if (currDir.getCatalog().isEmpty())
            return;
        //System.out.println(currDir.getName());
        for (String name : currDir.getCatalog().keySet()) {
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            if (currDir.getCatalog().get(name) instanceof Document)
                System.out.println("\033[30m" + "├" + name + " " + ((Document) currDir.getCatalog().get(name)).getType() + " " + currDir.getCatalog().get(name).getSize());
            if (currDir.getCatalog().get(name) instanceof Directory) {
                System.out.println("\033[32m" + "├" + name + " " + currDir.getCatalog().get(name).getSize());
                down_rList((Directory) currDir.getCatalog().get(name), level + 1);
            }
        }
    }

    public void up_rList(Directory currDir) {
        if (currDir.getParent() == null) {
            System.out.println("\033[32m" + currDir.getName());
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
                    System.out.println("\033[32m" + "├" + name + " " + parent.getCatalog().get(name).getSize());
                if (parent.getCatalog().get(name) instanceof Document)
                    System.out.println("\033[30m" + "├" + name + " " + ((Document)parent.getCatalog().get(name)).getType() + " " + parent.getCatalog().get(name).getSize());
            }
        }
        for (int i = 0; i < currDir.getLevel(); i++) {
            System.out.print("\t");
        }

        if (currDir.getLevel() == this.getLevel()) {
            System.out.println("├" + currDir.getName() + " " + currDir.getSize() + "(Current Directory)");
            for (String name : this.catalog.keySet()) {
                for (int i = 0; i < currDir.getLevel() + 1; i++) {
                    System.out.print("\t");
                }
                if (this.catalog.get(name) instanceof Directory)
                    System.out.println("\033[32m" + name + " " + this.catalog.get(name).getSize());
                if (this.catalog.get(name) instanceof Document)
                    System.out.println("\033[30m" + name + " " + ((Document) this.catalog.get(name)).getType() + " " + this.catalog.get(name).getSize());
            }

        } else
            System.out.println("\033[32m" + "├" + currDir.getName() + " " + currDir.getSize());
    }

    /**
     * A list with a filter.
     *
     * @param criName The filter.
     */
    public void search(Criterion criName) {
        if (this.catalog.isEmpty()) {
            System.out.println("\033[31m" + "There is no files in current directory!");
            return;
        }
        for (String name : this.catalog.keySet()) {
            if (criName.check(this.catalog.get(name))) {
                if (this.catalog.get(name) instanceof Directory)
                    System.out.println("\033[32m" + name + " " + this.catalog.get(name).getSize());
                if (this.catalog.get(name) instanceof Document)
                    System.out.println("\033[30m" + name + " " + ((Document) this.catalog.get(name)).getType() + " " + this.catalog.get(name).getSize());
            }
        }
    }

    /**
     * A rList with a filter.
     *
     * @param criName The filter.
     */
    public void rSearch(Directory currDir, int level, Criterion criName) {
        if (currDir.getCatalog().isEmpty())
            return;
        //System.out.println(currDir.getName());
        for (String name : currDir.getCatalog().keySet()) {
            if (criName.check(this.catalog.get(name))) {
                for (int i = 0; i < level; i++) {
                    System.out.print("\t");
                }
                if (currDir.getCatalog().get(name) instanceof Document)
                    System.out.println("\033[30m" + "├" + name + " " + ((Document) currDir.getCatalog().get(name)).getType() + " " + currDir.getCatalog().get(name).getSize());
                if (currDir.getCatalog().get(name) instanceof Directory) {
                    System.out.println("\033[32m" + "├" + name + " " + currDir.getCatalog().get(name).getSize());
                    rSearch((Directory) currDir.getCatalog().get(name), level + 1, criName);
                }
            }
        }
    }

    public static void main(String[] args) {
        Disk root = new Disk(100);
        root.newDoc("config", DocType.TXT, "");
        root.newDir("Desktop");
        root.newDir("Download");

        Directory desktop = (Directory) root.getCatalog().get("Desktop");
        Directory download = (Directory) root.getCatalog().get("Download");
        Directory bin = (Directory) root.getCatalog().get("bin");

        desktop.newDir("NLP");
        desktop.newDir("CV");
        desktop.newDir("PYTHON");
        desktop.newDir("JAVA");
        //desktop.list();
        desktop.newDoc("1", DocType.TXT, "1");
        //desktop.list();
        desktop.newDoc("2", DocType.TXT, "2");
        desktop.newDoc("3", DocType.TXT, "3");

        download.newDir("Download1");
        download.newDir("Download2");
        download.newDir("Download3");

        Directory nlp = (Directory) desktop.getCatalog().get("NLP");
        Directory java = (Directory) desktop.getCatalog().get("JAVA");
        nlp.newDir("data1");
        nlp.newDir("data2");
        nlp.newDoc("EMNLP", DocType.TXT, "blah~blah~");
        java.newDoc("test", DocType.JAVA, "xxx");
        System.out.println("**TEST** list nlp");
        nlp.list();
        System.out.println("**TEST** list java");
        java.list();
        /*
        System.out.println("**TEST** copy \"EMNLP\" from nlp to java");
        nlp.move(java,"EMNLP",false);
        System.out.println("-list nlp");
        nlp.list();
        System.out.println("-list java");
        java.list();

        System.out.println("**TEST** move \"data1\" from nlp to java");
        nlp.move(java,"data1",true);
        System.out.println("-list nlp");
        nlp.list();
        System.out.println("-list java");
        java.list();

        System.out.println("**TEST** move \"data1\" from nlp to java");
        nlp.move(java,"data1",true);
        System.out.println("-list nlp");
        nlp.list();
        System.out.println("-list java");
        java.list();

        System.out.println("**TEST** delete \"data2\" from nlp to java");
        nlp.delete(root,"data2");
        System.out.println("-list nlp");
        nlp.list();
        System.out.println("-list bin");
        ((Directory) root.getCatalog().get("Bin")).list();


        //root.down_rList(root, 0);
        //nlp.up_rList(nlp);
        //desktop.list();

         */
    }
}

















































