package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelTeacher.Teacher;

/**
 * Adds a teacher to the address book.
 */
public class AddFinanceCommand extends Command {

    public static final String COMMAND_WORD = "add-teacher";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a teacher to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_SALARY + "SALARY "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New teacher added: %1$s";
    public static final String MESSAGE_DUPLICATE_TEACHER = "This teacher already exists in the address book";

    private final Teacher toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Teacher}
     */
    public AddFinanceCommand(Teacher teacher) {
        requireNonNull(teacher);
        toAdd = teacher;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTeacher(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEACHER);
        }

        model.addTeacher(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFinanceCommand // instanceof handles nulls
                && toAdd.equals(((AddFinanceCommand) other).toAdd));
    }
}