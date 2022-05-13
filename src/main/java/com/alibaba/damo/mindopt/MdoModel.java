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


import com.alibaba.damo.mindopt.impl.*;
import com.sun.jna.Callback;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *  This object implements the data structure to hold an optimization model.
 *
 *  Typical steps to input a column into the optimization model are:
 *  <OL>
 *  <LI>Create a set of empty variable objects by calling MdoModel.addVar</LI>
 *  <LI>Specify the variable's lower bound, upper bound, and objective coefficients</LI>
 *  <LI>Create an empty linear expression object by class MdoExprLinear</LI>
 *  <LI>Input a constraint by calling MdoModel.addCons</LI>
 *  <LI>Optimize the problem by calling MdoModel.solveProb</LI>
 *  </OL>
 */
public class MdoModel implements MdoProblem, MdoSolver {
    protected MdoNativeModel model = new MdoNativeModel();
    protected List<MdoVar> vars = new ArrayList<MdoVar>();
    protected List<MdoCons> conss = new ArrayList<MdoCons>();
    protected boolean freed = false;
    /**
     * Default constructor
     * @deprecated Constructor of MdoModel is deprecated since 0.19, use <code>MdoEnv.createModel()</code> instead.
     */
    @Deprecated
    public MdoModel() {
        model.createMdl();
    }

    private MdoModel(MdoEnv env) {
        env.env.createMdlWithEnv(model);
    }

    private char inverse(char sense) {
        if (sense == Mdo.LESS_EQUAL) {
            return Mdo.GREATER_EQUAL;
        } else if (sense == Mdo.GREATER_EQUAL) {
            return Mdo.LESS_EQUAL;
        } else {
            return Mdo.EQUAL;
        }
    }

    protected void syncData(boolean syncConss, boolean syncVars) {
        if (syncConss) {
            this.conss.clear();
            for (int i = 0; i < model.getNumRows(); i++) {
                this.conss.add(new MdoConsImpl(this, i));
            }
        }
        if (syncVars) {
            this.vars.clear();
            for (int i = 0; i < model.getNumCols(); i++) {
                this.vars.add(new MdoVarImpl(this, i));
            }
        }
    }

    @Override
    public MdoVar addVar(double lb, double ub, double obj, boolean isInteger, String name) {
        return addVar(lb, ub, obj, isInteger, null, null, name);
    }

    @Override
    public MdoVar addVar(double lb, double ub, double obj, boolean isInteger, MdoCol col, String name) {
        if (col == null) {
            return this.addVar(lb, ub, obj, isInteger, null, null, name);
        } else {
            MdoCons[] conss = new MdoCons[col.size()];
            double[] coeffs = new double[col.size()];

            for(int i = 0; i < col.size(); ++i) {
                conss[i] = col.getCons(i);
                coeffs[i] = col.getCoeff(i);
            }
            return this.addVar(lb, ub, obj, isInteger, conss, coeffs, name);
        }
    }

    @Override
    public MdoVar addVar(double lb, double ub, double obj, boolean isInteger, MdoCons[] conss, double[] coeffs, String name) {
        int code;
        Pointer pName = MemoryUtil.charArray(name);
        if (conss == null || conss.length == 0) {
            code = model.addCol(
                    lb, ub, obj, 0,
                    Pointer.NULL, Pointer.NULL,
                    pName, isInteger ? 1 : 0
            );
        } else {
            int size = conss.length;
            Pointer indices = MemoryUtil.intArray(size);
            Pointer values = MemoryUtil.doubleArray(size);
            for (int i = 0; i < size; i++) {
                MemoryUtil.setInt(indices, i, conss[i].getIndex());
                if (coeffs == null || coeffs.length <= i) {
                    MemoryUtil.setDouble(values, i, 1);
                } else {
                    MemoryUtil.setDouble(values, i, coeffs[i]);
                }
            }
            code = model.addCol(lb, ub, obj, size, indices, values, pName, isInteger ? 1 : 0);
        }
        MdoResult.checkResult(code);
        MdoVar var = new MdoVarImpl(this, vars.size());
        vars.add(var);
        return var;
    }

