package com.aurxsiu.datahomework.entity;

public class Experience {
    private int experienceNum;
    private int rank;
    public Experience(){
        experienceNum=0;
        rank=-1;
    }

    public int getExperienceNum() {
        return experienceNum;
    }

    public void setExperienceNum(int experienceNum) {
        this.experienceNum = experienceNum;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Experience(int experienceNum, int rank) {
        this.experienceNum = experienceNum;
        this.rank = rank;
    }
}
