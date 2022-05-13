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

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;


/**
 *  Description
 *  -----------
 *
 *  Linear optimization (diet problem).
 *
 *  The goal is to select foods that satisfy daily nutritional requirements while minimizing the total cost.
 *  The constraints in this problem limit the number of calories, the volume of good consumed, and the amount of
 *  vitamins, protein, carbohydrates, calcium, and iron in the diet.
 *
 *  Note
 *  ----
 *
 *  The model below will be inputted in a row-wise order.
 *
 *  Formulation
 *  -----------
 *
 * Minimize
 * Obj:        1.840000000 Cheeseburger + 2.190000000 HamSandwich + 1.840000000 Hamburger + 1.440000000 FishSandwich +
 *             2.290000000 ChickenSandwich + 0.770000000 Fries + 1.290000000 SausageBiscuit + 0.600000000 LowfatMilk +
 *             0.720000000 OrangeJuice
 * Subject To
 * Cal:        510 Cheeseburger + 370 HamSandwich + 500 Hamburger + 370 FishSandwich +
 *             400 ChickenSandwich + 220 Fries + 345 SausageBiscuit + 110 LowfatMilk + 80 OrangeJuice >= 2000
 * Carbo:      34 Cheeseburger + 35 HamSandwich + 42 Hamburger + 38 FishSandwich + 42 ChickenSandwich +
 *             26 Fries + 27 SausageBiscuit + 12 LowfatMilk + 20 OrangeJuice <= 375
 * Carbo_low:  34 Cheeseburger + 35 HamSandwich + 42 Hamburger + 38 FishSandwich + 42 ChickenSandwich +
 *             26 Fries + 27 SausageBiscuit + 12 LowfatMilk + 20 OrangeJuice >= 350
 * Protein:    28 Cheeseburger + 24 HamSandwich + 25 Hamburger + 14 FishSandwich + 31 ChickenSandwich +
 *             3 Fries + 15 SausageBiscuit + 9 LowfatMilk + OrangeJuice >= 55
 * VitA:       15 Cheeseburger + 15 HamSandwich + 6 Hamburger + 2 FishSandwich + 8 ChickenSandwich +
 *             4 SausageBiscuit + 10 LowfatMilk + 2 OrangeJuice >= 100
 * VitC:       6 Cheeseburger + 10 HamSandwich + 2 Hamburger + 15 ChickenSandwich +
 *             15 Fries + 4 LowfatMilk + 120 OrangeJuice >= 100
 * Calc:       30 Cheeseburger + 20 HamSandwich + 25 Hamburger + 15 FishSandwich +
 *             15 ChickenSandwich + 20 SausageBiscuit + 30 LowfatMilk + 2 OrangeJuice >= 100
 * Iron:       20 Cheeseburger + 20 HamSandwich + 20 Hamburger + 10 FishSandwich +
 *             8 ChickenSandwich + 2 Fries + 15 SausageBiscuit + 2 OrangeJuice >= 100
 * Volume:     4 Cheeseburger + 7.500000000 HamSandwich + 3.500000000 Hamburger + 5 FishSandwich +
 *             7.300000000 ChickenSandwich + 2.600000000 Fries + 4.100000000 SausageBiscuit + 8 LowfatMilk + 12 OrangeJuice <= 75
 * Bounds
 * End
 */

public class MdoLoDiet2 {
    public static class Table {
        Object[][] data;

        public Table(Object[][] data) {
            this.data = data;
        }

        public String getString(int x, int y) {
            return (String) data[x][y];
        }

        public double getDouble(int x, int y) {
            return ((Number) data[x][y]).doubleValue();
        }

        public int size() {
            return data.length;
        }
    }

    static Table req = new Table(new Object[][] {
            //  requirement ,            lower bound,  upper bound
            {  "Cal"        ,                  2000, Mdo.INFINITY },
            {  "Carbo"      ,                   350,          375 },
            {  "Protein"    ,                    55, Mdo.INFINITY },
            {  "VitA"       ,                   100, Mdo.INFINITY },
            {  "VitC"       ,                   100, Mdo.INFINITY },
            {  "Calc"       ,                   100, Mdo.INFINITY },
            {  "Iron"       ,                   100, Mdo.INFINITY },
            {  "Volume"     , Mdo.NEGATIVE_INFINITY,           75 }
    });

