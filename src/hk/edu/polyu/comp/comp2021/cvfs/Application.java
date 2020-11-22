package hk.edu.polyu.comp.comp2021.cvfs;

import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFSController;
import hk.edu.polyu.comp.comp2021.cvfs.model.CVFS;
import hk.edu.polyu.comp.comp2021.cvfs.view.CVFSView;

/**
 *
 */
@SuppressWarnings("InfiniteLoopStatement")
public class Application {
    /**
     * @param args Arguments;
     */
    public static void main(String[] args) {
        CVFS cvfs = new CVFS();
        CVFSView view = new CVFSView();
        CVFSController control = new CVFSController(cvfs, view);
        // initialize and utilize the system

        view.welcome();
        while (true) {
            control.terminal();

        }
    }
}
