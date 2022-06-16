package com.example.mickey_mouse;

import javax.swing.*;
import java.util.LinkedList;

public class ScoreListModel extends AbstractListModel<ExitScore> {
    private LinkedList<ExitScore> scoreList;

    ScoreListModel(LinkedList<ExitScore> ScoreList){
        scoreList = ScoreList;
    }

    @Override
    public int getSize() {
        return scoreList.size();
    }

    @Override
    public ExitScore getElementAt(int index) {
        return scoreList.get(index);
    }
}