    static Table food = new Table(new Object[][] {
            // food             , lower bound,  upper bound, cost
            { "Cheeseburger"    ,           0, Mdo.INFINITY, 1.84 },
            { "HamSandwich"     ,           0, Mdo.INFINITY, 2.19 },
            { "Hamburger"       ,           0, Mdo.INFINITY, 1.84 },
            { "FishSandwich"    ,           0, Mdo.INFINITY, 1.44 },
            { "ChickenSandwich" ,           0, Mdo.INFINITY, 2.29 },
            { "Fries"           ,           0, Mdo.INFINITY, 0.77 },
            { "SausageBiscuit"  ,           0, Mdo.INFINITY, 1.29 },
            { "LowfatMilk"      ,           0, Mdo.INFINITY, 0.60 },
            { "OrangeJuice"     ,           0, Mdo.INFINITY, 0.72 }
    });

    static Table reqValue = new Table(new Object[][] {
            // requirement,          food     , value
            { "Cal",        "Cheeseburger"    , 510 },
            { "Cal",        "HamSandwich"     , 370 },
            { "Cal",        "Hamburger"       , 500 },
            { "Cal",        "FishSandwich"    , 370 },
            { "Cal",        "ChickenSandwich" , 400 },
            { "Cal",        "Fries"           , 220 },
            { "Cal",        "SausageBiscuit"  , 345 },
            { "Cal",        "LowfatMilk"      , 110 },
            { "Cal",        "OrangeJuice"     ,  80 },

            { "Carbo",      "Cheeseburger"    ,  34 },
            { "Carbo",      "HamSandwich"     ,  35 },
            { "Carbo",      "Hamburger"       ,  42 },
            { "Carbo",      "FishSandwich"    ,  38 },
            { "Carbo",      "ChickenSandwich" ,  42 },
            { "Carbo",      "Fries"           ,  26 },
            { "Carbo",      "SausageBiscuit"  ,  27 },
            { "Carbo",      "LowfatMilk"      ,  12 },
            { "Carbo",      "OrangeJuice"     ,  20 },

            { "Protein",    "Cheeseburger"    ,  28 },
            { "Protein",    "HamSandwich"     ,  24 },
            { "Protein",    "Hamburger"       ,  25 },
            { "Protein",    "FishSandwich"    ,  14 },
            { "Protein",    "ChickenSandwich" ,  31 },
            { "Protein",    "Fries"           ,   3 },
            { "Protein",    "SausageBiscuit"  ,  15 },
            { "Protein",    "LowfatMilk"      ,   9 },
            { "Protein",    "OrangeJuice"     ,   1 },

            { "VitA",       "Cheeseburger"    ,  15 },
            { "VitA",       "HamSandwich"     ,  15 },
            { "VitA",       "Hamburger"       ,   6 },
            { "VitA",       "FishSandwich"    ,   2 },
            { "VitA",       "ChickenSandwich" ,   8 },
            { "VitA",       "Fries"           ,   0 },
            { "VitA",       "SausageBiscuit"  ,   4 },
            { "VitA",       "LowfatMilk"      ,  10 },
            { "VitA",       "OrangeJuice"     ,   2 },

            { "VitC",       "Cheeseburger"    ,   6 },
            { "VitC",       "HamSandwich"     ,  10 },
            { "VitC",       "Hamburger"       ,   2 },
            { "VitC",       "FishSandwich"    ,   0 },
            { "VitC",       "ChickenSandwich" ,  15 },
            { "VitC",       "Fries"           ,  15 },
            { "VitC",       "SausageBiscuit"  ,   0 },
            { "VitC",       "OrangeJuice"     ,   4 },
            { "VitC",       "LowfatMilk"      , 120 },

            { "Calc",       "Cheeseburger"    ,  30 },
            { "Calc",       "HamSandwich"     ,  20 },
            { "Calc",       "Hamburger"       ,  25 },
            { "Calc",       "FishSandwich"    ,  15 },
            { "Calc",       "ChickenSandwich" ,  15 },
            { "Calc",       "Fries"           ,   0 },
            { "Calc",       "SausageBiscuit"  ,  20 },
            { "Calc",       "LowfatMilk"      ,  30 },
            { "Calc",       "OrangeJuice"     ,   2 },

            { "Iron",       "Cheeseburger"    ,  20 },
            { "Iron",       "HamSandwich"     ,  20 },
            { "Iron",       "Hamburger"       ,  20 },
            { "Iron",       "FishSandwich"    ,  10 },
            { "Iron",       "ChickenSandwich" ,   8 },
            { "Iron",       "Fries"           ,   2 },
            { "Iron",       "SausageBiscuit"  ,  15 },
            { "Iron",       "LowfatMilk"      ,   0 },
            { "Iron",       "OrangeJuice"     ,   2 },

            { "Volume",     "Cheeseburger"    ,   4 },
            { "Volume",     "HamSandwich"     , 7.5 },
            { "Volume",     "Hamburger"       , 3.5 },
            { "Volume",     "FishSandwich"    ,   5 },
            { "Volume",     "ChickenSandwich" , 7.3 },
            { "Volume",     "Fries"           , 2.6 },
            { "Volume",     "SausageBiscuit"  , 4.1 },
            { "Volume",     "LowfatMilk"      ,   8 },
            { "Volume",     "OrangeJuice"     ,  12 }
    });


