package com.ebits.restro.activities.model;

/**
 * Created by Mr. Satyaki Mukherjee on 08/07/16.
 */
public class GroupModel {
    private String groupName;
    private String groupId;
    private String groupImage;

    public GroupModel() {
    }

    public GroupModel(String groupName, String groupId, String groupImage) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupImage = groupImage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }
}
