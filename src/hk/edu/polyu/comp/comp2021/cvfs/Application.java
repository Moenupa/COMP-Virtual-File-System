/*
 * from Group Project sample
 *
 * Last Modified: NONE
 */


package hk.edu.polyu.comp.comp2021.cvfs;

import hk.edu.polyu.comp.comp2021.cvfs.model.CVFS;
import hk.edu.polyu.comp.comp2021.cvfs.view.CVFSView;
import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFSController;

public class Application {
    public static void main(String[] args) {
        CVFS cvfs = new CVFS();
        CVFSView view = new CVFSView();
        CVFSController control = new CVFSController(cvfs, view);
        // initialize and utilize the system

        while (true){
            control.terminal();

        }
    }
}
