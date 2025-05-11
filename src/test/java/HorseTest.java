import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    MockedStatic<Horse> mockedStatic;

    @BeforeEach
    public void setUp(){
        mockedStatic = Mockito.mockStatic(Horse.class);
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор первым параметром null " +
            "будет выброшено IllegalArgumentException. " +
            "выброшенное исключение будет содержать сообщение 'Name cannot be null.'")
    public void checkIllegalArgumentExceptionWhenNameNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 2, 3));

        assertEquals("Name cannot be null.", e.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей " +
            "только пробельные символы (пробел, табуляция и т.д.), будет выброшено IllegalArgumentException. " +
            "Выброшенное исключение будет содержать сообщение 'Name cannot be blank.'")
    @ValueSource(strings = {"", "  ", "\n", "\n\n", "\t", "\t\t"})
    public void checkIllegalArgumentExceptionWhenNameEmpty(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 2, 3));

        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор вторым параметром отрицательного числа " +
            "будет выброшено IllegalArgumentException. " +
            "выброшенное исключение будет содержать сообщение 'Speed cannot be negative.'")
    public void checkIllegalArgumentExceptionWhenSpeedNegative() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Horse("horse", -2, 3));

        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор третьим параметром отрицательного числа " +
            "будет выброшено IllegalArgumentException. " +
            "выброшенное исключение будет содержать сообщение 'Distance cannot be negative.'")
    public void checkIllegalArgumentExceptionWhenDistanceNegative() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Horse("horse", 2, -3));

        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    @DisplayName("Проверить, что метод getName() - возвращает строку, которая была передана " +
            "первым параметром в конструктор")
    public void checkGetName(){
        Horse horse = new Horse("horse", 2, 3);

        assertEquals("horse", horse.getName());
    }

    @Test
    @DisplayName("Проверить, что метод getSpeed() - возвращает число, которое было передано " +
            "вторым параметром в конструктор")
    public void checkGetSpeed(){
        Horse horse = new Horse("horse", 2, 3);

        assertEquals(2, horse.getSpeed());
    }

    @Test
    @DisplayName("Проверить, что метод getDistance() - возвращает число, которое было передано " +
            "третьим параметром в конструктор")
    public void checkGetDistance(){
        Horse horse = new Horse("horse", 2, 3);

        assertEquals(3, horse.getDistance());
    }

    @Test
    @DisplayName("Проверить, что метод getDistance() возвращает ноль, " +
            "если объект был создан с помощью конструктора с двумя параметрами")
    public void checkGetDistanceWhenConstructorHasTwoParametersIsZero(){
        Horse horse = new Horse("horse", 2);

        assertEquals(0, horse.getDistance());
    }

    @Test
    @DisplayName("Проверить, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9")
    public void checkMoveWithRandomDoubleParametersFromTask(){
        new Horse("horse", 2).move();
        mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
    }

    @ParameterizedTest
    @DisplayName("Проверить, что метод присваивает дистанции значение высчитанное по формуле: " +
            "distance + speed * getRandomDouble(0.2, 0.9)")
    @CsvSource({"1, 2","2, 3","5, 5"})
    public void checkMoveThatDistanceCorrespondsToTheFormula(double speed, double distance){
        // given
        double coefficient = 0.5;
        double expectedDistance = distance + speed*coefficient;
        Horse horse = new Horse("horse", speed, distance);
        // when
        Mockito.when(Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(coefficient);
        // then
        horse.move();
        assertEquals(expectedDistance, horse.getDistance());
    }

    @AfterEach
    public void tearDown(){
        mockedStatic.close();
    }
}