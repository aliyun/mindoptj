MindOpt Java SDK
================
MindOpt (Machine INtelligence of Damo OPTimizer), a high performance mathematical programming optimizer, developed by Decision Intelligence Lab of Damo Academy.

Contact: solver.damo@list.alibaba-inc.com


Example
=======
Illustration of a simple linear programming problem:

```java
package example;

import com.alibaba.damo.mindopt.Mdo;
import com.alibaba.damo.mindopt.MdoExprLinear;
import com.alibaba.damo.mindopt.MdoModel;
import com.alibaba.damo.mindopt.MdoVar;

public class Example {
    public static void main(String args[]) {
        // Load library at very beginning
        Mdo.load("${your_mindopt_dynamic_library_path}");

        // Instantiate a model
        MdoModel model = new MdoModel();

        // Add continuous variables: x {x >= 0}, y {y >= 0}, with objective coefficients: 1, 2
        MdoVar x = model.addVar(0, Mdo.INFINITY, 1, false, "x");
        MdoVar y = model.addVar(0, Mdo.INFINITY, 2, false, "y");

        // Add constraint c: x + y <= 1
        MdoExprLinear exp = new MdoExprLinear();
        exp.addTerm(1, x);
        exp.addTerm(1, y);
        model.addCons(exp, Mdo.LESS_EQUAL, 1, "c");

        // Set maximization sense
        model.setMaxObjSense();
        model.solveProb();

        // Output solver status: OPTIMAL
        System.out.println(model.explainStatus(model.getStatus()));

        // Output model objective value: 2.0
        System.out.println(model.getRealAttr(Mdo.REAL_ATTR_PRIMAL_OBJ_VAL));
    }
}

```
