import java.util.Comparator;

public class BoardComparator implements Comparator<Board>{

			@Override
			public int compare(Board o1, Board o2) {
				if(o1.manhattan() + o1.moves > o2.manhattan()) {
					return 1;
				}
				if(o2.manhattan() > o1.manhattan()) {
					return -1;
				}
				return 0;
			}
			
		}