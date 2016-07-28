package com.jpmorgan.cri.ifm.hadoop.partitioner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/*
Input Format: name	age	gender		score
Problem Statement:
We will use custom partitioning in MapReduce program to find the maximum scorer in 
each gender and three age categories: less than 20, 20 to 50, greater than 50.
output:
Partition - 0: (this partition contains the maximum 
scorers for each gender whose age is less than 20)
Partition - 1: (this partition contains the maximum 
scorers for each gender whose age is between 20 and 50)
Partition - 2: (this partition contains the maximum 
scorers for each gender whose age is greater than 50)

 */

public class SamplePartitioner{
	
/* The data belonging to the same partition go to the same reducer. 
 * In a particular partition, all the values with the same key are iterated 
 * and the person with the maximum score is found. Therefore the output 
 * of the reducer will contain the male and female maximum scorers in 
 * each of the 3 age categories.
*/


	public static void main(String[] args) {
		try{
		Configuration conf = new Configuration();
	    String[] otherArgs = new GenericOptionsParser( conf, args ).getRemainingArgs();
	    Job job = new Job(conf, "Partitioning Sample");
	    
	    job.setJarByClass( SamplePartitioner.class );
	    job.setMapperClass( PartitionMapper.class );
	    job.setReducerClass( PartitionReducer.class );
	    job.setPartitionerClass(AgePartitioner.class);
	    job.setNumReduceTasks(3);
	    
	    job.setMapOutputKeyClass( Text.class );
	    job.setMapOutputValueClass( Text.class );
	    
	    job.setOutputKeyClass( Text.class );	    
	    job.setOutputValueClass( Text.class );
	    
	    FileInputFormat.addInputPath( job, new Path( otherArgs[0] ) );
	    FileOutputFormat.setOutputPath( job, new Path( otherArgs[1] ) );
	    
	    System.exit( job.waitForCompletion( true ) ? 0 : 1 );
		}//end of try
		catch(Exception e){e.printStackTrace();}
		}//end of main
	}//end of class
