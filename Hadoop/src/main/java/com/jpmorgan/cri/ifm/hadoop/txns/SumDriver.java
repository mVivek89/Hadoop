package com.jpmorgan.cri.ifm.hadoop.txns;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class SumDriver {
	
	public static void main(String[] args) throws Exception {
	    
		Configuration conf = new Configuration();
		
	    String[] otherArgs = new GenericOptionsParser( conf, args ).getRemainingArgs();
	    
	    Job job = new Job(conf, "Total Spend by Products");
	    
	    job.setJarByClass( SumDriver.class );
	    job.setMapperClass( SumMapper.class );

	    job.setReducerClass( SumReducer.class );
	    
	    job.setMapOutputKeyClass( Text.class );
	    job.setMapOutputValueClass( DoubleWritable.class );
	    
	    job.setOutputKeyClass( Text.class );	    
	    job.setOutputValueClass( Text.class );
	    
	    FileInputFormat.addInputPath( job, new Path( otherArgs[0] ) );
	    FileOutputFormat.setOutputPath( job, new Path( otherArgs[1] ) );
	    
	    System.exit( job.waitForCompletion( true ) ? 0 : 1 );
	
	}

}
