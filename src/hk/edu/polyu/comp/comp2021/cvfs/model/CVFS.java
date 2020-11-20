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

    {
        criteria.put("IsDocument",Criterion.getIsDocument());
    }
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
        if (criteria.containsKey(name)) {
            System.out.println("Error: Invalid Arguments. Details: Already exists Criterion " + name);
            return;
        }

        if (!Criterion.isValidCri(name, attr, op, val)) {
            System.out.println("Error: Invalid Arguments. Details: Illegal Criterion " + name);
            return;
        }

        Criterion newCri = new Criterion(name, attr, op, val);
        criteria.put(name, newCri);
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
            System.out.println("Error: Invalid Arguments. Details: Already exists Criterion " + name1);
            return;
        }
        if (!Criterion.isValidCriName(name1)) {
            System.out.println("Error: Invalid Criterion Name " + name1);
            return;
        }

        criteria.put(name1, criteria.get(name2).getNegCri(name1));
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
        try{
            if(criteria.containsKey(name1))
                throw new Exception("Error: Invalid Arguments. Details: Already exists Criterion " + name1);
            if (!Criterion.isValidCriName(name1))
                throw new Exception("Error: Invalid Criterion Name " + name1);
            if (!BinCri.isValidOperator(op))
                throw new Exception("Error: Invalid Argument op " + op);
            if (!criteria.containsKey(name3) || name3 == null)
                throw new Exception("Error: Cannot find argument " + name3);
            if (!criteria.containsKey(name4) || name4 == null)
                throw new Exception("Error: Cannot find argument " + name4);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        criteria.put(name1, new BinCri(name1, criteria.get(name3), op, criteria.get(name4)));
    }

    /**
     * Print all criteria in the memory in a formatted form.
     */
    public void printAllCriteria() {
        System.out.println("╓ printing all the criteria");
        criteria.forEach((key, value)->
                        System.out.println("╟── " + value)
                );
        System.out.println("╙ " + criteria.size() + " criteria(criterion) in total");
    }

}