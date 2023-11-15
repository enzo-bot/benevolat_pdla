import static org.junit.jupiter.api.Assertions.*;

import org.benevolat.UserAlreadyExistingException;
import org.benevolat.controllers.DBManager;
import org.benevolat.models.User;
import org.benevolat.models.UserType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

public class DBManagerTest {

    private static DBManager manager;
    @BeforeAll
    public static void setUp() {
        DBManagerTest.manager = DBManager.getInstance();

    }

    @BeforeEach
    public void setUpEach() {
        //Insertion de users dont Claude
        DBManagerTest.manager.recreateDatabase();
    }


    @DisplayName("Test pour la recuperation d'utilisateur valide")
    @Test
    public void recuperationValidUserTest() {
        User claude = new User("Claude", "Claude", UserType.Voluntary);
        assertDoesNotThrow(() -> {
            User user = manager.getUser("Claude", "Claude");
            assertEquals(claude, user);
        });
    }

    @DisplayName("Test pour la recuperation d'utilisateur invalide")
    @Test
    public void recuperationInvalidUserTest() {
        assertThrows(Exception.class, () -> {
            manager.getUser("Bob", "LeBricoleur");
        });
    }

    @DisplayName("Test pour l'insertion utilisateur")
    @Test
    public void insertionUserTest() {
        User marie = new User("Marie", "Marie", UserType.Voluntary);
        assertDoesNotThrow(() -> {
            manager.addUser(marie);
            assertEquals(marie, manager.getUser("Marie","Marie"));
        });
}

    @DisplayName("Test pour l'insertion d'un utilisateur existant")
    @Test
    public void insertionUserTwiceTest() {
        User marie = new User("Marie", "Marie", UserType.Voluntary);
        try {
            manager.addUser(marie);
        } catch (UserAlreadyExistingException e) {
            throw new RuntimeException(e);
        }
        assertThrows(UserAlreadyExistingException.class,() -> {
            manager.addUser(marie);
        });
    }
}