    @Override
    public MdoVar[] addVars(int count, boolean isInteger) {
        MdoVar[] vars = new MdoVar[count];
        for (int i = 0; i < count; i++) {
            vars[i] = this.addVar(0, Mdo.INFINITY, 0, isInteger, null);
        }
        return vars;
    }

    @Override
    public MdoVar[] addVars(double[] lbs, double[] ubs, double[] objs, boolean[] areIntegers, String[] names) {
        int size = lbs.length;
        return this.addVars(lbs, ubs, objs, areIntegers, names, 0, size);
    }

    @Override
    public MdoVar[] addVars(double[] lbs, double[] ubs, double[] objs, boolean[] areIntegers, String[] names, int start, int len) {
        if (len < 0) {
            return null;
        } else if (len == 0) {
            return new MdoVar[0];
        }

        MdoVar[] result = new MdoVar[len];
        for (int i = start; i < start + len; i++) {
            result[i - start] = this.addVar(lbs[i], ubs[i], objs[i], areIntegers[i], names == null ? null : names[i]);
        }

        return result;
    }

    @Override
    public MdoVar[] addVars(double[] lbs, double[] ubs, double[] objs, boolean[] areIntegers, MdoCol[] cols, String[] names) {
        if (lbs.length == 0) {
            return new MdoVar[0];
        }
        MdoVar[] result = new MdoVar[lbs.length];
        for (int i = 0; i < lbs.length; i++) {
            result[i] = this.addVar(lbs[i], ubs[i], objs[i], areIntegers[i], cols[i] , names == null ? null : names[i]);
        }
        return result;
    }

    @Override
    public MdoCons addCons(MdoExprLinear lhs, char sense, MdoExprLinear rhs, String name) {
        MdoExprLinear expr = new MdoExprLinear(lhs);
        expr.multAdd(-1, rhs);
        return this.addCons(expr, sense, 0, name);
    }

    @Override
    public MdoCons addCons(MdoVar lhs, char sense, MdoVar rhs, String name) {
        MdoExprLinear expr = new MdoExprLinear();
        expr.addTerm(1, lhs);
        expr.addTerm(-1, rhs);
        return this.addCons(expr, sense, 0, name);
    }

    @Override
    public MdoCons addCons(MdoExprLinear lhs, char sense, MdoVar rhs, String name) {
        MdoExprLinear expr = new MdoExprLinear(lhs);
        expr.addTerm(-1, rhs);
        return this.addCons(expr, sense, 0, name);
    }

    @Override
    public MdoCons addCons(MdoVar lhs, char sense, MdoExprLinear rhs, String name) {
        return this.addCons(rhs, inverse(sense), lhs, name);
    }

    @Override
    public MdoCons addCons(double lhs, char sense, MdoExprLinear rhs, String name) {
        return this.addCons(rhs, inverse(sense), lhs, name);
    }

    @Override
    public MdoCons addCons(MdoVar lhs, char sense, double rhs, String name) {
        MdoExprLinear expr = new MdoExprLinear();
        expr.addTerm(1, lhs);
        return this.addCons(expr, sense, rhs, name);
    }

    @Override
    public MdoCons addCons(double lhs, char sense, MdoVar rhs, String name) {
        return this.addCons(rhs, inverse(sense), lhs, name);
    }

    @Override
    public MdoCons addCons(MdoExprLinear lhs, char sense, double rhs, String name) {
        MdoExprLinear expr = new MdoExprLinear(lhs);

        if (rhs > Mdo.NEGATIVE_INFINITY && rhs < Mdo.INFINITY) {
            rhs -= expr.getConstant();
        }

        expr.purge();
        int size = expr.size();
        Pointer indices = MemoryUtil.intArray(size);
        Pointer values = MemoryUtil.doubleArray(size);
        Pointer pName = MemoryUtil.charArray(name);

        for (int i = 0; i < expr.size(); i++) {
            MemoryUtil.setInt(indices, i, expr.getVar(i).getIndex());
            MemoryUtil.setDouble(values, i, expr.getCoeff(i));
        }

        double l = rhs;
        double r = rhs;

        if (sense == Mdo.LESS_EQUAL) {
            l = Mdo.NEGATIVE_INFINITY;
        } else if (sense == Mdo.GREATER_EQUAL) {
            r = Mdo.INFINITY;
        }

        int code = model.addRow(l, r, size, indices, values, pName);
        MdoResult.checkResult(code);

        MdoCons cons = new MdoConsImpl(this, conss.size());
        conss.add(cons);
        return cons;
    }

