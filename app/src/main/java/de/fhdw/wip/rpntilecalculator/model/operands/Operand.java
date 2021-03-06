package de.fhdw.wip.rpntilecalculator.model.operands;

import org.jetbrains.annotations.NotNull;

/*
 * Summary: Main class for all operands that can be used for calculating
 * Author:  Tim Schwenke
 * Date:    2019/11/12
 */
public abstract class Operand {

    /**
     * Multiplies all values of the {@link Operand} with {@code -1}.
     */
    public abstract @NotNull Operand turnAroundSign();

    /**
     * Makes all values of the {@link Operand} negative.
     */
    public abstract @NotNull Operand negateValue();

    public abstract @NotNull Operand inverseValue();

    public abstract boolean equalsValue(Operand operand);

}
