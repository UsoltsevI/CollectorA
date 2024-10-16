package org.example.CollectorA.Database;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.apache.hadoop.hbase.util.Bytes;
import lombok.Data;
import lombok.ToString;

/**
 * This class describes the model of user data collected and recorded in the DB
 * @author UsoltsevI
 */
@Component
@Data
@ToString
public class UserDataModel implements DataModel {
    private String id;
    private boolean isPrivate;
    private Long subscriptionsAmount;
    private Long followersAmount;
    private Long averageLikesAmount;
    private Long averageCommentsAmount;
    private Long averageRepostAmount;
    private Long averagePostSize;
    private Long averagePostingPeriod;

    @Override
    public byte[] getId() {
        return Bytes.toBytes(id);
    }

    /**
     * Setter that is not created by lombok
     * @param isPrivate
     */
    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    /**
     * Returns all fields (except id) as HashMap<byte[] fieldName, byte[] fieldValue>
     * @return all fileds and its values as bytes
     */
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
