package org.example.CollectorA.InstagramSearchEngine;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.apache.hadoop.hbase.util.Bytes;
import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.ToString;

import org.example.CollectorA.Database.DataModel;

/**
 * This class describes the model of user data collected and recorded in the DB
 * @author UsoltsevI
 */
@Component
@Data
@ToString
public class PinterestUserDataModel implements DataModel {
    private String id;
    private boolean isPrivate;
    private long subscriptionsAmount;
    private long followersAmount;
    private long averageLikesAmount;
    private long averageCommentsAmount;
    private long averageRepostAmount;
    private long averagePostSize;
    private long averagePostingPeriod;

    @Override
    public byte[] getId() {
        return Bytes.toBytes(id);
    }

    @Override
    public HashMap<byte[],byte[]> getFieldsAsBytes() {
        HashMap<byte[],byte[]> map = new HashMap<>();

        map.put(Bytes.toBytes("isPrivate"),            Bytes.toBytes(isPrivate)            );
        map.put(Bytes.toBytes("subscriptionsAmount"),  Bytes.toBytes(subscriptionsAmount)  );
        map.put(Bytes.toBytes("followersAmount"),      Bytes.toBytes(followersAmount)      );
        map.put(Bytes.toBytes("averageLikesAmount"),   Bytes.toBytes(averageLikesAmount)   );
        map.put(Bytes.toBytes("averageCommentsAmount"),Bytes.toBytes(averageCommentsAmount));
        map.put(Bytes.toBytes("averageRepostAmount"),  Bytes.toBytes(averageRepostAmount)  );
        map.put(Bytes.toBytes("averagePostSize"),      Bytes.toBytes(averagePostSize)      );
        map.put(Bytes.toBytes("averagePostingPeriod"), Bytes.toBytes(averagePostingPeriod) );

        return map;
    }
}
