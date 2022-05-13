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

import com.alibaba.damo.mindopt.impl.MdoNativeAPI;
import com.alibaba.damo.mindopt.impl.MdoNativeModel;

public class Mdo {
    /**
     * Value of infinity
     */
    public static final double INFINITY = 1e20;
    /**
     * Value of -infinity
     */
    public static final double NEGATIVE_INFINITY = -1e20;
    /**
     * Max string length for which string returned from native library
     */
    public static final int MAX_NATIVE_STR_LEN = 1024;
    /**
     * Less then or equal to
     */
    public static final char LESS_EQUAL = 'L';
    /**
     * Equal to
     */
    public static final char EQUAL = 'E';
    /**
     * Greater than or equal to
     */
    public static final char GREATER_EQUAL = 'G';

    // parameter names
    public static final String INT_PARAM_METHOD = "Method";
    public static final String INT_PARAM_NUM_THREADS = "NumThreads";
    public static final String INT_PARAM_PRESOLVE = "Presolve";
    public static final String INT_PARAM_DUALIZATION = "Dualization";
    public static final String INT_PARAM_SPX_MAX_ITERS = "SPX/MaxIterations";
    public static final String INT_PARAM_SPX_COL_GENS = "SPX/ColumnGeneration";
    public static final String INT_PARAM_SPX_CRASH_START = "SPX/CrashStart";
    public static final String INT_PARAM_IPM_MAX_ITERS = "IPM/MaxIterations";

    public static final String REAL_PARAM_MAX_TIME = "MaxTime";
    public static final String REAL_PARAM_SPX_PRIMAL_TOL = "SPX/PrimalTolerance";
    public static final String REAL_PARAM_SPX_DUAL_TOL = "SPX/DualTolerance";
    public static final String REAL_PARAM_IPM_PRIMAL_TOL = "IPM/PrimalTolerance";
    public static final String REAL_PARAM_IPM_DUAL_TOL = "IPM/DualTolerance";
    public static final String REAL_PARAM_IPM_GAP_TOL = "IPM/GapTolerance";

    public static final String STR_PARAM_REMOTE_TOKEN = "Remote/Token";
    public static final String STR_PARAM_REMOTE_DESC = "Remote/Desc";
    public static final String STR_PARAM_REMOTE_SERVER = "Remote/Server";
    public static final String STR_PARAM_REMOTE_FILE_MODEL = "Remote/File/Model";
    public static final String STR_PARAM_REMOTE_FILE_PARAM = "Remote/File/Param";
    public static final String STR_PARAM_REMOTE_FILE_SOLUTION = "Remote/File/Soln";
    public static final String STR_PARAM_REMOTE_FILE_PATH = "Remote/File/Path";

    // modeling attribute names
    public static final String INT_ATTR_MIN_SENSE = "MinSense";
    public static final String INT_ATTR_NUM_VARS = "NumVars";
    public static final String INT_ATTR_NUM_CONSS = "NumConss";
    public static final String INT_ATTR_NUM_ENTS = "NumEnts";
    public static final String INT_ATTR_IS_INTEGER = "IsInteger";

    public static final String REAL_ATTR_OBJ_CONST = "ObjConst";
    public static final String REAL_ATTR_LB = "LB";
    public static final String REAL_ATTR_UB = "UB";
    public static final String REAL_ATTR_OBJ = "Obj";
    public static final String REAL_ATTR_LHS = "LHS";
    public static final String REAL_ATTR_RHS = "RHS";

    public static final String STR_ATTR_PROB_NAME = "ProbName";

    public static final String STR_ATTR_COL_NAME = "ColName";
    public static final String STR_ATTR_ROW_NAME = "RowName";

    // remote attribute names
    public static final String STR_ATTR_REMOTE_TOKEN = "Remote/Token";
    public static final String STR_ATTR_REMOTE_DESC = "Remote/Desc";
    public static final String STR_ATTR_REMOTE_SERVER = "Remote/Server";
    public static final String STR_ATTR_REMOTE_FILE_MODEL = "Remote/File/Model";
    public static final String STR_ATTR_REMOTE_FILE_PARAM = "Remote/File/Param";
    public static final String STR_ATTR_REMOTE_FILE_SOLN = "Remote/File/Soln";
    public static final String STR_ATTR_REMOTE_FILE_PATH = "Remote/File/Path";

    // Solution attribute names
    public static final String INT_ATTR_HAS_SOLUTION = "HasSolution";
    public static final String INT_ATTR_HAS_PRIMAL_RAY = "HasPrimalRay";

    public static final String REAL_ATTR_SOLUTION_TIME = "SolutionTime";
    public static final String REAL_ATTR_PRIMAL_OBJ_VAL = "PrimalObjVal";
    public static final String REAL_ATTR_DUAL_OBJ_VAL = "DualObjVal";
    public static final String REAL_ATTR_PRIMAL_SOLUTION = "PrimalSoln";
    public static final String REAL_ATTR_DUAL_SOLUTION = "DualSoln";
    public static final String REAL_ATTR_ACTIVITY = "Activity";
    public static final String REAL_ATTR_REDUCED_COST = "ReducedCost";

    public static final String INT_ATTR_SPX_NUM_ITERS = "SPX/NumIters";

    public static final String INT_ATTR_IPX_NUM_ITERS = "IPM/NumIters";

    /**
     * Load the mindopt native library, this needs to be called at very beginning
     * @param nativeLibrary full path of mindopt native dynamic library
     */
    public static void load(String nativeLibrary) {
        MdoNativeAPI.InstanceHolder.load(nativeLibrary);
    }
}
