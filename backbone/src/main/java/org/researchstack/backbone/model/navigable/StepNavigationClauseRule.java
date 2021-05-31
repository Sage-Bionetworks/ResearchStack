package org.researchstack.backbone.model.navigable;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.utils.StepResultHelper;

import java.io.Serializable;

public abstract class StepNavigationClauseRule implements Serializable {

    protected String sourceStepIdentifier;
    protected Object value;
    protected AnswerFormat.Type ruleType;
    protected RuleClauseOperand operand;

    public StepNavigationClauseRule(String sourceStepIdentifier, Object value, AnswerFormat.Type ruleType, RuleClauseOperand operand) {
        this.sourceStepIdentifier = sourceStepIdentifier;
        this.value = value;
        this.operand = operand;
        this.ruleType = ruleType;
    }

    public boolean fulfillsCondition(TaskResult result) {
        if (sourceStepIdentifier != null) {
            StepResult stepResult = StepResultHelper.findStepResult(result, sourceStepIdentifier);
            if (stepResult != null) {
                return evalClause(stepResult);
            }
        }
        return false;
    }

    public String getSourceStepIdentifier() {
        return sourceStepIdentifier;
    }

    public void setSourceStepIdentifier(String sourceStepIdentifier) {
        this.sourceStepIdentifier = sourceStepIdentifier;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public AnswerFormat.Type getRuleType() {
        return ruleType;
    }

    public void setRuleType(AnswerFormat.Type ruleType) {
        this.ruleType = ruleType;
    }

    public RuleClauseOperand getOperand() {
        return operand;
    }

    public void setOperand(RuleClauseOperand operand) {
        this.operand = operand;
    }

    public boolean eval(TaskResult result) {
        if (sourceStepIdentifier != null) {
            StepResult stepResult = StepResultHelper.findStepResult(result, sourceStepIdentifier);
            if (stepResult != null) {
                return evalClause(stepResult);
            }
        }
        return false;
    }

    protected Integer compareResult(StepResult stepResult) {
        AnswerFormat.Type resultType = this.ruleType;
        Object stepResultValue = stepResult.getResult();
        if (resultType.equals(AnswerFormat.Type.Text)) {

            // Text Value
            return String.valueOf(stepResultValue.toString()).compareTo(String.valueOf(value.toString()));

        } else if (resultType.equals(AnswerFormat.Type.Integer)
                || resultType.equals(AnswerFormat.Type.Decimal)) {

            // Numeric Value
            return Float.valueOf(stepResultValue.toString()).compareTo(Float.valueOf(value.toString()));

        } else if ((resultType.equals(AnswerFormat.Type.MultipleChoice))
            || (resultType.equals(AnswerFormat.Type.SingleChoice))) {

            if (stepResultValue instanceof Object[]) {
                Object[] array = (Object[]) stepResultValue;
                for (Object stepValue : array) {
                    if (String.valueOf(stepValue).equalsIgnoreCase(String.valueOf(value))) {
                       return 0;
                    }
                }
            } else {
                return String.valueOf(stepResultValue).compareTo(String.valueOf(value));
            }
            return null;

        } else if (resultType.equals(AnswerFormat.Type.Boolean)) {

            // Boolean Value
            return Boolean.valueOf(stepResultValue.toString()).compareTo(Boolean.valueOf(value.toString()));

        } else if (resultType.equals(AnswerFormat.Type.Date)
                || resultType.equals(AnswerFormat.Type.DateAndTime)
                || resultType.equals(AnswerFormat.Type.TimeOfDay)) {

            //TODO: implement this case
            // Date Value
            return null;
        }
        return null;
    }

    /**
     * Gets sourceStepResult, value, and operator and evaluates the clause
     * @return true if the Clause is fulfilled, false otherwise
     */
    protected abstract boolean evalClause(StepResult stepResult);
}