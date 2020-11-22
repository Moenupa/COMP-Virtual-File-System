package hk.edu.polyu.comp.comp2021.cvfs.controller;

/**
 * The class to check whether commands are valid and get types of commands
 */
public class CommandSwitch {

    /**
     *
     * @param command command input
     * @return CommandType.invalid if the command, else return the type of the giving command
     */
    public static CommandType getType(String command){
        String[] elements=command.split(" ");
        switch (elements[0]){

            case "newDir":
                return CommandType.newDir;

            case "newDoc":
                return CommandType.newDoc;

            case "newDisk":
                return CommandType.newDisk;

            case "delete":
                return CommandType.delete;

            case "rename":
                return CommandType.rename;

            case "changeDir":
                return CommandType.changeDir;

            case "list":
                return CommandType.list;

            case "rlist":
                return CommandType.rList;

            case "search":
                return CommandType.search;

            case "rSearch":
                return CommandType.rsearch;

            case "newSimpleCri":
                return CommandType.newSimpleCri;

            case "newNegation":
                return CommandType.newNegation;

            case "newBinaryCri":
                return CommandType.newBinaryCri;

            case "printAllCriteria":
                return CommandType.printAllCriteria;

            case "undo":
                return CommandType.undo;

            case "redo":
                return CommandType.redo;

            case "store":
                return CommandType.store;

            case "load":
                return CommandType.load;
        }

        if (command.equals(""))System.out.println("Please input command");
        else System.out.println("unknown command: "+elements[0]);
        return CommandType.invalid;
    }
}
