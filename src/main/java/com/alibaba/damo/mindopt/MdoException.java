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
 *  MdoException An error exception class.
 */
public class MdoException extends RuntimeException {
    private int code;

    /**
     * Constructor
     * @param code error code
     */
    public MdoException(int code) {
        this.code = code;
    }

    /**
     * Get the error code
     * @return error code
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Get the error info corresponding to the error code
     * @return error info
     */
    public MdoResult getResult() {
        return MdoResult.fromCode(code);
    }

    /**
     * Get the error code and error info
     * @return error code and error info
     */
    @Override
    public String getMessage() {
        return "Error: " + getCode() + ": " + getResult().name();
    }

}