    @Override
    public MdoCons addRange(MdoExprLinear expr, double lower, double upper, String name) {
        MdoExprLinear expr1 = new MdoExprLinear(expr);

        if (upper > Mdo.NEGATIVE_INFINITY && upper < Mdo.INFINITY) {
            upper -= expr.getConstant();
        }

        if (lower > Mdo.NEGATIVE_INFINITY && lower < Mdo.INFINITY) {
            lower -= expr.getConstant();
        }

        expr1.purge();
        int size = expr1.size();
        Pointer indices = MemoryUtil.intArray(size);
        Pointer values = MemoryUtil.doubleArray(size);
        Pointer pName = MemoryUtil.charArray(name);

        for (int i = 0; i < expr1.size(); i++) {
            MemoryUtil.setInt(indices, i, expr1.getVar(i).getIndex());
            MemoryUtil.setDouble(values, i, expr1.getCoeff(i));
        }

        int code = model.addRow(lower, upper, size, indices, values, pName);
        MdoResult.checkResult(code);

        MdoCons cons = new MdoConsImpl(this, conss.size());
        conss.add(cons);
        return cons;
    }

    @Override
    public MdoCons[] addConss(int count) {
        MdoCons conss[] = new MdoCons[count];
        for (int i = 0; i < count; i++) {
            int code = model.addRow(Mdo.NEGATIVE_INFINITY, 0, 0, Pointer.NULL, Pointer.NULL, Pointer.NULL);
            MdoResult.checkResult(code);

            MdoCons cons = new MdoConsImpl(this, this.conss.size());
            this.conss.add(cons);
            conss[i] = cons;
        }
        return conss;
    }

    @Override
    public MdoCons[] addConss(MdoExprLinear[] lhss, char[] senses, double[] rhss, String[] names) {
        MdoCons[] conss = new MdoCons[lhss.length];
        for (int i = 0; i < lhss.length; i++) {
            conss[i] = this.addCons(lhss[i], senses[i], rhss[i], names == null ? null : names[i]);
        }
        return conss;
    }

    @Override
    public MdoCons[] addConss(MdoExprLinear[] lhss, char[] senses, double[] rhss, String[] names, int start, int len) {
        MdoCons[] conss = new MdoCons[len];
        for (int i = start; i < start + len; i++) {
            conss[i - start] = this.addCons(lhss[i], senses[i], rhss[i], names == null ? null : names[i]);
        }
        return conss;
    }

    @Override
    public MdoCons[] addRanges(MdoExprLinear[] exprs, double[] lowers, double[] uppers, String[] names) {
        MdoCons[] conss = new MdoCons[exprs.length];
        for (int i = 0; i < exprs.length; i++) {
            conss[i] = this.addRange(exprs[i], lowers[i], uppers[i], names == null ? null : names[i]);
        }
        return conss;
    }

    @Override
    public MdoVar getVar(int j) {
        if (j < 0 || j >= vars.size()) {
            MdoResult.checkResult(MdoResult.MDO_MODEL_INVALID_COL_IDX.getCode());
        }
        return vars.get(j);
    }

    @Override
    public MdoCons getCons(int i) {
        if (i < 0 || i >= conss.size()) {
            MdoResult.checkResult(MdoResult.MDO_MODEL_INVALID_ROW_IDX.getCode());
        }
        return conss.get(i);
    }

    @Override
    public MdoVar getVar(String name) {
        int j = model.getColIndex(MemoryUtil.charArray(name));
        if (j < 0) {
            MdoResult.checkResult(MdoResult.MDO_MODEL_INVALID_COL_NAME.getCode());
        }
        return vars.get(j);
    }

