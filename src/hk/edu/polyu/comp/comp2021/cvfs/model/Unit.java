package hk.edu.polyu.comp.comp2021.cvfs.model;

public abstract class Unit {
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
    static public boolean isValidName(String name) {
        return false;
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
        getParent().updateSizeBy(offset);
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
