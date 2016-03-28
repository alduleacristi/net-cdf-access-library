package ro.unitbv.fmi.tmis.netcdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ro.unitbv.fmi.tmis.netcdf.dto.Latitude;
import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.ma2.Range;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class NetCdfInstance {
	private NetcdfFile cdfFile;

	public NetCdfInstance(String location) throws IOException {
		cdfFile = NetcdfFile
				.open("/root/Dizertatie/Software/wildfly-9.0.2.Final/data/nasa/precipitatii/pr_day_BCSD_rcp45_r1i1p1_ACCESS1-0_2010.nc");
	}

	public List<Latitude> getLatitudes() throws 
			IOException, InvalidRangeException {
		List<Latitude> latitudes = new ArrayList<Latitude>();
		Variable latVar = cdfFile.findVariable("lat");
		int length = latVar.getShape(0);
		List<Range> ranges = new ArrayList<Range>();
		ranges.add(new Range(0, length-1));

		Array results = latVar.read(ranges);
		Index index = results.getIndex();
		for(int i=0;i<length;i++){
			double lat = results.getDouble(index.set(i));
			System.out.println(lat);
		}

		return latitudes;
	}
}
