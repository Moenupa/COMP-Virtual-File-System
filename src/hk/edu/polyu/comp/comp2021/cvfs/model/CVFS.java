package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Vitrual File System.
 */
public class CVFS {
    // the implementation of the CVFS system.
    /**
     * A hashmap storing all criteria.
     */
    private final Map<String, Criterion> criteria = new HashMap<>();
    /**
     * Stores the current disk in use.
     */
    private Disk disk;
    /**
     * Stores the reference to the current working directory.
     */
    private Directory cwd;

    {
        criteria.put("IsDocument", Criterion.getIsDocument());
    }

    /**
     * @return The sets of criteria.
     */
    public Map<String, Criterion> getCriteria() {
        return criteria;
    }

    /**
     * Create a new disk and return its reference.
     *
     * @param diskSize The capacity of the disk.
     */
    public void newDisk(int diskSize) {
        Disk tmp = disk;
        disk = new Disk(diskSize);
        TraceLogger.getInstance().newLog(TraceLogger.OpType.SD, tmp, disk, this);
        cwd = disk;
    }

    /**
     * Set the current disk to another one.
     *
     * @param disk The disk to be switched to.
     */
    public void setDisk(Disk disk) {
        this.disk = disk;
        cwd = disk;
    }

    /**
     * @return The reference to the current working directory.
     */
    public Directory getCwd() {
        return cwd;
    }

    /**
     * Set the current working directory to a new place.
     *
     * @param cwd New location of the current working directory.
     */
    public void setCwd(Directory cwd) {
        this.cwd = cwd;
    }

