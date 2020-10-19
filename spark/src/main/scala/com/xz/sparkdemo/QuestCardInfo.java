package com.xz.sparkdemo;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/7/22
 */
public class QuestCardInfo {

    private String subjectId;
    private String paperQuestNum;
    private Double score;
    private String answer;
    private String subObjTag;
    private String questId;
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPaperQuestNum() {
        return paperQuestNum;
    }

    public void setPaperQuestNum(String paperQuestNum) {
        this.paperQuestNum = paperQuestNum;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSubObjTag() {
        return subObjTag;
    }

    public void setSubObjTag(String subObjTag) {
        this.subObjTag = subObjTag;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }
}
