package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.model.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.DocType;

import java.util.Arrays;

/**
 * The class to check whether commands are valid and get types of commands
 */
public class CommandSwitch {

    /**
     *
     * @param name name of doc or dir
     * @return true if the name is valid, false if not
     */
    public boolean isInvalidName(String name){
        return !(name.matches("[a-zA-z0-9]+")&&name.length()<=10);
    }

    /**
     *
     * @param type docType input
     * @return true if the type is valid, false if not
     */
    public boolean isInvalidDocType(String type){

        for(DocType a:DocType.values()){
            if(a.toString().equals(type))return false;
        }
        return true;
    }

    /**
     *
     * @param criName criterion name
     * @return true if the name is valid, false if not
     */
    public boolean isInvalidCriName(String criName){
        return criName.matches("[a-zA-z]+")&&criName.length()==2;
    }

    private final String[] sizeOP={">","<",">=","<=","==","!="};

    /**
     * @param attrName name of attribute, which can be "name", "type", or "size"
     * @param op operation about the attribute
     * @param val certian param of the opetation
     * @return true if the criterion is valid, false if not
     */
    public boolean isValidCri(String attrName, String op, String val){
        switch (attrName){
            case "name":
                if(!op.equals("contains")){
                    System.out.println("when the attribute name is name, the operation must be contains");
                    return false;
                }
                if(val.charAt(0)!='\"'||val.charAt(val.length()-1)!='\"') {
                    System.out.println("when the attribute name is name, the value must be in double quote");
                    return false;
                }
                return true;

            case "type":
                if(!op.equals("equals")){
                    System.out.println("when the attribute name is type, the operation must be equals");
                    return false;
                }
                if(val.charAt(0)!='\"'||val.charAt(val.length()-1)!='\"'|| isInvalidDocType(val.substring(1, val.length() - 1))) {
                    System.out.println("when the attribute name is type, the value must be \"txt\", \"java\", \"html\", \"css\"");
                    return false;
                }
                return true;

            case "size":
                if (Arrays.asList(getSizeOP()).contains(op)){
                    System.out.println("when the attribute name is type, the operation must be >, < ,>= ,<= ,== ,!=");
                    return false;
                }
                if (!val.matches("\\d+")){
                    System.out.println("when the attribute name is type, the value must be an integer");
                    return false;
                }
                return true;
        }

        System.out.println("the criterion name must be \"name\", \"type\", \"size\"");
        return false;
    }

