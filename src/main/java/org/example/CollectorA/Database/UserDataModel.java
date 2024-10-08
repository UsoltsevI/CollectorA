package org.example.CollectorA.Database;

import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * This class describes the model of data collected and recorded in the DB.
 *
 * @author UsoltsevI
 */

@Component
@Data
public class UserDataModel {
    private String userID;
    private boolean isPrivate;
    private Long SubscriptionsAmount;
    private Long followersAmount;
    private Long averageLikesAmount;
    private Long averageCommentsAmount;
    private Long averageRepostAmount;
    private Long averagePostSize;
    private Long averagePostingPostsFrequency;
}