    public static void main(String args[]) {
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
            model.setIntAttr(Mdo.INT_ATTR_MIN_SENSE, 1);
            Map<Map.Entry<String, String>, Double> reqValueMap = new HashMap<Map.Entry<String, String>, Double>();
            Map<String, MdoVar> namedVars = new HashMap<String, MdoVar>();

            MdoVar[] vars = model.addVars(food.size(), false);
            MdoCons[] conss = model.addConss(req.size());

            // Construct data structure
            for (int i = 0; i < reqValue.size(); i++) {
                String reqName = reqValue.getString(i, 0);
                String foodName = reqValue.getString(i, 1);
                double value = reqValue.getDouble(i, 2);
                reqValueMap.put(new AbstractMap.SimpleEntry<String, String>(reqName, foodName), value);
            }

            // Edit variables
            for (int i = 0; i < food.size(); i++) {
                String foodName = food.getString(i, 0);
                vars[i].setRealAttr(Mdo.REAL_ATTR_LB, food.getDouble(i, 1));
                vars[i].setRealAttr(Mdo.REAL_ATTR_UB, food.getDouble(i, 2));
                vars[i].setRealAttr(Mdo.REAL_ATTR_OBJ, food.getDouble(i, 3));
                vars[i].setStrAttr(Mdo.STR_ATTR_COL_NAME, foodName);
                namedVars.put(foodName, vars[i]);
            }

            // Edit constraints
            for (int i = 0; i < req.size(); i++) {
                String reqName = req.getString(i, 0);
                MdoVar[] cols = new MdoVar[food.size()];
                double[] coeffs = new double[food.size()];

                for (int j = 0; j < food.size(); j++) {
                    cols[j] = vars[j];
                    String foodName = food.getString(j, 0);
                    coeffs[j] = reqValueMap.get(new AbstractMap.SimpleEntry<String, String>(reqName, foodName));
                }
                double lhs = req.getDouble(i, 1);
                double rhs = req.getDouble(i, 2);

                conss[i].setElements(cols, coeffs);
                conss[i].setRealAttr(Mdo.REAL_ATTR_LHS, lhs);
                conss[i].setRealAttr(Mdo.REAL_ATTR_RHS, rhs);
                conss[i].setStrAttr(Mdo.STR_ATTR_ROW_NAME, reqName);
            }

            // Solve it
            model.solveProb();
            model.displayResult();

            MdoStatus status = model.getStatus();
            if (status == MdoStatus.MDO_OPTIMAL) {
                String message = "Optimizer terminated with an OPTIMAL status (%d).";
                message = String.format(message, status.getCode());
                System.out.println(message);

                double cost = model.getRealAttr("PrimalObjVal");
                cost = Math.round(cost * 100.0) / 100.0;
                message = String.format("Daily cost           : %.2f", cost);
                System.out.println(message);

                for (Map.Entry<String, MdoVar> entry : namedVars.entrySet()) {
                    double value = entry.getValue().getRealAttr("PrimalSoln");
                    value = Math.round(value * 100.0) / 100.0;
                    if (value > 0.01) {
                        message = String.format(" - %17s : %.2f", entry.getKey(), value);
                        System.out.println(message);
                    }
                }
            } else {
                String message = "Optimizer terminated with a(n) %s status (%d).";
                message = String.format(message, status.name(), status.getCode());
                System.out.println(message);
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

            // Free environment is necessary if you creatModel throng environment.
            env.free();
        }
    }
}
