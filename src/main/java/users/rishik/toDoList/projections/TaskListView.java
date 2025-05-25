package users.rishik.toDoList.projections;

public interface TaskListView {
    long getId();
    String getName();
    String getDescription();
    UserView getUser();
}
