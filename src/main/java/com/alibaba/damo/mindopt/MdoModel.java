/**
 * Copyright 1999-2021 Alibaba Cloud All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.damo.mindopt;


import com.alibaba.damo.mindopt.impl.MdoMatrixImpl;
import com.alibaba.damo.mindopt.impl.MemoryUtil;
import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.util.ArrayList;
import java.util.List;

/**
 *  This object implements the data structure to hold an optimization model.
 *
 *  Typical steps to to input a column into the optimization model are:
 *  <OL>
 *  <LI>Create a set of empty variable objects by calling MdoModel.addVar</LI>
 *  <LI>Specify the variable's lower bound, upper bound, and objective coefficients</LI>
 *  <LI>Create an empty linear expression object by class MdoExprLinear</LI>
 *  <LI>Input a constraint by calling MdoModel.addCons</LI>
 *  <LI>Optimize the problem by calling MdoModel.solveProb</LI>
 *  </OL>
 */
public class MdoModel extends MdoMatrixImpl {
    /**
     * This class represents the status of a specified remote task
     */
    public enum RemoteTaskStatus {
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

    public interface MdoRemoteTask {
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
        RemoteTaskStatus getRemoteTaskStatus();
    }

    /**
     * Irreducible Inconsistent Subsystem
     */
    public interface IIS {
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
    public interface LogCallback {
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
    public void readProb(String filename) {
        MdoResult.checkResult(model.readProb(MemoryUtil.charArray(filename)));
        syncData(true, true);
    }

    /**
     * Write problem to file.
     *
     * Note that the type of the outputted format is determined by the file suffix, valid suffixes are `.mps(.bz2/.gz)` or `.lp(.bz2/.gz)`
     * @param filename the file name
     */
    public void writeProb(String filename) {
        MdoResult.checkResult(model.writeProb(MemoryUtil.charArray(filename)));
    }

    /**
     * Write solution to a file.
     *
     * Note that the format of the solution is determined by the file suffix, valid suffixes are `.sol` or `.bas`.
     * @param filename the file name
     */
    public void writeSoln(String filename) {
        MdoResult.checkResult(model.writeSoln(MemoryUtil.charArray(filename)));
    }

    /**
     * Load optimization model task from a file.
     * @param filename the file name
     * @param readModel a boolean flag that specifies if the model shall be loaded
     * @param readParam a boolean flag that specifies if the parameters shall be loaded
     * @param readSoln a boolean flag that specifies if the solution shall be loaded
     */
    public void readTask(String filename, boolean readModel, boolean readParam, boolean readSoln) {
        MdoResult.checkResult(
                model.readTask(
                        MemoryUtil.charArray(filename), readModel ? 1 : 0,
                        readParam ? 1 : 0, readSoln ? 1 : 0
                )
        );
        if (readModel) {
            syncData(true, true);
        }
    }


    /**
     * Write an optimization model task to a file
     * @param filename the file name
     * @param writeModel a boolean flag that specifies if the model shall be outputted
     * @param writeParam a boolean flag that specifies if the parameters shall be outputted
     * @param writeSoln a boolean flag that specifies if the solution shall be outputted
     */
    public void writeTask(String filename, boolean writeModel, boolean writeParam, boolean writeSoln) {
        MdoResult.checkResult(
                model.writeTask(
                        MemoryUtil.charArray(filename), writeModel ? 1 : 0,
                        writeParam ? 1 : 0, writeSoln ? 1 : 0
                )
        );
    }

    /**
     * Submit an optimization model task to a remote server for optimization
     * @return the remote task id
     */
    public String submitTask() {
        Pointer id = MemoryUtil.nativeString();
        MdoResult.checkResult(model.submitTask(id));
        return Native.toString(id.getByteArray(0, Mdo.MAX_NATIVE_STR_LEN));
    }