    @Override
    public MdoCons getCons(String name) {
        int i = model.getRowIndex(MemoryUtil.charArray(name));
        if (i < 0) {
            MdoResult.checkResult(MdoResult.MDO_MODEL_INVALID_ROW_NAME.getCode());
        }
        return conss.get(i);
    }

    @Override
    public MdoVar[] getVars() {
        return vars.toArray(new MdoVar[0]);
    }

    @Override
    public MdoCons[] getConss() {
        return conss.toArray(new MdoCons[0]);
    }

    @Override
    public void deleteVars(MdoVar[] vars) {
        List<Integer> indices = new ArrayList<Integer>();
        for (MdoVar var : vars) {
            indices.add(var.getIndex());
        }

        Collections.sort(indices);
        int len = 1;
        for (int i = 1; i < indices.size(); i++) {
            if (indices.get(i).intValue() != indices.get(i - 1)) {
                len++;
            }
        }

        Pointer pIndices = MemoryUtil.intArray(len);
        int offset = 0;

        MemoryUtil.setInt(pIndices, offset++, indices.get(0));
        for (int i = 1; i < indices.size(); i++) {
            if (indices.get(i).intValue() != indices.get(i - 1)) {
                MemoryUtil.setInt(pIndices, offset++, indices.get(i));
            }
        }

        MdoResult.checkResult(model.deleteCols(len, pIndices));
        syncData(false, true);
    }

    @Override
    public void deleteConss(MdoCons[] conss) {
        List<Integer> indices = new ArrayList<Integer>();
        for (MdoCons cons : conss) {
            indices.add(cons.getIndex());
        }

        Collections.sort(indices);
        int len = 1;
        for (int i = 1; i < indices.size(); i++) {
            if (indices.get(i).intValue() != indices.get(i - 1)) {
                len++;
            }
        }

        Pointer pIndices = MemoryUtil.intArray(len);
        int offset = 0;

        MemoryUtil.setInt(pIndices, offset++, indices.get(0));
        for (int i = 1; i < indices.size(); i++) {
            if (indices.get(i).intValue() != indices.get(i - 1)) {
                MemoryUtil.setInt(pIndices, offset++, indices.get(i));
            }
        }

        MdoResult.checkResult(model.deleteRows(len, pIndices));
        syncData(true, false);
    }


    @Override
    public MdoCol getCol(MdoVar var) {
        int j = var.getIndex();
        Pointer realSize = MemoryUtil.intByReference();
        Pointer colIndices = MemoryUtil.intArray(1);
        MemoryUtil.setInt(colIndices, 0, j);

        MdoResult.checkResult(model.getCols(1, colIndices, Pointer.NULL, Pointer.NULL, Pointer.NULL, 0, realSize));
        MdoCol col = new MdoCol();
        if (realSize.getInt(0) > 0) {
            Pointer bgn = MemoryUtil.intArray(2);
            Pointer indices = MemoryUtil.intArray(realSize.getInt(0));
            Pointer values = MemoryUtil.doubleArray(realSize.getInt(0));
            MdoResult.checkResult(model.getCols(1, colIndices, bgn, indices, values, realSize.getInt(0), realSize));
            for (int i = 0; i < realSize.getInt(0); i++) {
                double coeff = MemoryUtil.getDouble(values, i);
                MdoCons cons = conss.get(MemoryUtil.getInt(indices, i));
                col.addTerm(coeff, cons);
            }
        }
        return col;
    }

