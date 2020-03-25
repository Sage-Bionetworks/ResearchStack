package org.sagebionetworks.researchstack.backbone.model.navigable;

import org.sagebionetworks.researchstack.backbone.answerformat.AnswerFormat;
import org.sagebionetworks.researchstack.backbone.result.StepResult;

public class GreaterThanNavigationClause extends StepNavigationClauseRule {

    public GreaterThanNavigationClause(String sourceStepIdentifier, Object value, AnswerFormat.Type ruleType, RuleClauseOperand operand) {
        super(sourceStepIdentifier, value, ruleType, operand);
    }

    @Override
    protected boolean evalClause(StepResult stepResult) {
        if (stepResult.getResult() != null) {
            Integer compareResult = compareResult(stepResult);
            if (compareResult != null) {
                return compareResult > 0;
            }
        }
        return false;
    }
}
