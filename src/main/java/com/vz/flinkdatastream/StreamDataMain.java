package com.vz.flinkdatastream;


import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamDataMain {
     static String TOPIC_IN = "adapt-inbound";
     static String BOOTSTRAP_SERVER = "adapt-db-server:9092,adapt-db-server:9093,adapt-db-server:9094";
    static Logger logger = LoggerFactory.getLogger(StreamDataMain.class);
    public static void main(String[] args) {
        try {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.enableCheckpointing(1000);
            FlinkProducer producer = new FlinkProducer();
            producer.start(env,TOPIC_IN,BOOTSTRAP_SERVER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}