    @Override
    public MdoExprLinear getExprLinear(MdoCons cons) {
        int i = cons.getIndex();
        Pointer realSize = MemoryUtil.intByReference();
        Pointer rowIndices = MemoryUtil.intArray(1);
        MemoryUtil.setInt(rowIndices, 0, i);

        MdoResult.checkResult(model.getRows(1, rowIndices, Pointer.NULL, Pointer.NULL, Pointer.NULL, 0, realSize));
        MdoExprLinear expr = new MdoExprLinear();
        if (realSize.getInt(0) > 0) {
            Pointer bgn = MemoryUtil.intArray(2);
            Pointer indices = MemoryUtil.intArray(realSize.getInt(0));
            Pointer values = MemoryUtil.doubleArray(realSize.getInt(0));
            MdoResult.checkResult(model.getRows(1, rowIndices, bgn, indices, values, realSize.getInt(0), realSize));
            for (int j = 0; j < realSize.getInt(0); j++) {
                double coeff = MemoryUtil.getDouble(values, j);
                MdoVar var = vars.get(MemoryUtil.getInt(indices, j));
                expr.addTerm(coeff, var);
            }
        }
        return expr;
    }

    @Override
    public boolean isMinObjSense() {
        return model.isMinObjSense() != 0;
    }

    @Override
    public boolean isMaxObjSense() {
        return model.isMaxObjSense() != 0;
    }

    @Override
    public void setMinObjSense() {
        model.setMinObjSense();
    }

    @Override
    public void setMaxObjSense() {
        model.setMaxObjSense();
    }

    @Override
    public double getObjOffset() {
        return model.getObjOffset();
    }

    @Override
    public void setObjOffset(double objFix) {
        model.setObjOffset(objFix);
    }

    @Override
    public double[] getObjs(MdoVar[] vars) {
        return getRealAttrVars(Mdo.REAL_ATTR_OBJ, vars);
    }

    @Override
    public void setObjs(MdoVar[] vars, double[] vals) {
        setRealAttrVars(Mdo.REAL_ATTR_OBJ, vars, vals);
    }

    @Override
    public double[] getElements(MdoCons[] conss, MdoVar[] vars) {
        Pointer values = MemoryUtil.doubleArray(conss.length);
        Pointer rowIndices = MemoryUtil.intArray(conss.length);
        Pointer colIndices = MemoryUtil.intArray(conss.length);

        for (int i = 0; i < conss.length; i++) {
            MemoryUtil.setInt(rowIndices, i, conss[i].getIndex());
            MemoryUtil.setInt(colIndices, i, vars[i].getIndex());
        }

        MdoResult.checkResult(model.getElements(conss.length, rowIndices, colIndices, values));
        return values.getDoubleArray(0, conss.length);
    }

    @Override
    public void setElements(MdoCons[] conss, MdoVar[] vars, double[] values) {
        Pointer pValues = MemoryUtil.doubleArray(conss.length);
        Pointer rowIndices = MemoryUtil.intArray(conss.length);
        Pointer colIndices = MemoryUtil.intArray(conss.length);

        for (int i = 0; i < conss.length; i++) {
            MemoryUtil.setInt(rowIndices, i, conss[i].getIndex());
            MemoryUtil.setInt(colIndices, i, vars[i].getIndex());
            MemoryUtil.setDouble(pValues, i, values[i]);
        }

        MdoResult.checkResult(model.setElements(conss.length, rowIndices, colIndices, pValues));
    }

    @Override
    public void deleteElements(MdoCons[] conss, MdoVar[] vars) {
        Pointer rowIndices = MemoryUtil.intArray(conss.length);
        Pointer colIndices = MemoryUtil.intArray(conss.length);

        for (int i = 0; i < conss.length; i++) {
            MemoryUtil.setInt(rowIndices, i, conss[i].getIndex());
            MemoryUtil.setInt(colIndices, i, vars[i].getIndex());
        }

        MdoResult.checkResult(model.deleteElements(conss.length, rowIndices, colIndices));
    }

    @Override
    public void deleteAllElements() {
        MdoResult.checkResult(model.deleteAllElements());
    }

    @Override
    public double[] getQuadraticElements(MdoVar[] vars1, MdoVar[] vars2) {
        Pointer values = MemoryUtil.doubleArray(vars1.length);
        Pointer colIndices1 = MemoryUtil.intArray(vars1.length);
        Pointer colIndices2 = MemoryUtil.intArray(vars1.length);

        for (int i = 0; i < vars1.length; i++) {
            MemoryUtil.setInt(colIndices1, i, vars1[i].getIndex());
            MemoryUtil.setInt(colIndices2, i, vars2[i].getIndex());
        }

        MdoResult.checkResult(model.getQuadraticElements(vars1.length, colIndices1, colIndices2, values));
        return values.getDoubleArray(0, vars1.length);
    }

