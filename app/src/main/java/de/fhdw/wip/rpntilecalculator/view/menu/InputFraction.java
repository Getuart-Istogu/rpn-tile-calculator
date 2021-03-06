package de.fhdw.wip.rpntilecalculator.view.menu;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.fhdw.wip.rpntilecalculator.view.MainActivity;
import de.fhdw.wip.rpntilecalculator.R;
import de.fhdw.wip.rpntilecalculator.model.operands.OFraction;
import de.fhdw.wip.rpntilecalculator.view.Tile;
import de.fhdw.wip.rpntilecalculator.view.TileMapping;
import de.fhdw.wip.rpntilecalculator.view.schemes.TileScheme;

/**
 * Summary: Class responsible for input of fractions
 * Author:  Dennis Gentges
 * Date:    2020/01/29
 **/

public class InputFraction extends DialogMenu {

    private Button confirmButton = this.dialog.findViewById(R.id.enterButton);
    private EditText numeratorText = this.dialog.findViewById(R.id.numerator);
    private EditText denumeratorText = this.dialog.findViewById(R.id.denumerator);

   public InputFraction(final MainActivity context, Tile displayTile, DialogMenu last)
   {
       super(context, displayTile, last);
       confirmButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               try {
                   int nummerator = Integer.parseInt(numeratorText.getText().toString());
                   int denumerator = Integer.parseInt(denumeratorText.getText().toString());
                   OFraction oFraction = new OFraction(nummerator, denumerator);
                   TileScheme newTileScheme = TileScheme.createTileScheme(TileMapping.O_FRACTION, oFraction, 0);
                   tile.update(newTileScheme);
                   tile.getTileLayout().removeFromStacks(tile);
                   dismissAll();
               }catch (Exception e)
               {
                   Toast.makeText(context, "Please check your input", Toast.LENGTH_SHORT).show();
               }
           }
       });
   }

    @Override
    protected void setContentView() {
        contentView = R.layout.input_fraction;
    }
}
