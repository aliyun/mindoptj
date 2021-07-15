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
 * Represents a column in matrix
 */
public class MdoCol {
    List<Map.Entry<MdoCons, Double>> terms = new ArrayList<Map.Entry<MdoCons, Double>>();

    private static class PairComparator implements Comparator<Map.Entry<MdoCons, Double>> {
        @Override
        public int compare(Map.Entry<MdoCons, Double> l, Map.Entry<MdoCons, Double> r) {
            return l.getKey().getIndex() - r.getKey().getIndex();
        }
    }

    /**
     * Default constructor
     */
    public MdoCol() {}

    /**
     * Copy constructor
     * @param rhs another column
     */
    public MdoCol(MdoCol rhs) {
        add(rhs);
    }

    /**
     * Sort terms by constraint index, merge terms with identical constraints
     */
    public void purge() {
        if (size() < 2) return;
        Collections.sort(terms, new PairComparator());
        List<Map.Entry<MdoCons, Double>> purged = new ArrayList<Map.Entry<MdoCons, Double>>();
        purged.add(terms.get(0));

        Map.Entry<MdoCons, Double> surface = purged.get(purged.size() - 1);

        for (int i = 1; i < terms.size(); i++) {
            if (surface.getKey().equals(getCons(i))) {
                surface.setValue(surface.getValue() + getCoeff(i));
            } else {
                purged.add(terms.get(i));
                surface = purged.get(purged.size() - 1);
            }
        }
        terms = purged;
    }

    /**
     * Add another column
     * @param rhs the column to be added
     */
    public void add(MdoCol rhs) {
        multAdd(1, rhs);
    }

    /**
     * Add a constant multiple of another column
     * @param multiplier the multiplier constant
     * @param rhs another column
     */
    public void multAdd(double multiplier, MdoCol rhs) {
        if (multiplier != 0d) {
            // Fix size first of all
            int size = rhs.size();
            for (int i = 0; i < size; i++) {
                addTerm(rhs.getCoeff(i) * multiplier, rhs.getCons(i));
            }
        }
    }

    /**
     * Subtract another column
     * @param rhs another column
     */
    public void subtract(MdoCol rhs) {
        multAdd(-1, rhs);
    }

    /**
     * Add a single term to this column
     * @param coeff the coefficient
     * @param cons the constraint object
     */
    public void addTerm(double coeff, MdoCons cons) {
        terms.add(new AbstractMap.SimpleEntry<MdoCons, Double>(cons, coeff));
    }

    /**
     * Add multiple terms to this column
     * @param coeffs the coefficient array
     * @param conss the constraint object array
     */
    public void addTerms(double[] coeffs, MdoCons[] conss) {
        for (int i = 0; i < coeffs.length; i++) {
            addTerm(coeffs[i], conss[i]);
        }
    }

    /**
     * Get the constraint object from a single term in this column
     * @param index the term index
     * @return the corresponding constraint object
     */
    public MdoCons getCons(int index) {
        return terms.get(index).getKey();
    }

    /**
     * Get the coefficient from a single term in this column
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
     * Remove a single term by constraint object, this operation may be insufficient
     * @param cons the constraint object
     * @return true for successful removal, false if no such term found
     */
    public boolean removeTerm(MdoCons cons) {
        boolean found = false;
        ListIterator<Map.Entry<MdoCons, Double>> iter = terms.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getKey().equals(cons)) {
                found = true;
                iter.remove();
            }
        }
        return found;
    }

    /**
     * Remove all terms from this column
     */
    public void clear() {
        terms.clear();
    }

    /**
     * Size of the column
     * @return the number of terms
     */
    public int size() {
        return terms.size();
    }

    /**
     * Test weather two columns are the same
     * @param rhs another column to be tested
     * @return true if two columns are the same
     */
    public boolean equals(MdoCol rhs) {
        if (size() != rhs.size()) return false;
        this.purge();
        rhs.purge();
        for (int i = 0; i < size(); i++) {
            if (getCoeff(i) != rhs.getCoeff(i)) {
                return false;
            }
            if (!getCons(i).equals(rhs.getCons(i))) {
                return false;
            }
        }
        return true;
    }
}
