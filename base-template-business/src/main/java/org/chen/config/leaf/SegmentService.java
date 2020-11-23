//package org.chen.config.leaf;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.chen.util.leaf.IDGen;
//import org.chen.util.leaf.common.Result;
//import org.chen.util.leaf.common.ZeroIDGen;
//import org.chen.util.leaf.segment.SegmentIDGenImpl;
//import org.chen.util.leaf.segment.dao.IDAllocDao;
//import org.chen.util.leaf.segment.dao.impl.IDAllocDaoImpl;
//import org.chen.util.leaf.snowflake.exception.InitException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.sql.SQLException;
//
//@Service("SegmentService")
//public class SegmentService {
//    private Logger logger = LoggerFactory.getLogger(SegmentService.class);
//
//    private IDGen idGen;
//    private DruidDataSource dataSource;
//
//    public SegmentService(LeafProperty property) throws SQLException, InitException {
//        boolean flag = property.getSegment().isEnable();
//        if (flag) {
//            LeafProperty.LeafSegmentProperty segment = property.getSegment();
//            // Config dataSource
//            dataSource = new DruidDataSource();
//            dataSource.setUrl(segment.getJdbcUrl());
//            dataSource.setUsername(segment.getJdbcPassword());
//            dataSource.setPassword(segment.getJdbcPassword());
//            dataSource.init();
//
//            // Config Dao
//            IDAllocDao dao = new IDAllocDaoImpl(dataSource);
//
//            // Config ID Gen
//            idGen = new SegmentIDGenImpl();
//            ((SegmentIDGenImpl) idGen).setDao(dao);
//            if (idGen.init()) {
//                logger.info("Segment Service Init Successfully");
//            } else {
//                throw new InitException("Segment Service Init Fail");
//            }
//        } else {
//            idGen = new ZeroIDGen();
//            logger.info("Zero ID Gen Service Init Successfully");
//        }
//    }
//
//    public Result getId(String key) {
//        return idGen.get(key);
//    }
//
//    public SegmentIDGenImpl getIdGen() {
//        if (idGen instanceof SegmentIDGenImpl) {
//            return (SegmentIDGenImpl) idGen;
//        }
//        return null;
//    }
//}
