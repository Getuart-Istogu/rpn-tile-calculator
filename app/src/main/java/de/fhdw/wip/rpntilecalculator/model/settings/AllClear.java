package de.fhdw.wip.rpntilecalculator.model.settings;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import de.fhdw.wip.rpntilecalculator.presenter.Presenter;

/**
 * Summary: Empties the stack of the presenter
 * Author:  Tim Jonas Meinerzhagen
 * Date:    2020/01/26
 */
public class AllClear extends Setting {

    @Contract(pure = true) @NotNull
    public static AllClear getInstance() { return new AllClear(); }

    /**
     * Clears the entire Stack and current input
     */
    @Override
    public boolean call() {
        Presenter presenter = Presenter.getInstance();
        presenter.getOperandStack().clear();
        presenter.resetInputTerm(null);
        presenter.updateStack();
        return true;
    }

}