package hk.edu.polyu.comp.comp2021.cvfs.view;

import hk.edu.polyu.comp.comp2021.cvfs.model.CVFS;
import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFSController;
import hk.edu.polyu.comp.comp2021.cvfs.model.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.Unit;

import java.util.Map;

/**
 * the view elements of the CVFS
 */
public class CVFSView {
    /**
     * The string storing the current path of the working directory.
     */
    private String curDirPath;

    /**
     * The working directory
     */
    private Directory curDir;

    /**
     * Print the welcome message.
     */
    public void welcome() {
        return;
    }

    /**
     * Update the path of current working directory after changing.
     *
     * @param cur The current working directory.
     */
    public void updateDir(Directory cur) {
        this.curDir=cur;
        return;
    }

    /**
     * show the status of the working directory
     */
    public void view(){
        curDir.list();
        return;
    }
}
