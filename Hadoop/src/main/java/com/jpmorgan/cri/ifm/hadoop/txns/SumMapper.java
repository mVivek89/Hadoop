package com.jpmorgan.cri.ifm.hadoop.txns;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SumMapper extends  Mapper<LongWritable, Text, Text, DoubleWritable> {

	@Override
	public void map( LongWritable key, Text value, Context context ) throws IOException, InterruptedException  {

		  String txnString = value.toString();	
		  String[] txnData = txnString.split( "," );
		  double amount = Double.parseDouble( txnData[3] );
	      context.write( new Text( txnData[5].trim() ), new DoubleWritable( amount ) );
	}

}
