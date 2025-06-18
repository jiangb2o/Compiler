package exceptions;

public class MismatchedCommentException extends LexicalException {
    public MismatchedCommentException() {
        this("Comment error.");
    }

    public MismatchedCommentException(String var1) {
        super(var1);
    }
}