    /**
     *
     * @param command command input
     * @return CommandType.invalid if the command, else return the type of the giving command
     */
    public CommandType getType(String command){
        String[] elements=command.split(" ");
        switch (elements[0]){

            case "newDir":
                if(elements.length>2){
                    System.out.println("command input too long");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("name of new directory not found");
                    return CommandType.invalid;
                }
                if (isInvalidName(elements[1])){
                    System.out.println("invalid directory name: "+elements[1]);
                    return  CommandType.invalid;
                }

                return CommandType.newDir;

            case "newDoc":
                if (elements.length<4){
                    System.out.println("name, type or content of new document not found");
                    return CommandType.invalid;
                }
                if (isInvalidName(elements[1])){
                    System.out.println("invalid document name: "+elements[1]);
                    return CommandType.invalid;
                }
                if(isInvalidDocType(elements[2])){
                    System.out.println("invalid document type: "+elements[2]);
                    return CommandType.invalid;
                }

                return CommandType.newDoc;

            case "newDisk":
                if(elements.length>2){
                    System.out.println("command input too long");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("size of new disk not found");
                    return CommandType.invalid;
                }
                if (!elements[1].matches("\\d+")){
                    System.out.println("invalid size of new disk: "+elements[1]);
                    return CommandType.invalid;
                }

                return CommandType.newDisk;

            case "delete":
                if(elements.length>2){
                    System.out.println("command input too long");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("name of file to be deleted not found");
                    return CommandType.invalid;
                }
                if (isInvalidName(elements[1])){
                    System.out.println("invalid file name: "+elements[1]);
                    return  CommandType.invalid;
                }

                return CommandType.delete;

            case "rename":
                if(elements.length>3){
                    System.out.println("command inout too long");
                    return CommandType.invalid;
                }
                if (elements.length<3){
                    System.out.println("old name or new name not found");
                    return CommandType.invalid;
                }
                if (isInvalidName(elements[1])){
                    System.out.println("invalid old name: "+elements[1]);
                    return  CommandType.invalid;
                }
                if (isInvalidName(elements[2])){
                    System.out.println("invalid new name: "+elements[2]);
                    return  CommandType.invalid;
                }

                return CommandType.rename;


//            case "IsDocument":
//                return CommandType.IsDocument;

            case "changeDir":
                if(elements.length>2){
                    System.out.println("command input too long");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("directory name not found");
                    return CommandType.invalid;
                }
                if (isInvalidName(elements[1])){
                    System.out.println("invalid directory name: "+elements[1]);
                    return  CommandType.invalid;
                }

                return CommandType.changeDir;

            case "list":
                if(elements.length>1){
                    System.out.println("command input too long");
                }
                return CommandType.list;

            case "rlist":
                if(elements.length>1){
                    System.out.println("command input too long");
                }
                return CommandType.rList;

            case "search":
                if(elements.length>2){
                    System.out.println("command input too long");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("criterion ont found");
                    return CommandType.invalid;
                }
                if (isInvalidCriName(elements[1])){
                    System.out.println("invalid criterion name: "+elements[1]);
                    return  CommandType.invalid;
                }

                return CommandType.search;

            case "rSearch":
                if(elements.length>2){
                    System.out.println("command input too long");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("criterion ont found");
                    return CommandType.invalid;
                }
                if (!isInvalidCriName(elements[1])){
                    System.out.println("invalid criterion name: "+elements[1]);
                    return  CommandType.invalid;
                }

                return CommandType.rsearch;

            case "newSimpleCri":
                if(elements.length>5){
                    System.out.println("command input too long");
                    return CommandType.invalid;
                }
                if (elements.length<5){
                    System.out.println("criterion name, attribute name, operation or value not found");
                    return CommandType.invalid;
                }
                if (!isInvalidCriName(elements[1])){
                    System.out.println("invalid critertion name: "+elements[1]);
                    return  CommandType.invalid;
                }
                if (!isValidCri(elements[2],elements[3],elements[4]))return CommandType.invalid;

                return CommandType.newSimpleCri;

            case "newNegation":
                if(elements.length>3){
                    System.out.println("command input too long");
                    return CommandType.invalid;
                }
                if (elements.length<3){
                    System.out.println("number of criterion name is less than 2");
                    return CommandType.invalid;
                }
                if (!isInvalidCriName(elements[1])){
                    System.out.println("invalid criterion name: "+elements[1]);
                    return  CommandType.invalid;
                }
                if (!isInvalidCriName(elements[2])){
                    System.out.println("invalid criterion name: "+elements[2]);
                    return  CommandType.invalid;
                }

                return CommandType.newNegation;

            case "newBinaryCri":
                if(elements.length>5){
                    System.out.println("command input too long");
                    return CommandType.invalid;
                }
                if (elements.length<5){
                    System.out.println("number of criterion name is less than 3, or operation not found");
                    return CommandType.invalid;
                }
                if (!isInvalidCriName(elements[1])){
                    System.out.println("invalid criterion name: "+elements[1]);
                    return  CommandType.invalid;
                }
                if (!isInvalidCriName(elements[2])){
                    System.out.println("invalid criterion name: "+elements[2]);
                    return  CommandType.invalid;
                }
                if (!isInvalidCriName(elements[4])){
                    System.out.println("invalid criterion name: "+elements[4]);
                    return  CommandType.invalid;
                }
                if(!(elements[3].equals("&&")||elements[3].equals("||"))){
                    System.out.println("the logic operation must be && or ||");
                    return CommandType.invalid;
                }

                return CommandType.newNegation;

            case "printAllCriteria":
                if(elements.length>1){
                    System.out.println("command input too long");
                }
                return CommandType.printAllCriteria;

            case "undo":
                if(elements.length>1){
                    System.out.println("command input too long");
                }
                return CommandType.undo;

            case "redo":
                if(elements.length>1){
                    System.out.println("command input too long");
                }
                return CommandType.redo;
            case "store":
                if(elements.length!=2)
                    return CommandType.invalid;
                return CommandType.store;
            case "load":
                if(elements.length!=2)
                    return CommandType.invalid;
                return CommandType.load;
        }

        if (command.equals(""))System.out.println("Please input command");
        else System.out.println("unknown command: "+elements[0]);
        return CommandType.invalid;
    }

    /**
     *all strings that can represent operations about size
     * @return all operations about size
     */
    public String[] getSizeOP() {
        return sizeOP;
    }

}
