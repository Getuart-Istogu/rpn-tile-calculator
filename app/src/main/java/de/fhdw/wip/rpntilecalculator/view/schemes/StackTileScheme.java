package de.fhdw.wip.rpntilecalculator.view.schemes;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import de.fhdw.wip.rpntilecalculator.model.operands.Operand;
import de.fhdw.wip.rpntilecalculator.view.TileMapping;
import de.fhdw.wip.rpntilecalculator.view.TileType;
import de.fhdw.wip.rpntilecalculator.view.layout.TileLayoutFactory;

public class StackTileScheme extends OperandTileScheme {

    private int rank = 1;
    private boolean first = false;

    /**
     * Crates an StackTileScheme with an operand in it
     * @param content rank of the stack field
     */
    public StackTileScheme(@NotNull TileMapping tileMapping, @NotNull String content, @NotNull int rank) {
        super(tileMapping, content);
        this.rank = rank;
    }

    StackTileScheme(@NotNull Operand operand, @NotNull int rank) {
        super(TileMapping.O_DOUBLE, operand);
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public int getStyle() {
        return TileType.STACK.getStyle();
    }

    @NonNull
    @Override
    public String toString() {
        return "S_STACK"
                + TileLayoutFactory.VALUE_SEPERATOR + this.getRank()
                + TileLayoutFactory.VALUE_SEPERATOR + this.getTileType()
                + TileLayoutFactory.VALUE_SEPERATOR + this.getContent();
    }
}