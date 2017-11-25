package kasania.time;

import java.util.HashMap;

public class TimeLine {
	
	private static final long nanoToMil = 1000000l;
	private static final long milToSec = 1000l;
	private static final long nanoToSec = nanoToMil*milToSec;
	private static HashMap<TimeElement, Long> TimeValue = new HashMap<>();
	
	public static long getNanoTime(){
		return System.nanoTime();
	}
	public static long getMilTime(){
		return System.nanoTime()/nanoToMil;
	}
	public static long getSecTime(){
		return System.nanoTime()/nanoToSec;
	}
	public static long getNanoToMil() {
		return nanoToMil;
	}
	public static long getMilToSec() {
		return milToSec;
	}
	public static long getNanoToSec() {
		return nanoToSec;
	}
	public static void addTimeValue(TimeElement TE, Long val){
		TimeValue.put(TE, val);
	}
	public static long getTimeValue(TimeElement TE){
		return TimeValue.get(TE);
	}
	public static long getTimeValueDiff(TimeElement TE, Long curTime){
		return curTime-TimeValue.get(TE);
	}
	public static void updateTimeValue(TimeElement TE, Long time){
		TimeValue.replace(TE, time);
	}
}
