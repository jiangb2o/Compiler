package Commands;

/**
 * command interface
 */
public interface ICommand {
    /**
     * execute command
     * @param args command args
     * @return command execute message
     */
    String exec(String[] args);
}
