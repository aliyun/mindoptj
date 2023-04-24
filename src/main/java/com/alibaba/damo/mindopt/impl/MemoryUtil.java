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

package com.alibaba.damo.mindopt.impl;

import com.alibaba.damo.mindopt.Mdo;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.StringArray;

public class MemoryUtil {

    public static final int INT_SIZE = Integer.SIZE / Byte.SIZE;
    public static final int DOUBLE_SIZE = Double.SIZE / Byte.SIZE;
    public static final Pointer EMPTY_MEMORY = new Memory(1);

    public static Pointer doubleArray(int size) {
        if (size == 0) {
            return EMPTY_MEMORY;
        }
        return new Memory(DOUBLE_SIZE * size);
    }

    public static Pointer doubleArray(double[] doubleArr) {
        if (doubleArr == null) {
            return Pointer.NULL;
        }
        if (doubleArr.length == 0) {
            return EMPTY_MEMORY;
        }
        Pointer result = doubleArray(doubleArr.length);
        for (int i = 0; i < doubleArr.length; i++) {
            setDouble(result, i, doubleArr[i]);
        }
        return result;
    }

    public static Pointer intArray(int size) {
        if (size == 0) {
            return EMPTY_MEMORY;
        }
        return new Memory(INT_SIZE * size);
    }

    public static Pointer intArray(int[] intArr) {
        if (intArr == null) {
            return Pointer.NULL;
        }
        if (intArr.length == 0) {
            return EMPTY_MEMORY;
        }
        Pointer result = intArray(intArr.length);
        for (int i = 0; i < intArr.length; i++) {
            setInt(result, i, intArr[i]);
        }
        return result;
    }

    public static Pointer pointerArray(int size) {
        if (size == 0) {
            return EMPTY_MEMORY;
        }
        return new Memory(Native.POINTER_SIZE * size);
    }

    public static Pointer charArray(String str) {
        if (str == null) {
            return EMPTY_MEMORY;
        }
        Memory m = new Memory(str.length() + 1);
        m.setString(0, str);
        m.setByte(str.length(), (byte) 0);
        return m;
    }

    public static Pointer stringArray(String[] stringArr) {
        if (stringArr == null) {
            return Pointer.NULL;
        }
        if (stringArr.length == 0) {
            return EMPTY_MEMORY;
        }
        return new StringArray(stringArr);
    }

    public static Pointer nativeString() {
        return new Memory(Mdo.MAX_NATIVE_STR_LEN);
    }

    public static void setDouble(Pointer memory, int index, double d) {
        memory.setDouble(index * DOUBLE_SIZE, d);
    }

    public static void setInt(Pointer memory, int index, int n) {
        memory.setInt(index * INT_SIZE, n);
    }

    public static double getDouble(Pointer memory, int index) {
        return memory.getDouble(index * DOUBLE_SIZE);
    }

    public static int getInt(Pointer memory, int index) {
        return memory.getInt(index * INT_SIZE);
    }

    public static Pointer intByReference() {
        return intArray(1);
    }

    public static Pointer doubleByReference() {
        return doubleArray(1);
    }

    public static void setPointer(Pointer memory, int index, Pointer p) {
        memory.setPointer(index * Native.POINTER_SIZE, p);
    }
}
