package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;

public abstract class Unit implements Serializable {
    /**
     * A non-null string. Only numbers and English letters are allowed. No more than 10 chars. Can't be empty except for the disk.
     */
    private String name;

    /**
     * A reference to the parent directory. Not null except for the disk.
     */
    private Unit parent;

    /**
     * The size of the file. Variable when the unit is a directory.
     */
    private int size;

    /**
     * Changing this to false marks the unit as deleted.
     */
    private boolean existent = true;

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
     * @param parent The parent of the unit.
     */
    public Unit(String name, Unit parent) {
        this.name = name;
        this.parent = parent;
    }

    /**
     * To check if the name is valid.Only numbers and English letters are allowed. No more than 10 chars.
     *
     * @param name The string to be checked.
     * @return true if it is valid.
     */
    public static boolean isValidName(String name) {
        if (name == null) {
            System.out.println("Error: Invalid Argument. Details: checked with null.");
            return false;
        }

        if (name.length() > 10) {
            // TODO: needs phrasing
            System.out.println("Error: Invalid Argument. Details: Name is too long");
            return false;
        }

        for (char x: name.toCharArray()) {
            if (!Character.isLetterOrDigit(x))
                return false;
        }

        return true;
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
    public Unit getParent() {
        return parent;
    }

    /**
     * Set a new parent to the current unit.
     *
     * @param newParent The new parent of the unit.
     */
    public void setParent(Unit newParent) {
        parent = newParent;
    }

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
     * Recursively update the size of the directory by a certain number.
     * First update the size of parent, then the current directory.
     *
     * @param offset Positive if the size increases, vice versa.
     */
    public void updateSizeBy(int offset) {
        parent.updateSizeBy(offset);
        setSize(getSize()+offset);
    }

    /**
     * get the level index of this unit;
     */
    public int getLevel() {
        Unit temp = this;
        int level = 0;
        while (temp.parent != null) {
            temp = temp.parent;
            level++;
        }
        return level;
    }

    /**
     * Mark the unit as deleted;
     */
    public void delete() {
        existent = false;
    }

    /**
     * Mark the deleted unit as recovered.
     */
    public void recover() {
        existent = true;
    }


}
