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

public interface MdoAttrAccessor {
    /**
     * Set corresponding int attribute value
     * @param att the attribute name
     * @param val the attribute value
     */
    void setIntAttr(String att, int val);

    /**
     * Get corresponding int attribute value
     * @param att the attribute name
     * @return the attribute value
     */
    int getIntAttr(String att);

    /**
     * Set corresponding real attribute value
     * @param att the attribute name
     * @param val the attribute value
     */
    void setRealAttr(String att, double val);

    /**
     * Get corresponding real attribute value
     * @param att the attribute name
     * @return the attribute value
     */
    double getRealAttr(String att);

    /**
     * Set corresponding string attribute value
     * @param att the attribute name
     * @param val the attribute value
     */
    void setStrAttr(String att, String val);

    /**
     * Get corresponding string attribute value
     * @param att the attribute name
     * @return the attribute value
     */
    String getStrAttr(String att);
}
