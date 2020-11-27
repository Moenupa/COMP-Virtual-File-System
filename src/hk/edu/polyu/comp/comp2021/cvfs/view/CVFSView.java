package hk.edu.polyu.comp.comp2021.cvfs.view;

import hk.edu.polyu.comp.comp2021.cvfs.model.CVFS;
import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFSController;
import hk.edu.polyu.comp.comp2021.cvfs.model.Directory;

public class CVFSView {
    /**
     * The string storing the current path of the working directory.
     */
    private String curDir;

    /**
     * Print the welcome message.
     */
    public void welcome() {
<<<<<<< Updated upstream
        return;
=======
        System.out.println("\n" +
                "██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗    ████████╗ ██████╗      ██████╗██╗   ██╗███████╗███████╗\n" +
                "██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝    ╚══██╔══╝██╔═══██╗    ██╔════╝██║   ██║██╔════╝██╔════╝\n" +
                "██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗         ██║   ██║   ██║    ██║     ██║   ██║█████╗  ███████╗\n" +
                "██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝         ██║   ██║   ██║    ██║     ╚██╗ ██╔╝██╔══╝  ╚════██║\n" +
                "╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗       ██║   ╚██████╔╝    ╚██████╗ ╚████╔╝ ██║     ███████║\n" +
                " ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝       ╚═╝    ╚═════╝      ╚═════╝  ╚═══╝  ╚═╝     ╚══════╝\n" +
                "                                                                                                                         \n" +
                "                                                     Developed by Group 30: MAN Furui, WANG Meng, XING Shiji, ZHANG Yubo.\n" +
                "                        COMP2021 Object-Oriented Programming, Year 2020 Semester 1, The Hong Kong Polytechnic University.\n"
        );
>>>>>>> Stashed changes
    }

    /**
     * Update the path of current working directory after changing.
     *
     * @param cur The current working directory.
     */
    public void updateDir(Directory cur) {
        return;
    }

    /**
     * Print a prompt including the current working directory.
     */
    public void printPrompt(){
        return;
    }
}
