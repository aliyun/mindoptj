package com.alibaba.damo.mindopt.impl;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * The mindopt environment native interface, it is strongly recommended to use MdoEnv instead
 */
public class MdoNativeEnv {
    static MdoNativeAPI inst() {
        if (MdoNativeAPI.InstanceHolder.get() == null) {
            throw new RuntimeException("Mdo.load() needs to be called at very beginning.");
        }
        return MdoNativeAPI.InstanceHolder.get();
    }

    private Pointer getEnv() {
        return env.getValue();
    }
    private PointerByReference env = new PointerByReference();

    /**
     * Create a mindopt environment with default parameter settings.
     * @return MdoResult code
     */
    public int createEnv(
    ) {
        return inst().Mdo_createEnv(
                env
        );
    }

    /**
     * Create a mindopt environment with default parameter settings.
     * @return MdoResult code
     */
    public int createMdlWithEnv(
            MdoNativeModel model
    ) {
        return inst().Mdo_createMdlWithEnv(
                model.model,
                env.getValue()
        );
    }

    /**
     * Destroy an env and all associated data.
     */
    public void freeEnv(
    ) {
        inst().Mdo_freeEnv(
                env
        );
    }
}
