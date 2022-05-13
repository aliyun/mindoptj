package com.alibaba.damo.mindopt;

import com.alibaba.damo.mindopt.impl.MdoNativeEnv;

import java.lang.reflect.Constructor;

public class MdoEnv {
    MdoNativeEnv env = new MdoNativeEnv();
    boolean freed = false;

    /**
     * Mindopt environment constructor
     */
    public MdoEnv() {
        env.createEnv();
    }

    /**
     * Create model through environment object(recommended)
     * @return MdoModel object
     */
    public MdoModel createModel() {
        Constructor<MdoModel> constructor = null;
        try {
            constructor = MdoModel.class.getDeclaredConstructor(MdoEnv.class);
            constructor.setAccessible(true);
            return constructor.newInstance(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mindopt environment releaser
     */
    public void free() {
        if (!freed) {
            freed = true;
            env.freeEnv();
        }
    }

    protected void finalize() throws MdoException {
        free();
    }
}
