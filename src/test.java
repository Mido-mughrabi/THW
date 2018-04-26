import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import io.IO;
import junit.framework.TestCase;
import model.Person;
import model.Functions;
import model.Unit_types;

class test extends TestCase{

	@Test
	void test() {
		Person p = new Person("0128","hello",Functions.SKILLED_WORKER,Unit_types.OV_STAB);
		Person p2 = new Person("23","hello",Functions.SKILLED_WORKER,Unit_types.OV_STAB);
		HashSet<Person> set = new HashSet<>();
		set.add(p);
		set.add(p2);
		IO.writePersons(set);
		Gson gson = new Gson();
		assertEquals(2, IO.readPersons().size());
	}

}
