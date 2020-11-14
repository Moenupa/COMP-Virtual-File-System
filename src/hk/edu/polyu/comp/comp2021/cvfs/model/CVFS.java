package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.HashMap;
import java.util.Map;

public class CVFS {
    // the implementation of the CVFS system.

    /**
     * A hashmap storing all disks.
     */
    Map<String, Disk> disks = new HashMap<>();

    /**
     * A hashmap storing all criteria.
     */
    Map<String, Criterion> criteria = new HashMap<>();

    /**
     * Create a new disk and return its reference.
     *
     * @param diskSize The capacity of the disk.
     * @return The reference of the disk.
     */
    public Disk newDisk(int diskSize) {
        return new Disk(diskSize);
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
        if (!Criterion.isValidCri(name, attr, op, val)) {
            System.out.println("Error: Invalid Arguments.");
            return;
        }
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
        return;
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
        return;
    }

    /**
     * Print all criteria in the memory in a formatted form.
     */
    public void printAllCriteria() {
        return;
    }


}