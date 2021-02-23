package org.java.study.lambda;

import java.util.ArrayList;
import java.util.List;

public class LambdaTest {

	public static void main(String[] args) {
		List<String> list=new ArrayList<String>() {{
			add("6533");
			add("8799");
		}};
		list.forEach(System.out::println);
		list.stream().filter("6533"::equals).forEach(System.out::println);
	}
}
