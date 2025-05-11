import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {

    @Test
    @DisplayName("Проверить, что метод выполняется не дольше 22 секунд. Для этого воспользуйся аннотацией Timeout.")
    @Disabled
    @Timeout(value = 22)
    public void checkTimeOut() throws Exception {
        Main.main(null);
    }

}