    @Override
    public void setQuadraticElements(MdoVar[] vars1, MdoVar[] vars2, double[] values) {
        Pointer pValues = MemoryUtil.doubleArray(vars1.length);
        Pointer colIndices1 = MemoryUtil.intArray(vars1.length);
        Pointer colIndices2 = MemoryUtil.intArray(vars1.length);

        for (int i = 0; i < vars1.length; i++) {
            MemoryUtil.setInt(colIndices1, i, vars1[i].getIndex());
            MemoryUtil.setInt(colIndices2, i, vars2[i].getIndex());
            MemoryUtil.setDouble(pValues, i, values[i]);
        }

        MdoResult.checkResult(model.setQuadraticElements(vars1.length, colIndices1, colIndices2, pValues));
    }

    @Override
    public void deleteQuadraticElements(MdoVar[] vars1, MdoVar[] vars2) {
        Pointer colIndices1 = MemoryUtil.intArray(vars1.length);
        Pointer colIndices2 = MemoryUtil.intArray(vars1.length);

        for (int i = 0; i < vars1.length; i++) {
            MemoryUtil.setInt(colIndices1, i, vars1[i].getIndex());
            MemoryUtil.setInt(colIndices2, i, vars2[i].getIndex());
        }

        MdoResult.checkResult(model.deleteQuadraticElements(vars1.length, colIndices1, colIndices2));
    }

    @Override
    public void deleteAllQuadraticElements() {
        MdoResult.checkResult(model.deleteAllQuadraticElements());
    }

    @Override
    public void setStrAttrIndex(String att, int index, String val) {
        MdoResult.checkResult(model.setStrAttrIndex(MemoryUtil.charArray(att), index, MemoryUtil.charArray(val)));
    }

    @Override
    public String getStrAttrIndex(String att, int index) {
        Pointer buffer = new Memory(Mdo.MAX_NATIVE_STR_LEN);
        MdoResult.checkResult(model.getStrAttrIndex(MemoryUtil.charArray(att), index, Mdo.MAX_NATIVE_STR_LEN, buffer));
        return Native.toString(buffer.getByteArray(0, Mdo.MAX_NATIVE_STR_LEN));
    }

    @Override
    public void setIntAttrIndex(String att, int index, int val) {
        MdoResult.checkResult(model.setIntAttrIndex(MemoryUtil.charArray(att), index, val));
    }

    @Override
    public int getIntAttrIndex(String att, int index) {
        Pointer n = MemoryUtil.intByReference();
        MdoResult.checkResult(model.getIntAttrIndex(MemoryUtil.charArray(att), index, n));
        return n.getInt(0);
    }

    @Override
    public void setRealAttrIndex(String att, int index, double val) {
        MdoResult.checkResult(model.setRealAttrIndex(MemoryUtil.charArray(att), index, val));
    }

    @Override
    public double getRealAttrIndex(String att, int index) {
        Pointer d = MemoryUtil.doubleByReference();
        MdoResult.checkResult(model.getRealAttrIndex(MemoryUtil.charArray(att), index, d));
        return d.getDouble(0);
    }

    @Override
    public void setIntAttrArray(String att, int start, int len, int[] val) {
        MdoResult.checkResult(model.setIntAttrArray(MemoryUtil.charArray(att), start, len, MemoryUtil.intArray(val)));
    }

    @Override
    public int[] getIntAttrArray(String att, int start, int len) {
        Pointer val = MemoryUtil.intArray(len);
        MdoResult.checkResult(model.getIntAttrArray(MemoryUtil.charArray(att), start, len, val));
        return val.getIntArray(0, len);
    }

    @Override
    public void setRealAttrArray(String att, int start, int len, double[] val) {
        MdoResult.checkResult(model.setRealAttrArray(MemoryUtil.charArray(att), start, len, MemoryUtil.doubleArray(val)));
    }

