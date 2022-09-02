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


/**
 * Result status.
 */
public enum MdoResult{
    /** Nothing wrong.  */
    MDO_OKAY(0),
    /** Unspecified internal error.  */
    MDO_ERROR(-1),
    /** Insufficient memory.  */
    MDO_NOMEMORY(-2),
    /** License is not valid.  */
    MDO_INVALID_LICENSE(-10),
    /** MINDOPT_HOME not found. */
    MDO_HOME_ENV_NOT_FOUND(-11),
    /**  MINDOPT bin folder is not found. */
    MDO_LIB_FOLDER_NOT_FOUND(-12),
    /** (I/O) General IO error.  */
    MDO_IO_ERROR(-1000),
    /** (I/O) Failed to read data from file.  */
    MDO_FILE_READ_ERROR(-1001),
    /** (I/O) Failed to write data to file.  */
    MDO_FILE_WRITE_ERROR(-1002),
    /** (I/O) Invalid directory.  */
    MDO_DIRECTORY_ERROR(-1003),
    /** (I/O) Failed to parse the file.  */
    MDO_FORMAT_ERROR(-1100),
    /** (I/O) Failed to load model/parameter from file due to incompatible version error. */
    MDO_VERSION_ERROR(-1101),
    /** (I/O) The input token ID for the remote computing is not valid.  */
    MDO_REMOTE_INVALID_TOKEN(-1200),
    /** (I/O) Failed to connect to the remote computing server.  */
    MDO_REMOTE_CONNECTION_ERROR(-1201),
    /** Failed to input/load a model.  */
    MDO_MODEL_INPUT_ERROR(-2000),
    /** Model is empty.  */
    MDO_MODEL_EMPTY(-2001),
    /** Row index is not valid.  */
    MDO_MODEL_INVALID_ROW_IDX(-2002),
    /** Column index is not valid.  */
    MDO_MODEL_INVALID_COL_IDX(-2003),
    /** Row name is not valid.  */
    MDO_MODEL_INVALID_ROW_NAME(-2004),
    /** Column name is not valid.  */
    MDO_MODEL_INVALID_COL_NAME(-2005),
    /** A string attribute was not recognized.  */
    MDO_MODEL_INVALID_STR_ATTR(-2010),
    /** An integer attribute was not recognized.  */
    MDO_MODEL_INVALID_INT_ATTR(-2011),
    /** A real attribute was not recognized.  */
    MDO_MODEL_INVALID_REAL_ATTR(-2012),
    /** Solution is not available.  */
    MDO_NO_SOLN(-3000),
    /** Unbounded ray is not available.  */
    MDO_NO_RAY(-3001),
    /** Solver statistics is not available.  */
    MDO_NO_STATISTICS(-3002),
    /** Unrecognized basis status.  */
    MDO_INVALID_BASIS_STATUS(-3003),
    /** Failed to change a parameter value.  */
    MDO_PARAM_SET_ERROR(-4000),
    /** Failed to retrieve a parameter value.  */
    MDO_PARAM_GET_ERROR(-4001),
    /** Iteration limit was reached in optimization.  */
    MDO_ABORT_ITERATION_LIMIT(-9000),
    /** Time limit was reached in optimization.  */
    MDO_ABORT_TIME_LIMIT(-9001),
    /** Control-C command was captured in optimization.  */
    MDO_ABORT_CTRL_C(-9002),
    /** Node limit was reached in optimization. */
    MDO_ABORT_NODE_LIMIT(-9003),
    /** Stalling node limit was reached in optimization. */
    MDO_ABORT_STALLING_NODE_LIMIT(-9004),
    /** Selected optimization method is not supported. */
    MDO_ABORT_INVALID_METHOD(-9011),
    /** Optimization solver is not available for the input model. */
    MDO_ABORT_SOLVER_NOT_AVAILABLE(-9012),
    /** Numerical difficulties in Simplex algorithm.  */
    MDO_SIMPLEX_NUMERIC(-10000),
    /** Numerical difficulties in Interior-point algorithm.  */
    MDO_INTERIOR_NUMERIC(-20000);
    int code = 0;
    MdoResult(int code) { this.code = code; }
    public static void checkResult(int code) { if (code != MDO_OKAY.code) throw new MdoException(code); }
    public int getCode() { return code; }
    public static MdoResult fromCode(int code) {
        for (MdoResult value : MdoResult.values()) {
            if (value.code == code) return value;
        }
        throw new RuntimeException("Bad MdoResult code: " + code);
    }
}