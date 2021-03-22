package com.example.hwlife.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.hwlife.R;
import com.xw.repo.BubbleSeekBar;

public class HWAddDialog extends Dialog{

    private String TAG = "***HWAddDialog";

    private Context context;

    public HWAddDialog(@NonNull Context context){
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hwadd_dialog);

        BubbleSeekBar bubbleSeekBar = findViewById(R.id.hwadd_seekbar);

        bubbleSeekBar.getConfigBuilder()
                .min(0)
                .max(120)
                .sectionCount(5)
                .trackColor(ContextCompat.getColor(context, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(context, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(context, R.color.color_blue))
                .showSectionText()
                .sectionTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .sectionTextSize(18)
                .showThumbText()
                .thumbTextColor(ContextCompat.getColor(context, R.color.color_red))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(context, R.color.color_red))
                .bubbleTextSize(18)
                .showSectionMark()
                .seekStepSection()
                .touchToSeek()
                .hideBubble()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build();

    }



}
