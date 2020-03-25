package org.sagebionetworks.researchstack.backbone.storage.database.staged.records;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.sagebionetworks.researchstack.backbone.model.staged.StagedActivityState;
import org.sagebionetworks.researchstack.backbone.model.staged.StagedEvent;
import org.sagebionetworks.researchstack.backbone.result.TaskResult;
import org.sagebionetworks.researchstack.backbone.task.Task;
import org.sagebionetworks.researchstack.backbone.utils.FormatHelper;

import java.util.Date;

import co.touchlab.squeaky.field.DataType;
import co.touchlab.squeaky.field.DatabaseField;
import co.touchlab.squeaky.table.DatabaseTable;

/**
 * Created by mauriciosouto on 14/9/17.
 */

@DatabaseTable
public class StagedEventRecord {

    private static final Gson GSON = new GsonBuilder().setDateFormat(FormatHelper.DATE_FORMAT_ISO_8601).create();

    public static final String ACTIVITY_ID_COLUMN = "stagedActivityId";
    public static final String STATUS_COLUMN = "status";

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public String stagedActivityId;

    @DatabaseField
    public Date eventStartDate;

    @DatabaseField(canBeNull = false)
    public Date eventEndDate;

    @DatabaseField
    public StagedActivityState status;

    @DatabaseField(canBeNull = true)
    public String taskId;

    @DatabaseField(dataType = DataType.SERIALIZABLE, canBeNull = true)
    public Object task;

    @DatabaseField(canBeNull = true)
    public String taskResultId;

    @DatabaseField(canBeNull = true)
    public String lastStepId;

    public static StagedEvent toStagedEvent(StagedEventRecord record, TaskResult result) {
        StagedEvent stagedEvent = new StagedEvent();
        stagedEvent.setId(record.id);
        stagedEvent.setActivityId(record.stagedActivityId);
        stagedEvent.setEventStartDate(record.eventStartDate);
        stagedEvent.setEventEndDate(record.eventEndDate);
        stagedEvent.setTask((Task) record.task);
        stagedEvent.addResult(result, record.status);
        stagedEvent.setLastStepId(record.lastStepId);
        return stagedEvent;
    }

    public static StagedEventRecord toRecord(StagedEvent event) {
        StagedEventRecord record = new StagedEventRecord();
        record.id = event.getId();
        record.stagedActivityId = event.getActivityId();
        record.eventStartDate = event.getEventStartDate();
        record.eventEndDate = event.getEventEndDate();
        record.status = event.getStatus();
        if (event.getTask() != null) {
            record.task = event.getTask();
            record.taskId = event.getTask().getIdentifier();
        }
        if (event.getResult() != null) {
            record.taskResultId = event.getResult().getIdentifier();
        }
        record.lastStepId = event.getLastStepId();
        return record;
    }


}
