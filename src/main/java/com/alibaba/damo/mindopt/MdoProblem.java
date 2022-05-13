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

public interface MdoProblem extends MdoAttrAccessor, MdoParamAccessor {
    /**
     * Add a single decision variable
     * @param lb lower bound for this variable, set to {@code -Mdo.INFINITY} if it's lower-bound-free
     * @param ub upper bound for this variable, set to {@code Mdo.INFINITY} if it's upper-bound-free
     * @param obj objective coefficient for this variable
     * @param isInteger set to true if variable is an integer variable
     * @param name variable name, null for default name
     * @return the newly created variable object
     */
    MdoVar addVar(double lb, double ub, double obj, boolean isInteger, String name);

    /**
     * Add a single decision variable with its non-zero coefficients
     * @param lb lower bound for this variable, set to {@code -Mdo.INFINITY} if it's lower-bound-free
     * @param ub upper bound for this variable, set to {@code Mdo.INFINITY} if it's upper-bound-free
     * @param obj objective coefficient for this variable
     * @param isInteger set to true if variable is an integer variable
     * @param conss array of constraint objects in which the variable participates, can be null
     * @param coeffs array of coefficients for each constraint object in which the variable participates, can be null
     * @param name variable name, null for default name
     * @return the newly created variable object
     */
    MdoVar addVar(double lb, double ub, double obj, boolean isInteger, MdoCons[] conss, double[] coeffs, String name);

    /**
     * Add a decision variable, with a set of constraint objects to which the new variable belongs {@code col}
     * @param lb lower bound for this variable, set to {@code -Mdo.INFINITY} if it's lower-bound-free
     * @param ub upper bound for this variable, set to {@code Mdo.INFINITY} if it's upper-bound-free
     * @param obj objective coefficient for this variable
     * @param isInteger set to true if variable is an integer variable
     * @param col the column specifying constraint objects to which the variable belongs, can be null
     * @param name variable name, null for default name
     * @return the newly created variable object
     */
    MdoVar addVar(double lb, double ub, double obj, boolean isInteger, MdoCol col, String name);

    /**
     * Add decision variables with type, each variable must be between 0 and {@code Mdo.INFINITY} bounds
     * @param count the number of variables to be added
     * @param areIntegers set to true if variables are integer variables
     * @return the newly created variable objects
     */
    MdoVar[] addVars(int count, boolean areIntegers);

    /**
     * Add decision variables
     * @param lbs the lower bounds for variables
     * @param ubs the upper bounds for variables
     * @param objs objective coefficients for variables
     * @param areIntegers each element specifying weather variable is an integer variable
     * @param names variable names, null for default names
     * @return the newly created variable objects
     */
    MdoVar[] addVars(double[] lbs, double[] ubs, double[] objs, boolean[] areIntegers, String[] names);

    /**
     * Add decision variables
     * @param lbs the lower bounds for variables
     * @param ubs the upper bounds for variables
     * @param objs objective coefficients for variables
     * @param areIntegers each element specifying weather variable is an integer variable
     * @param names variable names, null for default names
     * @param start offset of arrays
     * @param len number of variables to be created
     * @return the newly created variable objects
     */
    MdoVar[] addVars(double[] lbs, double[] ubs, double[] objs, boolean[] areIntegers, String[] names, int start, int len);

    /**
     * Add decision variables, with an array of {@code MdoCol}
     * @param lbs the lower bounds for variables
     * @param ubs the upper bounds for variables
     * @param objs objective coefficients for variables
     * @param areIntegers each element specifying weather variable is an integer variable
     * @param cols the array of ${code MdoCol}, each element specifying constraint objects to which the corresponding variable belongs
     * @param names variable names, null for default names
     * @return the newly created variable objects
     */
    MdoVar[] addVars(double[] lbs, double[] ubs, double[] objs, boolean[] areIntegers, MdoCol[] cols, String[] names);


