package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.HashMap;
import java.util.Map;
//import hk.edu.polyu.comp.comp2021.cvfs.model.DocType;

public class Directory extends Unit {
    /**
     * The contents in the directory.
     */
    private Map<String, Unit> catalog = new HashMap<>();

    /**
     * Construct a new directory.
     *
     * @param name   The name of the directory.
     * @param parent The parent of this directory.
     */
    public Directory(String name, Unit parent) {
        super(name, parent);
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
    public void newDir(String name) {
        if (!isValidName(name)) {
            System.out.println("Error: Invalid Argument.");
            return;
        }
        this.catalog.put(name, new Directory(name, this));
        return;
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
        return;
    }

    /**
     * Delete a file in the current directory.
     * Print a warning and return if there is no such file.
     * Then update the size of current directory
     *
     * @param name The name of the file to be deleted.
     */
    public void delete(String name) {
        return;
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
        Unit renamedItem = null;
        if (this.catalog.get(oldName) instanceof Directory)
            renamedItem = (Directory) this.catalog.get(oldName);
        if (this.catalog.get(oldName) instanceof Document)
            renamedItem = (Document) this.catalog.get(oldName);
        this.catalog.remove(oldName);
        this.catalog.put(newName, renamedItem);

    }

    /**
     * List all files in the directory and report the total number and size of files listed.
     * For each document, list the name, type, and size.
     * For each directory, list the name and size.
     */
    public void list() {
        if (this.catalog.size() == 0) {
            System.out.println("\033[31m" + "There is no files in current directory!");
            return;
        }
        for (String name : this.catalog.keySet()) {
            if (this.catalog.get(name) instanceof Directory)
                System.out.println("\033[32m" + name);
            if (this.catalog.get(name) instanceof Document)
                System.out.println("\033[30m" + name);
        }
    }

    /**
     * Recursively list the files in the directory.
     * Use indentation to indicate the level of each line.
     * Report the total number and size of files listed.
     */
    public void rList(Directory currDict) {
            // 判断是否是第一级目录
        if (currDict.getParent() == null) {
            System.out.println("\033[32m" + currDict.getName());// 一级目录只打名称
            return;
        }
        rList((Directory) currDict.getParent());
        for (int i = 0; i < currDict.getLevel(); i++) {
            System.out.print(" ");
        }
        if (currDict.getLevel() == this.getLevel()) {
            System.out.println("├" + currDict.getName() + "(Current Directory)");
            for (String name : this.catalog.keySet()) {
                for (int i = 0; i < currDict.getLevel() + 1; i++) {
                    System.out.print(" ");
                }
                if (this.catalog.get(name) instanceof Directory)
                    System.out.println("├" + "\033[32m" + name);
                if (this.catalog.get(name) instanceof Document)
                    System.out.println("├" + "\033[30m" + name);

            }

        }
        else
            System.out.println("├" + "\033[32m" + currDict.getName());
    }

    /**
     * A list with a filter.
     *
     * @param criName The filter.
     */
    public void search(Criterion criName) {
        return;
    }

    /**
     * A rList with a filter.
     *
     * @param criName The filter.
     */
    public void rSearch(Criterion criName) {
        return;
    }

    public static void main(String[] args) {
        Disk root = new Disk("zyb",100);
        root.newDir("Desktop");
        root.newDir("Download");

        Directory desktop = (Directory) root.getCatalog().get("Desktop");
        Directory download = (Directory) root.getCatalog().get("Download");

        desktop.newDir("NLP");
        desktop.newDir("CV");
        desktop.newDir("PYTHON");
        desktop.newDir("JAVA");
        desktop.newDoc("1.txt", DocType.TXT, "1");
        desktop.newDoc("2.txt", DocType.TXT, "2");
        desktop.newDoc("3.txt", DocType.TXT, "3");

        download.newDir("Download1");
        download.newDir("Download2");
        download.newDir("Download3");

        Directory nlp = (Directory) desktop.getCatalog().get("NLP");
        nlp.newDir("data1");
        nlp.newDir("data2");
        nlp.newDir("data3");
        nlp.newDoc("EMNLP2020.txt",DocType.TXT,"blah~blah~");
        desktop.list();
        System.out.println();
        nlp.rList(nlp);

    }
}

















































