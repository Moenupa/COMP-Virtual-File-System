# Project Report

---

<center><b>Group XYZ</b></center>

<center>COMP2021 Object-Oriented Programming (Fall 2020)</center>

<center>MAN Furui, WANG Meng, Xing Shiji, ZHANG Yubo</center>

## To-do List

- [ ] Design
    - [ ]  Layout
        - [ ]  Controller
        - [ ]  Model
        - [ ]  View
    - [ ]  Implementation of important methods
        - [ ]  Unit → Document, Dir
        - [ ]  Criterion → BinCri
        - [ ]  CVFS
        - [ ]  Controller
        - [ ]  View
- [ ]  Requirements
    - [ ]  REQ1
    - [ ]  REQ2
    - [ ]  REQ3
    - [ ]  REQ4
    - [ ]  REQ5
    - [ ]  REQ6
    - [ ]  REQ7
    - [ ]  REQ8
    - [ ]  REQ9
    - [ ]  REQ10
    - [ ]  REQ11
    - [ ]  REQ12
    - [ ]  REQ13
    - [ ]  REQ14
    - [ ]  BON1
    - [ ]  BON2
- [ ]  User Manual

> some resources
> 1. The requirement is implemented.
> 2. Implementation
> 3. Error Conditions and Error Handling

## 1. Introduction

This document describes the design and implementation of the Comp Virtual File System (CVFS) by group XYZ. The project is part of the course COMP2021 Object-Oriented Programming at PolyU.

## 2 The Comp Virtual File System (CVFS)

In this section, we describe first the **overall design** of the CVFS and then the **implementation** details of the requirements.

### 2.1 Design

Use class diagram(s) to give an overview of the system design and describe in general terms how different components fit together. Feel free to elaborate on design patterns used (except for the MVC pattern) and/or anything else that might help others understand your design.

### 2.2 Requirements

Requirements For each (compulsory and bonus) requirement, describe 1) whether it is implemented and, when yes, 2) how you implemented the requirement as well as 3) how you handled various error conditions.

---

> [REQ1] The tool should support the creation of a new disk with a specified maximum size.

1. The requirement is implemented.
2. Implementation

    [REQ1] is implemented by codes in <code>package hk.edu.polyu...cvfs.model</code>. When <code>newDoc</code> instruction is passed from the command line onto the core, the creation of a new disk is handled by <code>CVFS.newDisk()</code>, and then calls <code>Disk()</code> constructor extended from <code>Unit()</code> constructor, returning the reference to the newly-created disk. **TODO: this part needs fixing, when <code>store()</code> and <code>load()</code> is added**

    ```java
    // package  hk.edu.polyu.comp.comp2021.cvfs.model;
    // file     CVFS.java
    public class CVFS{
        public Disk newDisk(int diskSize) {
            return new Disk(diskSize);
        }
    }
    // file     Disk.java
    public class Disk{
        private final int capacity;
        public Disk(int capacity) {
            super("Disk", null);
            this.capacity = capacity;
        }
    }
    // file     Unit.java
    public class Unit{
        private String name;
        private Unit parent;
        public Unit(String name, Unit parent) {
            this.name = name;
            this.parent = parent;
        }
    }
    ```

3. Error Conditions and Error Handling

    command <code>newDisk</code> does not result in any error or warning, thereafter part 3 is omitted.

> [REQ2] The tool should support the creation of a new document in the working directory.

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ3] The tool should support the creation of a new directory in the working directory.

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ4] The tool should support the deletion of an existing file in the working directory.


1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ5] The tool should support the rename of an existing file in the working directory. 

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ6] The tool should support the change of working directory. 

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ7] The tool should support listing all files direclty contained in the working directory. 

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ8] The tool should support recursively listing all files in the working directory.

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ9] The tool should support the construction of simple criteria. 

1. The requirement is implemented.
2. Implementation
    
    [REQ9] is implemented by codes in <code>package hk.edu.polyu...cvfs.model</code>. When a valid <code>newSimpleCri</code> command is handled by controller, a new instance is raised by <code>CVFS.java</code> to and stored into a HashMap where all the criteria are stored.

    ```java
    // package  hk.edu.polyu.comp.comp2021.cvfs.model
    // file     CVFS.java
    public class CVFS{
        public void newSimpleCri(String name, String attr, String op, String val) {
            Map<String, Criterion> criteria = new HashMap<>();

            // Checking Error Conditions
            Criterion newCri = new Criterion(name, attr, op, val);
            criteria.put(name, newCri);
        }
    }
    // file     Criterion.java
    public class Criterion implements Clonable {
        private String name, attr, op, val;

        public Criterion(String name, String attr, String op, String val) {
            this.name = name;
            this.attr = attr;
            this.op = op;
            this.val = val;
        }
    }
    ```

3. Error Conditions and Error Handling

    TODO: Error Conditions

> [REQ10] The tool should support a simple criterion to check whether a file is a document. 

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ11] The tool should support the construction of composite criteria.

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ12] The tool should support the printing of all defined criteria. 

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ13] The tool should support searching for files in the working directory based on an existing criterion.

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

> [REQ14] The tool should support recursively searching for files in the working directory based on an existing criterion.

1. The requirement is implemented.
2. Implementation
3. Error Conditions and Error Handling

## 3 User Manual

In this section, we explain how the CVFS works from a user's perspective. Describe here all the commands that the system supports. For each command, use screenshots to show the result under different inputs.