    /**
     * Add a single linear constraint
     * @param lhs left-hand side expression for constraint
     * @param sense sense for constraint, possible values include {@code Mdo.LESS_EQUAL}, {@code Mdo.EQUAL}, {@code Mdo.GREATER_EQUAL}
     * @param rhs right-hand side expression for constraint
     * @param name constraint name, null for default name
     * @return the newly created constraint object
     */
    MdoCons addCons(MdoExprLinear lhs, char sense, MdoExprLinear rhs, String name);

    /**
     * Add a single linear constraint
     * @param lhs left-hand side variable for constraint
     * @param sense sense for constraint, possible values include {@code Mdo.LESS_EQUAL}, {@code Mdo.EQUAL}, {@code Mdo.GREATER_EQUAL}
     * @param rhs right-hand side variable for constraint
     * @param name constraint name, null for default name
     * @return the newly created constraint object
     */
    MdoCons addCons(MdoVar lhs, char sense, MdoVar rhs, String name);

    /**
     * Add a single linear constraint
     * @param lhs left-hand side expression for constraint
     * @param sense sense for constraint, possible values include {@code Mdo.LESS_EQUAL}, {@code Mdo.EQUAL}, {@code Mdo.GREATER_EQUAL}
     * @param rhs right-hand side variable for constraint
     * @param name constraint name, null for default name
     * @return the newly created constraint object
     */
    MdoCons addCons(MdoExprLinear lhs, char sense, MdoVar rhs, String name);

    /**
     * Add a single linear constraint
     * @param lhs left-hand side variable for constraint
     * @param sense sense for constraint, possible values include {@code Mdo.LESS_EQUAL}, {@code Mdo.EQUAL}, {@code Mdo.GREATER_EQUAL}
     * @param rhs right-hand side expression for constraint
     * @param name constraint name, null for default name
     * @return the newly created constraint object
     */
    MdoCons addCons(MdoVar lhs, char sense, MdoExprLinear rhs, String name);

    /**
     * Add a single linear constraint
     * @param lhs left-hand side expression for constraint
     * @param sense sense for constraint, possible values include {@code Mdo.LESS_EQUAL}, {@code Mdo.EQUAL}, {@code Mdo.GREATER_EQUAL}
     * @param rhs right-hand side constant for constraint
     * @param name constraint name, null for default name
     * @return the newly created constraint object
     */
    MdoCons addCons(MdoExprLinear lhs, char sense, double rhs, String name);

    /**
     * Add a single linear constraint
     * @param lhs left-hand side constant for constraint
     * @param sense sense for constraint, possible values include {@code Mdo.LESS_EQUAL}, {@code Mdo.EQUAL}, {@code Mdo.GREATER_EQUAL}
     * @param rhs right-hand side expression for constraint
     * @param name constraint name, null for default name
     * @return the newly created constraint object
     */
    MdoCons addCons(double lhs, char sense, MdoExprLinear rhs, String name);

    /**
     * Add a single linear constraint
     * @param lhs left-hand side variable for constraint
     * @param sense sense for constraint, possible values include {@code Mdo.LESS_EQUAL}, {@code Mdo.EQUAL}, {@code Mdo.GREATER_EQUAL}
     * @param rhs right-hand side constant for constraint
     * @param name constraint name, null for default name
     * @return the newly created constraint object
     */
    MdoCons addCons(MdoVar lhs, char sense, double rhs, String name);

    /**
     * Add a single linear constraint
     * @param lhs left-hand side constant for constraint
     * @param sense sense for constraint, possible values include {@code Mdo.LESS_EQUAL}, {@code Mdo.EQUAL}, {@code Mdo.GREATER_EQUAL}
     * @param rhs right-hand side variable for constraint
     * @param name constraint name, null for default name
     * @return the newly created constraint object
     */
    MdoCons addCons(double lhs, char sense, MdoVar rhs, String name);

    /**
     * Add a range constraint, specifies expression must be between {@code lower} and {@code upper} bounds
     * @param expr the expression
     * @param lower the lower bound of this constraint, set to {@code -Mdo.INFINITY} if it's lower-bound-free
     * @param upper the upper bound of this constraint, set to {@code Mdo.INFINITY} if it's upper-bound-free
     * @param name constraint name, null for default name
     * @return the newly created constraint object
     */
    MdoCons addRange(MdoExprLinear expr, double lower, double upper, String name);

