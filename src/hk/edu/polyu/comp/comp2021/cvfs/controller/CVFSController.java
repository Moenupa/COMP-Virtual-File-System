package hk.edu.polyu.comp.comp2021.cvfs.controller;


import hk.edu.polyu.comp.comp2021.cvfs.model.*;
import hk.edu.polyu.comp.comp2021.cvfs.view.CVFSView;

import java.util.HashMap;
import java.util.Scanner;

/**
 * the control element of the CVFS
 */
public class CVFSController {
    private final CVFS cvfs;
    private final CVFSView view;
    private final loggerParser logger = new loggerParser();



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
    public void processCommand(CommandType type, String command) {

        String[] elements=command.split(" ");
        Directory twd;
        String tname;
        Object[] tres;

        switch (type){
            case newDisk:
                if(elements.length>2){
                    throw new IllegalArgumentException("Command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("Size of new disk not found");
                }
                if (!elements[1].matches("\\d+")){
                    throw new IllegalArgumentException("Invalid size of new disk: "+elements[1]);
                }

                cvfs.newDisk(Integer.parseInt(elements[1]));
                return;

            case newDoc:
                if(cvfs.getCwd()==null)
                    throw new IllegalStateException("Please first create a disk.");
                if (elements.length<4){
                    throw new IllegalArgumentException("Name, type or content of new document not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("Invalid document name: "+elements[1]);
                }
                if(!DocType.isValidDocType(elements[2])){
                    throw new IllegalArgumentException("Invalid document type: "+elements[2]);
                }
                tres = cvfs.parsePath(elements[1]);
                twd = (Directory) tres[0];
                tname = (String) tres[1];
                twd.newDoc(tname,DocType.parse(elements[2]),elements[3]);
                return;

            case newDir:
                if(cvfs.getCwd()==null)
                    throw new IllegalStateException("Please first create a disk.");
                if(elements.length>2){
                    throw new IllegalArgumentException("Command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("Name of new directory not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("Invalid directory name: "+elements[1]);
                }
                tres = cvfs.parsePath(elements[1]);
                twd = (Directory) tres[0];
                tname = (String) tres[1];
                twd.newDir(tname);
                return;

            case list:
                if(cvfs.getCwd()==null)
                    throw new IllegalStateException("Please first create a disk.");
                if(elements.length>1){
                    throw new IllegalArgumentException("Command input too long");
                }

                cvfs.getCwd().list();
                return;

            case rList:
                if(cvfs.getCwd()==null)
                    throw new IllegalStateException("Please first create a disk.");
                if(elements.length>1){
                    throw new IllegalArgumentException("Command input too long");
                }

                cvfs.getCwd().down_rList();
                return;

            case search:
                if(cvfs.getCwd()==null)
                    throw new IllegalStateException("Please first create a disk.");
                if(elements.length>2){
                    throw new IllegalArgumentException("Command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("Name of file to be deleted not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("Invalid file name: "+elements[1]);
                }

                cvfs.getCwd().search(cvfs.getCri(elements[1]));
                return;

            case rsearch:
                if(cvfs.getCwd()==null)
                    throw new IllegalStateException("Please first create a disk.");
                if(elements.length>2){
                    throw new IllegalArgumentException("Command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("Name of file to be deleted not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("Invalid file name: "+elements[1]);
                }

                cvfs.getCwd().rSearch(cvfs.getCri(elements[1]));
                return;

            case rename:
                if(cvfs.getCwd()==null)
                    throw new IllegalStateException("Please first create a disk.");
                if(elements.length>3){
                    throw new IllegalArgumentException("Command inout too long");
                }
                if (elements.length<3){
                    throw new IllegalArgumentException("Old name or new name not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("Invalid old name: "+elements[1]);
                }
                if (!Unit.isValidName(elements[2])){
                    throw new IllegalArgumentException("Invalid new name: "+elements[2]);
                }
                tres = cvfs.parsePath(elements[1]);
                twd = (Directory) tres[0];
                tname = (String) tres[1];

                twd.rename(tname,elements[2]);
                return;

            case delete:
                if(cvfs.getCwd()==null)
                    throw new IllegalStateException("Please first create a disk.");
                if(elements.length>2){
                    throw new IllegalArgumentException("Command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("Name of file to be deleted not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("Invalid file name: "+elements[1]);
                }
                tres = cvfs.parsePath(elements[1]);
                twd = (Directory) tres[0];
                tname = (String) tres[1];

                twd.delete(tname);
                return;

            case changeDir:
                if(cvfs.getCwd()==null)
                    throw new IllegalStateException("Please first create a disk.");
                if(elements.length>2){
                    throw new IllegalArgumentException("Command input too long");
                }
                if (elements.length<2){
                    throw new IllegalArgumentException("Directory name not found");
                }
                if (!Unit.isValidName(elements[1])){
                    throw new IllegalArgumentException("Invalid directory name: "+elements[1]);
                }

                cvfs.changeDir(elements[1]);
                return;

            case newNegation:
                if(elements.length>3){
                    throw new IllegalArgumentException("Command input too long");
                }
                if (elements.length<3){
                    throw new IllegalArgumentException("Number of criterion name is less than 2");
                }
                if (!Criterion.isValidCriName(elements[1])){
                    throw new IllegalArgumentException("Invalid criterion name: "+elements[1]);
                }
                if (!Criterion.isValidCriName(elements[2])){
                    throw new IllegalArgumentException("Invalid criterion name: "+elements[2]);
                }

                cvfs.newNegation(elements[1],elements[2]);
                return;

            case newBinaryCri:
                if(elements.length>5){
                    throw new IllegalArgumentException("Command input too long");
                }
                if (elements.length<5){
                    throw new IllegalArgumentException("Number of criterion name is less than 3, or operation not found");
                }
                if (!Criterion.isValidCriName(elements[1])){
                    throw new IllegalArgumentException("Invalid criterion name: "+elements[1]);
                }
                if (!Criterion.isValidCriName(elements[2])){
                    throw new IllegalArgumentException("Invalid criterion name: "+elements[2]);
                }
                if (!Criterion.isValidCriName(elements[4])){
                    throw new IllegalArgumentException("Invalid criterion name: "+elements[4]);
                }
                if(!(elements[3].equals("&&")||elements[3].equals("||"))){
                    throw new IllegalArgumentException("The logic operation must be && or ||");
                }

                cvfs.newBinaryCri(elements[1],elements[2],elements[3],elements[4]);
                return;

            case newSimpleCri:
                if(elements.length>5){
                    throw new IllegalArgumentException("Command input too long");
                }
                if (elements.length<5){
                    throw new IllegalArgumentException("Criterion name, attribute name, operation or value not found");
                }
                if (!Criterion.isValidCriName(elements[1])){
                    throw new IllegalArgumentException("Invalid critertion name: "+elements[1]);
                }

                cvfs.newSimpleCri(elements[1],elements[2],elements[3],elements[4]);
                return;

            case printAllCriteria:
                if(elements.length>1){
                    throw new IllegalArgumentException("Command input too long");
                }

                cvfs.printAllCriteria();
                return;


            case undo:
                if(elements.length>1){
                    throw new IllegalArgumentException("Command input too long");
                }
                logger.undo();
                return;

            case redo:
                if(elements.length>1){
                    throw new IllegalArgumentException("Command input too long");
                }
                logger.redo();
                return;

            case load:
                if(elements.length>2){
                    throw new IllegalArgumentException("Command input too long");
                }
                cvfs.load(elements[1]);
                return;

            case store:
                if(elements.length>2){
                    throw new IllegalArgumentException("Command input too long");
                }
                cvfs.store(elements[1]);
                TraceLogger.getInstance().newLog(TraceLogger.OpType.DD,elements[1],cvfs);
        }

    }


    /**
     * The user-interface, receiving and handling user requests.
     */
    public void terminal() {

        view.updateDir(cvfs.getCwd());

        String command;

        view.printPrompt();
        command = getCommand();

        CommandType type = CommandSwitch.getType(command);

        while (type == CommandType.invalid) {
            view.printPrompt();
            command = getCommand();
            type = CommandSwitch.getType(command);
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
        private final Ops add = args -> {
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
        private final Ops del = args -> {
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
        private final Ops ren = args -> {
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
        private final Ops cd = args -> {
            Directory newDir = (Directory) args[0];
            Directory oldDir = (Directory) args[1];
            CVFS cvfs = (CVFS) args[2];
            cvfs.setCwd(newDir);
        };
        /**
         * Switch to another disk.
         */
        private final Ops sd = args -> {
            Disk newDisk = (Disk) args[0];
            Disk oldDisk = (Disk) args[1];
            CVFS cvfs = (CVFS) args[2];
            cvfs.setDisk(newDisk);
        };
        /**
         * Delete a disk.
         */
        private final Ops dd = args -> {
            String name = (String)args[0];
            CVFS cvfs = (CVFS) args[1];
            cvfs.delDisk(name);

        };
        /**
         * Store a disk to local storage.
         */
        private final Ops ld = args -> {
            String name = (String)args[0];
            CVFS cvfs = (CVFS) args[1];
            cvfs.store(name);

        };

        private final TraceLogger logger = TraceLogger.getInstance();

        private final HashMap<TraceLogger.OpType,Ops> typeMap = new HashMap<>();
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
