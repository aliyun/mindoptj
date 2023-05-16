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
 * Model status.
 */
public enum MdoStatus{
    /** Model status is not available.  */
    MDO_UNKNOWN(0),
    /** Model was proven to be primal/dual feasible, and an optimal solution is available.  */
    MDO_OPTIMAL(1),
    /** Model was proven to be primal infeasible.  */
    MDO_INFEASIBLE(2),
    /** Model was proven to be primal unbounded.  */
    MDO_UNBOUNDED(3),
    /** Model was proven to be either primal infeasible or primal unbounded.  */
    MDO_INF_OR_UBD(4);
    /** A sub-optimal solution is availabel.  */
    MDO_SUB_OPTIMAL(5);
    int code = 0;
    MdoStatus(int code) { this.code = code; }
    public int getCode() { return code; }
    public static MdoStatus fromCode(int code) {
        for (MdoStatus value : MdoStatus.values()) {
            if (value.code == code) return value;
        }
        throw new RuntimeException("Bad MdoStatus code: " + code);
    }
}
