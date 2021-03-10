package com.qf.pojo;

public class Speaker {
    private Integer id;

    private String speakerName;

    private String speakerJob;

    private String headImgUrl;

    private String speakerDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerJob() {
        return speakerJob;
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "id=" + id +
                ", speakerName='" + speakerName + '\'' +
                ", speakerJob='" + speakerJob + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", speakerDesc='" + speakerDesc + '\'' +
                '}';
    }

    public void setSpeakerJob(String speakerJob) {
        this.speakerJob = speakerJob;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getSpeakerDesc() {
        return speakerDesc;
    }

    public void setSpeakerDesc(String speakerDesc) {
        this.speakerDesc = speakerDesc;
    }
}