package ro.unitbv.fmi.netcdf;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.ma2.Range;
import ucar.nc2.NCdumpW;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class Main {
	public static void main(String[] args) throws InvalidRangeException {
		NetcdfFile ncfile = null;

		try {
			ncfile = NetcdfFile.open("F:/Disertatie/Date/Nasa/pr_day_BCSD_historical_r1i1p1_bcc-csm1-1_1950.nc");
			//ncfile = NetcdfFile.open("http://nasanex.s3.amazonaws.com/NEX-GDDP/BCSD/historical/day/atmos/pr/r1i1p1/v1.0/pr_day_BCSD_historical_r1i1p1_CCSM4_1950.nc");
			String varName = "time";
			String precipation = "pr";
			Variable v = ncfile.findVariable(varName);
			Variable precipitationVar = ncfile.findVariable(precipation);
			if (precipitationVar == null) {
				System.out.println("->Lat variable is null");
			} else {
				System.out.println("->Lat variable is not null");
				System.out.println(v.read().getSize());
				// Array array = v.read("0:10");
				// Array array2 = precipitationVar.read("0:2, 0:1, 0:1");
				/*
				 * int[] origin = new int[] { 2, 0, 0 }; int[] size = new int[]
				 * { 1, 2, 2 }; Array array3 = precipitationVar.read(origin,
				 * size).reduce(0);
				 */

				List<Range> ranges = new ArrayList<>();
				ranges.add(new ucar.ma2.Range(0, 0));
				ranges.add(new ucar.ma2.Range(0, 3));
				ranges.add(new ucar.ma2.Range(0, 0));
				Array array4 = precipitationVar.read(ranges);
				int[] shape = array4.getShape();
				Index index = array4.getIndex();

				for (int i = 0; i < shape[0]; i++) {
					for (int j = 0; j < shape[1]; j++) {
						for (int k = 0; k < shape[2]; k++) {
							double dval = array4.getDouble(index.set(i, j, k));
							double dvalT = dval / 1000;
							System.out.println(String.format("%d,%d,%d,%f", i, j, k,dvalT));
							System.out.println("Shape 1 -> " + shape[1]);
							//System.out.println("Max double -> "+String.format("%f", Double.MAX_VALUE));
						}
					}
				}

				// NCdumpW.printArray(array4, precipation, new
				// PrintWriter(System.out), null);
			}
			System.out.println("->The file was opened");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
