package hk.edu.polyu.comp.comp2021.cvfs.model;

/**
 * This class implements disk similar to <code>Directory</code>
 * but without parent and have a given fixed capacity.
 */
public class Disk extends Directory {
    private final int capacity;

    /**
     * create a new disk
     * @param capacity size max limit
     */
    public Disk(int capacity) {
        super("Disk", null);
        this.capacity = capacity;
        setSize(0);
    }

    /**
     * Update the current size of the disk.
     * Print a warning and return if the disk capacity is not enough.
     *
     * @param offset Positive if the size increases, vice versa.
     */
    @Override
    public void updateSizeBy(int offset) {
        if (getSize() + offset > getCapacity()) {
            throw new IllegalStateException("No Enough Space Left.");
        }
        setSize(getSize() + offset);
    }

    /**
     * Safely get the disk size.
     *
     * @return The capacity of the disk.
     */
    public int getCapacity() {
        return capacity;
    }
    
    @Override
    public StringBuilder getPath() {
        StringBuilder str = new StringBuilder();
        str.append("Disk");
        return str;
    }

    @Override
    public String toString() {
        return String.format("\033[32m%-14s \033[33m(%d/%d)\033[0m", getName(), getSize(), getCapacity());
    }
}