    @Override
    public double[] getRealAttrArray(String att, int start, int len) {
        Pointer val = MemoryUtil.doubleArray(len);
        MdoResult.checkResult(model.getRealAttrArray(MemoryUtil.charArray(att), start, len, val));
        return val.getDoubleArray(0, len);
    }

    @Override
    public void setIntAttrVars(String att, MdoVar[] vars, int[] vals) {
        for (int i = 0; i < vars.length; i++) {
            MdoVar var = vars[i];
            setIntAttrIndex(att, var.getIndex(), vals[i]);
        }
    }

    @Override
    public int[] getIntAttrVars(String att, MdoVar[] vars) {
        int[] vals = new int[vars.length];
        for (int i = 0; i < vars.length; i++) {
            MdoVar var = vars[i];
            vals[i] = getIntAttrIndex(att, var.getIndex());
        }
        return vals;
    }

    @Override
    public void setRealAttrVars(String att, MdoVar[] vars, double[] vals) {
        for (int i = 0; i < vars.length; i++) {
            MdoVar var = vars[i];
            setRealAttrIndex(att, var.getIndex(), vals[i]);
        }
    }

    @Override
    public double[] getRealAttrVars(String att, MdoVar[] vars) {
        double[] vals = new double[vars.length];
        for (int i = 0; i < vars.length; i++) {
            MdoVar var = vars[i];
            vals[i] = getRealAttrIndex(att, var.getIndex());
        }
        return vals;
    }

    @Override
    public void setIntAttrConss(String att, MdoCons[] conss, int[] vals) {
        for (int i = 0; i < conss.length; i++) {
            MdoCons cons = conss[i];
            setIntAttrIndex(att, cons.getIndex(), vals[i]);
        }
    }

    @Override
    public int[] getIntAttrConss(String att, MdoCons[] conss) {
        int[] vals = new int[conss.length];
        for (int i = 0; i < conss.length; i++) {
            MdoCons cons = conss[i];
            vals[i] = getIntAttrIndex(att, cons.getIndex());
        }
        return vals;
    }

    @Override
    public void setRealAttrConss(String att, MdoCons[] conss, double[] vals) {
        for (int i = 0; i < conss.length; i++) {
            MdoCons cons = conss[i];
            setRealAttrIndex(att, cons.getIndex(), vals[i]);
        }
    }

    @Override
    public double[] getRealAttrConss(String att, MdoCons[] conss) {
        double[] vals = new double[conss.length];
        for (int i = 0; i < conss.length; i++) {
            MdoCons cons = conss[i];
            vals[i] = getRealAttrIndex(att, cons.getIndex());
        }
        return vals;
    }

    @Override
    public void setIntAttr(String att, int val) {
        MdoResult.checkResult(model.setIntAttr(MemoryUtil.charArray(att), val));
    }

    @Override
    public int getIntAttr(String att) {
        Pointer val = MemoryUtil.intByReference();
        MdoResult.checkResult(model.getIntAttr(MemoryUtil.charArray(att), val));
        return val.getInt(0);
    }

    @Override
    public void setRealAttr(String att, double val) {
        MdoResult.checkResult(model.setRealAttr(MemoryUtil.charArray(att), val));
    }

    @Override
    public double getRealAttr(String att) {
        Pointer val = MemoryUtil.doubleByReference();
        MdoResult.checkResult(model.getRealAttr(MemoryUtil.charArray(att), val));
        return val.getDouble(0);
    }

    @Override
    public void setStrAttr(String att, String val) {
        MdoResult.checkResult(model.setStrAttr(MemoryUtil.charArray(att), MemoryUtil.charArray(val)));
    }

    @Override
    public String getStrAttr(String att) {
        Pointer buffer = new Memory(Mdo.MAX_NATIVE_STR_LEN);
        MdoResult.checkResult(model.getStrAttr(MemoryUtil.charArray(att), Mdo.MAX_NATIVE_STR_LEN, buffer));
        return Native.toString(buffer.getByteArray(0, Mdo.MAX_NATIVE_STR_LEN));
    }

