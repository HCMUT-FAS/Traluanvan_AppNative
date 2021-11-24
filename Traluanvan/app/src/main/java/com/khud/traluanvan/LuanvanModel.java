package com.example.navigation;
//Set model Luanvan
public class LuanvanModel {
    private int LV_Ma;
    private String LV_Ten;
    private String LV_TenTiengAnh;
    private String SV1_Ten;
    private String MSSV1;
    private String SV2_Ten;
    private String MSSV2;
    private String GV1_Ten;
    private String GV2_Ten;
    public LuanvanModel( int LV_Ma, String LV_Ten, String LV_TenTiengAnh, String SV1_Ten, String MSSV1, String SV2_Ten, String MSSV2, String GV1_Ten, String GV2_Ten) {
        this.LV_Ma=LV_Ma;
        this.LV_Ten = LV_Ten;
        this.LV_TenTiengAnh=LV_TenTiengAnh;
        this.SV1_Ten = SV1_Ten;
        this.MSSV1 = MSSV1;
        this.SV2_Ten = SV2_Ten;
        this.MSSV2 = MSSV2;
        this.GV1_Ten = GV1_Ten;
        this.GV2_Ten = GV2_Ten;

    }
    public int getLV_Ma() {
        return LV_Ma;
    }

    public void setLV_Ma(int LV_Ma) {
        this.LV_Ma = LV_Ma;
    }

    public String getLV_Ten() {
        return LV_Ten;
    }

    public void setLV_Ten(String LV_Ten) {
        this.LV_Ten = LV_Ten;
    }

    public String getLV_TenTiengAnh() {
        return LV_TenTiengAnh;
    }

    public void setLV_TenTiengAnh(String LV_TenTiengAnh) {
        this.LV_TenTiengAnh = LV_TenTiengAnh;
    }

    public String getSV1_Ten() {
        return SV1_Ten;
    }

    public void setSV1_Ten(String SV1_Ten) {
        this.SV1_Ten = SV1_Ten;
    }

    public String getMSSV1() {
        return MSSV1;
    }

    public void setMSSV1(String MSSV1) {
        this.MSSV1 = MSSV1;
    }

    public String getSV2_Ten() {
        return SV2_Ten;
    }

    public void setSV2_Ten(String SV2_Ten) {
        this.SV2_Ten = SV2_Ten;
    }

    public String getMSSV2() {
        return MSSV2;
    }

    public void setMSSV2(String MSSV2) {
        this.MSSV2 = MSSV2;
    }

    public String getGV1_Ten() {
        return GV1_Ten;
    }

    public void setGV1_Ten(String GV1_Ten) {
        this.GV1_Ten = GV1_Ten;
    }

    public String getGV2_Ten() {
        return GV2_Ten;
    }

    public void setGV2_Ten(String GV2_Ten) {
        this.GV2_Ten = GV2_Ten;
    }

    @Override
    public String toString() {
        String Luanvan= " Tên luận văn :" + LV_Ten +
                "\n Sinh viên :" + SV1_Ten +
                "," + SV2_Ten ;
        return Luanvan;
    }

}
