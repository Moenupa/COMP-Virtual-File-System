package hk.edu.polyu.comp.comp2021.cvfs.view;

import junit.framework.TestCase;

public class CVFSViewTest extends TestCase {

    public void testWelcome() {
        CVFSView view = new CVFSView();
        view.welcome();
    }
}