    @Override
    public void setStrParam(String par, String val) {
        MdoResult.checkResult(model.setStrParam(MemoryUtil.charArray(par), MemoryUtil.charArray(val)));
    }

    @Override
    public String getStrParam(String par) {
        Pointer val = new Memory(Mdo.MAX_NATIVE_STR_LEN);
        MdoResult.checkResult(model.getStrParam(MemoryUtil.charArray(par), Mdo.MAX_NATIVE_STR_LEN, val));
        return Native.toString(val.getByteArray(0, Mdo.MAX_NATIVE_STR_LEN));
    }

    @Override
    public void setIntParam(String par, int val) {
        MdoResult.checkResult(model.setIntParam(MemoryUtil.charArray(par), val));
    }

    @Override
    public int getIntParam(String par) {
        Pointer val = MemoryUtil.intByReference();
        MdoResult.checkResult(model.getIntParam(MemoryUtil.charArray(par), val));
        return val.getInt(0);
    }

    @Override
    public void setRealParam(String par, double val) {
        MdoResult.checkResult(model.setRealParam(MemoryUtil.charArray(par), val));
    }

    @Override
    public double getRealParam(String par) {
        Pointer val = MemoryUtil.doubleByReference();
        MdoResult.checkResult(model.getRealParam(MemoryUtil.charArray(par), val));
        return val.getDouble(0);
    }

    @Override
    public void free() {
        if (!freed) {
            freed = true;
            model.freeMdl();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        free();
    }

    @Override
    public void readProb(String filename) {
        MdoResult.checkResult(model.readProb(MemoryUtil.charArray(filename)));
        syncData(true, true);
    }

    @Override
    public void writeProb(String filename) {
        MdoResult.checkResult(model.writeProb(MemoryUtil.charArray(filename)));
    }

    @Override
    public void writeSoln(String filename) {
        MdoResult.checkResult(model.writeSoln(MemoryUtil.charArray(filename)));
    }

    @Override
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

    @Override
    public void writeTask(String filename, boolean writeModel, boolean writeParam, boolean writeSoln) {
        MdoResult.checkResult(
                model.writeTask(
                        MemoryUtil.charArray(filename), writeModel ? 1 : 0,
                        writeParam ? 1 : 0, writeSoln ? 1 : 0
                )
        );
    }

    @Override
    public String submitTask() {
        Pointer id = MemoryUtil.nativeString();
        MdoResult.checkResult(model.submitTask(id));
        return Native.toString(id.getByteArray(0, Mdo.MAX_NATIVE_STR_LEN));
    }

    @Override
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

    @Override
    public void setLogToConsole(boolean flag) {
        MdoResult.checkResult(model.setLogToConsole(flag ? 1 : 0));
    }

    @Override
    public void setLogFile(String filename) {
        MdoResult.checkResult(model.setLogFile(MemoryUtil.charArray(filename)));
    }

    @Override
    public void setLogCallback(final LogCallback logCallback) {
        Callback callback = new Callback() {
            public void invoke(String msg, Pointer userdata) {
                logCallback.log(msg);
            }
        };
        MdoResult.checkResult(model.setLogCallback(callback, Pointer.NULL));
    }

    @Override
    public void solveProb() {
        MdoResult.checkResult(model.solveProb());
    }

    @Override
    public void displayResult() {
        model.displayResults();
    }

    @Override
    public MdoStatus getStatus() {
        return MdoStatus.fromCode(model.getStatus());
    }

    @Override
    public String explainStatus(MdoStatus status) {
        Pointer val = MemoryUtil.nativeString();
        model.explainStatus(status.getCode(), val);
        return Native.toString(val.getByteArray(0, Mdo.MAX_NATIVE_STR_LEN));
    }

    @Override
    public String explainResult(MdoResult result) {
        Pointer val = MemoryUtil.nativeString();
        model.explainResult(result.getCode(), val);
        return Native.toString(val.getByteArray(0, Mdo.MAX_NATIVE_STR_LEN));
    }

    @Override
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