    /**
     * Add constraints, constraints are all of the form 0 &lt;= 0.
     * @param count number of constraints to be added
     * @return the newly created constraint objects
     */
    MdoCons[] addConss(int count);

    /**
     * Add constraints
     * @param lhss left-hand side expressions for constraints
     * @param senses senses for constraints, element possible values include {@code Mdo.LESS_EQUAL}, {@code Mdo.EQUAL}, {@code Mdo.GREATER_EQUAL}
     * @param rhss right-hand side constants for constraints
     * @param names constraint names, null for default names
     * @return the newly created constraint objects
     */
    MdoCons[] addConss(MdoExprLinear[] lhss, char[] senses, double[] rhss, String[] names);

    /**
     * Add constraints
     * @param lhss left-hand side expressions for constraints
     * @param senses senses for constraints, element possible values include {@code Mdo.LESS_EQUAL}, {@code Mdo.EQUAL}, {@code Mdo.GREATER_EQUAL}
     * @param rhss right-hand side constants for constraints
     * @param names constraint names, null for default names
     * @param start offset of arrays
     * @param len number of constraints to be added
     * @return the newly created constraint objects
     */
    MdoCons[] addConss(MdoExprLinear[] lhss, char[] senses, double[] rhss, String[] names, int start, int len);

    /**
     * Add range constraints, specify {@code exprs} must be between {@code lowers} and {@code uppers} bounds
     * @param exprs expressions
     * @param lowers the lower bounds of constraints, set element to {@code -Mdo.INFINITY} if it's lower-bound-free
     * @param uppers the upper bounds of constraints, set element to {@code Mdo.INFINITY} if it's upper-bound-free
     * @param names constraint names, null for default names
     * @return the newly created constraint objects
     */
    MdoCons[] addRanges(MdoExprLinear[] exprs, double[] lowers, double[] uppers, String[] names);

    /**
     * Get variable object by its index in model
     * @param j variable index
     * @return the indexed variable object
     */
    MdoVar getVar(int j);

    /**
     * Get constraint object by its index in model
     * @param i constraint index
     * @return the indexed constraint object
     */
    MdoCons getCons(int i);

    /**
     * Get variable object by its name
     * @param name variable name
     * @return the named variable object
     */
    MdoVar getVar(String name);

    /**
     * Get constraint object by its name
     * @param name constraint name
     * @return the named constraint object
     */
    MdoCons getCons(String name);

    /**
     * Get all variable objects in model
     * @return all variable objects in model
     */
    MdoVar[] getVars();

    /**
     * Get all constraint objects in model
     * @return all constraint objects in model
     */
    MdoCons[] getConss();

    /**
     * Remove variables from model
     * @param vars variable objects to be removed
     */
    void deleteVars(MdoVar[] vars);

    /**
     * Remove constraints from model
     * @param conss constraint objects to be removed
     */
    void deleteConss(MdoCons[] conss);

    /**
     * Get column object of variable, it contains all constraints variable participates
     * @param var the variable object
     * @return the corresponding column
     */
    MdoCol getCol(MdoVar var);

    /**
     * Get linear expression object of constraint
     * @param cons the constraint object
     * @return the corresponding linear expression
     */
    MdoExprLinear getExprLinear(MdoCons cons);

    /**
     * Check weather objective function has a minimization sense
     * @return A boolean flag that specifies if the objective function has a minimization sense.
     */
    boolean isMinObjSense();

    /**
     * Check weather objective function has a maximization sense
     * @return A boolean flag that specifies if the objective function has a maximization sense
     */
    boolean isMaxObjSense();

    /**
     * Set objective function to minimization sense.
     */
    void setMinObjSense();

    /**
     * Set objective function to maximization sense.
     */
    void setMaxObjSense();

    /**
     * Retrieve the objective offset (constant term)
     * @return The value of the objective offset (constant term).
     */
    double getObjOffset();