    /**
     * Retrieve remote task status by its task id
     * @param jobId the task id returned on task submission
     * @return remote task status
     */
    public MdoRemoteTask retrieveTask(String jobId) {
        final Pointer status = MemoryUtil.nativeString();
        final Pointer code = MemoryUtil.intByReference();
        final Pointer result = MemoryUtil.intByReference();
        final Pointer hasSol = MemoryUtil.intByReference();

        MdoResult.checkResult(model.retrieveTask(MemoryUtil.charArray(jobId), status, code, result, hasSol));
        return new MdoRemoteTask() {
            @Override
            public int getStatusCode() {
                return code.getInt(0);
            }

            @Override
            public MdoStatus getStatus() {
                return MdoStatus.fromCode(getStatusCode());
            }

            @Override
            public int getResultCode() {
                return result.getInt(0);
            }

            @Override
            public MdoResult getResult() {
                return MdoResult.fromCode(getResultCode());
            }

            @Override
            public boolean hasSolution() {
                return hasSol.getInt(0) != 0;
            }

            @Override
            public RemoteTaskStatus getRemoteTaskStatus() {
                return RemoteTaskStatus.valueOf(
                        Native.toString(status.getByteArray(0, Mdo.MAX_NATIVE_STR_LEN))
                );
            }
        };
    }

    /**
     * Print log to standard output or not
     * @param flag a flag that specified if the log shall be printed out to screen or not
     */
    public void setLogToConsole(boolean flag) {
        MdoResult.checkResult(model.setLogToConsole(flag ? 1 : 0));
    }

    /**
     * Redirect log to a file
     * @param filename the file name
     */
    public void setLogFile(String filename) {
        MdoResult.checkResult(model.setLogFile(MemoryUtil.charArray(filename)));
    }

    /**
     * Customize log output to a user-defined callback
     * @param logCallback the log callback function wrapper class
     */
    public void setLogCallback(final LogCallback logCallback) {
        Callback callback = new Callback() {
            public void invoke(String msg, Pointer userdata) {
                logCallback.log(msg);
            }
        };
        MdoResult.checkResult(model.setLogCallback(callback, Pointer.NULL));
    }

    /**
     * Solve the loaded optimization problem
     */
    public void solveProb() {
        MdoResult.checkResult(model.solveProb());
    }

    /**
     * Display the current solver results
     */
    public void displayResult() {
        model.displayResults();
    }

    /**
     * Get the current solver status
     * @return the solver status
     */
    public MdoStatus getStatus() {
        return MdoStatus.fromCode(model.getStatus());
    }

    /**
     * Explain the solver status enumeration
     * @param status the status to explain
     * @return a string that holds the explanation of the solver status enumeration
     */
    public String explainStatus(MdoStatus status) {
        Pointer val = MemoryUtil.nativeString();
        model.explainStatus(status.getCode(), val);
        return Native.toString(val.getByteArray(0, Mdo.MAX_NATIVE_STR_LEN));
    }

    /**
     * Explain the solver result enumeration
     * @param result the result to explain
     * @return a string that holds the explanation of the solver result enumeration
     */
    public String explainResult(MdoResult result) {
        Pointer val = MemoryUtil.nativeString();
        model.explainResult(result.getCode(), val);
        return Native.toString(val.getByteArray(0, Mdo.MAX_NATIVE_STR_LEN));
    }

    /**
     * Compute an Irreducible Inconsistent Subsystem (IIS)
     * @return the irreducible inconsistent rows and columns
     */
    public IIS computeIIS() {
        final List<Integer> idxRows = new ArrayList<Integer>();
        final List<Integer> idxCols = new ArrayList<Integer>();

        int numRows = model.getNumRows();
        int numCols = model.getNumCols();
        Pointer idxRowArr = MemoryUtil.intArray(numRows);
        Pointer idxColArr = MemoryUtil.intArray(numCols);
        Pointer pNumRows = MemoryUtil.intByReference();
        Pointer pNumCols = MemoryUtil.intByReference();

        MdoResult.checkResult(model.computeIIS(pNumRows, idxRowArr, pNumCols, idxColArr));
        for (int i = 0; i < pNumRows.getInt(0); i++) {
            idxRows.add(MemoryUtil.getInt(idxRowArr, i));
        }
        for (int i = 0; i < pNumCols.getInt(0); i++) {
            idxCols.add(MemoryUtil.getInt(idxColArr, i));
        }

        return new IIS() {
            @Override
            public List<Integer> getRowIndices() {
                return idxRows;
            }

            @Override
            public List<Integer> getColIndices() {
                return idxCols;
            }
        };
    }
}
