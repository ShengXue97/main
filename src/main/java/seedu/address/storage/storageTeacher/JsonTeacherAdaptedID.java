package seedu.address.storage.storageTeacher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ID;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonTeacherAdaptedID {

  private final String value;

  /**
   * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
   */
  @JsonCreator
  public JsonTeacherAdaptedID(String value) {
    this.value = value;
  }

  /**
   * Converts a given {@code ID} into this class for Jackson use.
   */
  public JsonTeacherAdaptedID(ID source) {
    value = source.value;
  }

  @JsonValue
  public String getIDName() {
    return value;
  }

  /**
   * Converts this Jackson-friendly adapted tag object into the model's {@code ID} object.
   *
   * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
   */
  public ID toModelType() throws IllegalValueException {
    if (!ID.isValidId(value)) {
      throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
    }
    return new ID(value);
  }

}
