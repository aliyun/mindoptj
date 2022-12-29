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

public class Mdo {
    /** Value of infinity */
    public static final double INFINITY = 1e20;
    /** Value of -infinity */
    public static final double NEGATIVE_INFINITY = -1e20;
    /** Max string length for which string returned from native library. */
    public static final int MAX_NATIVE_STR_LEN = 1024;
    /** Less than or equal to */
    public static final char LESS_EQUAL = 'L';
    /** Equal to */
    public static final char EQUAL = 'E';
    /** Greater than or equal to */
    public static final char GREATER_EQUAL = 'G';

    /** The token in the remote computing service */
    public static final String STR_PARAM_REMOTE_TOKEN = "Remote/Token";
    /** The description of the optimization problem in the remote computing service */
    public static final String STR_PARAM_REMOTE_DESC = "Remote/Desc";
    /** The server address in the remote computing service */
    public static final String STR_PARAM_REMOTE_SERVER = "Remote/Server";
    /** The name of the serialized model file in the remote computing service */
    public static final String STR_PARAM_REMOTE_FILE_MODEL = "Remote/File/Model";
    /** The name of the serialized parameter file in the remote computing service */
    public static final String STR_PARAM_REMOTE_FILE_PARAM = "Remote/File/Param";
    /** The name of the serialized solution file in the remote computing service */
    public static final String STR_PARAM_REMOTE_FILE_SOLUTION = "Remote/File/Soln";
    /** The path for storing the model and parameter files in the remote computing service */
    public static final String STR_PARAM_REMOTE_FILE_PATH = "Remote/File/Path";

    /** The optimization method */
    public static final String INT_PARAM_METHOD = "Method";
    /** Specifies whether to enable presolving */
    public static final String INT_PARAM_PRESOLVE = "Presolve";
    /** Specifies whether to perform dualization for the model */
    public static final String INT_PARAM_DUALIZATION = "Dualization";
    /** The number of used threads */
    public static final String INT_PARAM_NUM_THREADS = "NumThreads";
    /** Specifies whether to enable the network simplex method */
    public static final String INT_PARAM_ENABLE_NETWORK_FLOW = "EnableNetworkFlow";
    /** Specifies whether to enable structure detection for stochastic linear programming */
    public static final String INT_PARAM_ENABLE_STOCHASTIC_LP = "EnableStochasticLP";
    /** Specifies whether to use column generation in the simplex method */
    public static final String INT_PARAM_SPX_COLUMN_GENERATION = "SPX/ColumnGeneration";
    /** Specifies whether to use initial basic solution generation in the simplex method */
    public static final String INT_PARAM_SPX_CRASH_START = "SPX/CrashStart";
    /** The maximum number of iterations in the simplex method */
    public static final String INT_PARAM_SPX_MAX_ITERS ="SPX/MaxIterations";
    /** The primal pricing strategy in the simplex method */
    public static final String INT_PARAM_SPX_PRIMAL_PRICING = "SPX/PrimalPricing";
    /** The dual pricing strategy in the simplex method */
    public static final String INT_PARAM_SPX_DUAL_PRICING = "SPX/DualPricing";
    /** The maximum number of iterations in the interior point method (IPM) */
    public static final String INT_PARAM_IPM_MAX_ITERS = "IPM/MaxIterations";
    /** The maximum number of nodes in MIP */
    public static final String INT_PARAM_MIP_MAX_NODES = "MIP/MaxNodes";
    /** The Root Parallelism in MIP */
    public static final String INT_PARAM_MIP_ROOT_PARALLELISM = "MIP/RootParallelism";

    /** The maximum solving time */
    public static final String REAL_PARAM_MAX_TIME = "MaxTime";
    /** The primal feasible tolerance in the simplex method */
    public static final String REAL_PARAM_SPX_PRIMAL_TOLERANCE = "SPX/PrimalTolerance";
    /** The dual feasible tolerance in the simplex method */
    public static final String REAL_PARAM_SPX_DUAL_TOLERANCE = "SPX/DualTolerance";
    /** The primal feasible (relative) tolerance in the interior point method (IPM) */
    public static final String REAL_PARAM_IPM_PRIMAL_TOLERANCE = "IPM/PrimalTolerance";
    /** The dual feasible (relative) tolerance in the IPM */
    public static final String REAL_PARAM_IPM_DUAL_TOLERANCE = "IPM/DualTolerance";
    /** The dual gap (relative) tolerance in the IPM */
    public static final String REAL_PARAM_IPM_GAP_TOLERANCE = "IPM/GapTolerance";
    /** The tolerance of the value of an integer for MILP */
    public static final String REAL_PARAM_MIP_INTEGER_TOLERANCE = "MIP/IntegerTolerance";
    /** The tolerance of the objective for MILP */
    public static final String REAL_PARAM_MIP_OBJECTIVE_TOLERANCE = "MIP/ObjectiveTolerance";
    /** The allowable gap (absolute) for MILP */
    public static final String REAL_PARAM_MIP_GAP_ABS = "MIP/GapAbs";
    /** The allowable gap (relative) for MILP */
    public static final String REAL_PARAM_MIP_GAP_REL = "MIP/GapRel";

