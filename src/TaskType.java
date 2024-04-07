public abstract class TaskType {
    private int taskTypeID;
    private int size;

    public TaskType(int taskTypeID, int size) {
        this.taskTypeID = taskTypeID;
        this.size = 1;
    }

    public TaskType() {
        this.taskTypeID = 0;
        this.size = 0;
    }
}
