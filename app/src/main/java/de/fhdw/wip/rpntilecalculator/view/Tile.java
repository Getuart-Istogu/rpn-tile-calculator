package de.fhdw.wip.rpntilecalculator.view;

import android.content.Context;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import de.fhdw.wip.rpntilecalculator.MainActivity;
import de.fhdw.wip.rpntilecalculator.view.layout.TileLayout;
import de.fhdw.wip.rpntilecalculator.view.layout.schemes.TileScheme;
import de.fhdw.wip.rpntilecalculator.view.menu.InputTileType;

/**
 * Summary: Tile acts as button and forwards the connected type and click to the handler
 * Author:  Tom Bockhorn
 **/

public class Tile extends AppCompatButton implements TypeQuestionable {

    //Margin between tiles
    private static final int TILE_MARGIN = 3;

    private MainActivity context;
    private TileScheme scheme;
    @Nullable private TileLayout tileLayout;

    public TileLayout getTileLayout()
    {
        return tileLayout;
    }

    /**
     * Creating a TileScheme in a TileLayout
     * @param scheme what type of scheme it is going to be
     * @param tileLayout the layout (if there is one)
     */
    public Tile(@NotNull Context context, @NotNull TileScheme scheme, @Nullable TileLayout tileLayout) {
        super(context);
        this.tileLayout = tileLayout;
        this.context = (MainActivity) context;
        update(scheme);
    }

    /**
     * Updates the tile's display and text
     * @param scheme new scheme
     */
    public void update(@Nullable TileScheme scheme) {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        layoutParams.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        setLayoutParams(layoutParams);

        if(scheme != null) {
            this.scheme = scheme;
            this.setBackgroundResource(getScheme().getStyle());
            this.setText(scheme.toDisplayText());
        } else {
            System.out.println("[TILE] Could not draw Tile " + getText());
            return; //TODO: Throw exception
        }

    }

    /**
     * Enables and sets the onLongClick function for the given tile
     */
    public void enableMenuListener() {
        setOnLongClickListener(new InputTileType(context, this));
    }

    public TileScheme getScheme() {
        return scheme;
    }

    @Override
    public boolean isStack() {
        return scheme.getTileType().getType().isStack();
    }

    @Override
    public boolean isOperand() {
        return scheme.getTileType().getType().isOperand();
    }

    @Override
    public boolean isAction() {
        return scheme.getTileType().getType().isAction();
    }

    @Override
    public boolean isSetting() {
        return scheme.getTileType().getType().isSetting();
    }

    @Override
    public boolean isHistory() {
        return scheme.getTileType().getType().isHistory();
    }
}