import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    @DisplayName("Проверить, что при передаче в конструктор null " +
            "будет выброшено IllegalArgumentException. " +
            "выброшенное исключение будет содержать сообщение 'Horses cannot be null.'")
    public void checkIllegalArgumentExceptionWhenListIsNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));

        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор пустого списка " +
            "будет выброшено IllegalArgumentException. " +
            "выброшенное исключение будет содержать сообщение 'Horses cannot be empty.'")
    public void checkIllegalArgumentExceptionWhenListIsEmpty() {
        List<Horse> horses = new ArrayList<>();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(horses));

        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    @DisplayName("Проверить, что метод getHorses() возвращает список, который содержит те же объекты и " +
            "в той же последовательности,  что и список который был передан в конструктор. " +
            "При создании объекта Hippodrome передай в конструктор список из 30 разных лошадей.")
    public void checkGetHorsesReturnListEqualListFromConstructor() {
        // given
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(String.valueOf(i), i, i));
        }
        // then
        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    @DisplayName("Проверить, что метод Hippodrome.move вызывает метод move у всех лошадей. При создании объекта " +
            "Hippodrome передай в конструктор список из 50 моков лошадей и воспользуйся методом verify.")
    public void checkMoveForEveryHorse() {
        // given
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        // then
        new Hippodrome(horses).move();
        for (Horse horse : horses) {

            verify(horse).move();
        }
    }

    @Test
    @DisplayName("Проверить, что метод getWinner() возвращает лошадь с самым большим значением distance.")
    public void checkGetWinnerReturnHorseWithMaxDistance() {
        // given
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            horses.add(new Horse(String.valueOf(i), i, i*10));
        }
        Horse expectedHorse = horses.get(4);
        // then
        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(expectedHorse, hippodrome.getWinner());
    }
}