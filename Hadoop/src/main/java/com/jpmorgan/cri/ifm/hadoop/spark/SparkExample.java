package com.jpmorgan.cri.ifm.hadoop.spark;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;
import org.apache.spark.api.java.function.PairFunction;


@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class SparkExample {

	// Create a Java Spark Context
	SparkConf conf = new SparkConf().setAppName("wordCount");
	JavaSparkContext sc = new JavaSparkContext(conf);
	// Load our input data.
	JavaRDD<String> input = sc.textFile("MaxTemp.java");
	// Split up into words.
	
	JavaRDD<String> words = input.flatMap(new FlatMapFunction<String, String>() {
		public Iterator<String> call(String x) {
			return (Iterator<String>) Arrays.asList(x.split(" "));
		}
	});
	// Transform into pairs and count.
	
	JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {				
		public Tuple2<String, Integer> call(String x) {
			return new Tuple2(x, 1);
		}
	}).reduceByKey(new Function2<Integer, Integer, Integer>() {
		public Integer call(Integer x, Integer y) {
			return x + y;
		}
	});
	// Save the word count back out to a text file, causing evaluation.
//	counts.saveAsTextFile("Output.txt");
}
