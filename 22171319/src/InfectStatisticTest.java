import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InfectStatisticTest {

	InfectStatistic i=new InfectStatistic();
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testMain() {
		String []s={"list","-log","D:\\log","-out","D:\\output1.txt","-date","2020-01-04","-type","ip","sp","cure","dead","-province","全国"," 北京"," 福建","湖北"};
		i.main(s);
	}

}
