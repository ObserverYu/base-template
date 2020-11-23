package org.chen.util.leaf.common;


import org.chen.util.leaf.IDGen;
import org.chen.util.leaf.IDGen;

public class ZeroIDGen implements IDGen {
    @Override
    public Result get(String key) {
        return new Result(0, Status.SUCCESS);
    }

    @Override
    public boolean init() {
        return true;
    }
}
