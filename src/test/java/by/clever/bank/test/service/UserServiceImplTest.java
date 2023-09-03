package by.clever.bank.test.service;

import by.clever.bank.bean.Transaction;
import by.clever.bank.bean.User;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.UserService;
import by.clever.bank.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceImplTest {

    UserService userService;

    @BeforeEach
    void setUpTest() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Nested
    public class CreateUserTest {

        @Test
        public void should_Throw_ServiceException_When_UserArgumentIsNull() {
            //given
            User user = null;

            //when
            Executable executable = () -> {
                userService.createUser(user);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class UpdateUserTest {

        @Test
        public void should_Throw_ServiceException_When_OldUserArgumentIsNull() {
            //given
            User oldUser = null;
            User newUser = new User();

            //when
            Executable executable = () -> {
                userService.updateUser(oldUser, newUser);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_NewUserArgumentIsNull() {
            //given
            User oldUser = new User();
            User newUser = null;

            //when
            Executable executable = () -> {
                userService.updateUser(oldUser, newUser);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class ReadUserTest {
        @Test
        public void should_Throw_ServiceException_When_UserIDArgumentIsNegativeOrZero() {
            //given
            int userID = 0;

            //when
            Executable executable = () -> {
                userService.readUser(userID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class DeleteUserTest {
        @Test
        public void should_Throw_ServiceException_When_UserIDArgumentIsNegativeOrZero() {
            //given
            int userID = 0;

            //when
            Executable executable = () -> {
                userService.deleteUser(userID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }
}
