package de.fhdw.wip.rpntilecalculator.view.layout;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Summary: Class that loads and saves tile layouts in csv
 * Author:  Tim Jonas Meinerzhagen
 * Date:    2020/01/06
 */
public class TileLayoutLoader {

    /**
     * Loads a saved or hard coded layout
     * @param context activity, from which to access storage
     * @param indicator name of the layout
     * @return TileLayout
     */
    public String loadLayout(@NotNull Context context, @NotNull String indicator) {
        String layout = "";

        if(indicator.equals("h_Morestack")) {
            layout = "S_STACK,1,O_Empty, ;S_STACK,2,O_Empty, ;S_STACK,3,O_Empty, ;A_PLUS,+;A_SIN,sin;H_HISTORY,1,O_Empty, ;H_HISTORY,6,O_Empty, ;H_HISTORY,11,O_Empty, \nO_SET,[1,2];O_TUPLE,(0,2);O_DOUBLE,3;A_MINUS,-;A_COS,cos;H_HISTORY,2,O_Empty, ;H_HISTORY,7,O_Empty, ;H_HISTORY,12,O_Empty, \nO_DOUBLE,4;O_DOUBLE,5;O_DOUBLE,6;A_SLASH,/;A_TAN,tan;H_HISTORY,3,O_Empty, ;H_HISTORY,8,O_Empty, ;H_HISTORY,13,O_Empty, \nO_DOUBLE,7;O_DOUBLE,8;O_DOUBLE,9;A_TIMES,*;A_MODULO,%;H_HISTORY,4,O_Empty, ;H_HISTORY,9,O_Empty, ;H_HISTORY,14,O_Empty, \nS_AC,AC;O_DOUBLE,0;S_ENTER,Enter;S_SPLIT,Split;A_ROOT,root;H_HISTORY,5,O_Empty, ;H_HISTORY,10,O_Empty, ;H_HISTORY,15,O_Empty, \nS_DEL,delete;S_TURNAROUNDSIGN,+/-;S_SWAP,Swap;S_INVERSE,1/x;A_POWER,pow;S_CLEARHISTORY,ifuknowwhatimean;S_LOADLAYOUT,Load;S_SAVELAYOUT,Save";
        }
        else if(indicator.equals("h_Standardlayout")) {
            layout = "S_STACK,1,O_Empty, ;S_STACK,2,O_Empty, ;S_STACK,3,O_Empty, ;A_PLUS,+;A_SIN,sin;H_HISTORY,1,O_Empty, ;H_HISTORY,6,O_Empty, ;H_HISTORY,11,O_Empty, \nO_DOUBLE,1;O_DOUBLE,2;O_DOUBLE,3;A_MINUS,-;A_COS,cos;H_HISTORY,2,O_Empty, ;H_HISTORY,7,O_Empty, ;H_HISTORY,12,O_Empty, \nO_DOUBLE,4;O_DOUBLE,5;O_DOUBLE,6;A_SLASH,/;A_TAN,tan;H_HISTORY,3,O_Empty, ;H_HISTORY,8,O_Empty, ;H_HISTORY,13,O_Empty, \nO_DOUBLE,7;O_DOUBLE,8;O_DOUBLE,9;A_TIMES,*;A_MODULO,%;H_HISTORY,4,O_Empty, ;H_HISTORY,9,O_Empty, ;H_HISTORY,14,O_Empty, \nS_DEL,Delete;O_DOUBLE,0;S_ENTER,Enter;S_INVERSE,1/x;A_ROOT,root;H_HISTORY,5,O_Empty, ;H_HISTORY,10,O_Empty, ;H_HISTORY,15,O_Empty, \nS_DOT,.;S_TURNAROUNDSIGN,+/-;S_SWAP,Swap;S_SPLIT,split;A_POWER,pow;S_CLEARHISTORY,ifuknowwhatimean;S_LOADLAYOUT,Load;S_SAVELAYOUT,Save";
        }else if(indicator.equals("v_Standardlayout")) {
            // TODO
            layout = "S_STACK,1,O_Empty, ;S_STACK,2,O_Empty, \nO_DOUBLE,1;O_DOUBLE,2\nA_PLUS,+;A_SIN,sin";
        } else {
            layout = readLayout(context, indicator);
        }

        return layout;
    }

    public static ArrayList<String> getSavedLayouts(@NotNull Context context, final ScreenOrientation orientation){
        File[] files = context.getFilesDir().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".csv") && (s.charAt(0) == orientation.getIndicator());
            }
        });

        ArrayList<String> layouts = new ArrayList<>();
        for(File f : files){
            System.out.println(f.getName());
            System.out.println(f.getName().substring(0, f.getName().lastIndexOf(".")));
            layouts.add(f.getName().substring(0, f.getName().lastIndexOf(".")));
        }
        return layouts;
    }

    //Callable method to save a certain Layout
    public static boolean saveLayout(@NotNull Context context, @NotNull TileLayout tileLayout) {
        String layoutText = tileLayout.generateLayoutText();
        return writeLayout(context, layoutText, tileLayout.getIndicator());
    }

    //Callable method to clean all saved layouts
    public static void clearLayouts(@NotNull Context context) {
        File dir = context.getFilesDir();
        String[] files = dir.list();
        for(int i = 0; i < files.length; i++) {
            new File(dir, files[i]).delete();
        }
    }


    private static boolean writeLayout(Context context, String layoutText, String indicator) {
        try {
            //The path to the file is defined by its indicator, no doubles allowed!
            //String filePath = FOLDER + indicator + ".txt";
            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(indicator + ".csv", Context.MODE_PRIVATE));
            out.write(layoutText);
            out.close();
            return true;
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
    }

    private static String readLayout(Context context, String indicator) {
        //String filepath = FOLDER + indicator + ".txt";
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(indicator + ".csv");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString + "\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
            ret = null;
        } catch (IOException e) {
            Log.e("Exception", "Can not read file: " + e.toString());
            ret = null;
        }

        return ret;
    }

}
