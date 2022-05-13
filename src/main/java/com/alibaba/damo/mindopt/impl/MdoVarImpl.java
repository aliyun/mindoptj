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

import com.alibaba.damo.mindopt.MdoCons;
import com.alibaba.damo.mindopt.MdoProblem;
import com.alibaba.damo.mindopt.MdoVar;

import java.util.Arrays;

public class MdoVarImpl implements MdoVar {
    MdoProblem model;
    int index;

    public MdoVarImpl(MdoProblem model, int index) {
        this.model = model;
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public boolean sameAs(MdoVar rhs) {
        if (rhs instanceof MdoVarImpl) {
            return this.model == ((MdoVarImpl) rhs).model && this.getIndex() == rhs.getIndex();
        }
        return false;
    }

    @Override
    public void setElements(MdoCons[] conss, double[] coeffs) {
        MdoVar[] vars = new MdoVar[conss.length];
        Arrays.fill(vars, this);
        model.setElements(conss, vars, coeffs);
    }

    @Override
    public double[] getElements(MdoCons[] conss) {
        MdoVar[] vars = new MdoVar[conss.length];
        Arrays.fill(vars, this);
        return model.getElements(conss, vars);
    }

    @Override
    public void deleteElements(MdoCons[] conss) {
        MdoVar[] vars = new MdoVar[conss.length];
        Arrays.fill(vars, this);
        model.deleteElements(conss, vars);
    }

    @Override
    public boolean equals(MdoVar rhs) {
        return sameAs(rhs);
    }

    @Override
    public void setStrAttr(String att, String val) {
        model.setStrAttrIndex(att, getIndex(), val);
    }

    @Override
    public String getStrAttr(String att) {
        return model.getStrAttrIndex(att, getIndex());
    }

    @Override
    public void setIntAttr(String att, int val) {
        model.setIntAttrIndex(att, getIndex(), val);
    }

    @Override
    public int getIntAttr(String att) {
        return model.getIntAttrIndex(att, getIndex());
    }

    @Override
    public void setRealAttr(String att, double val) {
        model.setRealAttrIndex(att, getIndex(), val);
    }

    @Override
    public double getRealAttr(String att) {
        return model.getRealAttrIndex(att, getIndex());
    }

    @Override
    public int hashCode() {
        return index;
    }
}
