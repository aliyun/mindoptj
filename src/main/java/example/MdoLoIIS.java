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
 *  Linear optimization.
 *   - Compute IIS of an infeasible problem.
 *
 *  Formulation
 *  -----------
 *
 *  Minimize
 *  Obj:
 *  Subject To
 *  c0:  -0.500000000 x0 + x1 >= 0.500000000
 *  c1:  2 x0 - x1 >= 3
 *  c2:  3 x0 + x1 <= 6
 *  c3:  3 x3 - x4 <= 2 <- conflicts with variable bounds listed below!
 *  c4:  x0 + x4 <= 10
 *  c5:  x0 + 2 x1 + x3 <= 14
 *  c6:  x1 + x3 >= 1
 *  Bounds
 *   5 <= x3
 *   0 <= x4 <= 2
 *  End
 */

public class MdoLoIIS {
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
            // Turn-off the presolver so that solver won't terminate with an MDO_INF_OR_UBD status.
            model.setIntParam(Mdo.INT_PARAM_PRESOLVE, 0);
            // Use dual-Simplex method.
            model.setIntParam(Mdo.INT_PARAM_METHOD, 1);

            // Change to minimization problem
            model.setMinObjSense();

            // Create variables
            MdoVar[] x = new MdoVar[5];
            x[0] = model.addVar(0.0, Mdo.INFINITY, 0.0, false, "x0");
            x[1] = model.addVar(0.0, Mdo.INFINITY, 0.0, false, "x1");
            x[2] = model.addVar(0.0, Mdo.INFINITY, 0.0, false, "x2");
            x[3] = model.addVar(5.0, Mdo.INFINITY, 0.0, false, "x3");
            x[4] = model.addVar(0.0, 2.0, 0.0, false, "x4");

            // Add constraints
            MdoCons[] conss = new MdoCons[7];
            MdoExprLinear c0 = new MdoExprLinear();
            c0.addTerm(-0.5, x[0]);
            c0.addTerm(1.0, x[1]);
            conss[0] = model.addCons(c0, Mdo.GREATER_EQUAL, 0.5, "c0");

            MdoExprLinear c1 = new MdoExprLinear();
            c1.addTerm(2.0, x[0]);
            c1.addTerm(-1.0, x[1]);
            conss[1] = model.addCons(c1, Mdo.GREATER_EQUAL, 3.0, "c1");

            MdoExprLinear c2 = new MdoExprLinear();
            c2.addTerm(3.0, x[0]);
            c2.addTerm(1.0, x[1]);
            conss[2] = model.addCons(c2, Mdo.LESS_EQUAL, 6.0, "c2");

            MdoExprLinear c3 = new MdoExprLinear();
            c3.addTerm(3.0, x[3]);
            c3.addTerm(-1.0, x[4]);
            conss[3] = model.addCons(c3, Mdo.LESS_EQUAL, 2.0, "c3");

            MdoExprLinear c4 = new MdoExprLinear();
            c4.addTerm(1.0, x[0]);
            c4.addTerm(1.0, x[4]);
            conss[4] = model.addCons(c4, Mdo.LESS_EQUAL, 10.0, "c4");

            MdoExprLinear c5 = new MdoExprLinear();
            c5.addTerm(1.0, x[0]);
            c5.addTerm(2.0, x[1]);
            c5.addTerm(1.0, x[3]);
            conss[5] = model.addCons(c5, Mdo.LESS_EQUAL, 14.0, "c5");

            MdoExprLinear c6 = new MdoExprLinear();
            c6.addTerm(1.0, x[1]);
            c6.addTerm(1.0, x[3]);
            conss[6] = model.addCons(c6, Mdo.GREATER_EQUAL, 1.0, "c6");

            // Solve model
            model.solveProb();
            model.displayResult();

            switch (model.getStatus()) {
                case MDO_UNKNOWN:
                    System.out.println("Optimizer terminated with an UNKNOWN status.");
                    break;
                case MDO_OPTIMAL:
                    System.out.println("Optimizer terminated with an OPTIMAL status.");
                    break;
                case MDO_INFEASIBLE:
                    System.out.println("Optimizer terminated with an INFEASIBLE status.");
                    System.out.println("Start computing IIS.");
                    model.computeIIS();
                    System.out.println("Writing IIS into file.");
                    model.writeProb("test1.ilp");
                    System.out.println("Populating all bounds participate in the computed IIS.");
                    for (MdoCons c : conss) {
                        if (c.getIntAttr(Mdo.INT_ATTR_ROW_IIS) == 2) {
                            System.out.printf("The upper bound of inequality constraint [%s] participates in the IIS.\n", c.getStrAttr(Mdo.STR_ATTR_ROW_NAME));
                        } else if (c.getIntAttr(Mdo.INT_ATTR_ROW_IIS) == 3) {
                            System.out.printf("The lower bound of inequality constraint [%s] participates in the IIS.\n", c.getStrAttr(Mdo.STR_ATTR_ROW_NAME));
                        } else if (c.getIntAttr(Mdo.INT_ATTR_ROW_IIS) == 5) {
                            System.out.printf("[%s] is an equality constraint, and both its lower bound and upper bound participate in the IIS.\n", c.getStrAttr(Mdo.STR_ATTR_ROW_NAME));
                        }
                    }
                    for (MdoVar v : x) {
                        if (v.getIntAttr(Mdo.INT_ATTR_COL_IIS) == 2) {
                            System.out.printf("The upper bound of variable [%s] participates in the IIS.\n", v.getStrAttr(Mdo.STR_ATTR_COL_NAME));
                        } else if (v.getIntAttr(Mdo.INT_ATTR_COL_IIS) == 3) {
                            System.out.printf("The lower bound of variable [%s] participates in the IIS.\n", v.getStrAttr(Mdo.STR_ATTR_COL_NAME));
                        } else if (v.getIntAttr(Mdo.INT_ATTR_COL_IIS) == 5) {
                            System.out.printf("[%s] is a fixed variable, and both its lower bound and upper bound participate in the IIS.\n", v.getStrAttr(Mdo.STR_ATTR_COL_NAME));
                        }
                    }
                    break;
                case MDO_UNBOUNDED:
                    System.out.println("Optimizer terminated with an UNBOUNDED status.");
                    break;
                case MDO_INF_OR_UBD:
                    System.out.println("Optimizer terminated with an INFEASIBLE or UNBOUNDED status.");
                    break;
            }
        } catch (MdoException e) {
            System.out.println("Received Mindopt exception.");
            System.out.println(" - Code          : " + e.getCode());
            System.out.println(" - Reason        : " + e.getMessage());
        } finally {
            // Optionally, you can free native memory in time manually
            // Or it will be freed automatically after a system GC
            // But native memory usage is GC-invisible, GC may not be triggered
            model.free();

            // Free environment is necessary if you create model through `env.createModel`.
            env.free();
        }
    }
}
