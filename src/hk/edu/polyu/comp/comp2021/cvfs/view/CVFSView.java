package hk.edu.polyu.comp.comp2021.cvfs.view;

import hk.edu.polyu.comp.comp2021.cvfs.model.Directory;

/**
 * the view elements of the CVFS
 */
public class CVFSView {
    /**
     * The string storing the current path of the working directory.
     */
    private String curDirPath;


    /**
     * Print the welcome message.
     */
    public void welcome() {
        System.out.println("Welcome to the Comp VFS developed by group 30");
        System.out.println("===============================================================");

    }

    /**
     * Update the path of current working directory after changing.
     *
     * @param cur The current working directory.
     */
    public void updateDir(Directory cur) {
        if (cur == null) {
            curDirPath = "> ";
            return;
        }
        StringBuilder str = cur.getPath();
        str.append("> ");
        curDirPath = str.toString();
    }


    /**
     * Print a prompt including the current working directory.
     */
    public void printPrompt() {
        System.out.print(curDirPath);
    }
}
