package de.fhdw.wip.rpntilecalculator.core.ui;

import android.graphics.Matrix;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fraction.Fraction;

import java.util.Set;

import de.fhdw.wip.rpntilecalculator.core.calculation.Action;
import de.fhdw.wip.rpntilecalculator.core.calculation.Minus;
import de.fhdw.wip.rpntilecalculator.core.calculation.Plus;
import de.fhdw.wip.rpntilecalculator.core.calculation.Slash;
import de.fhdw.wip.rpntilecalculator.core.calculation.Times;
import de.fhdw.wip.rpntilecalculator.core.model.operand.ODouble;
import de.fhdw.wip.rpntilecalculator.core.model.operand.OFraction;
import de.fhdw.wip.rpntilecalculator.core.model.operand.OMatrix;
import de.fhdw.wip.rpntilecalculator.core.model.operand.OPolynom;
import de.fhdw.wip.rpntilecalculator.core.model.operand.OSet;
import de.fhdw.wip.rpntilecalculator.core.model.operand.OTuple;
import de.fhdw.wip.rpntilecalculator.core.model.operand.Operand;
import de.fhdw.wip.rpntilecalculator.core.ui.TileType;

public enum TileMapping {

    O_DOUBLE(TileType.OPERAND, ODouble.class, Double.class),
    O_FRACTION(TileType.OPERAND, OFraction.class, Fraction.class),
    O_MATRIX(TileType.OPERAND, OMatrix.class, Matrix.class),
    O_POLYNOM(TileType.OPERAND, OPolynom.class, PolynomialFunction.class),
    O_SET(TileType.OPERAND, OSet.class, Set.class),
    O_TUPLE(TileType.OPERAND, OTuple.class, Double[].class),
    
    A_MINUS(TileType.ACTION, Minus.getInstance(), "-"),
    A_PLUS(TileType.ACTION, Plus.getInstance(), "+"),
    A_Slash(TileType.ACTION, Slash.getInstance(), "/"),
    A_Times(TileType.ACTION, Times.getInstance(), "*"),


    X_ERROR(TileType.ACTION, Minus.getInstance(), "N/A");


    private TileType type;
    private Action actionType;
    private String actionText;
    private Class<? extends Operand> operandType;
    private Class<?> operandValueType;

    TileMapping(TileType type, Action actionType, String actionText) {
        this.type = type;
        this.actionType = actionType;
        this.actionText = actionText;
    }

    TileMapping(TileType type, Class<? extends Operand>  operandType, Class<?> operandValueType) {
        this.type = type;
        this.operandType = operandType;
        this.operandValueType = operandValueType;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public Class<? extends Operand> getOperandType() {
        return operandType;
    }

    public Action getActionType() {
        return actionType;
    }

    public String getActionText() {
        return actionText;
    }

    public Class<?> getOperandValueType() {
        return operandValueType;
    }
}
