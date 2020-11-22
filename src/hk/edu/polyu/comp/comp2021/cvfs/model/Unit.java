package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;

/**
 * The basic properties of a file.
 */
public abstract class Unit implements Serializable {
    /**
     * A non-null string. Only numbers and English letters are allowed. No more than 10 chars. Can't be empty except for the disk.
     */
    private String name;

    /**
     * The size of the file. Variable when the unit is a directory.
     */
    private int size;

// --Commented out by Inspection START (2020/11/22 17:54):
//    /**
//     * Changing this to false marks the unit as deleted.
//     */
//    private boolean existent = true;
// --Commented out by Inspection STOP (2020/11/22 17:54)

    /**
     * A constant size that a Unit takes:
     * DocSize = CONSTANT + content.length() *2
     * DirSize = CONSTANT + sub-DocSize + sub-DirSize
     */
    protected static final int SIZE_CONSTANT = 40;

    /**
     * Construct a new unit.
     *
     * @param name   The name of the unit.
     */
    public Unit(String name) {
        this.name = name;
    }

    /**
     * To check if the name is valid.Only numbers and English letters are allowed. No more than 10 chars.
     *
     * @param name The string to be checked.
     * @return true if it is valid.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValidName(String name) {
        return name.matches("^[a-zA-Z0-9]{1,10}$");
    }

    /**
     * Safely get the name of the unit.
     *
     * @return the name of the unit.
     */
    public String getName() {
        return name;
    }

    /**
     * Rename the unit.
     *
     * @param newName The new name to be used.
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * safely get the parent of the unit.
     *
     * @return the reference of the parent.
     */
    public abstract Unit getParent();

    /**
     * Set a new parent to the current unit.
     *
     * @param newParent The new parent of the unit.
     */
    @SuppressWarnings("unused")
    public abstract void setParent(Unit newParent);

    /**
     * Get the size of the unit.
     *
     * @return the size of the unit.
     */
    public int getSize() {
        return size;
    }

    /**
     * Resize the unit.
     *
     * @param newSize The new size.
     */
    public void setSize(int newSize) {
        size = newSize;
    }

    /**
     * get level index of this
     * @return The level index of this unit;
     */
    public int getLevel() {
        int level = 0;
        for (Unit temp = this; temp.getParent() != null; temp = temp.getParent())
            level++;
        return level;
    }

// --Commented out by Inspection START (2020/11/22 17:55):
//    /**
//     * Mark the unit as deleted;
//     */
//    public void delete() {
//        existent = false;
//    }
// --Commented out by Inspection STOP (2020/11/22 17:55)

// --Commented out by Inspection START (2020/11/22 17:54):
//    /**
//     * Mark the deleted unit as recovered.
//     */
//    public void recover() {
//        existent = true;
//    }
// --Commented out by Inspection STOP (2020/11/22 17:54)

}
