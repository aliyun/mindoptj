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
 *  Semidefinite optimization (row-wise input).
 *
 *  Formulation
 *  -----------
 *
 *  Maximize
 *  obj: tr(C X)
 *
 *  Subject To
 *    c0 : tr(A X) = 1
 *  Matrix
 *    C = [ -3  0  1 ]  A = [ 3 0 1 ]
 *        [  0 -2  0 ]      [ 0 4 0 ]
 *        [  1  0 -3 ]      [ 1 0 5 ]
 *  End
 */

public class MdoSdoEx1 {
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

        String mat_name = "X";
        int    num_mats = 1;
        int    dim_mat = 3; /* Dimension of the matrix variables. */

        int    C_size = 4;
        int[] C_row_indices =  {  0,   0,    1,    2   }; /* Row index of a matrix variable. */
        int[] C_col_indices =  {  0,   2,    1,    2   }; /* Column index of a matrix variable. */
        double[] C_values =  { -3.0, 1.0, -2.0, -3.0 }; /* Values of a matrix variable. */

        int    A_size = 4;
        int    A_mat_idx = 1;
        int[] A_row_indices_in_cone = { 0, 2, 1, 2 };    /* Row index in a matrix variable. */
        int[] A_col_indices_in_cone = { 0, 0, 1, 2 };    /* Column index in a matrix variable. */
        double[] A_values_in_cone = { 3.0, 1.0, 4.0, 5.0 }; /* Values of a matrix variable. */

        try {
            // Change to minimization problem
            model.setMaxObjSense();

            // Add matrix variable
            model.addSymMat(dim_mat, mat_name);

            // Input objective coefficients
            model.replaceSymMatObjs(0, C_size, C_row_indices, C_col_indices, C_values);

            // Input first constraint
            model.addRange(new MdoExprLinear(), 1.0, 1.0, "c0");
            model.replaceSymMatElements(
                    0, 0, A_size, A_row_indices_in_cone, A_col_indices_in_cone, A_values_in_cone);

            // Solve model
            model.solveProb();

            switch (model.getStatus()) {
                case MDO_UNKNOWN:
                    System.out.println("Optimizer terminated with an UNKNOWN status.");
                    break;
                case MDO_OPTIMAL: {
                    double cost = model.getRealAttr(Mdo.REAL_ATTR_PRIMAL_OBJ_VAL);
                    cost = Math.round(cost * 100.0) / 100.0;
                    System.out.printf(" - Primal objective : %.2f\n", cost) ;
                    double[] soln = model.getRealAttrSymMat(Mdo.REAL_ATTR_SYM_MAT_PRIMAL_SOLN, 0, dim_mat * dim_mat, null, null);
                    System.out.println("X = ");
                    for (int i = 0; i < dim_mat; ++i)
                    {
                        System.out.print("  (");
                        for (int j = 0; j < dim_mat; ++j)
                        {
                            System.out.printf(" %e", soln[i * dim_mat + j]);
                        }
                        System.out.println("  )");
                    }
                    break;
                }
                case MDO_INFEASIBLE:
                    System.out.println("Optimizer terminated with an INFEASIBLE status.");
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
