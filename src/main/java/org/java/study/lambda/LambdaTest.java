package org.java.study.lambda;

import java.util.ArrayList;
import java.util.List;

public class LambdaTest {

	public static void main(String[] args) {
		List<String> list=new ArrayList<String>() {{
			add("6533");
			add("8799");
		}};
		list.stream().forEach(value->{
			System.out.println(value);
		});
		list.stream().filter((value)->("6533".equals(value))).forEach(value->{
			System.out.println(value);
		});
	}
}
