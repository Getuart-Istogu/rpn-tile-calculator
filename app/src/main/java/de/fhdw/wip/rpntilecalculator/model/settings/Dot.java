package de.fhdw.wip.rpntilecalculator.model.settings;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import de.fhdw.wip.rpntilecalculator.model.operands.ODouble;
import de.fhdw.wip.rpntilecalculator.presenter.Presenter;

/**
 * Summary: Places a '.' in the input term to create decimal values
 * Author:  Hendrik Falk
 * Date:    2020/02/03
 */
public class Dot extends Setting {

    @Contract(pure = true) @NotNull
    public static Dot getInstance() { return new Dot(); }

    @Override
    public boolean call() {
        if(Presenter.INPUT_TERM.toString().equals(Presenter.INPUT_FINALIZED)) {
            ODouble oDouble = new ODouble(0);
            Presenter.resetInputTerm(oDouble);
            Presenter.OPERAND_STACK.push(oDouble);
        }
        if(!Presenter.INPUT_TERM.toString().contains(".")) Presenter.INPUT_TERM.append(".");
        Presenter.updateStack();
        return true;
    }
}