    /**
     * Stores the current disk in local storage.
     * Throw an exception if the name is invalid OR
     * such file already exists.
     *
     * @param name The name of the file.
     */
    public void store(String name) {
        try {
            if (!Unit.isValidName(name))
                throw new IllegalArgumentException("Invalid name.");
            String path = System.getProperty("user.dir") + "/disks/" + name + ".ser";
            File file = new File(path);
            if (file.exists())
                throw new FileAlreadyExistsException("File Already Exists.");
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            FileOutputStream buffer = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(buffer);
            out.writeObject(disk);
            out.close();
            buffer.close();
            System.out.println("Current disk stored in " + path);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (FileAlreadyExistsException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a local copy of the disk.
     *
     * @param name The name of the file to be deleted.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void delDisk(String name) {
        File file = new File(System.getProperty("user.dir") + "/disks/" + name + ".ser");
        file.delete();
    }

    /**
     * Load a disk from local storage
     *
     * @param name the name of the disk to be loaded.
     */
    public void load(String name) {
        try {
            String path = System.getProperty("user.dir") + "/disks/" + name + ".ser";
            if (!new File(path).exists())
                throw new FileNotFoundException("File Not Found.");
            FileInputStream buffer = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(buffer);
            Disk tmp = (Disk) in.readObject();
            TraceLogger.getInstance().newLog(TraceLogger.OpType.SD, disk, tmp, this);
            setDisk(tmp);
            in.close();
            buffer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
        System.out.println("Disk " + name + " Loaded.");
    }

    /**
     * Return a criterion by name.
     *
     * @param criName The name of the criterion
     * @return The reference to the criterion.
     */
    public Criterion getCri(String criName) {
        return criteria.get(criName);
    }

    /**
     * Create a new criterion. Print a warning and return if one of the arguments is invalid.
     *
     * @param name The name of the criterion.
     * @param attr The name of the attribute.
     * @param op   The name of the operation.
     * @param val  The value of the operation.
     */
    public void newSimpleCri(String name, String attr, String op, String val) {
        if (criteria.containsKey(name)) {
            System.out.println("Invalid Arguments. Details: Already exists Criterion " + name);
            return;
        }

        if (!Criterion.isValidCri(name, attr, op, val)) {
            System.out.println("Invalid Arguments. Details: Illegal Criterion " + name);
            return;
        }

        Criterion newCri = new Criterion(name, attr, op, val);
        criteria.put(name, newCri);
        TraceLogger.getInstance().newLog(TraceLogger.OpType.DEL, newCri, this);
    }

    /**
     * Create a negated criterion of name2.
     * Print a warning and return if name2 can't be found OR
     * name1 is invalid.
     *
     * @param name1 The name of the new criterion.
     * @param name2 The name of the criterion to be negated.
     */
    public void newNegation(String name1, String name2) {
        if (criteria.containsKey(name1)) {
            System.out.println("Invalid Arguments. Details: Already exists Criterion " + name1);
            return;
        }
        if (!Criterion.isValidCriName(name1)) {
            System.out.println("Invalid Criterion Name " + name1);
            return;
        }

        Criterion newCri = criteria.get(name2).getNegCri(name1);
        TraceLogger.getInstance().newLog(TraceLogger.OpType.DEL, newCri, this);
        criteria.put(name1, newCri);
    }

    /**
     * Create a combined criterion.
     * Print a warning and return if name1 is invalid OR
     * name3 or name4 cannot be found OR
     * op is invalid.
     *
     * @param name1 The name of the new criterion.
     * @param name3 The name of the first criterion to be combined.
     * @param op    The logic operation of the combination.
     * @param name4 The name of the second criterion to be combined.
     */
    public void newBinaryCri(String name1, String name3, String op, String name4) {
        try {
            if (criteria.containsKey(name1))
                throw new Exception("Invalid Arguments. Details: Already exists Criterion " + name1);
            if (!Criterion.isValidCriName(name1))
                throw new Exception("Invalid Criterion Name " + name1);
            if (!BinCri.isValidOperator(op))
                throw new Exception("Invalid Argument op " + op);
            if (!criteria.containsKey(name3) || name3 == null)
                throw new Exception("Cannot find argument " + name3);
            if (!criteria.containsKey(name4) || name4 == null)
                throw new Exception("Cannot find argument " + name4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        BinCri newCri = new BinCri(name1, criteria.get(name3), op, criteria.get(name4));
        TraceLogger.getInstance().newLog(TraceLogger.OpType.DEL, newCri, this);
        criteria.put(name1, newCri);
    }

    /**
     * Print all criteria in the memory in a formatted form.
     */
    public void printAllCriteria() {
        System.out.println("╓ printing all the criteria");
        for (Criterion criterion : criteria.values())
            System.out.println("╟── " + criterion);
        System.out.println("╙ " + criteria.size() + " criteria(criterion) in total");
    }

    /**
     * Change the current working directory to the desired one.
     * Print a warning and return if the desired directory does not exist.
     *
     * @param name The new directory.
     */
    public void changeDir(String name) {
        if (name.equals("..")) {
            if (cwd.getParent() == null)
                throw new IllegalArgumentException("This is the root directory.");
            setCwd(cwd.getParent());
            return;
        }
        Object[] res = parsePath(name);
        Directory tmpDir = (Directory) res[0];
        String tname = (String) res[1];
        Unit newDir = tmpDir.getCatalog().get(tname);
        if (newDir == null)
            throw new IllegalArgumentException("Invalid path.");
        if (!(newDir instanceof Directory))
            throw new IllegalArgumentException("This is not a directory.");
        TraceLogger.getInstance().newLog(TraceLogger.OpType.CD, getCwd(), newDir);
        setCwd((Directory) newDir);
    }

    /**
     * Parse the path and return the directory of the target file and the name of the target file.
     *
     * @param path The string to be parsed.
     * @return The parent of the target file and the name of the file..
     */
    public Object[] parsePath(String path) {
        String[] paths = path.split(":");
        Directory cur = getCwd();
        for (int i = 0; i < paths.length - 1; i++) {
            String s = paths[i];
            if (s.equals("$")) continue;
            cur = (Directory) cur.getCatalog().get(s);
            if (cur == null) throw new IllegalArgumentException("Invalid Path.");
        }
        Object[] result = new Object[2];
        result[0] = cur;
        result[1] = paths[paths.length - 1];
        return result;
    }
}