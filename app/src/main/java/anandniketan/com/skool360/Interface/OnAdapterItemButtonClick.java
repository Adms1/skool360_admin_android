package anandniketan.com.skool360.Interface;

public interface OnAdapterItemButtonClick {

    void onItemButtonClick(Action target, int posID);

    enum Action {ADD, DELETE, UPDATE, MODIFY, APPROVE, REJECT}
}
