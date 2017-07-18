package com.dmspro.trieungo.hackernewapi.DTO;

import java.io.Serializable;

/**
 * Created by Trieu Ngo on 7/18/2017.
 */

public class Post implements Serializable{

    private String mPostID;
    private String mTitle;
    private String mAuthor;
    private String mScore;
    private String mTime;
    private String mType;
    private String mURL;
    private String mDescendants;

    public Post(String mPostID, String mTitle, String mAuthor, String mScore, String mTime, String mType, String mURL, String mDescendants) {
        this.mPostID = mPostID;
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mScore = mScore;
        this.mTime = mTime;
        this.mType = mType;
        this.mURL = mURL;
        this.mDescendants = mDescendants;
    }

    public Post() {
    }

    public void setmScore(String mScore) {
        this.mScore = mScore;
    }

    public void setmDescendants(String mDescendants) {
        this.mDescendants = mDescendants;
    }

    public String getmScore() {
        return mScore;
    }

    public String getmDescendants() {
        return mDescendants;
    }

    public String getmPostID() {
        return mPostID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmType() {
        return mType;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmPostID(String mPostID) {
        this.mPostID = mPostID;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

}
