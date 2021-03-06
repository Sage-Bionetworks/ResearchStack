package org.sagebionetworks.researchstack.backbone.task;

import android.content.Context;

import org.sagebionetworks.researchstack.backbone.result.TaskResult;
import org.sagebionetworks.researchstack.backbone.step.Step;
import org.sagebionetworks.researchstack.backbone.PermissionRequestManager;
import org.sagebionetworks.researchstack.backbone.TaskProvider;

@Deprecated // use ResearchStack.getInstance().getOnboardingManager().launchOnboarding(OnboardingTaskType.LOGIN, this);
public class SignInTask extends OnboardingTask {
    public static final int MINIMUM_STEPS = 0;
    public static final String ID_EMAIL = "ID_EMAIL";
    public static final String ID_PASSWORD = "ID_PASSWORD";
    private boolean hasPasscode;

    public SignInTask(Context context) {
        super(TaskProvider.TASK_ID_SIGN_IN);
    }

    @Override
    public Step getStepAfterStep(Step step, TaskResult result) {
        Step nextStep = null;

        if (step == null) {
            if (!hasPasscode) {
                nextStep = getPassCodeCreationStep();
            } else if (!PermissionRequestManager.getInstance().getPermissionRequests().isEmpty()) {
                nextStep = getPermissionStep();
            } else {
                nextStep = getSignInStep();
            }
        } else if (step.getIdentifier().equals(SignUpPassCodeCreationStepIdentifier)) {
            if (!PermissionRequestManager.getInstance().getPermissionRequests().isEmpty()) {
                nextStep = getPermissionStep();
            } else {
                nextStep = getSignInStep();
            }
        } else if (step.getIdentifier().equals(SignUpPermissionsStepIdentifier)) {
            nextStep = getSignInStep();
        }

        return nextStep;
    }

    @Override
    public Step getStepBeforeStep(Step step, TaskResult result) {
        // go back to onboarding
        return null;
    }

    @Override
    public TaskProgress getProgressOfCurrentStep(Step step, TaskResult result) {
        return new TaskProgress(0, MINIMUM_STEPS);
    }

    public void setHasPasscode(boolean hasPasscode) {
        this.hasPasscode = hasPasscode;
    }
}
