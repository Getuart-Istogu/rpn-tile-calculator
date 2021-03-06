package de.fhdw.wip.rpntilecalculator.view.schemes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import de.fhdw.wip.rpntilecalculator.model.calculation.Action;
import de.fhdw.wip.rpntilecalculator.view.TileMapping;

/**
 * Summary: TileScheme for an Operator (Action)
 * Author:  Dennis Gentges
 * Date:    2020/01/08
 */
public class ActionTileScheme extends TileScheme {

    @NotNull private Action action;

    /**
     * Creates a TileScheme for an Action
     * @param tileType exact type of the scheme
     * @param content display text (default is derived by type)
     */
    ActionTileScheme(@NotNull TileMapping tileType, @Nullable String content) {
        super(tileType, tileType.getActionText());
        this.action = tileType.getActionType();
    }

    public Action getAction() {
        return action;
    }
}
