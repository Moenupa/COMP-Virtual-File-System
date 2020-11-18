package hk.edu.polyu.comp.comp2021.cvfs.controller;


import hk.edu.polyu.comp.comp2021.cvfs.model.CVFS;
import hk.edu.polyu.comp.comp2021.cvfs.model.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.DocType;
import hk.edu.polyu.comp.comp2021.cvfs.model.Document;
import hk.edu.polyu.comp.comp2021.cvfs.view.CVFSView;

import java.util.Scanner;

public class CVFSController {
    private CVFS cvfs;
    private CVFSView view;

    /**
     * Stores the reference to the current working directory.
     */
    private Directory cur;

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
     * return the current working directory
     */
    public Directory getCur(){
        return cur;
    }

    /**
     * Change the current working directory to the desired one.
     * Print a warning and return if the desired directory does not exist.
     *
     * @param name The new directory.
     */
    public void changeDir(String name) {
        return;
    }



    public String getCommand(){

        Scanner scanner=new Scanner(System.in);

        String command = scanner.nextLine();

        return command;
    }



    public void processCommand(CommandType type, String command){

        String[] elements=new String[]{"","","","",""};
        String[] rawElements=command.split(" ");
        for (int i=0;i<rawElements.length;i++){
            elements[i]=rawElements[i];
        }


        switch (type){
            case newDisk:
                int diskSize=Integer.parseInt(elements[1]);
                //

            case newDoc:
                Document newDoc=new Document(elements[1],cur, DocType.valueOf(elements[2]),elements[3]);
                //

            case newDir:
                Directory newDic=new Directory(elements[1],cur);
                //

            case list:
                //

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

            case IsDocument:

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
                //
        }

    }


    /**
     * The user-interface, receiving and handling user requests.
     */
    public void terminal() {

        String command;
        CommandSwitch commandSwitch = new CommandSwitch();


        command = getCommand();
        CommandType type = commandSwitch.getType(command);

        while (type == CommandType.invalid) {
            command = getCommand();
            type = commandSwitch.getType(command);
        }
        processCommand(type, command);
    }

}
