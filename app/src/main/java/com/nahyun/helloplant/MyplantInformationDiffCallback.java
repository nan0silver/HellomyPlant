package com.nahyun.helloplant;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class MyplantInformationDiffCallback extends DiffUtil.Callback{
    private final List<MyplantInformationData> oldMyplantInfoList;
    private final List<MyplantInformationData> newMyplantInfoList;

    public MyplantInformationDiffCallback(List<MyplantInformationData> oldMyplantInfoList, List<MyplantInformationData> newMyplantInfoList) {
        this.oldMyplantInfoList = oldMyplantInfoList;
        this.newMyplantInfoList = newMyplantInfoList;
    }

    @Override
    public int getOldListSize() { return oldMyplantInfoList.size(); }

    @Override
    public int getNewListSize() { return newMyplantInfoList.size(); }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMyplantInfoList.get(oldItemPosition).getMyplant_information_attribute().equals(newMyplantInfoList.get(newItemPosition).getMyplant_information_attribute());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final MyplantInformationData oldMyplantInfoData = oldMyplantInfoList.get(oldItemPosition);
        final MyplantInformationData newMyplantInfoData = newMyplantInfoList.get(newItemPosition);
        return oldMyplantInfoData.getMyplant_information_attribute().equals(newMyplantInfoData.getMyplant_information_attribute());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
