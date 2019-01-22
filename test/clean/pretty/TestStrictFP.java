package sample;

import java.io.*;

strictfp public class TestStrictFP
		implements Serializable, TestStrictFPInterface {
	strictfp public double getValue() {
		return 2.0;
	};

	strictfp double increment() {
		return getValue() + 7.3;
	}

	strictfp interface NestedInterface {
		strictfp public double getValue();
	};

	strictfp class NestedClass implements NestedInterface {
		strictfp public double getValue() {
			return 4.0;
		}
	};
};

strictfp interface TestStrictFPInterface {
	strictfp public double getValue();
};

