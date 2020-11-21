package hk.edu.polyu.comp.comp2021.cvfs.controller;


import hk.edu.polyu.comp.comp2021.cvfs.model.*;
import hk.edu.polyu.comp.comp2021.cvfs.view.CVFSView;

import java.util.HashMap;
import java.util.Map;
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


                TraceLogger.getInstance().newLog(TraceLogger.OpType.DD,elements[1],cvfs);
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
            System.out.println("\033[31m" +"Error: Invalid argument(s). Please try again."+"\033[0m");
            command = getCommand();
            type = commandSwitch.getType(command);
        }
        try{
            processCommand(type, command);
        }
        catch (Exception e){
            System.out.println("\033[31m" + "Error: "+e.getLocalizedMessage()+"\033[0m");
        }
    }

    /**
     * Used to parse and operate undo and redo command
     */
    public static class loggerParser{
        /**
         * Reflect on different kinds of optypes.
         */
        public interface Ops{
            /**
             * To operate on an logger.
             * @param args The arguments of the operation.
             */
            void operate(Object[] args);
        }
        /**
         * Add an object.
         */
        private Ops add = args -> {
            Object obj =  args[0];
            if(obj instanceof Unit){
                Unit unit = (Unit)obj;
                Directory parent =(Directory)args[1];
                parent.getCatalog().put(unit.getName(),unit);
                parent.updateSizeBy(unit.getSize());
            }
            else{
                Criterion cri = (Criterion)obj;
                CVFS cvfs = (CVFS) args[1];
                cvfs.getCriteria().put(cri.getName(),cri);
            }
        };

        /**
         * Delete an Object.
         */
        private Ops del = args -> {
            Object obj =  args[0];
            if (obj instanceof Unit){
                Unit unit = (Unit) obj;
                Directory parent = (Directory) args[1];
                parent.delete(unit.getName());
            }
            else {
                Criterion cri = (Criterion)obj;
                CVFS cvfs = (CVFS) args[1];
                cvfs.getCriteria().remove(cri.getName());
            }
        };

        /**
         * Rename an object.
         */
        private Ops ren = args -> {
            Unit unit = (Unit) args[0];
            String newName = (String)args[1];
            String oldName = (String)args[2];
            unit.setName(newName);
            Directory parent = (Directory) unit.getParent();
            parent.getCatalog().remove(oldName);
            parent.getCatalog().put(newName,unit);
        };
        /**
         * Change directory.
         * */
        private Ops cd = args -> {
            Directory newDir = (Directory) args[0];
            Directory oldDir = (Directory) args[1];
            CVFS cvfs = (CVFS) args[2];
            cvfs.setCwd(newDir);
        };
        /**
         * Switch to another disk.
         */
        private  Ops sd = args -> {
            Disk newDisk = (Disk) args[0];
            Disk oldDisk = (Disk) args[1];
            CVFS cvfs = (CVFS) args[2];
            cvfs.setDisk(newDisk);
        };
        /**
         * Delete a disk.
         */
        private Ops dd = args -> {
            String name = (String)args[0];
            CVFS cvfs = (CVFS) args[1];
            cvfs.delDisk(name);

        };
        /**
         * Store a disk to local storage.
         */
        private Ops ld = args -> {
            String name = (String)args[0];
            CVFS cvfs = (CVFS) args[1];
            cvfs.store(name);

        };

        private TraceLogger logger = TraceLogger.getInstance();

        private HashMap<TraceLogger.OpType,Ops> typeMap = new HashMap<>();
        {
            typeMap.put(TraceLogger.OpType.ADD,add);
            typeMap.put(TraceLogger.OpType.DEL,del);
            typeMap.put(TraceLogger.OpType.REN,ren);
            typeMap.put(TraceLogger.OpType.CD,cd);
            typeMap.put(TraceLogger.OpType.SD,sd);
            typeMap.put(TraceLogger.OpType.DD,dd);
            typeMap.put(TraceLogger.OpType.LD,ld);
        }

        /**
         * To parse and operate a log.
         * @param log The log to be parsed.
         */
        public void parse(TraceLogger.Tracelog log){
            TraceLogger.OpType type = log.getType();
            Object[] args = log.getArgs();
            Ops ops= typeMap.get(type);
            ops.operate(args);
        }

        /**
         * To undo a step.
         */
        public void undo() {
            TraceLogger.Tracelog log;
            log=logger.getlog();
            parse(log);
        }

        /**
         * To redo a step.
         */
        public void redo() {
            TraceLogger.Tracelog log;
            log=logger.getRlog();
            parse(log);

        }
    }

}
