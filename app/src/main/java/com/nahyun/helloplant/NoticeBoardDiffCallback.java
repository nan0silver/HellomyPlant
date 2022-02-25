package com.nahyun.helloplant;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class NoticeBoardDiffCallback extends DiffUtil.Callback {
    private final List<NoticeBoardData> oldNoticeBoardList;
    private final List<NoticeBoardData> newNoticeBoardList;

    public NoticeBoardDiffCallback(List<NoticeBoardData> oldNoticeBoardList, List<NoticeBoardData> newNoticeBoardList) {
        this.oldNoticeBoardList = oldNoticeBoardList;
        this.newNoticeBoardList = newNoticeBoardList;
    }

    @Override
    public int getOldListSize() { return oldNoticeBoardList.size(); }

    @Override
    public int getNewListSize() { return newNoticeBoardList.size(); }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNoticeBoardList.get(oldItemPosition).getNoticeboard_name().equals(newNoticeBoardList.get(newItemPosition).getNoticeboard_name());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final NoticeBoardData oldNoticeBoardData = oldNoticeBoardList.get(oldItemPosition);
        final NoticeBoardData newNoticeBoardData = newNoticeBoardList.get(newItemPosition);
        return oldNoticeBoardData.getNoticeboard_name().equals(newNoticeBoardData.getNoticeboard_name());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
