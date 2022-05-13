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

import java.util.*;

public class MdoExprQuad implements MdoExpr {
    /**
     * Data Structure
     * A quadratic expression is represented as a linear expression, plus a list of quadratic terms.
     */
    MdoExprLinear linear = new MdoExprLinear();
    private List<Map.Entry<Map.Entry<MdoVar, MdoVar>, Double>> quadTerms = new ArrayList<>();

    private static class TripleComparator implements Comparator<Map.Entry<Map.Entry<MdoVar, MdoVar>, Double>> {
        @Override
        public int compare(Map.Entry<Map.Entry<MdoVar, MdoVar>, Double> l,
                           Map.Entry<Map.Entry<MdoVar, MdoVar>, Double> r) {
            Map.Entry<MdoVar, MdoVar> lVars = l.getKey();
            Map.Entry<MdoVar, MdoVar> rVars = r.getKey();
            int cmp = lVars.getKey().getIndex() - rVars.getKey().getIndex();
            if (cmp != 0) return cmp;
            return lVars.getValue().getIndex() - rVars.getValue().getIndex();
        }
    }

    /**
     * Default constructor
     */
    public MdoExprQuad() {}

    /**
     * Copy constructor
     * @param another another linear expression
     */
    public MdoExprQuad(MdoExprQuad another) {
        this.add(another);
    }

    public MdoExprQuad(MdoExprLinear le) {
        this.linear = le;
    }

    /**
     * Merge the same type
     */
    public void purge() {
        this.linear.purge();
        if (quadTerms.size() < 2) return;

        List<Map.Entry<Map.Entry<MdoVar, MdoVar>, Double>> remap = new ArrayList<>();
        for (Map.Entry<Map.Entry<MdoVar, MdoVar>, Double> entry : quadTerms) {
            if (entry.getKey().getKey().getIndex() <= entry.getKey().getValue().getIndex()) {
                remap.add(entry);
            } else {
                remap.add(new AbstractMap.SimpleEntry<>(
                        new AbstractMap.SimpleEntry<>(entry.getKey().getValue(), entry.getKey().getKey()),
                        entry.getValue()
                ));
            }
        }
        quadTerms = remap;
        Collections.sort(quadTerms, new MdoExprQuad.TripleComparator());

        List<Map.Entry<Map.Entry<MdoVar, MdoVar>, Double>> purged = new ArrayList<>();
        purged.add(quadTerms.get(0));

        Map.Entry<Map.Entry<MdoVar, MdoVar>, Double> surface = purged.get(0);

        for (int i = 1; i < quadTerms.size(); i++) {
            if (surface.getKey().getKey().equals(this.getVar1(i)) &&
                    surface.getKey().getValue().equals(this.getVar2(i))) {
                surface.setValue(surface.getValue() + getCoeff(i));
            } else {
                purged.add(quadTerms.get(i));
                surface = purged.get(purged.size() - 1);
            }
        }
        quadTerms = purged;
    }

    /**
     * Add another linear expression
     * @param rhs the linear expression to be added
     */
    public void add(MdoExprLinear rhs) {
        this.linear.add(rhs);
    }

    /**
     * Add another quadratic expression
     * @param rhs the quadratic expression to be added
     */
    public void add(MdoExprQuad rhs) {
        this.multAdd(1, rhs);
    }

    /**
     * Add a constant multiple of another linear expression
     * @param multiplier the multiplier constant
     * @param rhs another linear expression
     */
    public void multAdd(double multiplier, MdoExprLinear rhs) {
        this.linear.multAdd(multiplier, rhs);
    }

    /**
     * Add a constant multiple of another quadratic expression
     * @param multiplier the multiplier constant
     * @param rhs another quadratic expression
     */
    public void multAdd(double multiplier, MdoExprQuad rhs) {
        if (multiplier != 0d) {
            this.linear.multAdd(multiplier, rhs.linear);

            int size = rhs.size();
            for (int i = 0; i < size; i++) {
                this.addTerm(rhs.getCoeff(i) * multiplier, rhs.getVar1(i), rhs.getVar2(i));
            }
        }
    }

    /**
     * Subtract another linear expression
     * @param rhs another linear expression
     */
    public void subtract(MdoExprLinear rhs) {
        this.linear.subtract(rhs);
    }

    /**
     * Subtract another quadratic expression
     * @param rhs another quadratic expression
     */
    public void subtract(MdoExprQuad rhs) {
        this.multAdd(-1, rhs);
    }

    /**
     * Add a single to this linear expression
     * @param coeff the coefficient
     * @param var the variable object
     */
    public void addTerm(double coeff, MdoVar var) {
        this.linear.addTerm(coeff, var);
    }

    /**
     * Add multiple terms to this linear expression
     * @param coeffs the coefficient array
     * @param vars the variable object array
     */
    public void addTerms(double[] coeffs, MdoVar[] vars) {
        this.linear.addTerms(coeffs, vars, 0, vars.length);
    }

    /**
     * Add multiple terms to this linear expression
     * @param coeffs the coefficient array
     * @param vars the variable object array
     * @param start the offset of {@code coeffs} and {@code vars}
     * @param len the number of terms to be added from arrays
     */
    public void addTerms(double[] coeffs, MdoVar[] vars, int start, int len) {
        this.linear.addTerms(coeffs, vars, start, len);
    }

    /**
     * Add a single to this quadratic expression
     * @param coeff the coefficient
     * @param var1 the variable object
     * @param var2 the variable object
     */
    public void addTerm(double coeff, MdoVar var1, MdoVar var2) {
        Map.Entry<MdoVar, MdoVar> key = new AbstractMap.SimpleEntry<>(var1, var2);
        this.quadTerms.add(new AbstractMap.SimpleEntry<>(key, coeff));
    }

