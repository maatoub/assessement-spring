package ma.digiup.assignement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NiceBankApplicationTests {
	Calcule c = new Calcule();

	@Test
	void itShouldReturn() {
		// given
		int a = 10;
		int b = 10;

		// when
		int result = c.addTwoNumber(a, b);
		// then
		assertEquals(result, 20);

	}

	class Calcule {
		int addTwoNumber(int a, int b) {
			return a + b;
		}
	}
}