    /* Model attributes */
    /** This attribute is used to set and query the name of the current optimization model */
    public static final String STR_ATTR_PROB_NAME = "ProbName";
    /** Indicates whether the optimized problem is a minimization problem */
    public static final String INT_ATTR_MIN_SENSE = "MinSense";
    /** The constant offset of the target function */
    public static final String REAL_ATTR_OBJ_CONST = "ObjConst";
    /** The total number of variables in the model */
    public static final String INT_ATTR_NUM_VARS = "NumVars";
    /** The total number of constraints in the model */
    public static final String INT_ATTR_NUM_CONSS = "NumConss";
    /** The total number of non-zero elements in the model */
    public static final String INT_ATTR_NUM_ENTS = "NumEnts";
    /** The lower bound of a variable */
    public static final String REAL_ATTR_LB = "LB";
    /** The upper bound of a variable */
    public static final String REAL_ATTR_UB = "UB";
    /** The coefficient of variables in the target linear function */
    public static final String REAL_ATTR_OBJ = "Obj";
    /** The lower bound (left-side value) of a constraint */
    public static final String REAL_ATTR_LHS = "LHS";
    /** The upper bound (right-side value) of a constraint */
    public static final String REAL_ATTR_RHS = "RHS";
    /** The name of a variable (column) */
    public static final String STR_ATTR_COL_NAME = "ColName";
    /** The name of a constraint (row) */
    public static final String STR_ATTR_ROW_NAME = "RowName";
    /** Indicates whether a variable is of the integer type */
    public static final String INT_ATTR_IS_INTEGER = "IsInteger";


    /* Solution attributes */
    /** The solution time, in seconds */
    public static final String REAL_ATTR_SOLUTION_TIME = "SolutionTime";
    /** Indicates whether the optimization solution is available */
    public static final String INT_ATTR_HAS_SOLUTION = "HasSolution";
    /** Indicates whether the primal ray is available */
    public static final String INT_ATTR_HAS_PRIMAL_RAY = "HasPrimalRay";
    /** Indicates whether the dual ray is available */
    public static final String INT_ATTR_HAS_DUAL_RAY = "HasDualRay";
    /** The primal object value */
    public static final String REAL_ATTR_PRIMAL_OBJ_VAL = "PrimalObjVal";
    /** The dual object value */
    public static final String REAL_ATTR_DUAL_OBJ_VAL = "DualObjVal";
    /** The primal solution */
    public static final String REAL_ATTR_PRIMAL_SOLUTION = "PrimalSoln";
    /** The dual solution */
    public static final String REAL_ATTR_DUAL_SOLUTION = "DualSoln";
    /** The basis of the primal solution */
    public static final String INT_ATTR_COL_BASIS = "ColBasis";
    /** The basis of the dual solution */
    public static final String INT_ATTR_ROW_BASIS = "RowBasis";
    /** The value of the primal activity */
    public static final String REAL_ATTR_ACTIVITY = "Activity";
    /** The value of allowable gap (absolute) for MILP */
    public static final String REAL_ATTR_MIP_GAP_ABS = "MIP/GapAbs";
    /** The value of allowable gap (relative) for MILP */
    public static final String REAL_ATTR_MIP_GAP_REL = "MIP/GapRel";
    /** The value of reduced costs */
    public static final String REAL_ATTR_REDUCED_COST = "ReducedCost";
    /** The value of sym matrix primal solution */
    public static final String REAL_ATTR_SYM_MAT_PRIMAL_SOLN = "SymMatPrimalSoln";

    /* Simplex method attributes */
    /** The number of iterations in the simplex method */
    public static final String INT_ATTR_SPX_NUM_ITERS = "SPX/NumIters";

    /* IPM attributes */
    /** Number of iterations in the interior point method (IPM). */
    public static final String INT_ATTR_IPX_NUM_ITERS = "IPM/NumIters";

    /** The presolver cost time, in seconds */
    public static final String REAL_ATTR_PRESOLVER_TIME = "PresolverTime";
    /** The solver cost time, in seconds */
    public static final String REAL_ATTR_SOLVER_TIME = "SolverTime";

    /* Remote attribute names */
    /** The token in the remote computing service */
    public static final String STR_ATTR_REMOTE_TOKEN = "Remote/Token";
    /** The description of the optimization problem in the remote computing service */
    public static final String STR_ATTR_REMOTE_DESC = "Remote/Desc";
    /** The server address in the remote computing service */
    public static final String STR_ATTR_REMOTE_SERVER = "Remote/Server";
    /** The name of the serialized model file in the remote computing service */
    public static final String STR_ATTR_REMOTE_FILE_MODEL = "Remote/File/Model";
    /** The name of the serialized parameter file in the remote computing service */
    public static final String STR_ATTR_REMOTE_FILE_PARAM = "Remote/File/Param";
    /** The name of the serialized solution file in the remote computing service */
    public static final String STR_ATTR_REMOTE_FILE_SOLN = "Remote/File/Soln";
    /** The path for storing the model and parameter files in the remote computing service */
    public static final String STR_ATTR_REMOTE_FILE_PATH = "Remote/File/Path";

    /**
     * Load the mindopt native library, this needs to be called at very beginning
     * @param nativeLibrary full path of mindopt native dynamic library
     */
    public static void load(String nativeLibrary) {
        MdoNativeAPI.InstanceHolder.load(nativeLibrary);
    }
}
