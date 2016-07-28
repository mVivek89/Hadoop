package com.jpmorgan.cri.ifm.hadoop.partitioner;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/* PartitionMapper parses the input records and emits the key, 
 * value pairs suitable for the partitioner and the reducer. 
 * Mapper output format : gender is the key, the value is formed 
 * by concatenating the name, age and the score
*/
	public class PartitionMapper extends
			Mapper<Object, Text, Text, Text> {

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			String[] tokens = value.toString().split("\t");

			String gender = tokens[2].toString();
			String nameAgeScore = tokens[0]+"\t"+tokens[1]+"\t"+tokens[3];
			
			context.write(new Text(gender), new Text(nameAgeScore));
		}//end of map
	}//end of mapper
