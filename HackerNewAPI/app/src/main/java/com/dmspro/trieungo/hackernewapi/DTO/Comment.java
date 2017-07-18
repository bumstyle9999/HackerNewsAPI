package com.dmspro.trieungo.hackernewapi.DTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Trieu Ngo on 7/18/2017.
 */

public class Comment implements Serializable {

    private int mCommentID;
    private String mAuthor;
    private int mParent;
    private String mText;
    private String mDatetime;
    private String mComment;

    public Comment(int mCommentID, String mAythor, List mlistKids, int mParent, String mText, String mDatetime, String mComment) {
        this.mCommentID = mCommentID;
        this.mAuthor = mAythor;
        this.mParent = mParent;
        this.mText = mText;
        this.mDatetime = mDatetime;
        this.mComment = mComment;
    }

    public Comment() {
    }

    public void setmCommentID(int mCommentID) {
        this.mCommentID = mCommentID;
    }

    public void setmAythor(String mAythor) {
        this.mAuthor = mAythor;
    }

    public void setmParent(int mParent) {
        this.mParent = mParent;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public void setmDatetime(String mDatetime) {
        this.mDatetime = mDatetime;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public int getmCommentID() {
        return mCommentID;
    }

    public String getmAythor() {
        return mAuthor;
    }

    public int getmParent() {
        return mParent;
    }

    public String getmText() {
        return mText;
    }

    public String getmDatetime() {
        return mDatetime;
    }

    public String getmComment() {
        return mComment;
    }
}
