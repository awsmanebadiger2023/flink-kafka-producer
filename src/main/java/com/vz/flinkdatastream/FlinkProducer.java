package com.vz.flinkdatastream;

import com.vz.flinkdatastream.datagenerator.StreamDataGenerator;
import com.vz.flinkdatastream.models.StreamData;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.json.JsonSerializationSchema;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlinkProducer {
    static Logger logger = LoggerFactory.getLogger(FlinkProducer.class);
    public void start(StreamExecutionEnvironment env, String topicIn, String bootstrapServer) throws Exception {
        env.enableCheckpointing(100, CheckpointingMode.AT_LEAST_ONCE);
        JsonSerializationSchema<StreamData> jsonFormat=new JsonSerializationSchema<>();
        JsonSerializationSchema<StreamData> serializer =
                new JsonSerializationSchema<>();
        DataStream<StreamData> dataStream = env.addSource(new StreamDataGenerator());
        KafkaSink<StreamData> sink =  KafkaSink.<StreamData>builder()
                .setBootstrapServers(bootstrapServer)
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(topicIn)
                        .setValueSerializationSchema(jsonFormat)
                        .build()
                ).setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();
        dataStream.print();
        dataStream.sinkTo(sink);
        env.execute("FLINK PRODUCER");
    }

}