    /**
     * Set the objective offset (constant term)
     * @param objFix the objective offset (constant term)
     */
    void setObjOffset(double objFix);

    /**
     * Get the values of objective coefficients associated to specified variables
     * @param vars the variable objects
     * @return the values of objective coefficients
     */
    double[] getObjs(MdoVar[] vars);

    /**
     * Set the values of objective coefficients associated to specified variables
     * @param vars the variable objects
     * @param vals the new values of objective coefficients
     */
    void setObjs(MdoVar[] vars, double[] vals);

    /**
     * Get a set of values of all specified elements in the constraint matrix
     * <pre>
     *             vars[0]    vars[1]
     *
     *                |         |
     *  conss[0] --|-e[0]-|--|--|--
     *           --|--|---|--|--|--
     *  conss[1] --|--|---|--|--e[1]
     *           --|--|---|--|--|--
     * </pre>
     * @param conss the constraint objects to specify row ids
     * @param vars the variable objects to specify column ids
     * @return elements in constraint matrix at [(rowId[0], colId[0]), (rowId[1], colId[1]) ... , (rowId[N], colId[N])]
     */
    double[] getElements(MdoCons[] conss, MdoVar[] vars);

    /**
     * Modify a set of values of all specified elements in the constraint matrix
     * <pre>
     *             vars[0]    vars[1]
     *
     *                |         |
     *  conss[0] --|-e[0]-|--|--|--
     *           --|--|---|--|--|--
     *  conss[1] --|--|---|--|--e[1]
     *           --|--|---|--|--|--
     * </pre>
     * @param conss the constraint objects to specify row ids
     * @param vars the variable objects to specify column ids
     * @param values the new values for elements in constraint matrix at [(rowId[0], colId[0]), (rowId[1], colId[1]) ... , (rowId[N], colId[N])]
     */
    void setElements(MdoCons[] conss, MdoVar[] vars, double[] values);

    /**
     * Delete a set of elements from the constraint matrix
     * <pre>
     *             vars[0]    vars[1]
     *
     *                |         |
     *  conss[0] --|-e[0]-|--|--|--
     *           --|--|---|--|--|--
     *  conss[1] --|--|---|--|--e[1]
     *           --|--|---|--|--|--
     * </pre>
     * @param conss the constraint objects to specify row ids
     * @param vars the variable objects to specify column ids
     */
    void deleteElements(MdoCons[] conss, MdoVar[] vars);

    /**
     * Delete all elements from the constraint matrix.
     */
    void deleteAllElements();

    /**
     * Retrieve a set of values of all specified elements in the quadratic matrix of a quadratic program.
     * @param vars1 array that holds the first variable of quadratic terms.
     * @param vars2 array that holds the second variable of quadratic terms.
     * @return array of current nonzero values of all specified terms.
     */
    double[] getQuadraticElements(MdoVar[] vars1, MdoVar[] vars2);

    /**
     * Modify a set of values of all specified elements in the quadratic matrix of a quadratic program.
     * @param vars1 array that holds the first variable of quadratic terms.
     * @param vars2 array that holds the second variable of quadratic terms.
     * @param values array that holds the coefficient of all specified terms.
     */
    void setQuadraticElements(MdoVar[] vars1, MdoVar[] vars2, double[] values);

    /**
     * Delete a set of elements from the quadratic matrix of a quadratic program.
     * @param vars1 array that holds the first variable of quadratic terms.
     * @param vars2 array that holds the second variable of quadratic terms.
     */
    void deleteQuadraticElements(MdoVar[] vars1, MdoVar[] vars2);


    /**
     * Delete all elements from the quadratic matrix of a quadratic program.
     */
    void deleteAllQuadraticElements();

    /**
     * Change the value of a string-valued model attribute
     * @param att the attribute name
     * @param index the index of array to access
     * @param val the new attribute value
     */
    void setStrAttrIndex(String att, int index, String val);

    /**
     * Get the value of a string-valued model attribute
     * @param att the attribute name
     * @param index the index of array to access
     * @return the corresponding attribute value
     */
    String getStrAttrIndex(String att, int index);

