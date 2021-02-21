package com.mtons.mblog;

import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * SprintBootApplication
 */
@Slf4j
@SpringBootApplication
@EnableCaching
public class BootApplication {

    public static void main(String[] args) throws TasteException {
        ApplicationContext context = SpringApplication.run(BootApplication.class, args);
        String serverPort = context.getEnvironment().getProperty("server.port");
        log.info("mblog started at http://localhost:" + serverPort);

//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.username("root");
//        dataSourceBuilder.password("123");
//        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
//        dataSourceBuilder.url("jdbc:mysql://localhost/db_mblog?useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
//        DataModel dataModel = new MySQLJDBCDataModel(dataSourceBuilder.build(), "mto_ratings", "user_id", "post_id", "rating", "timestamp");
//        /**
//         * UserSimilarity 实现给出两个用户之间的相似度
//         * 可以从多种可能度量或计算机中选一种作为依赖
//         */
//        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
//        // UserNeighborhood 实现   明确与给定用户最相似的一组用户
//        UserNeighborhood neighborhood =
//                new NearestNUserNeighborhood(2, similarity, dataModel);
//        /**
//         *
//         * 生成推荐引擎
//         * Recommender 合并所有的组件为用户推荐物品
//         *
//         */
//        Recommender recommender = new GenericUserBasedRecommender(
//                dataModel, neighborhood, similarity);
//        // 为用户1 推荐2件  物品
//        List<RecommendedItem> recommendations =
//                recommender.recommend(1, 1);
//
//        for (RecommendedItem recommendation : recommendations) {
//            System.out.println("======="+recommendation.getItemID());
//        }
//        GenericItemBasedRecommender genericItemBasedRecommender = new GenericItemBasedRecommender()
    }

}