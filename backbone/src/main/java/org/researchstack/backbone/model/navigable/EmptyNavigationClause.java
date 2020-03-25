package org.sagebionetworks.researchstack.backbone.model.navigable;

import android.text.TextUtils;

import org.sagebionetworks.researchstack.backbone.answerformat.AnswerFormat;
import org.sagebionetworks.researchstack.backbone.result.StepResult;

public class EmptyNavigationClause extends StepNavigationClauseRule {

    public EmptyNavigationClause(String sourceStepIdentifier, AnswerFormat.Type ruleType, RuleClauseOperand operand) {
        super(sourceStepIdentifier, "", ruleType, operand);
    }

    @Override
    protected boolean evalClause(StepResult stepResult) {
        Object stepResultValue = stepResult.getResult();
        return stepResultValue == null || TextUtils.isEmpty(stepResultValue.toString());
    }
}
