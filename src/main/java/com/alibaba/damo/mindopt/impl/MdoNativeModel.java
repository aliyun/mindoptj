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

package com.alibaba.damo.mindopt.impl;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * The mindopt model native interface, it is strongly recommended to use MdoModel instead
 */
public class MdoNativeModel {
    static MdoNativeAPI inst() {
        if (MdoNativeAPI.InstanceHolder.get() == null) {
            throw new RuntimeException("Mdo.load() needs to be called at very beginning.");
        }
        return MdoNativeAPI.InstanceHolder.get();
    }

    private Pointer getModel() {
        return model.getValue();
    }
    PointerByReference model = new PointerByReference();
    /**
     * Change the value of a string-valued row/column attribute.
     * @param att [in] A string-valued row/column attribute.
     * @param idx [in] An index.
     * @param val [in] A new value.
     * @return MdoResult code
     */
    public int setStrAttrIndex(
            Pointer att,
            int idx,
            Pointer val
    ) {
        return inst().Mdo_setStrAttrIndex(
                getModel(),
                att,
                idx,
                val
        );
    }
    /**
     * Retrieve the value of a string-valued model attribute.
     * @param att [in] A string-valued row/column attribute.
     * @param idx [in] An index.
     * @param size [in] Max length of ``val``.
     * @param val [out] The current value.
     * @return MdoResult code
     */
    public int getStrAttrIndex(
            Pointer att,
            int idx,
            int size,
            Pointer val
    ) {
        return inst().Mdo_getStrAttrIndex(
                getModel(),
                att,
                idx,
                size,
                val
        );
    }
    /**
     *  Change the value of a string-valued model attribute.
     *  @param att [in] A string-valued model attribute.
     *  @param val [in] A new value.
     *  @return MdoResult code
     *
     */
    public int setStrAttr(
            Pointer att,
            Pointer val
    ) {
        return inst().Mdo_setStrAttr(
                getModel(),
                att,
                val
        );
    }
    /**
     * Change the value of a 32-bit integer-valued model attribute.
     * @param att [in] A 32-bit integer-valued model attribute.
     * @param val [in] A new value.
     * @return MdoResult code
     */
    public int setIntAttr(
            Pointer att,
            int val
    ) {
        return inst().Mdo_setIntAttr(
                getModel(),
                att,
                val
        );
    }
    /**
     * Retrieve the value of a string-valued model attribute.
     * @param att [in] A string-valued model attribute.
     * @param size [in] Max length of ``val``.
     * @param val [out] The current value.
     * @return MdoResult code
     */
    public int getStrAttr(
            Pointer att,
            int size,
            Pointer val
    ) {
        return inst().Mdo_getStrAttr(
                getModel(),
                att,
                size,
                val
        );
    }
    /**
     * Retrieve the value of a 32-bit integer-valued model attribute.
     * @param att [in] A 32-bit integer-valued model attribute.
     * @param val [out] The current value.
     * @return MdoResult code
     */
    public int getIntAttr(
            Pointer att,
            Pointer val
    ) {
        return inst().Mdo_getIntAttr(
                getModel(),
                att,
                val
        );
    }
    /**
     * Change the value of a 32-bit integer-valued row/column attribute.
     * @param att [in] A 32-bit integer-valued row/column attribute.
     * @param idx [in] An index.
     * @param val [in] A new value.
     * @return MdoResult code
     */
    public int setIntAttrIndex(
            Pointer att,
            int idx,
            int val
    ) {
        return inst().Mdo_setIntAttrIndex(
                getModel(),
                att,
                idx,
                val
        );
    }
    /**
     * Retrieve the value of a 32-bit integer-valued row/column attribute.
     * @param att [in] A 32-bit integer-valued row/column attribute.
     * @param idx [in] An index.
     * @param val [out] The current value.
     * @return MdoResult code
     */
    public int getIntAttrIndex(
            Pointer att,
            int idx,
            Pointer val
    ) {
        return inst().Mdo_getIntAttrIndex(
                getModel(),
                att,
                idx,
                val
        );
    }
    /**
     * Change the values of the specified array of a 32-bit integer-valued row/column attribute.
     * @param att [in] A 32-bit integer-valued row/column attribute.
     * @param bgn [in] Index of the first element to access.
     * @param len [in] Number of elements to access.
     * @param val [in] The new values for the specified array of the attribute.
     * @return MdoResult code
     */
    public int setIntAttrArray(
            Pointer att,
            int bgn,
            int len,
            Pointer val
    ) {
        return inst().Mdo_setIntAttrArray(
                getModel(),
                att,
                bgn,
                len,
                val
        );
    }
    /**
     * Retrieve the values of the specified array of a 32-bit integer-valued row/column attribute.
     * @param att [in] A 32-bit integer-valued row/column attribute.
     * @param bgn [in] Index of the first element to access.
     * @param len [in] Number of elements to access.
     * @param val [out] The current values of the specified array of the attribute.
     * @return MdoResult code
     */
    public int getIntAttrArray(
            Pointer att,
            int bgn,
            int len,
            Pointer val
    ) {
        return inst().Mdo_getIntAttrArray(
                getModel(),
                att,
                bgn,
                len,
                val
        );
    }
    /**
     * Change the value of a real-valued model attribute.
     * @param att [in] A real-valued model attribute.
     * @param val [in] A new value.
     * @return MdoResult code
     */
    public int setRealAttr(
            Pointer att,
            double val
    ) {
        return inst().Mdo_setRealAttr(
                getModel(),
                att,
                val
        );
    }
    /**
     * Retrieve the value of a real-valued model attribute.
     * @param att [in] A real-valued model attribute.
     * @param val [out] The current value.
     * @return MdoResult code
     */
    public int getRealAttr(
            Pointer att,
            Pointer val
    ) {
        return inst().Mdo_getRealAttr(
                getModel(),
                att,
                val
        );
    }
    /**
     * Change the value of a real-valued array of the row/column attribute.
     * @param att [in] A real-valued row/column attribute.
     * @param idx [in] An index.
     * @param val [in] A new value.
     * @return MdoResult code
     */
    public int setRealAttrIndex(
            Pointer att,
            int idx,
            double val
    ) {
        return inst().Mdo_setRealAttrIndex(
                getModel(),
                att,
                idx,
                val
        );
    }
    /**
     * Retrieve the value of a real-valued array of the row/column attribute.
     * @param att [in] A real-valued row/column attribute.
     * @param idx [in] An index.
     * @param val [out] The current value.
     * @return MdoResult code
     */
    public int getRealAttrIndex(
            Pointer att,
            int idx,
            Pointer val
    ) {
        return inst().Mdo_getRealAttrIndex(
                getModel(),
                att,
                idx,
                val
        );
    }
    /**
     * Change the values of the specified array of the row/column attribute.
     * @param att [in] The real-valued array of the row/column attribute.
     * @param bgn [in] Index of the first element to access.
     * @param len [in] Number of elements to access.
     * @param val [in] The new values for the specified array of the attribute.
     * @return MdoResult code
     */
    public int setRealAttrArray(
            Pointer att,
            int bgn,
            int len,
            Pointer val
    ) {
        return inst().Mdo_setRealAttrArray(
                getModel(),
                att,
                bgn,
                len,
                val
        );
    }
    /**
     * Retrieve the values of the specified array of the row/column attribute.
     * @param att [in] The attribute.
     * @param bgn [in] Index of the first element to access.
     * @param len [in] Number of elements to access.
     * @param val [out] The current values of the specified array of the attribute.
     * @return MdoResult code
     */
    public int getRealAttrArray(
            Pointer att,
            int bgn,
            int len,
            Pointer val
    ) {
        return inst().Mdo_getRealAttrArray(
                getModel(),
                att,
                bgn,
                len,
                val
        );
    }
    /**
     * Start command-line tool.
     * @param argc [in] ARGC.
     * @param argv [in] ARGV.
     * @return MdoResult code
     */
    public static int startCmd(
            int argc,
            Pointer argv
    ) {
        return inst().Mdo_startCmd(
                argc,
                argv
        );
    }
    /**
     * Read an optimization problem from a file.
     * @param filename [in] A character array that specifies the filename.
     * @return MdoResult code
     */
    public int readProb(
            Pointer filename
    ) {
        return inst().Mdo_readProb(
                getModel(),
                filename
        );
    }
    /**
     * Write an optimization problem to a file.
     * @param filename [in] A string array that specifies the filename.
     * @return MdoResult code
     */
    public int writeProb(
            Pointer filename
    ) {
        return inst().Mdo_writeProb(
                getModel(),
                filename
        );
    }
    /**
     * Write an optimization solution to a file.
     * @param filename [in] A character array that specifies the filename.
     * @return MdoResult code
     */
    public int writeSoln(
            Pointer filename
    ) {
        return inst().Mdo_writeSoln(
                getModel(),
                filename
        );
    }
    /**
     * Read an optimization model task from a file.
     * @param filename [in] A character array that specifies the model.
     * @param read_model [in] A boolean flag that specifies if the model shall be loaded.
     * @param read_param [in] A boolean flag that specifies if the parameters shall be loaded.
     * @param read_soln [in] A boolean flag that specifies if the solution shall be loaded.
     * @return MdoResult code
     */
    public int readTask(
            Pointer filename,
            int read_model,
            int read_param,
            int read_soln
    ) {
        return inst().Mdo_readTask(
                getModel(),
                filename,
                read_model,
                read_param,
                read_soln
        );
    }
    /**
     * Write an optimization model task to a file.
     * @param filename [in] A character array that specifies the filename.
     * @param write_model [in] A boolean flag that specifies if the model shall be outputted.
     * @param write_param [in] A boolean flag that specifies if the parameters shall be outputted.
     * @param write_soln [in] A boolean flag that specifies if the solution shall be outputted.
     * @return MdoResult code
     */
    public int writeTask(
            Pointer filename,
            int write_model,
            int write_param,
            int write_soln
    ) {
        return inst().Mdo_writeTask(
                getModel(),
                filename,
                write_model,
                write_param,
                write_soln
        );
    }
    /**
     * Print out log to screen or not.
     * @param flag [in] A flag that specified if the log shall be printed out to screen or not.
     * @return MdoResult code
     */
    public int setLogToConsole(
            int flag
    ) {
        return inst().Mdo_setLogToConsole(
                getModel(),
                flag
        );
    }
    /**
     * Redirect output log to a file.
     * @param filename [in] Path to the log file.
     * @return MdoResult code
     */
    public int setLogFile(
            Pointer filename
    ) {
        return inst().Mdo_setLogFile(
                getModel(),
                filename
        );
    }
    /**
     * Redirect output log to a user-defined callback function.
     * @param logcb [in] User defined callback function.
     * @param userdata [in] User defined data taht will be passed into the callback function.
     * @return MdoResult code
     */
    public int setLogCallback(
            Callback logcb,
            Pointer userdata
    ) {
        return inst().Mdo_setLogCallback(
                getModel(),
                logcb,
                userdata
        );
    }
    /**
     * Create a MindOpt model with default parameter settings.
     * @return MdoResult code
     */
    public int createMdl(
    ) {
        return inst().Mdo_createMdl(
                model
        );
    }
    /**
     * Destroy a model and all associated data.
     */
    public void freeMdl(
    ) {
        inst().Mdo_freeMdl(
                model
        );
    }
    /**
     * Load in a problem.
     * @param num_cols [in] Number of columns (variables).
     * @param num_rows [in] Number of rows (constraints).
     * @param bgn [in] An integer array that defines the beginning index of a CSC (compressed sparse column) matrix. Here bgn must have num_cols + 1 elements; as a result the length of the last column is bgn[num_cols] - bgn[num_cols - 1].
     * @param indices [in] An integer array that defines the column index of nonzero elements in the CSC matrix.
     * @param values [in] A real array that defines the values of nonzero elements in the CSC matrix.
     * @param lbs [in] A real array that holds the lower bounds of variables. Can be NULL; in this case 0 will be used as the default value for all lower bounds.
     * @param ubs [in] A real array that holds the upper bounds of variables. Can be NULL; in this case $ \infty $ will be used as the default value for all upper bounds.
     * @param objs [in] A real array that holds the linear objective coefficients. Can be NULL; in this case 0 will be used as the default value for all objective coefficients.
     * @param are_integers [in] A flag array that specifies if a variable is an integer variable or not. Can be NULL; in this case all variables will be treated as continuous variables.
     * @param obj_const [in] The objective offset.
     * @param is_min [in] A boolean flag to specify if the objective function has a minimization sense.
     * @param lhss [in] A real array that holds the lower bounds (LHS-values) of constraints. Can be NULL; in this case $ -\infty $ will be used as the default value for all lower bounds.
     * @param rhss [in] A real array that holds the upper bounds (RHS-values) of constraints. Can be NULL; in this case $ \infty $ will be used as the default value for all upper bounds.
     * @param col_names [in] A pointer array that holds the column (variable) names. Can be NULL.
     * @param row_names [in] A pointer array that holds the row (constraint) names. Can be NULL.
     * @return MdoResult code
     */
    public int loadModel(
            int num_cols,
            int num_rows,
            Pointer bgn,
            Pointer indices,
            Pointer values,
            Pointer lbs,
            Pointer ubs,
            Pointer objs,
            Pointer are_integers,
            double obj_const,
            int is_min,
            Pointer lhss,
            Pointer rhss,
            Pointer col_names,
            Pointer row_names
    ) {
        return inst().Mdo_loadModel(
                getModel(),
                num_cols,
                num_rows,
                bgn,
                indices,
                values,
                lbs,
                ubs,
                objs,
                are_integers,
                obj_const,
                is_min,
                lhss,
                rhss,
                col_names,
                row_names
        );
    }
    /**
     * Introduce a new column to the model.
     * @param lb [in] Lower bounded value.
     * @param ub [in] Upper bounded value.
     * @param obj [in] Objective coefficient.
     * @param size [in] Number of the nonzero entries, can be zero.
     * @param indices [in] Array that holds the index of all nonzero elements, can be NULL.
     * @param values [in] Array that holds the value of all nonzero elements, can be NULL.
     * @param name [in] Column name, can be NULL.
     * @param is_integer [in] Flag that specifies if this is an integer variable.
     * @return MdoResult code
     */
    public int addCol(
            double lb,
            double ub,
            double obj,
            int size,
            Pointer indices,
            Pointer values,
            Pointer name,
            int is_integer
    ) {
        return inst().Mdo_addCol(
                getModel(),
                lb,
                ub,
                obj,
                size,
                indices,
                values,
                name,
                is_integer
        );
    }
    /**
     * Add multiple columns to the model.
     * @param num_cols [in] Number of columns (variables).
     * @param lbs [in] A real array that holds the lower bounds of variables. Can be NULL; in this case 0 will be used as the default value for all lower bounds.
     * @param ubs [in] A real array that holds the upper bounds of variables. Can be NULL; in this case $ \infty $ will be used as the default value for all upper bounds.
     * @param objs [in] A real array that holds the linear objective coefficients. Can be NULL; in this case 0 will be used as the default value for all objective coefficients.
     * @param bgn [in] An integer array that defines the beginning index of a CSC (compressed sparse column) matrix. Here bgn must have num_cols + 1 elements; as a result the length of the last column is bgn[num_cols] - bgn[num_cols - 1].
     * @param indices [in] An integer array that defines the column index of nonzero elements in the CSC matrix.
     * @param values [in] A real array that defines the values of nonzero elements in the CSC matrix.
     * @param col_names [in] A pointer array that holds the column (variable) names. Can be NULL.
     * @param are_integers [in] A flag array that specifies if a variable is an integer variable or not. Can be NULL; in this case all variables will be treated as continuous variables.
     * @return MdoResult code
     */
    public int addCols(
            int num_cols,
            Pointer lbs,
            Pointer ubs,
            Pointer objs,
            Pointer bgn,
            Pointer indices,
            Pointer values,
            Pointer col_names,
            Pointer are_integers
    ) {
        return inst().Mdo_addCols(
                getModel(),
                num_cols,
                lbs,
                ubs,
                objs,
                bgn,
                indices,
                values,
                col_names,
                are_integers
        );
    }
    /**
     * Introduce a new linear constraint to the model.
     * @param lhs [in] Left-hand-side value.
     * @param rhs [in] Right-hand-side value.
     * @param size [in] Number of the nonzero entries, can be 0.
     * @param indices [in] Array that holds the index of all nonzero elements, can be NULL.
     * @param values [in] Array that holds the value of all nonzero elements, can be NULL.
     * @param name [in] Row name, can be NULL.
     * @return MdoResult code
     */
    public int addRow(
            double lhs,
            double rhs,
            int size,
            Pointer indices,
            Pointer values,
            Pointer name
    ) {
        return inst().Mdo_addRow(
                getModel(),
                lhs,
                rhs,
                size,
                indices,
                values,
                name
        );
    }
    /**
     * Add multiple rows to the model.
     * @param num_rows [in] Number of rows (constraints).
     * @param lhss [in] A real array that holds the lower bounds (LHS-values) of constraints. Can be NULL; in this case $ -\infty $ will be used as the default value for all lower bounds.
     * @param rhss [in] A real array that holds the upper bounds (RHS-values) of constraints. Can be NULL; in this case $ \infty $ will be used as the default value for all upper bounds.
     * @param bgn [in] An integer array that defines the beginning index of a CSR (compressed sparse row) matrix. Here bgn must have num_rows + 1 elements; as a result the length of the last row is bgn[num_rows] - bgn[num_rows - 1].
     * @param indices [in] An integer array that defines the row index of nonzero elements in the CSR matrix.
     * @param values [in] A real array that defines the values of nonzero elements in the CSR matrix.
     * @param row_names [in] A pointer array that holds the row (constraint) names. Can be NULL.
     * @return MdoResult code
     */
    public int addRows(
            int num_rows,
            Pointer lhss,
            Pointer rhss,
            Pointer bgn,
            Pointer indices,
            Pointer values,
            Pointer row_names
    ) {
        return inst().Mdo_addRows(
                getModel(),
                num_rows,
                lhss,
                rhss,
                bgn,
                indices,
                values,
                row_names
        );
    }
    /**
     * Extract a set of columns from the constraint matrix.
     * @param num_cols [in] Number of columns to access.
     * @param col_indices [in] Indices of columns to access.
     * @param bgn [in] An integer array that defines the beginning index of a CSC (compressed sparse column) matrix. Here bgn must have num_cols + 1 elements; as a result the length of the last column is bgn[num_cols] - bgn[num_cols - 1]. Can by NULL.
     * @param indices [in] An integer array that defines the column index of nonzero elements in the CSC matrix. Can by NULL.
     * @param values [in] A real array that defines the values of nonzero elements in the CSC matrix. Can by NULL.
     * @param size [in] Current lenghth of indices or values.
     * @param real_size [out] Minimal required lenghth for indices or values.
     * @return MdoResult code
     */
    public int getCols(
            int num_cols,
            Pointer col_indices,
            Pointer bgn,
            Pointer indices,
            Pointer values,
            int size,
            Pointer real_size
    ) {
        return inst().Mdo_getCols(
                getModel(),
                num_cols,
                col_indices,
                bgn,
                indices,
                values,
                size,
                real_size
        );
    }
    /**
     * Extract a set of rows from the constraint matrix.
     * @param num_rows [in] Number of rows to access.
     * @param row_indices [in] Indices of rows to access.
     * @param bgn [in] An integer array that defines the beginning index of a CSR (compressed sparse row) matrix. Here bgn must have num_rows + 1 elements; as a result the length of the last row is bgn[num_rows] - bgn[num_rows - 1]. Can by NULL.
     * @param indices [in] An integer array that defines the row index of nonzero elements in the CSR matrix. Can by NULL.
     * @param values [in] A real array that defines the values of nonzero elements in the CSR matrix. Can by NULL.
     * @param size [in] Current lenghth of indices or values.
     * @param real_size [out] Minimal required lenghth for indices or values.
     * @return MdoResult code
     */
    public int getRows(
            int num_rows,
            Pointer row_indices,
            Pointer bgn,
            Pointer indices,
            Pointer values,
            int size,
            Pointer real_size
    ) {
        return inst().Mdo_getRows(
                getModel(),
                num_rows,
                row_indices,
                bgn,
                indices,
                values,
                size,
                real_size
        );
    }
    /**
     * Check if the objective function has a minimization sense.
     * @return nonzero for yes, otherwise returns zero
     */
    public int isMinObjSense(
    ) {
        return inst().Mdo_isMinObjSense(
                getModel()
        );
    }
    /**
     * Check if the objective function has a maximization sense.
     * @return nonzero for yes, otherwise returns zero
     */
    public int isMaxObjSense(
    ) {
        return inst().Mdo_isMaxObjSense(
                getModel()
        );
    }
    /**
     * Change the objective function of the loaded problem to minimization sense.
     */
    public void setMinObjSense(
    ) {
        inst().Mdo_setMinObjSense(
                getModel()
        );
    }
    /**
     * Change the objective function of the loaded problem to maximization sense.
     */
    public void setMaxObjSense(
    ) {
        inst().Mdo_setMaxObjSense(
                getModel()
        );
    }
    /**
     * Retrieve the objective offset (constant term).
     * @return objective offset
     */
    public double getObjOffset(
    ) {
        return inst().Mdo_getObjOffset(
                getModel()
        );
    }
    /**
     * Retrieve the objective offset (constant term).
     * @param obj_fix [in] New objective offset (fixed cost).
     */
    public void setObjOffset(
            double obj_fix
    ) {
        inst().Mdo_setObjOffset(
                getModel(),
                obj_fix
        );
    }
    /**
     * Retrieve the total number of rows.
     * @return number of rows
     */
    public int getNumRows(
    ) {
        return inst().Mdo_getNumRows(
                getModel()
        );
    }
    /**
     * Retrieve the total number of columns.
     * @return number columns
     */
    public int getNumCols(
    ) {
        return inst().Mdo_getNumCols(
                getModel()
        );
    }
    /**
     * Retrieve the total number of the nonzero entries.
     * @return number of nonzero entries
     */
    public int getNumElements(
    ) {
        return inst().Mdo_getNumElements(
                getModel()
        );
    }
    /**
     * Retrieve a set of objective coefficients.
     * @param size [in] Number of variables to access.
     * @param indices [in] An array that holds the index of variables to access.
     * @param objs [out] An array that will hold the currnet objective coefficients of each specified variable.
     * @return MdoResult code
     */
    public int getObjs(
            int size,
            Pointer indices,
            Pointer objs
    ) {
        return inst().Mdo_getObjs(
                getModel(),
                size,
                indices,
                objs
        );
    }
    /**
     * Modify a set of objective coefficients.
     * @param size [in] Number of variables to access.
     * @param indices [in] An array that holds the index of variables to access.
     * @param objs [in] An array that holds the new objective coefficients for each specified variable.
     * @return MdoResult code
     */
    public int setObjs(
            int size,
            Pointer indices,
            Pointer objs
    ) {
        return inst().Mdo_setObjs(
                getModel(),
                size,
                indices,
                objs
        );
    }
    /**
     * Retrieve a set of variable lower bounds.
     * @param size [in] Number of variables to access.
     * @param indices [in] An array that holds the index of variables to access.
     * @param lbs [out] An array that will hold the currnet lower bounds of each specified variable.
     * @return MdoResult code
     */
    public int getLbs(
            int size,
            Pointer indices,
            Pointer lbs
    ) {
        return inst().Mdo_getLbs(
                getModel(),
                size,
                indices,
                lbs
        );
    }
    /**
     * Modify a set of variable lower bounds.
     * @param size [in] Number of variables to access.
     * @param indices [in] An array that holds the index of variables to access.
     * @param lbs [in] An array that holds the new lower bounds for each specified variable.
     * @return MdoResult code
     */
    public int setLbs(
            int size,
            Pointer indices,
            Pointer lbs
    ) {
        return inst().Mdo_setLbs(
                getModel(),
                size,
                indices,
                lbs
        );
    }
    /**
     * Retrieve a set of variable upper bounds.
     * @param size [in] Number of variables to access.
     * @param indices [in] An array that holds the index of variables to access.
     * @param ubs [out] An array that will hold the currnet upper bounds of each specified variable.
     * @return MdoResult code
     */
    public int getUbs(
            int size,
            Pointer indices,
            Pointer ubs
    ) {
        return inst().Mdo_getUbs(
                getModel(),
                size,
                indices,
                ubs
        );
    }
    /**
     * Modify a set of variable upper bounds.
     * @param size [in] Number of variables to access.
     * @param indices [in] An array that holds the index of variables to access.
     * @param ubs [in] An array that holds the new upper bounds for each specified variable.
     * @return MdoResult code
     */
    public int setUbs(
            int size,
            Pointer indices,
            Pointer ubs
    ) {
        return inst().Mdo_setUbs(
                getModel(),
                size,
                indices,
                ubs
        );
    }
    /**
     * Retrieve a set of flags that specifies the variable types (integer variables or not).
     * @param size [in] Number of variables to access.
     * @param indices [in] An array that holds the index of variables to access.
     * @param are_integers [out] An array that will hold the current variable types (integer or not) of each specified variable.
     * @return MdoResult code
     */
    public int getIntegers(
            int size,
            Pointer indices,
            Pointer are_integers
    ) {
        return inst().Mdo_getIntegers(
                getModel(),
                size,
                indices,
                are_integers
        );
    }
    /**
     * Modify a set of variable types (integer variables or not).
     * @param size [in] Number of variables to access.
     * @param indices [in] An array that holds the index of variables to access.
     * @param are_integers [in] An array that holds the new variable types (integer or not) for each specified variable.
     * @return MdoResult code
     */
    public int setIntegers(
            int size,
            Pointer indices,
            Pointer are_integers
    ) {
        return inst().Mdo_setIntegers(
                getModel(),
                size,
                indices,
                are_integers
        );
    }
    /**
     * Access the column name.
     * @param j [in] Column index.
     * @param col_name [in] Column name. Can be NULL.
     * @param size [in] Max string length.
     * @param real_size [in] Real length of the requested name length. Can be NULL
     * @return MdoResult code
     */
    public int getColName(
            int j,
            Pointer col_name,
            int size,
            Pointer real_size
    ) {
        return inst().Mdo_getColName(
                getModel(),
                j,
                col_name,
                size,
                real_size
        );
    }
    /**
     * Modify a set of variable names.
     * @param size [in] Number of variables to access.
     * @param indices [in] An array that holds the index of variables to access.
     * @param col_names [in] An array of new names for each specified variable.
     * @return MdoResult code
     */
    public int setColNames(
            int size,
            Pointer indices,
            Pointer col_names
    ) {
        return inst().Mdo_setColNames(
                getModel(),
                size,
                indices,
                col_names
        );
    }
    /**
     * Return the column index of a column name in the model.
     * @param name [in] Column name.
     * @return MdoResult code
     */
    public int getColIndex(
            Pointer name
    ) {
        return inst().Mdo_getColIndex(
                getModel(),
                name
        );
    }
    /**
     * Retrieve a set of LHS (left-hand-side) values for each specified constraint.
     * @param size [in] Number of constraints to access.
     * @param indices [in] An array that holds the index of constraints to access.
     * @param lhss [out] An array that will hold the currnet LHS values of each specified constraint.
     * @return MdoResult code
     */
    public int getLhss(
            int size,
            Pointer indices,
            Pointer lhss
    ) {
        return inst().Mdo_getLhss(
                getModel(),
                size,
                indices,
                lhss
        );
    }
    /**
     * Modify a set of LHS (left-hand-side) values for each specified constraint.
     * @param size [in] Number of constraints to access.
     * @param indices [in] An array that holds the index of constraints to access.
     * @param lhss [in] An array that holds the new LHS values for each specified constraint.
     * @return MdoResult code
     */
    public int setLhss(
            int size,
            Pointer indices,
            Pointer lhss
    ) {
        return inst().Mdo_setLhss(
                getModel(),
                size,
                indices,
                lhss
        );
    }
    /**
     * Retrieve a set of RHS (right-hand-side) values for each specified constraint.
     * @param size [in] Number of constraints to access.
     * @param indices [in] An array that holds the index of constraints to access.
     * @param rhss [out] An array that will hold the currnet RHS values of each specified constraint.
     * @return MdoResult code
     */
    public int getRhss(
            int size,
            Pointer indices,
            Pointer rhss
    ) {
        return inst().Mdo_getRhss(
                getModel(),
                size,
                indices,
                rhss
        );
    }
    /**
     * Modify a set of RHS (right-hand-side) values for each specified constraint.
     * @param size [in] Number of constraints to access.
     * @param indices [in] An array that holds the index of constraints to access.
     * @param rhss [in] An array that holds the new RHS values for each specified constraint.
     * @return MdoResult code
     */
    public int setRhss(
            int size,
            Pointer indices,
            Pointer rhss
    ) {
        return inst().Mdo_setRhss(
                getModel(),
                size,
                indices,
                rhss
        );
    }
    /**
     * Access the row name.
     * @param i [in] Row index.
     * @param row_name [in] Row name. Can be NULL.
     * @param size [in] Max string length.
     * @param real_size [in] Real length of the requested name length. Can be NULL
     * @return MdoResult code
     */
    public int getRowName(
            int i,
            Pointer row_name,
            int size,
            Pointer real_size
    ) {
        return inst().Mdo_getRowName(
                getModel(),
                i,
                row_name,
                size,
                real_size
        );
    }
    /**
     * Modify a set of constraint names.
     * @param size [in] Number of constraints to access.
     * @param indices [in] An array that holds the index of constraints to access.
     * @param row_names [in] An array of new names for each specified constraint.
     * @return MdoResult code
     */
    public int setRowNames(
            int size,
            Pointer indices,
            Pointer row_names
    ) {
        return inst().Mdo_setRowNames(
                getModel(),
                size,
                indices,
                row_names
        );
    }
    /**
     * Return the row index of a row name in the model.
     * @param name [in] Row name.
     * @return MdoResult code
     */
    public int getRowIndex(
            Pointer name
    ) {
        return inst().Mdo_getRowIndex(
                getModel(),
                name
        );
    }
    /**
     * Retrieve a set of values of all specified elements in the constraint matrix.
     * @param size [in] Number of elements to access.
     * @param row_indices [in] An array that holds the row index of elements to access.
     * @param col_indices [in] An array that holds the column index of elements to access.
     * @param values [in] An array that will hold the current nonzero values of all specified elements in the constraint matrix.
     * @return MdoResult code
     */
    public int getElements(
            int size,
            Pointer row_indices,
            Pointer col_indices,
            Pointer values
    ) {
        return inst().Mdo_getElements(
                getModel(),
                size,
                row_indices,
                col_indices,
                values
        );
    }
    /**
     * Modify a set of values of all specified elements in the constraint matrix.
     * @param size [in] Number of elements to access.
     * @param row_indices [in] An array that holds the row index of elements to access.
     * @param col_indices [in] An array that holds the column index of elements to access.
     * @param values [in] An array that holds the new nonzero values for all specified elements in the constraint matrix.
     * @return MdoResult code
     */
    public int setElements(
            int size,
            Pointer row_indices,
            Pointer col_indices,
            Pointer values
    ) {
        return inst().Mdo_setElements(
                getModel(),
                size,
                row_indices,
                col_indices,
                values
        );
    }
    /**
     * Retrieve a set of values of all specified elements in the quadratic matrix of a quadratic program.
     * @param size [in] Number of elements to access.
     * @param col_indices1 [in] An array that holds the first variable index of elements to access.
     * @param col_indices2 [in] An array that holds the second variable index of elements to access.
     * @param values [out] An array that will hold the current nonzero values of all specified elements.
     * @return MdoResult code
     */
    public int getQuadraticElements(
            int size,
            Pointer col_indices1,
            Pointer col_indices2,
            Pointer values
    ) {
        return inst().Mdo_getQuadraticElements(
                getModel(),
                size,
                col_indices1,
                col_indices2,
                values
        );
    }
    /**
     * Modify a set of values of all specified elements in the quadratic matrix of a quadratic program.
     * @param size [in] Number of elements to access.
     * @param col_indices1 [in] An array that holds the first variable index of elements to access.
     * @param col_indices2 [in] An array that holds the second variable index of elements to access.
     * @param values [in] An array that holds the new nonzero values for all specified elements.
     * @return MdoResult code
     */
    public int setQuadraticElements(
            int size,
            Pointer col_indices1,
            Pointer col_indices2,
            Pointer values
    ) {
        return inst().Mdo_setQuadraticElements(
                getModel(),
                size,
                col_indices1,
                col_indices2,
                values
        );
    }
    /**
     * Delete a set of rows.
     * @param size [in] Number of rows to be deleted.
     * @param indices [in] An array that holds the index of rows to be deleted.
     * @return MdoResult code
     */
    public int deleteRows(
            int size,
            Pointer indices
    ) {
        return inst().Mdo_deleteRows(
                getModel(),
                size,
                indices
        );
    }
    /**
     * Delete a set of columns.
     * @param size [in] Number of columns to be deleted.
     * @param indices [in] An array that holds the index of columns to be deleted.
     * @return MdoResult code
     */
    public int deleteCols(
            int size,
            Pointer indices
    ) {
        return inst().Mdo_deleteCols(
                getModel(),
                size,
                indices
        );
    }
    /**
     * Delete a set of elements from the constraint matrix.
     * @param size [in] Number of elements to be deleted.
     * @param row_indices [in] An array that holds the row index of elements to be deleted.
     * @param col_indices [in] An array that holds the column index of elements to be deleted.
     * @return MdoResult code
     */
    public int deleteElements(
            int size,
            Pointer row_indices,
            Pointer col_indices
    ) {
        return inst().Mdo_deleteElements(
                getModel(),
                size,
                row_indices,
                col_indices
        );
    }
    /**
     * Delete a set of elements from the quadratic matrix of a quadratic program.
     * @param size [in] Number of elements to be deleted.
     * @param col_indices1 [in] An array that holds the first variable index of elements to access.
     * @param col_indices2 [in] An array that holds the second variable index of elements to access.
     * @return MdoResult code
     */
    public int deleteQuadraticElements(
            int size,
            Pointer col_indices1,
            Pointer col_indices2
    ) {
        return inst().Mdo_deleteQuadraticElements(
                getModel(),
                size,
                col_indices1,
                col_indices2
        );
    }

