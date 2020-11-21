package hk.edu.polyu.comp.comp2021.cvfs.controller;


import hk.edu.polyu.comp.comp2021.cvfs.model.*;
import hk.edu.polyu.comp.comp2021.cvfs.view.CVFSView;

import java.util.Scanner;

/**
 * the control element of the CVFS
 */
public class CVFSController {
    private CVFS cvfs;
    private CVFSView view;



    /**
     * Initialize the CVFS Controller.
     *
     * @param cvfs the CVFS model to be used.
     * @param view the CVFS view to be used.
     */
    public CVFSController(CVFS cvfs, CVFSView view) {
        this.cvfs = cvfs;
        this.view = view;
    }


    /**
     * To deal with the user input.
     */
    private final Scanner scanner=new Scanner(System.in);


    /**
     * @return command input from the keyboard or the system
     */
    public String getCommand(){


        return scanner.nextLine();
    }


    /**
     * @param type type of command input
     * @param command command input
     */
    public void processCommand(CommandType type, String command){

        String[] elements=new String[]{"","","","",""};
        String[] rawElements=command.split(" ");
        for (int i=0;i<rawElements.length;i++){
            elements[i]=rawElements[i];
        }


        switch (type){
            case newDisk:
                cvfs.newDisk(Integer.parseInt(elements[0]));
                //

            case newDoc:
                cvfs.getCwd().newDoc(elements[1],DocType.parse(elements[2]),elements[3]);
                //

            case newDir:
                cvfs.getCwd().newDir(elements[1]);
                //

            case list:
                //TODO Mathch the command with the method provided in other classes(Directory, Criterion,CVFS).


            case rList:
                //

            case search:
                String criName=elements[1];
                //
            case rsearch:
                criName=elements[1];
                //

            case rename:
                String oldName=elements[1];
                String newName=elements[2];

            case delete:
                String unitName=elements[1];
                //

//            case IsDocument:

            case changeDir:
                String DirName=elements[1];
                //

            case newNegation:
                String criName1=elements[1];
                String criName2=elements[2];

            case newBinaryCri:
                criName1=elements[1];
                criName2=elements[2];
                String logicOP=elements[3];
                String criName3=elements[4];
                //

            case newSimpleCri:
                criName=elements[1];
                String attrName=elements[2];
                String op=elements[3];
                String val=elements[4];

            case printAllCriteria:


            case undo:// Those 4 can be saved for later

            case redo:

            case load:

            case store:
        }

    }


    /**
     * The user-interface, receiving and handling user requests.
     */
    public void terminal() {

        String command;
        //TODO better implemented with static methods
        CommandSwitch commandSwitch = new CommandSwitch();


        view.printPrompt();
        command = getCommand();

        CommandType type = commandSwitch.getType(command);

        while (type == CommandType.invalid) {
            System.out.println("Error: Invalid argument(s). Please try again.");
            command = getCommand();
            type = commandSwitch.getType(command);
        }
        processCommand(type, command);
    }

}
