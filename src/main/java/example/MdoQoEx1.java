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

package example;

import com.alibaba.damo.mindopt.*;

/**
 *  Description
 *  -----------
 *
 *  Quadratic optimization (row-wise input).
 *
 *  Formulation
 *  -----------
 *
 *  Minimize
 *    obj: 1 x0 + 1 x1 + 1 x2 + 1 x3
 *         + 1/2 [ x0^2 + x1^2 + x2^2 + x3^2 + x0 x1]
 *  Subject To
 *   c0 : 1 x0 + 1 x1 + 2 x2 + 3 x3 >= 1
 *   c1 : 1 x0 - 1 x2 + 6 x3 = 1
 *  Bounds
 *    0 <= x0 <= 10
 *    0 <= x1
 *    0 <= x2
 *    0 <= x3
 *  End
 */

public class MdoQoEx1 {
    public static void main(String[] args) {
        String libraryPath = System.getenv("MDO_NATIVE_LIBRARY");
        if (libraryPath == null) {
            System.err.println("Env variable MDO_NATIVE_LIBRARY needs to be set");
            System.exit(1);
        }

        // Load native library
        Mdo.load(libraryPath);

        // Create mindopt environment and create mindopt models through mindopt environment.
        // The previous interface for creating models `MdoModel()` is about to be deprecated.
        MdoEnv env = new MdoEnv();
        MdoModel model = env.createModel();

        try {
            // Change to minimization problem
            model.setMinObjSense();

            // Create variables
            MdoVar[] x = new MdoVar[4];
            x[0] = model.addVar(0.0, 10.0, 1.0, false, "x0");
            x[1] = model.addVar(0.0, Mdo.INFINITY, 1.0, false, "x1");
            x[2] = model.addVar(0.0, Mdo.INFINITY, 1.0, false, "x2");
            x[3] = model.addVar(0.0, Mdo.INFINITY, 1.0, false, "x3");

            // Add constraints
            MdoExprLinear c0 = new MdoExprLinear();
            c0.addTerm(1.0, x[0]);
            c0.addTerm(1.0, x[1]);
            c0.addTerm(2.0, x[2]);
            c0.addTerm(3.0, x[3]);
            model.addCons(c0, Mdo.GREATER_EQUAL, 1.0, "c0");

            MdoExprLinear c1 = new MdoExprLinear();
            c1.addTerm(1.0, x[0]);
            c1.addTerm(-1.0, x[2]);
            c1.addTerm(6.0, x[3]);
            model.addCons(c1, Mdo.EQUAL, 1.0, "c1");

            // Add quadratic objective matrix Q
            MdoExprQuad obj = new MdoExprQuad();
            obj.addTerm(1.0, x[0], x[0]);
            obj.addTerm(0.5, x[1], x[0]);
            obj.addTerm(1.0, x[1], x[1]);
            obj.addTerm(1.0, x[2], x[2]);
            obj.addTerm(1.0, x[3], x[3]);
            model.setQuadraticElements(obj.getVars1(), obj.getVars2(), obj.getCoeffs());

            // Solve model
            model.solveProb();
            model.displayResult();

        } catch (MdoException e) {
            System.out.println("Received Mindopt exception.");
            System.out.println(" - Code          : " + e.getCode());
            System.out.println(" - Reason        : " + e.getMessage());
        } finally {
            // Optionally, you can free native memory in time manually
            // Or it will be freed automatically after a system GC
            // But native memory usage is GC-invisible, GC may not be triggered
            model.free();

            // Free environment is necessary if you creatModel throng environment.
            env.free();
        }
    }
}
