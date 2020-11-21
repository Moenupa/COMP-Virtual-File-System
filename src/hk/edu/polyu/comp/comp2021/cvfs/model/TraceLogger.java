package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.Stack;

/**
 * Records user's operations and maintain undo/redo methods.
 */
public final class TraceLogger {

    private static TraceLogger traceLogger;
    /**
     * Stores al possible kinds of operations.
     */
    interface Operation{
        /**
         * @param args Arguments of the operation.
         */
        void operate(Object[] args);
    }

    /**
     * Types of operations.
     */
    public enum OpType{
        /**
         * Delete an Object.
         */
        DEL,
        /**
         * Add an object.
         */
        ADD,
        /**
         * Rename an object.
         */
        REN,
        /**
         * Change directory.
         * */
        CD,
        /**
         * Switch to another disk.
         */
        SD,
        /**
         * Delete a disk.
         */
        DD,
        /**
         * Store a disk to local storage.
         */
        LD
    }

    /**
     * Records a single log.
     */
    public static class Tracelog{
        private final OpType type;
        private final Object[] args;

        /**
         * Generate a single trace log
         * @param type The type of the operation
         * @param args The arguments of the operation
         */
        public Tracelog(OpType type, Object... args){
            this.type=type;
            this.args=args;
        }

        /**
         * @return The arguments of the log.
         */
        public Object[] getArgs() {
            return args;
        }

        /**
         * @return The type of the log.
         */
        public OpType getType() {
            return type;
        }

        /**
         * @return Switch the log between redo and undo.
         */
        public Tracelog switchLog(){
            switch (type){
                case ADD:
                    return new Tracelog(OpType.DEL, args);
                case DEL:
                    return new Tracelog(OpType.ADD, args);
                case REN:
                    return new Tracelog(OpType.REN,args[0],args[2],args[1]);
                case CD:
                    return new Tracelog(OpType.CD,args[1],args[0]);
                case SD:
                    return new Tracelog(OpType.SD,args[1],args[0]);
                case DD:
                    return new Tracelog(OpType.LD,args);
                case LD:
                    return new Tracelog(OpType.DD,args);
                default:
                    return null;
            }
        }
    }

    private final Stack<Tracelog> logger = new Stack<>();

    private final Stack<Tracelog> rLogger = new Stack<>();

    /**
     * Generate a new log and clear the redo logger
     * @param type The type of the log.
     * @param args The arguments of the log.
     */
    public void newLog(OpType type, Object[] args){
        rLogger.clear();
        logger.push(new Tracelog(type, args));
    }

    /**
     * Return the latest log and push it into redo logger.
     * @return The latest piece of log
     * @throws Exception If the logger is empty.
     */
    public Tracelog getlog() throws Exception {
        if(logger.isEmpty())
            throw new Exception("No more step can be undone.");
        Tracelog tmp = logger.pop();
        OpType type = tmp.getType();
        rLogger.push(tmp.switchLog());
        return tmp;
    }

    /**
     * @return The latest piece of redo log and push it into the logger.
     * @throws Exception If the logger is empty.
     */
    public Tracelog getRlog()throws Exception {
        if(rLogger.isEmpty())
            throw new Exception("No more step can be redone");
         Tracelog tmp = rLogger.pop();
         logger.push(tmp.switchLog());
         return tmp;
    }

    /**
     * Empty constructor.
     */
    private TraceLogger(){}

    /**
     * @return the traceLogger with singleton guaranteed.
     */
    public static TraceLogger getInstance() {
        if(traceLogger==null){
            synchronized (TraceLogger.class){
                if(traceLogger==null){
                    traceLogger = new TraceLogger();
                }
            }
        }
        return traceLogger;
    }
}