    /**
     *  Delete all elements from the constraint matrix.
     *  @return MdoResult code.
     */
    public int deleteAllElements() {
        return inst().Mdo_deleteAllElements(
                getModel()
        );
    }

    /**
     *  Delete all elements from the quadratic matrix of a quadratic program.
     *  @return MdoResult code
     */
    public int deleteAllQuadraticElements() {
        return inst().Mdo_deleteAllQuadraticElements(
                getModel()
        );
    }
    /**
     * Change the value of a string-valued parameter.
     * @param par [in] A string-valued parameter.
     * @param val [in] A new value for the string-valued parameter.
     * @return MdoResult code
     */
    public int setStrParam(
            Pointer par,
            Pointer val
    ) {
        return inst().Mdo_setStrParam(
                getModel(),
                par,
                val
        );
    }
    /**
     * Retrieve the value of a string-valued parameter.
     * @param par [in] A string-valued parameter.
     * @param size [in] Length of `val`.
     * @param val [out] The current value of the string-valued parameter.
     * @return MdoResult code
     */
    public int getStrParam(
            Pointer par,
            int size,
            Pointer val
    ) {
        return inst().Mdo_getStrParam(
                getModel(),
                par,
                size,
                val
        );
    }
    /**
     * Change the value of a 32-bit integer-valued parameter.
     * @param par [in] An integer-valued parameter.
     * @param val [in] A new value for the 32-bit integer-valued parameter.
     * @return MdoResult code
     */
    public int setIntParam(
            Pointer par,
            int val
    ) {
        return inst().Mdo_setIntParam(
                getModel(),
                par,
                val
        );
    }
    /**
     * Retrieve the value of a 32-bit integer-valued parameter.
     * @param par [in] An integer-valued parameter.
     * @param val [out] The current value of the 32-bit integer-valued parameter.
     * @return MdoResult code
     */
    public int getIntParam(
            Pointer par,
            Pointer val
    ) {
        return inst().Mdo_getIntParam(
                getModel(),
                par,
                val
        );
    }
    /**
     * Change the value of a real-valued parameter.
     * @param par [in] A real-valued parameter.
     * @param val [in] A new value for the real-valued parameter.
     * @return MdoResult code
     */
    public int setRealParam(
            Pointer par,
            double val
    ) {
        return inst().Mdo_setRealParam(
                getModel(),
                par,
                val
        );
    }
    /**
     * Retrieve the value of a real-valued parameter.
     * @param par [in] A real-valued parameter.
     * @param val [out] The current value of the real-valued parameter.
     * @return MdoResult code
     */
    public int getRealParam(
            Pointer par,
            Pointer val
    ) {
        return inst().Mdo_getRealParam(
                getModel(),
                par,
                val
        );
    }
    /**
     * Submit an optimization model task to a remote server for optimization.
     * @param job_id [out] Job ID, at least 1024 characters.
     * @return MdoResult code
     */
    public int submitTask(
            Pointer job_id
    ) {
        return inst().Mdo_submitTask(
                getModel(),
                job_id
        );
    }
    /**
     * Check the status of the submitted task, and then retrieve the corresponding optimization result if available.
     * @param job_id [in] Job ID, at least 1024 characters.
     * @param status [out] A character array that specifies the status of the submitted task.
     * @param code [out] A status code that specifies the model status.
     * @param result [out] A response code that specifies the optimization status, that is, the response code while calling Mdo_solveProb on a remote server.
     * @param has_soln [out] A flag that specifies the availability of the solution. If `true`, user may then use Mdo_readTask to read the solution file that was saved to the designated location.
     * @return MdoResult code
     */
    public int retrieveTask(
            Pointer job_id,
            Pointer status,
            Pointer code,
            Pointer result,
            Pointer has_soln
    ) {
        return inst().Mdo_retrieveTask(
                getModel(),
                job_id,
                status,
                code,
                result,
                has_soln
        );
    }
    /**
     * Solve the loaded optimization problem.
     * @return MdoResult code
     */
    public int solveProb(
    ) {
        return inst().Mdo_solveProb(
                getModel()
        );
    }
    /**
     * Display the current solver results.
     */
    public void displayResults(
    ) {
        inst().Mdo_displayResults(
                getModel()
        );
    }
    /**
     * Return the problem status code.
     * @return MdoStatus code
     */
    public int getStatus(
    ) {
        return inst().Mdo_getStatus(
                getModel()
        );
    }
    /**
     * Explain the solver status code.
     * @param status [in] Solver status code to be explained.
     * @param reason [out] A string that holds the explanation of the given solver status code.
     */
    public void explainStatus(
            int status,
            Pointer reason
    ) {
        inst().Mdo_explainStatus(
                getModel(),
                status,
                reason
        );
    }
    /**
     * Explain the solver result code.
     * @param result [in] Solver result code to be explained.
     * @param reason [out] A string that holds the explanation of the given solver result code.
     */
    public void explainResult(
            int result,
            Pointer reason
    ) {
        inst().Mdo_explainResult(
                getModel(),
                result,
                reason
        );
    }
    /**
     * Compute an IIS.
     * @param num_rows [out] Number of constraints involved in an IIS.
     * @param idx_rows [out] A pointer array that holds all constraints involved in an IIS.
     * @param num_cols [out] Number of variables involved in an IIS.
     * @param idx_cols [out] A pointer array that holds all variables involved in an IIS.
     * @return MdoResult code
     */
    public int computeIIS(
            Pointer num_rows,
            Pointer idx_rows,
            Pointer num_cols,
            Pointer idx_cols
    ) {
        return inst().Mdo_computeIIS(
                getModel(),
                num_rows,
                idx_rows,
                num_cols,
                idx_cols
        );
    }
}