    /**
     * Change the value of a integer-valued model attribute
     * @param att the attribute name
     * @param index the index of array to access
     * @param val the new attribute value
     */
    void setIntAttrIndex(String att, int index, int val);

    /**
     * Get the value of a integer-valued model attribute
     * @param att the attribute name
     * @param index the index of array to access
     * @return the corresponding attribute value
     */
    int getIntAttrIndex(String att, int index);

    /**
     * Change the value of a real-valued model attribute
     * @param att the attribute name
     * @param index the index of array to access
     * @param val the new attribute value
     */
    void setRealAttrIndex(String att, int index, double val);

    /**
     * Get the value of a real-valued model attribute
     * @param att the attribute name
     * @param index the index of array to access
     * @return the corresponding attribute value
     */
    double getRealAttrIndex(String att, int index);

    /**
     * Change the values of the specified array of a 32-bit integer-valued row/column attribute.
     * @param att the attribute name
     * @param start index of the first element to access
     * @param len number of elements to access
     * @param val the new values for the specified array of the attribute
     */
    void setIntAttrArray(String att, int start, int len, int[] val);

    /**
     * Get the values of the specified array of a integer-valued row/column attribute
     * @param att the attribute name
     * @param start index of the first element to access
     * @param len number of elements to access
     * @return values for the specified array of the attribute
     */
    int[] getIntAttrArray(String att, int start, int len);

    /**
     * Change the values of the specified array of real-valued row/column attribute.
     * @param att the attribute name
     * @param start index of the first element to access
     * @param len number of elements to access
     * @param val the new values for the specified array of the attribute
     */
    void setRealAttrArray(String att, int start, int len, double[] val);

    /**
     * Get the values of the specified array of a real-valued row/column attribute
     * @param att the attribute name
     * @param start index of the first element to access
     * @param len number of elements to access
     * @return values for the specified array of the attribute
     */
    double[] getRealAttrArray(String att, int start, int len);

    /**
     * Change the values of the specified array of a integer-valued column attribute
     * @param att the attribute name
     * @param vars the variable objects
     * @param vals the new values for the specified array of the attribute
     */
    void setIntAttrVars(String att, MdoVar[] vars, int[] vals);

    /**
     * Get the values of the specified array of a integer-valued column attribute
     * @param att the attribute name
     * @param vars the variable objects
     * @return values for the specified array of the attribute
     */
    int[] getIntAttrVars(String att, MdoVar[] vars);

    /**
     * Change the values of the specified array of a real-valued column attribute
     * @param att the attribute name
     * @param vars the variable objects
     * @param vals the new values for the specified array of the attribute
     */
    void setRealAttrVars(String att, MdoVar[] vars, double[] vals);

    /**
     * Get the values of the specified array of a real-valued column attribute
     * @param att the attribute name
     * @param vars the variable objects
     * @return values for the specified array of the attribute
     */
    double[] getRealAttrVars(String att, MdoVar[] vars);

    /**
     * Change the values of the specified array of a integer-valued row attribute
     * @param att the attribute name
     * @param conss the constraint objects
     * @param vals the new values for the specified array of the attribute
     */
    void setIntAttrConss(String att, MdoCons[] conss, int[] vals);

    /**
     * Get the values of the specified array of a integer-valued row attribute
     * @param att the attribute name
     * @param conss the constraint objects
     * @return values for the specified array of the attribute
     */
    int[] getIntAttrConss(String att, MdoCons[] conss);

    /**
     * Change the values of the specified array of a real-valued row attribute
     * @param att the attribute name
     * @param conss the constraint objects
     * @param vals the new values for the specified array of the attribute
     */
    void setRealAttrConss(String att, MdoCons[] conss, double[] vals);

    /**
     * Get the values of the specified array of a real-valued row attribute
     * @param att the attribute name
     * @param conss the constraint objects
     * @return values for the specified array of the attribute
     */
    double[] getRealAttrConss(String att, MdoCons[] conss);

    /**
     * Release native memory allocated of matrix
     */
    void free();
}
