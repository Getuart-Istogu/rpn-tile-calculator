package de.fhdw.wip.rpntilecalculator.core.calculation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import de.fhdw.wip.rpntilecalculator.core.model.operand.OPolynom;
import de.fhdw.wip.rpntilecalculator.core.model.operand.Operand;

/*
 * Summary: A Class that can calculate the zeros of functions ans quadratic functions.
 * Author:  Jannis Luca Keienburg
 * Date:    2020/01/19
 */
public class Zeros extends Action {

    @NotNull public static final Zeros ZEROS = new Zeros();

    @Contract(pure = true) @NotNull public static Zeros getInstance() { return ZEROS; }
    public Zeros() { }

    @NotNull @Override
    public Operand with(@NotNull Operand... operands) throws CalculationException {
        scopedAction = this;
        return super.with(operands);
    }

    @Contract(pure = true) @NotNull double[] on(@NotNull OPolynom oPolynom) {
        return calculateZeros(oPolynom);
    }

    // Function that calculates the zeros when called
    public double [] calculateZeros(OPolynom oPolynom)
    {
        double [] zeros = new double[] {};
        double[] functionAsDouble = oPolynom.getPolynom().getCoefficients();
        int type = normalOrQuadraticFunction(functionAsDouble);
        if (type == 1)
        {
            zeros = zerosTypeOne(functionAsDouble);
        }
        else if(type == 2)
        {
            zeros = zerosTypeTwo(functionAsDouble);
        }
        return zeros;
    }

    // Checks if it is a normal function or an quadratic funtion
    private int normalOrQuadraticFunction(double [] functionAsDouble)
    {
        int result = 1;
        if(functionAsDouble.length == 3)
        {
            result = 2;
        }
        return result;
    }

    // Calculates the zeros for functions like:
    // a * x + b = 0
    private double[] zerosTypeOne(double [] functionAsDouble)
    {
        double [] zeros = new double[1];
        zeros[0] = (functionAsDouble[1] * (-1)) / functionAsDouble[0];
        return zeros;
    }

    // Calculates the zeros for functions like:
    // a * x^2 + b * x + c = 0
    // uses the Mitternachtsformel
    private double[] zerosTypeTwo(double [] functionAsDouble)
    {
        double [] zeros = new double[2];
        zeros[0] = -functionAsDouble[1]
                - Math.sqrt(((functionAsDouble[1] * functionAsDouble[1]) - (4 * functionAsDouble[0] * functionAsDouble[2])) )
                / (2 * functionAsDouble[0]);
        zeros[1] = -functionAsDouble[1]
                + Math.sqrt(((functionAsDouble[1] * functionAsDouble[1]) - (4 * functionAsDouble[0] * functionAsDouble[2])) )
                / (2 * functionAsDouble[0]);
        return zeros;
    }
}