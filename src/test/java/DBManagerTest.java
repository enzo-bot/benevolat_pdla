import static org.junit.jupiter.api.Assertions.*;

import org.benevolat.controllers.DBManager;
import org.benevolat.models.User;
import org.benevolat.models.UserType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DBManagerTest {

    private static DBManager manager;
    @BeforeAll
    public static void setUp() {
        DBManagerTest.manager = DBManager.getInstance();
        //Insertion de users dont Claude
        DBManagerTest.manager.recreateDatabase();
    }

    @DisplayName("Test pour la recuperation d'utilisateur valide")
    @Test
    public void recuperationValidUserTest() {
        try {
            User claude = new User("Claude", "Claude", UserType.Voluntary);
            User user = manager.getUser("Claude", "Claude");
            assertEquals(claude, user);
        } catch (Exception e) {
            fail();
        }
    }

    @DisplayName("Test pour la recuperation d'utilisateur invalide")
    @Test
    public void recuperationInvalidUserTest() {
        try {
//            User invalidUser = new User("Bob", "LeBricoleur", UserType.Voluntary);
            //User user = ;
            assertThrows(Exception.class, () -> {
                manager.getUser("Bob", "LeBricoleur");
            });
            //assertNotEquals(invalidUser, user);
        } catch (Exception e) {
            fail();
        }
    }

    @DisplayName("Test pour l'insertion utilisateur ")
    @Test
    public void insertionUserTest() {
        try {
            User marie = new User("Marie", "Marie", UserType.Voluntary);
            manager.addUser(marie);
            assertEquals(marie, manager.getUser("Marie","Marie"));
        } catch (Exception e) {
            fail();
        }
    }

    


}
