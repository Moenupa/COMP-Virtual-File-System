package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.model.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.DocType;

public class CommandSwitch {

    /**
     *
     * @param name name of doc or dir
     * @return true if the name is valid, false if the name is invalid
     */
    public boolean isValidName(String name){
        return true;
    }

    /**
     *
     * @param command
     * @return CommandType.invalid if the command, else return the type of the giving command
     */
    public CommandType getType(String command){
        String[] elements=command.split(" ");
        switch (elements[0]){

            case "newDir":
                if(elements.length>2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!isValidName(elements[1])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }

                return CommandType.newDir;

            case "newDoc":
                if (elements.length<4){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!isValidName(elements[1])){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if(!DocType.isValidDocType(elements[2])){
                    System.out.println("x");
                    return CommandType.invalid;
                }

                return CommandType.newDoc;

            case "newDisk":
                if(elements.length>2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!elements[1].matches("\\d+")){
                    System.out.println("x");
                    return CommandType.invalid;
                }

                return CommandType.newDisk;

            case "delete":
                if(elements.length>2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!isValidName(elements[1])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }

                return CommandType.delete;

            case "rename":
                if(elements.length>3){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (elements.length<3){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!isValidName(elements[1])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }
                if (!isValidName(elements[2])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }

                return CommandType.rename;


            case "IsDocument":
                return CommandType.IsDocument;

            case "changeDir":
                if(elements.length>2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!isValidName(elements[1])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }

                return CommandType.changeDir;

            case "list":
                if(elements.length>1){
                    System.out.println("x");
                }
                return CommandType.list;

            case "rlist":
                if(elements.length>1){
                    System.out.println("x");
                }
                return CommandType.rList;

            case "search":
                if(elements.length>2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!Criterion.isValidCriName(elements[1])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }

                return CommandType.search;

            case "rSearch":
                if(elements.length>2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (elements.length<2){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!Criterion.isValidCriName(elements[1])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }

                return CommandType.rsearch;

            case "newSimpleCri":
                if(elements.length>5){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (elements.length<5){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!Criterion.isValidCri(elements[1], elements[2], elements[3], elements[4]))
                    return CommandType.invalid;

                return CommandType.newSimpleCri;

            case "newNegation":
                if(elements.length>3){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (elements.length<3){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!Criterion.isValidCriName(elements[1])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }
                if (!Criterion.isValidCriName(elements[2])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }

                return CommandType.newNegation;

            case "newBinaryCri":
                if(elements.length>5){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (elements.length<5){
                    System.out.println("x");
                    return CommandType.invalid;
                }
                if (!Criterion.isValidCriName(elements[1])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }
                if (!Criterion.isValidCriName(elements[2])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }
                if (!Criterion.isValidCriName(elements[4])){
                    System.out.println("x");
                    return  CommandType.invalid;
                }
                if(!(elements[3].equals("&&")||elements[3].equals("||"))){
                    System.out.println("x");
                    return CommandType.invalid;
                }

                return CommandType.newNegation;

            case "printAllCriteria":
                if(elements.length>1){
                    System.out.println("x");
                }
                return CommandType.printAllCriteria;

            case "undo":
                if(elements.length>1){
                    System.out.println("x");
                }
                return CommandType.undo;

            case "redo":
                if(elements.length>1){
                    System.out.println("x");
                }
                return CommandType.redo;
        }
        return CommandType.invalid;
    }
}
