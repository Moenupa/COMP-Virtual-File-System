package hk.edu.polyu.comp.comp2021.cvfs.model;

public class Disk extends Directory {
    private final int capacity;

    public Disk(int capacity) {
        super("Disk",null);
        this.capacity = capacity;
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
            System.out.println("Error: No Enough Space Left.");
            return;
        }
        return;
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
}
