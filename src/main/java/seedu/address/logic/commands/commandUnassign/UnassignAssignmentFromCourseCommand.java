package seedu.address.logic.commands.commandUnassign;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAssign.AssignCommandBase;
import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.manager.EdgeManager;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.person.ID;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;

/** This class will be in charge of assigning stuff (e.g Assignments, teacher, etc) to a course. */
public class UnassignAssignmentFromCourseCommand extends UnassignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such Course that with ID";
    public static final String MESSAGE_INVALID_ASSIGNMENT_ID = "There is no such Assignment that with ID";
    public static final String MESSAGE_SUCCESS = "Successfully assigned Assignment %s (%s) to Course %s (%s)";

    private final AssignDescriptor assignDescriptor;
    private Set<Tag> ArrayList;

    public UnassignAssignmentFromCourseCommand(AssignDescriptor assignDescriptor) {
        requireNonNull(assignDescriptor);

        this.assignDescriptor = assignDescriptor;
    }

    public static boolean isValidDescriptor(AssignDescriptor assignDescriptor) {
        Set<Prefix> allAssignEntities = assignDescriptor.getAllAssignKeys();
        return allAssignEntities.contains(PREFIX_COURSEID) && allAssignEntities.contains(PREFIX_ASSIGNMENTID);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {

        // Check whether both IDs even exists
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID AssignmentID = this.assignDescriptor.getAssignID(PREFIX_ASSIGNMENTID);

        boolean courseExists = model.hasCourse(courseID);
        boolean AssignmentExists = model.hasAssignment(AssignmentID);

        if (!courseExists) {
            throw new CommandException(MESSAGE_INVALID_COURSE_ID);
        } else if (!AssignmentExists) {
            throw new CommandException(MESSAGE_INVALID_ASSIGNMENT_ID);
        } else {
            Course assignedCourse = model.getCourse(courseID);
            Assignment assigningAssignment = model.getAssignment(AssignmentID);

            boolean assignedCourseContainsAssignment = assignedCourse.containsAssignment(AssignmentID);
            boolean assigningAssignmentContainsCourse = assigningAssignment.isAssignedToCourse();

            if(!assignedCourseContainsAssignment) {
                throw new CommandException("This course doesn't have the assignment assigned to it! :(");
            } else if(!assigningAssignmentContainsCourse) {
                throw new CommandException("The assignment isn't assigned to this course! :(");
            } else {
                EdgeManager.unassignAssignmentFromCourse(AssignmentID, courseID);

                return new CommandResult(String.format(MESSAGE_SUCCESS,
                        assigningAssignment.getName(), AssignmentID.value,
                        assignedCourse.getName(), courseID.value));
            }
        }
    }
}