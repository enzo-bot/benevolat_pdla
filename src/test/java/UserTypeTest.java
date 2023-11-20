import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.benevolat.InvalidUserTypeIdException;
import org.benevolat.models.UserType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTypeTest {
    @DisplayName("Test de conversion INT vers UserType")
    @Test
    public void methodFromIntTest() {

        assertDoesNotThrow(() -> {
            assertEquals(UserType.Voluntary, UserType.fromInt(1));
            assertEquals(UserType.Asker, UserType.fromInt(2));
            assertEquals(UserType.Checker, UserType.fromInt(3));
        });

        assertThrows(InvalidUserTypeIdException.class,() -> {
            UserType.fromInt(4);
        });
    }
}
