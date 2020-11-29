package hk.edu.polyu.comp.comp2021.cvfs.controller;

/**
 * all possible type of command input
 */
public enum CommandType {
    /**
     * invalid if the command is invalid
     */
    invalid,
    /**
     * newDisk if the command is to create a disk
     */
    newDisk,
    /**
     * newDoc if the command is to create a document
     */
    newDoc,
    /**
     * newDir if the command is to create a directory
     */
    newDir,
    /**
     * delete if the command is to delete a file
     */
    delete,
    /**
     * rename if the command is to rename a file
     */
    rename,
    /**
     * changeDir if the command is to change the working directory
     */
    changeDir,
    /**
     * list if the command is to list files directly contained in the working directory
     */
    list,
    /**
     * rlist if the command is to list files contained in the working directory
     */
    rList,
    /**
     * newSimpleCri if the command is to create simple criterion
     */
    newSimpleCri,
    /**
     * newNegation if the command is to construct a composite criterion
     */
    newNegation,
    /**
     * newBinaryCri if the command is to construct a criterion with logical AND or logical OR
     */
    newBinaryCri,
    /**
     * printAllCriteria if the command is to print all criteria
     */
    printAllCriteria,
    /**
     * search if the command is to search in files directly contained by the working directory with a criterion
     */
    search,
    /**
     * search if the command is to search in files contained by the working directory with a criterion
     */
    rsearch,
    /**
     * store if the command is to store a virtual disk to the local file system
     */
    store,
    /**
     * load if the command is to load a virtual disk from the local file system
     */
    load,
    /**
     * undo if the command is to undo
     */
    undo,
    /**
     * redo if the command is to redo
     */
    redo,
    /**
     * exit the CVFS
     */
    exit
}
