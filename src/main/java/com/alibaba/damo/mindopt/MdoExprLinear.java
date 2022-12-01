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

/**
 * Mathematical linear expression
 */
public class MdoExprLinear implements MdoExpr {
    List<Map.Entry<MdoVar, Double>> terms = new ArrayList<Map.Entry<MdoVar, Double>>();
    double constant;

    private static class PairComparator implements Comparator<Map.Entry<MdoVar, Double>> {
        @Override
        public int compare(Map.Entry<MdoVar, Double> l, Map.Entry<MdoVar, Double> r) {
            return l.getKey().getIndex() - r.getKey().getIndex();
        }
    }

    /**
     * Sorts terms by constraint index, merge terms with identical variables
     */
    public void purge() {
        if (size() < 2) return;
        Collections.sort(terms, new PairComparator());
        List<Map.Entry<MdoVar, Double>> purged = new ArrayList<Map.Entry<MdoVar, Double>>();
        purged.add(terms.get(0));

        Map.Entry<MdoVar, Double> surface = purged.get(purged.size() - 1);

        for (int i = 1; i < terms.size(); i++) {
            if (surface.getKey().equals(getVar(i))) {
                surface.setValue(surface.getValue() + getCoeff(i));
            } else {
                purged.add(terms.get(i));
                surface = purged.get(purged.size() - 1);
            }
        }
        terms = purged;
    }

    /**
     * Default constructor
     */
    public MdoExprLinear() {}

    /**
     * Copy constructor
     * @param another another linear expression
     */
    public MdoExprLinear(MdoExprLinear another) {
        add(another);
    }

    /**
     * Add another linear expression
     * @param rhs the linear expression to be added
     */
    public void add(MdoExprLinear rhs) {
        multAdd(1, rhs);
    }

    /**
     * Add a constant multiple of another linear expression
     * @param multiplier the multiplier constant
     * @param rhs another linear expression
     */
    public void multAdd(double multiplier, MdoExprLinear rhs) {
        if (multiplier != 0d) {
            // Fix size first of all
            int size = rhs.size();
            for (int i = 0; i < size; i++) {
                addTerm(rhs.getCoeff(i) * multiplier, rhs.getVar(i));
            }
            addConstant(multiplier * rhs.getConstant());
        }
    }

    /**
     * Subtract another linear expression
     * @param rhs another linear expression
     */
    public void subtract(MdoExprLinear rhs) {
        multAdd(-1, rhs);
    }

    /**
     * Add a single to this linear expression
     * @param coeff the coefficient
     * @param var the variable object
     */
    public void addTerm(double coeff, MdoVar var) {
        terms.add(new AbstractMap.SimpleEntry<MdoVar, Double>(var, coeff));
    }

    /**
     * Add multiple terms to this linear expression
     * @param coeffs the coefficient array
     * @param vars the variable object array
     */
    public void addTerms(double[] coeffs, MdoVar[] vars) {
        for (int i = 0; i < coeffs.length; i++) {
            addTerm(coeffs[i], vars[i]);
        }
    }

    /**
     * Add multiple terms to this linear expression
     * @param coeffs the coefficient array
     * @param vars the variable object array
     * @param start the offset of {@code coeffs} and {@code vars}
     * @param len the number of terms to be added from arrays
     */
    public void addTerms(double[] coeffs, MdoVar[] vars, int start, int len) {
        for (int i = start; i < start + len; i++) {
            addTerm(coeffs[i], vars[i]);
        }
    }

    /**
     * Get the variable object from a single term in this linear expression
     * @param index the term index
     * @return the corresponding variable object
     */
    public MdoVar getVar(int index) {
        return terms.get(index).getKey();
    }

    /**
     * Get the coefficient from a single term in this linear expression
     * @param index the term index
     * @return the corresponding coefficient
     */
    public double getCoeff(int index) {
        return terms.get(index).getValue();
    }

    /**
     * Remove a single term
     * @param index the term index
     */
    public void removeTerm(int index) {
        terms.remove(index);
    }

    /**
     * Remove a single term by variable object, this operation may be insufficient
     * @param var the variable object
     * @return true for successful removal, false if no such term found
     */
    public boolean removeTerm(MdoVar var) {
        boolean found = false;
        ListIterator<Map.Entry<MdoVar, Double>> iter = terms.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getKey().equals(var)) {
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
        return constant;
    }

    /**
     * Set the constant part of this linear expression
     * @param constant the constant value
     */
    public void setConstant(double constant) {
        this.constant = constant;
    }

    /**
     * Add a value to the constant part of this linear expression
     * @param constant the value to be added
     */
    public void addConstant(double constant) {
        this.constant += constant;
    }

    /**
     * Remove all terms in this linear expression, and reset constant to zero
     */
    public void clear() {
        terms.clear();
        this.constant = 0;
    }

    /**
     * Size of the linear expression
     * @return the number of terms
     */
    public int size() {
        return this.terms.size();
    }

    /**
     * Test weather two linear expressions are the same
     * @param rhs another linear expression to be tested
     * @return true if two linear expressions are the same
     */
    public boolean equals(MdoExprLinear rhs) {
        if (size() != rhs.size()) return false;
        if (getConstant() != rhs.getConstant()) {
            return false;
        }
        this.purge();
        rhs.purge();
        for (int i = 0; i < size(); i++) {
            if (getCoeff(i) != rhs.getCoeff(i)) {
                return false;
            }
            if (!getVar(i).equals(rhs.getVar(i))) {
                return false;
            }
        }
        return true;
    }
}
