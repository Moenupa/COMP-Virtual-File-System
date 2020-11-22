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
     * @throws Exception if no file or criterion of input name exists or the command is invalid in format
     */
    public void processCommand(CommandType type, String command) throws Exception {

        String[] elements=command.split(" ");

        switch (type){
            case newDisk:
                if(elements.length>2){
                    throw new IllegalArgumentException("command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("size of new disk not found");
                }
                if (!elements[1].matches("\\d+")){
                    throw new IllegalArgumentException("invalid size of new disk: "+elements[1]);
                }

                cvfs.newDisk(Integer.parseInt(elements[0]));

            case newDoc:
                if (elements.length<4){
                    throw new IllegalArgumentException("name, type or content of new document not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("invalid document name: "+elements[1]);
                }
                if(!DocType.isValidDocType(elements[2])){
                    throw new IllegalArgumentException("invalid document type: "+elements[2]);
                }

                cvfs.getCwd().newDoc(elements[1],DocType.parse(elements[2]),elements[3]);

            case newDir:
                if(elements.length>2){
                    throw new IllegalArgumentException("command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("name of new directory not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("invalid directory name: "+elements[1]);
                }

                cvfs.getCwd().newDir(elements[1]);

            case list:
                if(elements.length>1){
                    throw new IllegalArgumentException("command input too long");
                }

                cvfs.getCwd().list();

            case rList:
                if(elements.length>1){
                    throw new IllegalArgumentException("command input too long");
                }

                cvfs.getCwd().down_rList();

            case search:
                if(elements.length>2){
                    throw new IllegalArgumentException("command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("name of file to be deleted not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("invalid file name: "+elements[1]);
                }

                cvfs.getCwd().search(cvfs.getCri(elements[1]));

            case rsearch:
                if(elements.length>2){
                    throw new IllegalArgumentException("command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("name of file to be deleted not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("invalid file name: "+elements[1]);
                }

                cvfs.getCwd().rSearch(cvfs.getCri(elements[1]));

            case rename:
                if(elements.length>3){
                    throw new IllegalArgumentException("command inout too long");
                }
                if (elements.length<3){
                    throw new IllegalArgumentException("old name or new name not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("invalid old name: "+elements[1]);
                }
                if (!Unit.isValidName(elements[2])){
                    throw new IllegalArgumentException("invalid new name: "+elements[2]);
                }

                cvfs.getCwd().rename(elements[1],elements[2]);

            case delete:
                if(elements.length>2){
                    throw new IllegalArgumentException("command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("name of file to be deleted not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("invalid file name: "+elements[1]);
                }

                cvfs.getCwd().delete(elements[1]);

            case changeDir:
                if(elements.length>2){
                    throw new IllegalArgumentException("command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("directory name not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("invalid directory name: "+elements[1]);
                }

                cvfs.changeDir(elements[1]);

            case newNegation:
                if(elements.length>3){
                    throw new IllegalArgumentException("command input too long");
                }
                if (elements.length<3){
                    throw new IllegalArgumentException("number of criterion name is less than 2");
                }
                if (!Criterion.isValidCriName(elements[1])){
                    throw new IllegalArgumentException("invalid criterion name: "+elements[1]);
                }
                if (!Criterion.isValidCriName(elements[2])){
                    throw new IllegalArgumentException("invalid criterion name: "+elements[2]);
                }

                cvfs.newNegation(elements[1],elements[2]);

            case newBinaryCri:
                if(elements.length>5){
                    throw new IllegalArgumentException("command input too long");
                }
                if (elements.length<5){
                    throw new IllegalArgumentException("number of criterion name is less than 3, or operation not found");
                }
                if (!Criterion.isValidCriName(elements[1])){
                    throw new IllegalArgumentException("invalid criterion name: "+elements[1]);
                }
                if (!Criterion.isValidCriName(elements[2])){
                    throw new IllegalArgumentException("invalid criterion name: "+elements[2]);
                }
                if (!Criterion.isValidCriName(elements[4])){
                    throw new IllegalArgumentException("invalid criterion name: "+elements[4]);
                }
                if(!(elements[3].equals("&&")||elements[3].equals("||"))){
                    throw new IllegalArgumentException("the logic operation must be && or ||");
                }

                cvfs.newBinaryCri(elements[1],elements[2],elements[3],elements[4]);

            case newSimpleCri:
                if(elements.length>5){
                    throw new IllegalArgumentException("command input too long");
                }
                if (elements.length<5){
                    throw new IllegalArgumentException("criterion name, attribute name, operation or value not found");
                }
                if (!Criterion.isValidCriName(elements[1])){
                    throw new IllegalArgumentException("invalid critertion name: "+elements[1]);
                }

                cvfs.newSimpleCri(elements[1],elements[2],elements[3],elements[4]);

            case printAllCriteria:
                if(elements.length>1){
                    throw new IllegalArgumentException("command input too long");
                }

                cvfs.printAllCriteria();


            case undo:// Those 4 can be saved for later
                if(elements.length>1){
                    throw new IllegalArgumentException("command input too long");
                }

            case redo:
                if(elements.length>1){
                    throw new IllegalArgumentException("command input too long");
                }

            case load:

            case store:
        }

    }


    /**
     * The user-interface, receiving and handling user requests.
     * @throws Exception if no file or criterion of input name exists when processing commands
     */
    public void terminal() throws Exception {

        String command;

        view.printPrompt();
        command = getCommand();

        CommandType type = CommandSwitch.getType(command);

        while (type == CommandType.invalid) {
            System.out.println("Error: Invalid argument(s). Please try again.");
            command = getCommand();
            type = CommandSwitch.getType(command);
        }
        processCommand(type, command);
    }

}
