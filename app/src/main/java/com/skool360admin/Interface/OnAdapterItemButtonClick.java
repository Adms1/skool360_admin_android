package com.skool360admin.Interface;

public interface OnAdapterItemButtonClick {

    public static enum Action {ADD,DELETE,UPDATE,MODIFY,APPROVE,REJECT};

    void onItemButtonClick(Action target, int posID);
}