    /**
     * Add multiple terms to this quadratic expression
     * @param coeffs the coefficient array
     * @param vars1 the variable object
     * @param vars2 the variable object
     */
    public void addTerms(double[] coeffs, MdoVar[] vars1, MdoVar[] vars2) {
        for (int i = 0; i < coeffs.length; i++) {
            addTerm(coeffs[i], vars1[i], vars2[i]);
        }
    }

    /**
     * Add multiple terms to this quadratic expression
     * @param coeffs the coefficient array
     * @param vars1 the variable object array
     * @param vars2 the variable object array
     * @param start the offset of {@code coeffs} and {@code vars}
     * @param len the number of terms to be added from arrays
     */
    public void addTerms(double[] coeffs, MdoVar[] vars1, MdoVar[] vars2, int start, int len) {
        for (int i = start; i < start + len; i++) {
            addTerm(coeffs[i], vars1[i], vars2[i]);
        }
    }

    /**
     * Get the first variable object with a single term from this quadratic expression
     * @param index the term index
     * @return the corresponding variable object
     */
    public MdoVar getVar1(int index) {
        return this.quadTerms.get(index).getKey().getKey();
    }

    /**
     * Get all the first variable object within array from this quadratic expression
     * @return the corresponding variable object
     */
    public MdoVar[] getVars1() {
        MdoVar[] vars = new MdoVar[this.size()];
        for (int i = 0; i < this.size(); i++) {
            vars[i] = this.getVar1(i);
        }
        return vars;
    }

    /**
     * Get the second variable object with a single term from this quadratic expression
     * @param index the term index
     * @return the corresponding variable object
     */
    public MdoVar getVar2(int index) {
        return this.quadTerms.get(index).getKey().getValue();
    }

    /**
     * Get all the second variable object within array from this quadratic expression
     * @return the corresponding variable object
     */
    public MdoVar[] getVars2() {
        MdoVar[] vars = new MdoVar[this.size()];
        for (int i = 0; i < this.size(); i++) {
            vars[i] = this.getVar2(i);
        }
        return vars;
    }

    /**
     * Get the coefficient with a single term from this quadratic expression
     * @param index the term index
     * @return the corresponding coefficient
     */
    public double getCoeff(int index) {
        return this.quadTerms.get(index).getValue();
    }

    /**
     * Get all the coefficient within array from this quadratic expression
     * @return the corresponding coefficient
     */
    public double[] getCoeffs() {
        double[] coeffs = new double[this.size()];
        for (int i = 0; i < this.size(); i++) {
            coeffs[i] = this.getCoeff(i);
        }
        return coeffs;
    }

    /**
     * Get the linear expression associated with the quadratic expression
     * @return the linear expression
     */
    public MdoExprLinear getLinear() {
        return this.linear;
    }

    public MdoVar getLinearTerm(int index) {
        return this.linear.getVar(index);
    }

    public Map.Entry<MdoVar, MdoVar> getQuadTerm(int index) {
        return this.quadTerms.get(index).getKey();
    }

    public boolean removeTerm(MdoVar var) {
        return this.linear.removeTerm(var);
    }

    /**
     * Remove the quadratic term stored at index i of the expression
     * @param index the term index
     */
    public void removeTerm(int index) {
        this.quadTerms.remove(index);
    }

    /**
     * Remove all terms associated with variable var from the quadratic expression
     * @param var the variable object
     * @return true for successful removal, false if no such term found
     */
    public boolean removeTerm(Map.Entry<MdoVar, MdoVar> var) {
        boolean found = false;
        ListIterator<Map.Entry<Map.Entry<MdoVar, MdoVar>, Double>> iter = quadTerms.listIterator();
        while (iter.hasNext()) {
            Map.Entry<MdoVar, MdoVar> it = iter.next().getKey();
            if ((it.getKey().equals(var.getKey()) && it.getValue().equals(var.getValue())) ||
                    (it.getKey().equals(var.getValue())  && it.getValue().equals(var.getKey()))
            ) {
                found = true;
                iter.remove();
            }
        }
        return found;
    }

    /**
     * Get the constant part of this linear expression
     * @return the constant value
     */
    public double getConstant() {
        return this.linear.getConstant();
    }

    /**
     * Set the constant part of this linear expression
     * @param constant the constant value
     */
    public void addConstant(double constant) {
        this.linear.addConstant(constant);
    }

    /**
     * Add a value to the constant part of this linear expression
     * @param constant the value to be added
     */
    public void setConstant(double constant) {
        this.linear.setConstant(constant);
    }

    /**
     * Remove all terms in this quadratic expression(also include linear express), and reset constant to zero
     */
    public void clear() {
        this.linear.clear();
        this.quadTerms.clear();
    }

    /**
     * Size of the linear expression
     * @return the number of terms
     */
    public int size() {
        return this.quadTerms.size();
    }

    /**
     * Test weather two quadratic expressions are the same
     * @param rhs another quadratic expression to be tested
     * @return true if two quadratic expressions are the same
     */
    public boolean equals(MdoExprQuad rhs) {
        if (this.size() != rhs.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (!this.getVar1(i).equals(rhs.getVar1(i)) || !this.getVar2(i).equals(rhs.getVar2(i)) || this.getCoeff(i) != rhs.getCoeff(i)) {
                return false;
            }
        }
        return this.linear.equals(rhs.linear);
    }
}
