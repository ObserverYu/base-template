package org.chen.config.leaf;


import org.chen.util.leaf.IDGen;
import org.chen.util.leaf.common.Result;
import org.chen.util.leaf.common.ZeroIDGen;
import org.chen.util.leaf.snowflake.SnowflakeIDGenImpl;
import org.chen.util.leaf.snowflake.exception.InitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.chen.util.leaf.IDGen;

@Service("SnowflakeService")
public class SnowflakeService {
    private Logger logger = LoggerFactory.getLogger(SnowflakeService.class);

    private IDGen idGen;

    @Autowired
    public SnowflakeService(LeafProperty property) throws InitException {
        LeafProperty.LeafSnowflakeProperty snowflake = property.getSnowflake();
        boolean flag = snowflake.isEnable();
        if (flag) {
            idGen = new SnowflakeIDGenImpl(property);
            if(idGen.init()) {
                logger.info("Snowflake Service Init Successfully");
            } else {
                throw new InitException("Snowflake Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            logger.info("Zero ID Gen Service Init Successfully");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }
}
