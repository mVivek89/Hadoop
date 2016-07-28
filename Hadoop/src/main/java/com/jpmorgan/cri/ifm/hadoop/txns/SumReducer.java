package com.jpmorgan.cri.ifm.hadoop.txns;

import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SumReducer extends Reducer<Text, DoubleWritable, Text, Text> {

	public void reduce( Text key, Iterable<DoubleWritable> values, Context context ) throws IOException, InterruptedException {
		
		Double sum = 0.00;

		for (DoubleWritable val : values) {			
            sum += val.get();            
        }
		
		DecimalFormat formatter = new DecimalFormat( "0.##" );
		
        context.write( key, new Text( formatter.format( sum ) ) );

	}

}