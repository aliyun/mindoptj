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
 * Refers to a constraint in model
 */
public interface MdoCons extends MdoAttrAccessor {
    /**
     * Get the current index of this constraint in matrix model
     * @return index of constraint in model
     */
    int getIndex();

    /**
     * Check weather two constraint objects refer to the same constraint
     * @param rhs another constraint object
     * @return True if two constraint objects refer to the same constraint, false otherwise
     * @see #equals(MdoCons)
     */
    boolean sameAs(MdoCons rhs);

    /**
     * Set the constraint coefficients to new values
     * @param vars the associated variable objects
     * @param coeffs the new values
     */
    void setElements(MdoVar[] vars, double[] coeffs);

    /**
     * Get the constraint coefficients
     * @param vars the associated variable objects
     * @return constraint coefficients for {@code vars}
     */
    double[] getElements(MdoVar[] vars);

    /**
     * Reset the constraint coefficients of specified variables to zero
     * @param vars the associated variable objects
     */
    void deleteElements(MdoVar[] vars);

    /**
     * Check weather two constraint objects refer to the same constraint
     * @param rhs another constraint object
     * @return True if two constraint objects refer to the same constraint, false otherwise
     * @see #sameAs(MdoCons)
     */
    boolean equals(MdoCons rhs);

    /**
     * Override hash code of {@code Object}
     * @return hash code
     */
    int hashCode();
}
