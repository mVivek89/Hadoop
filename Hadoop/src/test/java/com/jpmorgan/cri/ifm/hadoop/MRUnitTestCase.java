package com.jpmorgan.cri.ifm.hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.cri.ifm.hadoop.txns.SumMapper;
import com.jpmorgan.cri.ifm.hadoop.txns.SumReducer;

public class MRUnitTestCase {

	MapReduceDriver<LongWritable, Text, Text, DoubleWritable, Text, Text> mapReduceDriver;
	MapDriver<LongWritable, Text, Text, DoubleWritable> mapDriver;
	ReduceDriver<Text, DoubleWritable, Text, Text> reduceDriver;

	@Before
	public void setUp() {
		System.setProperty("hadoop.home.dir", "D:\\Vivek\\hadoop_home\\hadoop-common-2.2.0-bin-master");
		SumMapper mapper = new SumMapper();
		SumReducer reducer = new SumReducer();

		mapDriver = new MapDriver<LongWritable, Text, Text, DoubleWritable>();
		mapDriver.setMapper(mapper);

		reduceDriver = new ReduceDriver<Text, DoubleWritable, Text, Text>();
		reduceDriver.setReducer(reducer);

		mapReduceDriver = new MapReduceDriver<LongWritable, Text, Text, DoubleWritable, Text, Text>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer);
	}

	@Test
	public void testMapper() throws IOException {
		mapDriver.withInput(new LongWritable(0), new Text(
				"00000000,01-03-2011,4006236,045.28,Outdoor Play Equipment,Sandboxes,New York,New York,credit"));
		mapDriver.withOutput(new Text("Sandbox"), new DoubleWritable(045.28));
		mapDriver.runTest();
	}

	@Test
	public void testReducer() throws IOException {
		List<DoubleWritable> values = new ArrayList<DoubleWritable>();
		values.add(new DoubleWritable(10.00));
		values.add(new DoubleWritable(15.00));
		reduceDriver.withInput(new Text("SANDBOXES"), values);
		reduceDriver.withOutput(new Text("SANDBOX"), new Text("25"));
		reduceDriver.runTest();
	}

	@Test
	public void testMapReduceSingleProduct() throws IOException {
		mapReduceDriver.withInput(new LongWritable(0), new Text(
				"00000000,01-03-2011,4006236,045.00,Outdoor Play Equipment,Sandboxes,New York,New York,credit"));
		mapReduceDriver.withInput(new LongWritable(0), new Text(
				"00000000,01-03-2011,4006236,045.00,Outdoor Play Equipment,Sandboxes,New York,New York,credit"));
		mapReduceDriver.addOutput(new Text("Sandboxe"), new Text("90"));
		mapReduceDriver.runTest();
	}

	@Test
	public void testMapReduceMultipleProducts() throws IOException {
		mapReduceDriver.withInput(new LongWritable(0), new Text(
				"00000000,01-03-2011,4006236,045.00,Outdoor Play Equipment,Sandboxes,New York,New York,credit"));
		mapReduceDriver.withInput(new LongWritable(0),
				new Text("00000000,01-03-2011,4006236,045.00,Outdoor Play Equipment,Skating,New York,New York,credit"));
		mapReduceDriver.addOutput(new Text("Sandboxes"), new Text("45"));
		mapReduceDriver.addOutput(new Text("Skating"), new Text("45"));
		mapReduceDriver.runTest();
	}

}
