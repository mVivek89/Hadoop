package com.jpmorgan.cri.ifm.hadoop;

// imports will come here.
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MaxTemp {
	// MaxMapper
	public static class MaxMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();
			String year = line.substring(15, 19);
			int airTemperature;
			if (line.charAt(87) == '+') { // parseInt doesn't like leading plus
											// signs
				airTemperature = Integer.parseInt(line.substring(88, 92));
			} else {
				airTemperature = Integer.parseInt(line.substring(87, 92));
			}
			context.write(new Text(year), new IntWritable(airTemperature));
		}
	}// end of MaxMapper Class

	// MyReducer

	public static class MaxReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int maxValue = Integer.MIN_VALUE;
			while (values.iterator().hasNext()) {
				maxValue = Math.max(maxValue, values.iterator().next().get());
			}
			context.write(key, new IntWritable(maxValue));

		}
	}// end of MaxReducer

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		Job job = new Job(conf, "Max Temperature!");

		job.setJarByClass(MaxTemp.class);
		job.setMapperClass(MaxMapper.class);
		job.setCombinerClass(MaxReducer.class);
		job.setReducerClass(MaxReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
