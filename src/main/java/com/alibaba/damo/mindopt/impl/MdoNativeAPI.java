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
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface MdoNativeAPI extends Library {
    class InstanceHolder {
        static MdoNativeAPI INSTANCE;
        public static MdoNativeAPI get() {
            return INSTANCE;
        }
        public static void load(String libName) {
            if (INSTANCE == null) {
                INSTANCE = (MdoNativeAPI) Native.loadLibrary(libName, MdoNativeAPI.class);
            }
        }
    }
    int /* MdoResult */ Mdo_setStrAttrIndex(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ idx,
            Pointer /* char * */ val
    );

    int /* MdoResult */ Mdo_getStrAttrIndex(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ idx,
            int /* int */ size,
            Pointer /* char * */ val
    );

    int /* MdoResult */ Mdo_setStrAttr(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            Pointer /* char * */ val
    );

    int /* MdoResult */ Mdo_setIntAttr(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ val
    );

    int /* MdoResult */ Mdo_getStrAttr(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int * */ size,
            Pointer /* char * */ val
    );

    int /* MdoResult */ Mdo_getIntAttr(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            Pointer /* int * */ val
    );

    int /* MdoResult */ Mdo_setIntAttrIndex(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ idx,
            int /* int */ val
    );

    int /* MdoResult */ Mdo_getIntAttrIndex(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ idx,
            Pointer /* int * */ val
    );

    int /* MdoResult */ Mdo_setIntAttrArray(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ bgn,
            int /* int */ len,
            Pointer /* int * */ val
    );

    int /* MdoResult */ Mdo_getIntAttrArray(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ bgn,
            int /* int */ len,
            Pointer /* int * */ val
    );

    int /* MdoResult */ Mdo_setRealAttr(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            double /* double */ val
    );

    int /* MdoResult */ Mdo_getRealAttr(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            Pointer /* double * */ val
    );

    int /* MdoResult */ Mdo_setRealAttrIndex(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ idx,
            double /* double */ val
    );

    int /* MdoResult */ Mdo_getRealAttrIndex(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ idx,
            Pointer /* double * */ val
    );

    int /* MdoResult */ Mdo_setRealAttrArray(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ bgn,
            int /* int */ len,
            Pointer /* double * */ val
    );

    int /* MdoResult */ Mdo_getRealAttrArray(
            Pointer /* void * */ mdl,
            Pointer /* char * */ att,
            int /* int */ bgn,
            int /* int */ len,
            Pointer /* double * */ val
    );

    int /* MdoResult */ Mdo_startCmd(
            int /* int */ argc,
            Pointer /* char * */ argv
    );

    int /* MdoResult */ Mdo_readProb(
            Pointer /* void * */ mdl,
            Pointer /* char * */ filename
    );

    int /* MdoResult */ Mdo_writeProb(
            Pointer /* void * */ mdl,
            Pointer /* char * */ filename
    );

    int /* MdoResult */ Mdo_writeSoln(
            Pointer /* void * */ mdl,
            Pointer /* char * */ filename
    );

    int /* MdoResult */ Mdo_readTask(
            Pointer /* void * */ mdl,
            Pointer /* char * */ filename,
            int /* int */ read_model,
            int /* int */ read_param,
            int /* int */ read_soln
    );

    int /* MdoResult */ Mdo_writeTask(
            Pointer /* void * */ mdl,
            Pointer /* char * */ filename,
            int /* int */ write_model,
            int /* int */ write_param,
            int /* int */ write_soln
    );

    int /* MdoResult */ Mdo_setLogToConsole(
            Pointer /* void * */ mdl,
            int /* int */ flag
    );

    int /* MdoResult */ Mdo_setLogFile(
            Pointer /* void * */ mdl,
            Pointer /* char * */ filename
    );

    int /* MdoResult */ Mdo_setLogCallback(
            Pointer /* void * */ mdl,
            Callback /* void(*)( char *msg, void *userdata) */ logcb,
            Pointer /* void * */ userdata
    );

    int /* MdoResult */ Mdo_createEnv(
            PointerByReference /* void * * */ env
    );

    int /* MdoResult */ Mdo_createMdlWithEnv(
            PointerByReference /* void * * */ mdl,
            Pointer /* void * */ env
    );

    void /* void */ Mdo_freeEnv(
            PointerByReference /* void * * */ env
    );

    int /* MdoResult */ Mdo_createMdl(
            PointerByReference /* void * * */ mdl
    );

    void /* void */ Mdo_freeMdl(
            PointerByReference /* void * * */ mdl
    );

    int /* MdoResult */ Mdo_loadModel(
            Pointer /* void * */ mdl,
            int /* int */ num_cols,
            int /* int */ num_rows,
            Pointer /* int * */ bgn,
            Pointer /* int * */ indices,
            Pointer /* double * */ values,
            Pointer /* double * */ lbs,
            Pointer /* double * */ ubs,
            Pointer /* double * */ objs,
            Pointer /* int * */ are_integers,
            double /* double */ obj_const,
            int /* int */ is_min,
            Pointer /* double * */ lhss,
            Pointer /* double * */ rhss,
            Pointer /* char ** */ col_names,
            Pointer /* char ** */ row_names
    );

    int /* MdoResult */ Mdo_addCol(
            Pointer /* void * */ mdl,
            double /* double */ lb,
            double /* double */ ub,
            double /* double */ obj,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ values,
            Pointer /* char * */ name,
            int /* int */ is_integer
    );

    int /* MdoResult */ Mdo_addCols(
            Pointer /* void * */ mdl,
            int /* int */ num_cols,
            Pointer /* double * */ lbs,
            Pointer /* double * */ ubs,
            Pointer /* double * */ objs,
            Pointer /* int * */ bgn,
            Pointer /* int * */ indices,
            Pointer /* double * */ values,
            Pointer /* char ** */ col_names,
            Pointer /* int * */ are_integers
    );

    int /* MdoResult */ Mdo_addRow(
            Pointer /* void * */ mdl,
            double /* double */ lhs,
            double /* double */ rhs,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ values,
            Pointer /* char * */ name
    );

    int /* MdoResult */ Mdo_addRows(
            Pointer /* void * */ mdl,
            int /* int */ num_rows,
            Pointer /* double * */ lhss,
            Pointer /* double * */ rhss,
            Pointer /* int * */ bgn,
            Pointer /* int * */ indices,
            Pointer /* double * */ values,
            Pointer /* char ** */ row_names
    );

    int /* MdoResult */ Mdo_getCols(
            Pointer /* void * */ mdl,
            int /* int */ num_cols,
            Pointer /* int * */ col_indices,
            Pointer /* int * */ bgn,
            Pointer /* int * */ indices,
            Pointer /* double * */ values,
            int /* int */ size,
            Pointer /* int * */ real_size
    );

    int /* MdoResult */ Mdo_getRows(
            Pointer /* void * */ mdl,
            int /* int */ num_rows,
            Pointer /* int * */ row_indices,
            Pointer /* int * */ bgn,
            Pointer /* int * */ indices,
            Pointer /* double * */ values,
            int /* int */ size,
            Pointer /* int * */ real_size
    );

    int /* int */ Mdo_isMinObjSense(
            Pointer /* void * */ mdl
    );

    int /* int */ Mdo_isMaxObjSense(
            Pointer /* void * */ mdl
    );

    void /* void */ Mdo_setMinObjSense(
            Pointer /* void * */ mdl
    );

    void /* void */ Mdo_setMaxObjSense(
            Pointer /* void * */ mdl
    );

    double /* double */ Mdo_getObjOffset(
            Pointer /* void * */ mdl
    );

    void /* void */ Mdo_setObjOffset(
            Pointer /* void * */ mdl,
            double /* double */ obj_fix
    );

    int /* int */ Mdo_getNumRows(
            Pointer /* void * */ mdl
    );

    int /* int */ Mdo_getNumCols(
            Pointer /* void * */ mdl
    );

    int /* int */ Mdo_getNumElements(
            Pointer /* void * */ mdl
    );

    int /* MdoResult */ Mdo_getObjs(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ objs
    );

    int /* MdoResult */ Mdo_setObjs(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ objs
    );

    int /* MdoResult */ Mdo_getLbs(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ lbs
    );

    int /* MdoResult */ Mdo_setLbs(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ lbs
    );

    int /* MdoResult */ Mdo_getUbs(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ ubs
    );

    int /* MdoResult */ Mdo_setUbs(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ ubs
    );

    int /* MdoResult */ Mdo_getIntegers(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* int * */ are_integers
    );

    int /* MdoResult */ Mdo_setIntegers(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* int * */ are_integers
    );

    int /* MdoResult */ Mdo_getColName(
            Pointer /* void * */ mdl,
            int /* int */ j,
            Pointer /* char * */ col_name,
            int /* int */ size,
            Pointer /* int * */ real_size
    );

    int /* MdoResult */ Mdo_setColNames(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* char ** */ col_names
    );

    int /* int */ Mdo_getColIndex(
            Pointer /* void * */ mdl,
            Pointer /* char * */ name
    );

    int /* MdoResult */ Mdo_getLhss(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ lhss
    );

    int /* MdoResult */ Mdo_setLhss(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ lhss
    );

    int /* MdoResult */ Mdo_getRhss(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ rhss
    );

    int /* MdoResult */ Mdo_setRhss(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* double * */ rhss
    );

    int /* MdoResult */ Mdo_getRowName(
            Pointer /* void * */ mdl,
            int /* int */ i,
            Pointer /* char * */ row_name,
            int /* int */ size,
            Pointer /* int * */ real_size
    );

    int /* MdoResult */ Mdo_setRowNames(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices,
            Pointer /* char ** */ row_names
    );

    int /* int */ Mdo_getRowIndex(
            Pointer /* void * */ mdl,
            Pointer /* char * */ name
    );

    int /* MdoResult */ Mdo_getElements(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ row_indices,
            Pointer /* int * */ col_indices,
            Pointer /* double * */ values
    );

    int /* MdoResult */ Mdo_getQuadraticElements(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ col_indices1,
            Pointer /* int * */ col_indices2,
            Pointer /* double * */ values
    );

    int /* MdoResult */ Mdo_setQuadraticElements(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ col_indices1,
            Pointer /* int * */ col_indices2,
            Pointer /* double * */ values
    );

    int /* MdoResult */ Mdo_setElements(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ row_indices,
            Pointer /* int * */ col_indices,
            Pointer /* double * */ values
    );

    int /* MdoResult */ Mdo_deleteRows(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices
    );

    int /* MdoResult */ Mdo_deleteCols(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ indices
    );

    int /* MdoResult */ Mdo_deleteElements(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ row_indices,
            Pointer /* int * */ col_indices
    );

    int /* MdoResult */ Mdo_deleteQuadraticElements(
            Pointer /* void * */ mdl,
            int /* int */ size,
            Pointer /* int * */ col_indices1,
            Pointer /* int * */ col_indices2
    );

    int /*MdoResult */ Mdo_deleteAllElements(
            Pointer /* void * */ mdl
    );

    int /*MdoResult */ Mdo_deleteAllQuadraticElements(
            Pointer /* void * */ mdl
    );

    int /* MdoResult */ Mdo_setStrParam(
            Pointer /* void * */ mdl,
            Pointer /* char * */ par,
            Pointer /* char * */ val
    );

    int /* MdoResult */ Mdo_getStrParam(
            Pointer /* void * */ mdl,
            Pointer /* char * */ par,
            int /* int */ size,
            Pointer /* char * */ val
    );

    int /* MdoResult */ Mdo_setIntParam(
            Pointer /* void * */ mdl,
            Pointer /* char * */ par,
            int /* int */ val
    );

    int /* MdoResult */ Mdo_getIntParam(
            Pointer /* void * */ mdl,
            Pointer /* char * */ par,
            Pointer /* int * */ val
    );

    int /* MdoResult */ Mdo_setRealParam(
            Pointer /* void * */ mdl,
            Pointer /* char * */ par,
            double /* double */ val
    );

    int /* MdoResult */ Mdo_getRealParam(
            Pointer /* void * */ mdl,
            Pointer /* char * */ par,
            Pointer /* double * */ val
    );

    int /* MdoResult */ Mdo_submitTask(
            Pointer /* void * */ mdl,
            Pointer /* char * */ job_id
    );

    int /* MdoResult */ Mdo_retrieveTask(
            Pointer /* void * */ mdl,
            Pointer /* char * */ job_id,
            Pointer /* char * */ status,
            Pointer /* MdoStatus * */ code,
            Pointer /* MdoResult * */ result,
            Pointer /* int * */ has_soln
    );

    int /* MdoResult */ Mdo_solveProb(
            Pointer /* void * */ mdl
    );

    void /* void */ Mdo_displayResults(
            Pointer /* void * */ mdl
    );

    int /* MdoStatus */ Mdo_getStatus(
            Pointer /* void * */ mdl
    );

    void /* void */ Mdo_explainStatus(
            Pointer /* void * */ mdl,
            int /* MdoStatus */ status,
            Pointer /* char * */ reason
    );

    void /* void */ Mdo_explainResult(
            Pointer /* void * */ mdl,
            int /* MdoResult */ result,
            Pointer /* char * */ reason
    );

    int /* MdoResult */ Mdo_computeIIS(
            Pointer /* void * */ mdl,
            Pointer /* int * */ num_rows,
            Pointer /* int * */ idx_rows,
            Pointer /* int * */ num_cols,
            Pointer /* int * */ idx_cols
    );


}