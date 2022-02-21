package com.nahyun.helloplant;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class MyplantListDiffCallback extends DiffUtil.Callback {
    private final List<MyplantListData> oldMyplantList;
    private final List<MyplantListData> newMyplantList;

    public MyplantListDiffCallback(List<MyplantListData> oldMyplantList, List<MyplantListData> newMyplantList) {
        this.oldMyplantList = oldMyplantList;
        this.newMyplantList = newMyplantList;
    }

    @Override
    public int getOldListSize() {
        return oldMyplantList.size();
    }

    @Override
    public int getNewListSize() {
        return newMyplantList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMyplantList.get(oldItemPosition).getMyplant_list_id().equals(newMyplantList.get(newItemPosition).getMyplant_list_id());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final MyplantListData oldMyplantListData = oldMyplantList.get(oldItemPosition);
        final MyplantListData newMyplantListData = newMyplantList.get(newItemPosition);
        return oldMyplantListData.getMyplant_list_id().equals(newMyplantListData.getMyplant_list_id());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
