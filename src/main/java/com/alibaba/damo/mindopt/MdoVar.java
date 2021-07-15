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
 * Refers to a variable in model
 */
public interface MdoVar extends MdoAttrAccessor {
    /**
     * Get the current index of this variable in matrix model
     * @return index of variable in model
     */
    int getIndex();

    /**
     * Check weather two variable objects refer to the same variable
     * @param rhs another variable object
     * @return True if two variable objects refer to the same variable, false otherwise
     * @see #equals(MdoVar)
     */
    boolean sameAs(MdoVar rhs);

    /**
     * Set the constraint matrix elements associated with this variable to new values
     * @param conss the constraints object
     * @param coeffs the new values
     */
    void setElements(MdoCons[] conss, double[] coeffs);

    /**
     * Get the constraint matrix elements associated with this variable
     * @param conss the constraints object
     * @return constraint coefficients of this variable for {@code conss}
     */
    double[] getElements(MdoCons[] conss);

    /**
     * Reset the constraint matrix elements associated with this variable to zero
     * @param conss the constraints object
     */
    void deleteElements(MdoCons[] conss);

    /**
     * Check weather two variable objects refer to the same variable
     * @param rhs another variable object
     * @return True if two variable objects refer to the same variable, false otherwise
     * @see #sameAs(MdoVar)
     */
    boolean equals(MdoVar rhs);

    /**
     * Override hash code of {@code Object}
     * @return hash code
     */
    int hashCode();
}
