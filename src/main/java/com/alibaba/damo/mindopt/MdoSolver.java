package com.alibaba.damo.mindopt;

import java.util.List;

public interface MdoSolver {
    enum RemoteTaskStatus {
        /**
         * This task is prepared to be scheduled
         */
        Submitted,
        /**
         * This task is now in solving phase
         */
        Solving,
        /**
         * This task is canceled
         */
        Canceled,
        /**
         * This task is finished (OPTIMAL, INFEASIBLE ...)
         */
        Finished,
        /**
         * This task is failed
         */
        Failed
    }

    interface MdoRemoteTask {
        /**
         * Get the remote task status code
         * @return the remote task status code
         */
        int getStatusCode();

        /**
         * Get the remote task status (OPTIMAL, INFEASIBLE ...)
         * @return the remote task status (OPTIMAL, INFEASIBLE ...)
         */
        MdoStatus getStatus();

        /**
         * Get the remote task result code
         * @return the remote task status code
         */
        int getResultCode();

        /**
         * Get the remote task result status (MDO_OK ...)
         * @return the remote task result status (MDO_OK ...)
         */
        MdoResult getResult();

        /**
         * Check weather remote task has a solution
         * @return weather remote task has a solution
         */
        boolean hasSolution();

        /**
         * Get remote task job status (Submitted, Solving...)
         * @return remote task job status (Submitted, Solving...)
         */
        MdoModel.RemoteTaskStatus getRemoteTaskStatus();
    }

    /**
     * Irreducible Inconsistent Subsystem
     */
    interface IIS {
        /**
         * Get all constraints involved in an IIS
         * @return all constraints involved in an IIS
         */
        List<Integer> getRowIndices();

        /**
         * Get all variables involved in an IIS
         * @return all variables involved in an IIS
         */
        List<Integer> getColIndices();
    }

    /**
     * Log callback
     */
    interface LogCallback {
        /**
         * log message
         * @param msg the message
         */
        void log(String msg);
    }

    /**
     * Load problem to model from file.
     *
     * Note that the type of the inputted format is determined by the file suffix, valid suffixes are `.mps(.bz2/.gz)` or `.lp(.bz2/.gz)`
     * @param filename the file name
     */
    void readProb(String filename);

    /**
     * Write problem to file.
     *
     * Note that the type of the outputted format is determined by the file suffix, valid suffixes are `.mps(.bz2/.gz)` or `.lp(.bz2/.gz)`
     * @param filename the file name
     */
    void writeProb(String filename);

    /**
     * Write solution to a file.
     *
     * Note that the format of the solution is determined by the file suffix, valid suffixes are `.sol` or `.bas`.
     * @param filename the file name
     */
    void writeSoln(String filename);

    /**
     * Load optimization model task from a file.
     * @param filename the file name
     * @param readModel a boolean flag that specifies if the model shall be loaded
     * @param readParam a boolean flag that specifies if the parameters shall be loaded
     * @param readSoln a boolean flag that specifies if the solution shall be loaded
     */
    void readTask(String filename, boolean readModel, boolean readParam, boolean readSoln);


    /**
     * Write an optimization model task to a file
     * @param filename the file name
     * @param writeModel a boolean flag that specifies if the model shall be outputted
     * @param writeParam a boolean flag that specifies if the parameters shall be outputted
     * @param writeSoln a boolean flag that specifies if the solution shall be outputted
     */
    void writeTask(String filename, boolean writeModel, boolean writeParam, boolean writeSoln);

    /**
     * Submit an optimization model task to a remote server for optimization
     * @return the remote task id
     */
    String submitTask();

    /**
     * Retrieve remote task status by its task id
     * @param jobId the task id returned on task submission
     * @return remote task status
     */
    MdoModel.MdoRemoteTask retrieveTask(String jobId);

    /**
     * Print log to standard output or not
     * @param flag a flag that specified if the log shall be printed out to screen or not
     */
    void setLogToConsole(boolean flag);

    /**
     * Redirect log to a file
     * @param filename the file name
     */
    void setLogFile(String filename);
    /**
     * Customize log output to a user-defined callback
     * @param logCallback the log callback function wrapper class
     */
    void setLogCallback(final MdoModel.LogCallback logCallback);

    /**
     * Solve the loaded optimization problem
     */
    void solveProb();

    /**
     * Display the current solver results
     */
    void displayResult();

    /**
     * Get the current solver status
     * @return the solver status
     */
    MdoStatus getStatus();

    /**
     * Explain the solver status enumeration
     * @param status the status to explain
     * @return a string that holds the explanation of the solver status enumeration
     */
    String explainStatus(MdoStatus status);

    /**
     * Explain the solver result enumeration
     * @param result the result to explain
     * @return a string that holds the explanation of the solver result enumeration
     */
    String explainResult(MdoResult result);

    /**
     * Compute an Irreducible Inconsistent Subsystem (IIS)
     * @return the irreducible inconsistent rows and columns
     */
    MdoModel.IIS computeIIS();
}
