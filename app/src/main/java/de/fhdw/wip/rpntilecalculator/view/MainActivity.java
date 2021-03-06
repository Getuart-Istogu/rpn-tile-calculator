package de.fhdw.wip.rpntilecalculator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.Toast;

import de.fhdw.wip.rpntilecalculator.R;
import de.fhdw.wip.rpntilecalculator.presenter.Presenter;
import de.fhdw.wip.rpntilecalculator.view.layout.ScreenOrientation;
import de.fhdw.wip.rpntilecalculator.view.layout.TileLayout;
import de.fhdw.wip.rpntilecalculator.view.layout.TileLayoutFactory;
import de.fhdw.wip.rpntilecalculator.view.layout.TileLayoutLoader;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * Summary: Starting activity that loads presenter and default layout
 * Author:  Tom Bockhorn
 * Date:    2019/09/04
 */
public class MainActivity extends AppCompatActivity {

    private static MainActivity mainActivity = null;
    private TileLayout tileLayout;
    private OrientationEventListener orientationListener;
    private int lastOrientation = ORIENTATION_PORTRAIT;
    private static TileLayout v_standardlayout;
    private static TableLayout v_tablelayout;
    private static TileLayout h_standardlayout;
    private static TableLayout h_tablelayout;
    private static boolean loaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_main);

        if(!loaded) {
            v_standardlayout = TileLayoutFactory.createLayout(this, "v_Standardlayout");
            v_tablelayout = v_standardlayout.createView(this);
            h_standardlayout = TileLayoutFactory.createLayout(this, "h_Standardlayout");
            h_tablelayout = h_standardlayout.createView(this);
            loaded = true;
        }
        lastOrientation = -1;
        orientationListener = new OrientationEventListener(getApplicationContext()) {
            @Override
            public final void onOrientationChanged(int orientation) {
                if (orientation < 0) {
                    return; // Flip screen, Not take account
                }
                int curOrientation;

                if (orientation <= 45) {
                    curOrientation = ORIENTATION_PORTRAIT;
                } else if (orientation <= 135) {
                    curOrientation = ORIENTATION_LANDSCAPE; //reverse
                } else if (orientation <= 225) {
                    curOrientation = ORIENTATION_PORTRAIT; //reverse
                } else if (orientation <= 315) {
                    curOrientation = ORIENTATION_LANDSCAPE;
                } else {
                    curOrientation = ORIENTATION_PORTRAIT;
                }
                if (curOrientation != lastOrientation) {
                    if(curOrientation == ORIENTATION_PORTRAIT) {
                        adoptTileLayout(v_standardlayout, v_tablelayout);
                        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
                    }else {
                        adoptTileLayout(h_standardlayout, h_tablelayout);
                        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
                    }
                    lastOrientation = curOrientation;
                }
            }
        };
        if (orientationListener.canDetectOrientation()) {
            orientationListener.enable();
        }
        if(((ConstraintLayout)findViewById(R.id.constraintLayout)).getChildCount() == 0) {
            lastOrientation = -1;
            orientationListener.onOrientationChanged(getWindowManager().getDefaultDisplay().getRotation() * 90);
        }
    }

    public static MainActivity getInstance(){
        return mainActivity;
    }

    public void setTileLayout(TileLayout tileLayout) {
        if(tileLayout == null){
            Toast.makeText(getApplicationContext(), "Could not load Layout", Toast.LENGTH_LONG).show();

        }else{
           adoptTileLayout(tileLayout, tileLayout.createView(this));
        }
    }

    public void adoptTileLayout(TileLayout tileLayout, TableLayout tableLayout) {
        this.tileLayout = tileLayout;
        Presenter presenter = Presenter.getInstance();
        tileLayout.updateStack(presenter.getOperandStack());
        tileLayout.updateHistoryStack(presenter.getHistoryStack());
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.removeAllViews();
        constraintLayout.removeAllViewsInLayout();
        constraintLayout.setBackgroundColor(Color.WHITE);
        if(tableLayout.getParent() != null)
            ((ConstraintLayout)tableLayout.getParent()).removeView(tableLayout);
        constraintLayout.addView(tableLayout);
        presenter.setCurrentLayout(tileLayout);
    }

    public TileLayout getTileLayout() {
        return tileLayout;
    }

}
