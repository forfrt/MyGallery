package forfrt.sheffiled.edu.assignment.view;

public interface EditImageViewInterface {
    // Used to tell users that the insert operation is complete
    public void titleDescritpionInsertedFeedback(String title, String description);
    public void titleDescritpionError(String title, String description, String errorString);
}
