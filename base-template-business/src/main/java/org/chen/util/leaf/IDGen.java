package org.chen.util.leaf;

import org.chen.util.leaf.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
