package de.fhdw.wip.rpntilecalculator.model.calculation;

import de.fhdw.wip.rpntilecalculator.model.operands.ODouble;
import de.fhdw.wip.rpntilecalculator.model.operands.OFraction;
import de.fhdw.wip.rpntilecalculator.model.operands.OMatrix;
import de.fhdw.wip.rpntilecalculator.model.operands.Operand;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;

/*
 * Summary: Defines the Root click.
 * Author:  Hendrik Falk
 */

public class Root extends Action{
    @NotNull private static final Root ROOT = new Root();
    @NotNull private static final Power POWER = Power.getInstance();

    @Contract(pure = true) @NotNull public static Root getInstance() { return ROOT; }
    private Root() {
        requiredNumOfOperands = new int[]{2};
    }

    @NotNull @Override
    public Operand with(@NotNull Operand... operands) throws CalculationException {
        scopedAction = this;
        return super.with(operands);
    }

    //region Double
    //------------------------------------------------------------------------------------

    @Contract(pure = true) @NotNull ODouble on(@NotNull ODouble radicand, @NotNull ODouble exponent) {
        return POWER.on(radicand, new ODouble(1/exponent.getDouble()));
    }

    @Contract(pure = true) @NotNull ODouble on(@NotNull ODouble radicand, @NotNull OFraction exponent){
        return POWER.on(radicand, new OFraction(1/exponent.getDouble()));
    }

    //region Fraction
    //------------------------------------------------------------------------------------
    @Contract(pure = true) @NotNull OFraction on(@NotNull OFraction radicand, @NotNull ODouble exponent){
        return POWER.on(radicand, new OFraction(1/exponent.getDouble()));
    }

    @Contract(pure = true) @NotNull OFraction on(@NotNull OFraction radicand, @NotNull OFraction exponent){
        return POWER.on(radicand, new ODouble(1/exponent.getDouble()));
    }

    //region Matrix
    //------------------------------------------------------------------------------------
    @Contract(pure = true) @NotNull OMatrix on(@NotNull OMatrix radicand, @NotNull ODouble exponent) {
        return POWER.on(radicand, new ODouble(1/exponent.getDouble()));
    }

    @Contract(pure = true) @NotNull OMatrix on(@NotNull OMatrix radicand, @NotNull OFraction exponent) {
        return POWER.on(radicand, new ODouble(1/exponent.getDouble()));
    }


}
