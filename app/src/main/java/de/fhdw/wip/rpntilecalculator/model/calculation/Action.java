package de.fhdw.wip.rpntilecalculator.model.calculation;

import de.fhdw.wip.rpntilecalculator.model.operands.Operand;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Summary: The framework for defining Actions. Actions are able to work with operands from the stack or executor functions.
 * Author:  Tim Schwenke
 * Date:    2020/01/04
 */
public abstract class Action {

    /**
     * Must be set by inheriting classes of {@link Action} for reflection to work.
     */
    protected Action scopedAction;

    /**
     * Must be overridden in case the required number of operands is a fixed amount.
     */
    protected int[] requiredNumOfOperands = new int[]{-1};

    /**
     * Leverages reflection for matching given arguments to a calculation method.
     *
     * @param operands List of operands.
     * @return Always a valid Operand.
     * @throws CalculationException In case the result cannot be computed.
     */
    @Contract(pure = true)
    public @NotNull Operand with(@NotNull List<Operand> operands) throws CalculationException {
        Operand[] target = new Operand[operands.size()];
        for (int i = 0; i < target.length; i++) {
            target[i] = operands.get(i);
        }
        return with(target);
    }

    /**
     * Leverages reflection for matching given arguments to a calculation method.
     *
     * @param operands List of operands.
     * @return Always a valid Operand.
     * @throws CalculationException In case the result cannot be computed.
     */
    @Contract(pure = true) public @NotNull Operand with(@NotNull Operand... operands) throws CalculationException {
        Class[] operandClasses = new Class[operands.length];
        Operand resultOperand;

        for (int i = 0; i < operands.length; i++)
            operandClasses[i] = operands[i].getClass();

        try {
            resultOperand = (Operand) scopedAction.getClass()
                    .getDeclaredMethod(
                            "on",
                            operandClasses
                    ).invoke(
                            scopedAction,
                            (Object[]) operands
                    );
        } catch (RuntimeException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new CalculationException(e.getMessage());
        }

        if (resultOperand != null) return resultOperand;
        else throw new CalculationException();
    }

    /**
     * @return Number of required operands for the concrete {@link Action}. If {@code -1}
     * the number of operands required is variable.
     */
    public int[] getRequiredNumOfOperands() {
        return requiredNumOfOperands;
    }

}
