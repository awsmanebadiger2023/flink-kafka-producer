package com.vz.flinkdatastream.datagenerator;

import com.vz.flinkdatastream.models.StreamData;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class StreamDataGenerator implements SourceFunction<StreamData> {
    @Override
    public void run(SourceContext<StreamData> sourceContext) throws Exception {
        Long id = Long.valueOf(1);
        Date currentDate = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
        while(true){
            StreamData rider = new StreamData("email_"+id, df.format(currentDate), "DEP_"+id, "LOCA_"+id, id , "A");
            ++id;
            sourceContext.collect(rider);
        }

    }

    @Override
    public void cancel() {

    }
}
